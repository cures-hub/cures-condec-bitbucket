package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlassian.applinks.api.ApplicationLink;
import com.atlassian.applinks.api.ApplicationLinkRequest;
import com.atlassian.applinks.api.ApplicationLinkRequestFactory;
import com.atlassian.applinks.api.ApplicationLinkResponseHandler;
import com.atlassian.applinks.api.ApplicationLinkService;
import com.atlassian.applinks.api.CredentialsRequiredException;
import com.atlassian.applinks.api.application.jira.JiraApplicationType;
import com.atlassian.sal.api.component.ComponentLocator;
import com.atlassian.sal.api.net.Request;
import com.atlassian.sal.api.net.Response;
import com.atlassian.sal.api.net.ResponseException;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;

/**
 * Class responsible for the communication between Bitbucket and Jira via
 * application links.
 */
public class JiraClient {

	private ApplicationLink jiraApplicationLink;

	/**
	 * The singleton instance of the JiraClient. Please use this instance.
	 */
	public static JiraClient instance = new JiraClient();

	public JiraClient() {
		ApplicationLinkService applicationLinkService = ComponentLocator.getComponent(ApplicationLinkService.class);
		// TODO
		// @issue There might be more than one application links to Jira. Currently, we
		// only support the first link. How can we support all links?
		this.jiraApplicationLink = applicationLinkService.getPrimaryApplicationLink(JiraApplicationType.class);
	}

	/**
	 * @return all Jira projects that they user is allowed to access as a set of
	 *         project keys.
	 */
	public Set<String> getJiraProjects() {
		String projectsAsJsonString = getResponseFromJiraWithApplicationLink("rest/api/2/project");
		if (projectsAsJsonString.isEmpty()) {
			return new HashSet<String>();
		}
		return parseJiraProjectsJson(projectsAsJsonString);
	}

	public Set<String> parseJiraProjectsJson(String projectsAsJsonString) {
		Set<String> projectKeys = new HashSet<String>();
		try {
			JSONArray projectArray = new JSONArray(projectsAsJsonString);
			for (Object project : projectArray) {
				JSONObject projectMap = (JSONObject) project;
				String projectKey = (String) projectMap.get("key");
				projectKeys.add(projectKey.toUpperCase());
			}
		} catch (Exception e) {
			projectKeys.add(projectsAsJsonString);
		}
		return projectKeys;
	}

	private String getResponseFromJiraWithApplicationLink(String jiraUrl) {
		String responseBody = "";
		if (jiraApplicationLink == null) {
			return responseBody;
		}
		try {
			ApplicationLinkRequestFactory requestFactory = jiraApplicationLink.createAuthenticatedRequestFactory();
			ApplicationLinkRequest request = requestFactory.createRequest(Request.MethodType.GET, jiraUrl);
			request.addHeader("Content-Type", "application/json");

			responseBody = request.executeAndReturn(new ApplicationLinkResponseHandler<String>() {
				@Override
				public String credentialsRequired(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}

				@Override
				public String handle(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}
			});
		} catch (CredentialsRequiredException | ResponseException e) {
			responseBody = e.getMessage();
		}
		return responseBody;
	}

	/**
	 * Retrieves the decision knowledge elements from Jira that match a certain
	 * query and the project key.
	 * 
	 * @param pullRequest
	 *            object of {@link PullRequest} class.
	 * @return JSON string.
	 */
	public String getDecisionKnowledgeFromJira(PullRequest pullRequest) {
		Set<String> jiraIssueKeys = pullRequest.getJiraIssueKeys();
		return getDecisionKnowledgeFromJira(jiraIssueKeys);
	}

	/**
	 * Retrieves the decision knowledge elements from Jira that are associated to a
	 * set of Jira issues.
	 * 
	 * @param jiraIssueKeys
	 *            as a set of strings.
	 * @return JSON string.
	 */
	public String getDecisionKnowledgeFromJira(Set<String> jiraIssueKeys) {
		String queryWithJiraIssues = getJiraCallQuery(jiraIssueKeys);
		String projectKey = retrieveProjectKey(jiraIssueKeys);
		return getDecisionKnowledgeFromJira(queryWithJiraIssues, projectKey);
	}

	/**
	 * Retrieves the decision knowledge elements from Jira that match a certain
	 * query and the project key.
	 * 
	 * @param query
	 *            JQL query.
	 * @param projectKey
	 *            of the Jira project.
	 * @return JSON String.
	 */
	public String getDecisionKnowledgeFromJira(String query, String projectKey) {
		return getResponseFromJiraWithApplicationLink(
				"rest/condec/latest/knowledge/getElements.json?query=" + query + "&projectKey=" + projectKey);
	}

	/**
	 * Returns all Jira issue keys mentioned in a message.
	 * 
	 * @param message
	 *            that might contain a Jira issue key, e.g., a commit message,
	 *            branch name, or pull request title.
	 * @return list of all mentioned Jira issue keys in upper case letters (is
	 *         ordered by their appearance in the message).
	 */
	public static Set<String> getJiraIssueKeys(String message) {
		Set<String> keys = new LinkedHashSet<String>();
		String[] words = message.split("[\\s,:]+");
		String projectKey = null;
		String number = "";
		for (String word : words) {
			try {
				word = word.toUpperCase(Locale.ENGLISH);
				if (word.contains("-")) {
					if (projectKey == null) {
						projectKey = word.split("-")[0];
						number = word.split("-")[1];
						word = projectKey + "-" + number;
					}
					if (word.startsWith(projectKey)) {
						if (word.contains("/")) {
							word = word.split("/")[1];
						}
						keys.add(word);
					}
				}
			} catch (Exception e) {

			}
		}
		return keys;
	}

	/**
	 * Returns the Jira project key (e.g. CONDEC).
	 * 
	 * @param jiraIssueKeys
	 *            as a set of strings.
	 * @return potential project key.
	 */
	public static String retrieveProjectKey(Set<String> jiraIssueKeys) {
		Set<String> projectKeys = JiraClient.instance.getJiraProjects();
		if (jiraIssueKeys == null || jiraIssueKeys.isEmpty()) {
			return "";
		}
		for (String jiraIssueKey : jiraIssueKeys) {
			String potentialProjectKey = jiraIssueKey.split("-")[0];
			if (isProjectKeyExisting(potentialProjectKey, projectKeys)) {
				return potentialProjectKey;
			}
		}
		return "";
	}

	public static boolean isProjectKeyExisting(String projectKey, Set<String> projectKeys) {
		return !projectKey.isEmpty() && projectKeys.contains(projectKey);
	}

	public static String getJiraCallQuery(Set<String> jiraIssueKeys) {
		String query = "?jql=key in (";
		Iterator<String> iterator = jiraIssueKeys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			query += key;
			if (iterator.hasNext()) {
				query += ",";
			}
		}
		query += ")";
		return encodeUserInputQuery(query);
	}

	public static String encodeUserInputQuery(String query) {
		String encodedUrl = "";
		try {
			encodedUrl = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedUrl;
	}
}

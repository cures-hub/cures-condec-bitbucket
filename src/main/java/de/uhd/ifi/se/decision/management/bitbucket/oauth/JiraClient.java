package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import de.uhd.ifi.se.decision.management.bitbucket.parser.JiraIssueKeyParser;

/**
 * Class responsible for the communication between Bitbucket and Jira via
 * application links.
 */
public class JiraClient {

	public ApplicationLink jiraApplicationLink;
	private static final Logger LOGGER = LoggerFactory.getLogger(JiraClient.class);

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

	private String getResponseFromJiraWithApplicationLink(String jiraUrl) {
		ApplicationLinkRequest request = createRequest(Request.MethodType.GET, jiraUrl);
		if (request == null) {
			return "";
		}
		return receiveResponseFromJiraWithApplicationLink(request);
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

	private String receiveResponseFromJiraWithApplicationLink(ApplicationLinkRequest request) {
		String responseBody = "";
		try {
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
		} catch (ResponseException e) {
			LOGGER.error(e.getMessage());
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
	public String getDecisionKnowledgeFromJiraAsJsonString(PullRequest pullRequest) {
		Set<String> jiraIssueKeys = pullRequest.getJiraIssueKeys();
		return getDecisionKnowledgeFromJiraAsJsonString(jiraIssueKeys);
	}

	/**
	 * Retrieves the decision knowledge elements from Jira that are associated to a
	 * set of Jira issues.
	 * 
	 * @param jiraIssueKeys
	 *            as a set of strings.
	 * @return JSON string.
	 */
	public String getDecisionKnowledgeFromJiraAsJsonString(Set<String> jiraIssueKeys) {
		if (!jiraIssueKeys.iterator().hasNext()) {
			return "";
		}
		String projectKey = JiraIssueKeyParser.retrieveProjectKey(jiraIssueKeys);
		JSONArray decisionKnowledgeFromJiraAsJsonArray = new JSONArray();
		for (String jiraIssueKey : jiraIssueKeys) {
			String jsonString = getDecisionKnowledgeFromJiraAsJsonString("", projectKey, jiraIssueKey);
			decisionKnowledgeFromJiraAsJsonArray = concatArray(decisionKnowledgeFromJiraAsJsonArray,
					new JSONArray(jsonString));
		}
		return decisionKnowledgeFromJiraAsJsonArray.toString();
	}

	private static JSONArray concatArray(JSONArray jsArr1, JSONArray jsArr2) {
		List<Object> list = jsArr1.toList();
		list.addAll(jsArr2.toList());
		return new JSONArray(list);
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
	public String getDecisionKnowledgeFromJiraAsJsonString(String query, String projectKey, String selectedElement) {
		return postResponseFromJiraWithApplicationLink("rest/condec/latest/knowledge/knowledgeElements.json",
				encodeUserInputQuery(query), projectKey, selectedElement);
	}

	private String postResponseFromJiraWithApplicationLink(String jiraUrl, String searchTerm, String projectKey,
			String selectedElement) {
		ApplicationLinkRequest request = createRequest(Request.MethodType.POST, jiraUrl);
		if (request == null) {
			return "";
		}
		request.setRequestBody(
				"{\"projectKey\":\"" + projectKey + "\",\"searchTerm\":\"" + searchTerm + "\","
						+ "\"selectedElement\":\"" + selectedElement + "\",\"createTransitiveLinks\":true}",
				MediaType.APPLICATION_JSON);
		return receiveResponseFromJiraWithApplicationLink(request);
	}

	private ApplicationLinkRequest createRequest(Request.MethodType type, String url) {
		if (jiraApplicationLink == null) {
			return null;
		}
		ApplicationLinkRequestFactory requestFactory = jiraApplicationLink.createAuthenticatedRequestFactory();
		try {
			return requestFactory.createRequest(type, url);
		} catch (CredentialsRequiredException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
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

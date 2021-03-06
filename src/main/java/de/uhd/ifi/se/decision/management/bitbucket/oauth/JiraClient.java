package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.impl.JiraClientImpl;

/**
 * Interface responsible for the communication between Bitbucket and Jira via
 * application links.
 */
public interface JiraClient {

	/**
	 * The singleton instance of the JiraClient. Please use this instance.
	 */
	public JiraClient instance = new JiraClientImpl();

	/**
	 * Returns all Jira projects that they user is allowed to access.
	 * 
	 * @return all Jira projects as a set of project keys.
	 */
	Set<String> getJiraProjects();

	/**
	 * Retrieves the decision knowledge elements from Jira that match a certain
	 * query and the project key.
	 * 
	 * @param pullRequest
	 *            object of {@link PullRequest} class.
	 * @return JSON string.
	 */
	String getDecisionKnowledgeFromJira(PullRequest pullRequest);

	/**
	 * Retrieves the decision knowledge elements from Jira that are associated to a
	 * set of Jira issues.
	 * 
	 * @param jiraIssueKeys
	 *            as a set of strings.
	 * @return JSON string.
	 */
	String getDecisionKnowledgeFromJira(Set<String> jiraIssueKeys);

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
	String getDecisionKnowledgeFromJira(String query, String projectKey);

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
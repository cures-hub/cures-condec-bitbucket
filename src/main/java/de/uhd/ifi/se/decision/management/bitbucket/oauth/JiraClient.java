package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import java.util.Set;

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
	 * Returns all Jira projects that are currently active.
	 * @return all Jira projects as a set of project keys.
	 */
	Set<String> getCurrentActiveJiraProjects();
	
	/**
	 * Retrieves the decision knowledge elements from Jira that match a certain query and the project key.
	 * @param query JQL query.
	 * @param projectKey of the Jira project.
	 * @return JSON String.
	 */
	String getDecisionKnowledgeFromJira(String query, String projectKey);
}
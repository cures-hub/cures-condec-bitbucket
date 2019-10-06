package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.impl.ApiLinkServiceImpl;

/**
 * Interface responsible for the communication between Bitbucket and Jira via
 * application links.
 */
public interface ApiLinkService {

	/**
	 * The singleton instance of the ApiLinkService. Please use this instance.
	 */
	public ApiLinkService instance = new ApiLinkServiceImpl();

	/**
	 * Retrieves the decision knowledge elements from Jira that match a certain query and the project key.
	 * @param query JQL query.
	 * @param projectKey of the Jira project.
	 * @return JSON String.
	 */
	String getDecisionKnowledgeFromJira(String query, String projectKey);

	/**
	 * Returns all Jira projects that are currently active.
	 * @return all Jira projects as a JSON String.
	 */
	String getCurrentActiveJiraProjects();
}
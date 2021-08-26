package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * Class to check the completeness of the documentation of decision knowledge
 * related to a pull request.
 */
public class CompletenessCheckHandler {

	public PullRequest pullRequest;
	private Set<String> jiraIssuesWithIncompleteDocumentation;

	public CompletenessCheckHandler(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
		this.jiraIssuesWithIncompleteDocumentation = new HashSet<String>();
	}

	/**
	 * Checks whether the documentation is complete.
	 * 
	 * @return true if the documentation is complete.
	 */
	public boolean isDocumentationComplete() {
		String knowledgeElementsAsJsonString = JiraClient.instance
				.getDecisionKnowledgeFromJiraAsJsonString(pullRequest.getJiraIssueKeys());
		return isDocumentationComplete(knowledgeElementsAsJsonString);
	}

	public boolean isDocumentationComplete(String knowledgeElementsAsJsonString) {
		boolean isDocumentationComplete = true;
		try {
			JSONArray jsonArray = new JSONArray(knowledgeElementsAsJsonString);
			boolean tempResult = checkIfDecisionsExists(jsonArray);
			if (!tempResult) {
				JSONObject firstKey = (JSONObject) jsonArray.get(0);
				jiraIssuesWithIncompleteDocumentation.add((String) firstKey.get("key"));
				isDocumentationComplete = false;
			}
		} catch (Exception e) {
			isDocumentationComplete = false;
		}
		return isDocumentationComplete;
	}

	public boolean checkIfDecisionsExists(JSONArray decisions) {
		boolean hasIssue = false;
		boolean hasDecision = false;
		for (Object current : decisions) {
			JSONObject currentObject = (JSONObject) current;
			String type = (String) currentObject.get("type");
			// Issue
			if ("issue".equalsIgnoreCase(type)) {
				hasIssue = true;
			}
			// Decision
			if ("decision".equalsIgnoreCase(type)) {
				hasDecision = true;
			}
		}
		return hasIssue && hasDecision;
	}

	/**
	 * @return set of Jira issue keys with an incomplete documentation.
	 */
	public Set<String> getJiraIssuesWithIncompleteDocumentation() {
		return jiraIssuesWithIncompleteDocumentation;
	}
}

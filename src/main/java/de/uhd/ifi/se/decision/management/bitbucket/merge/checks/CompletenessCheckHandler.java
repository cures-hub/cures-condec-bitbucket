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
		boolean isDocumentationComplete = true;
		for (String jiraIssueKey : pullRequest.getJiraIssueKeys()) {
			Set<String> subset = new HashSet<>();
			subset.add(jiraIssueKey);
			JSONArray knowledgeElementsAsJson = JiraClient.instance.getDecisionKnowledgeFromJiraAsJson(subset);
			if (!isDocumentationComplete(knowledgeElementsAsJson)) {
				isDocumentationComplete = false;
				jiraIssuesWithIncompleteDocumentation.add(jiraIssueKey);
			}
		}
		return isDocumentationComplete;
	}

	public boolean isDocumentationComplete(JSONArray knowledgeElementsAsJson) {
		boolean isDocumentationComplete = true;
		try {
			isDocumentationComplete &= checkIfDecisionsExists(knowledgeElementsAsJson);
		} catch (Exception e) {
			System.err.println(e);
			isDocumentationComplete = false;
		}
		System.out.println(isDocumentationComplete);
		return isDocumentationComplete;
	}

	public boolean checkIfDecisionsExists(JSONArray knowledgeElementsAsJson) {
		boolean hasIssue = false;
		boolean hasDecision = false;
		for (Object current : knowledgeElementsAsJson) {
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

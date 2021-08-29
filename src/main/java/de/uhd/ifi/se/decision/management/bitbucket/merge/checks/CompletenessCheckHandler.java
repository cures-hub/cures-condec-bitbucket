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
	 * @return true if the documentation of the entire pull request is complete
	 *         (checks the decision coverage).
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

	/**
	 * @return true if the documentation of the given knowledge elements is complete
	 *         (checks the decision coverage).
	 */
	public boolean isDocumentationComplete(JSONArray knowledgeElementsAsJson) {
		boolean hasIssue = false;
		boolean hasDecision = false;
		for (Object current : knowledgeElementsAsJson) {
			JSONObject currentObject = (JSONObject) current;
			String type = currentObject.getString("type");
			// Decision problem
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

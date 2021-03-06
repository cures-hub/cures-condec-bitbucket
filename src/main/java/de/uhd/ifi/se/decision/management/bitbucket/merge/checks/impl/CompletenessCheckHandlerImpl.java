package de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * Class to check the completeness of the documentation of decision knowledge
 * related to a pull request.
 */
public class CompletenessCheckHandlerImpl implements CompletenessCheckHandler {

	public PullRequest pullRequest;
	private Set<String> jiraIssuesWithIncompleteDocumentation;

	public CompletenessCheckHandlerImpl(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
		this.jiraIssuesWithIncompleteDocumentation = new HashSet<String>();
	}

	@Override
	public boolean isDocumentationComplete() {
		String knowledgeElementsAsJsonString = JiraClient.instance
				.getDecisionKnowledgeFromJira(pullRequest.getJiraIssueKeys());
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
			if ("issue".equals(type.toLowerCase())) {
				hasIssue = true;
			}
			// Decision
			if ("decision".equals(type.toLowerCase())) {
				hasDecision = true;
			}
		}
		return hasIssue && hasDecision;
	}

	@Override
	public Set<String> getJiraIssuesWithIncompleteDocumentation() {
		return jiraIssuesWithIncompleteDocumentation;
	}
}

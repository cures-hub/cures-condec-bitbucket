package de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.component.ComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.ApiLinkService;

/**
 * Class to check the completeness of the documentation of decision knowledge
 * related to a pull request.
 */
public class CompletenessCheckHandlerImpl implements CompletenessCheckHandler {

	public static String JIRA_QUERY;
	public static String PROJECT_KEY;
	private PullRequest pullRequest;
	private Set<String> jiraIssuesWithIncompleteDocumentation;

	public CompletenessCheckHandlerImpl(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
		this.jiraIssuesWithIncompleteDocumentation = new HashSet<String>();
	}

	public Iterable<Commit> getCommitsOfPullRequest() {
		try {
			CommitService commitService = ComponentLocator.getComponent(CommitService.class);
			CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(pullRequest);
			CommitsBetweenRequest commitsBetweenRequest = builder.build();
			Page<Commit> pageWithCommits = commitService.getCommitsBetween(commitsBetweenRequest,
					new PageRequestImpl(0, 1048476));
			return pageWithCommits.getValues();
		} catch (NullPointerException e) {

		}
		return new ArrayList<Commit>();
	}

	/**
	 * @param commits
	 * @param branchId
	 * @return String "(TEST-5, TEST-7,etc...)"
	 */
	public String getJiraCallQuery(Iterable<Commit> commits, String branchId, String branchTitle) {
		String query = "(";
		ArrayList<String> messageList = new ArrayList<String>();

		for (Commit commit : commits) {
			String message = commit.getMessage();
			messageList.add(message);
		}
		for (String message : messageList) {
			// Split message after first space
			if (message.contains(" ")) {
				String[] parts = message.split(" ");
				query = query + parts[0] + ",";
			}
		}

		// add Branch ID
		query += branchId + ",";
		// add Branch Title

		query += branchTitle;
		// add )
		query += ")";
		return query;
	}

	@Override
	public boolean isDocumentationComplete() {
		// find correct query out of projects, commitMessages and BranchId
		Iterable<Commit> commits = getCommitsOfPullRequest();
		String pullRequestTitle = pullRequest.getTitle();
		PullRequestRef pullRequestRef = pullRequest.getFromRef();
		String branchName = pullRequestRef.getDisplayId();
		String queryWithJiraIssues = "?jql=key in " + getJiraCallQuery(commits, pullRequestTitle, branchName);

		JIRA_QUERY = queryWithJiraIssues;
		PROJECT_KEY = getProjectKeyFromJiraAndCheckWhichOneCouldBe(commits, branchName, pullRequestTitle);

		// get decision knowledge out of Jira
		String knowledgeElementsAsJsonString = ApiLinkService.instance.getDecisionKnowledgeFromJira(queryWithJiraIssues,
				PROJECT_KEY);

		return isDocumentationComplete(knowledgeElementsAsJsonString);
	}

	public boolean isDocumentationComplete(String knowledgeElementsAsJsonString) {
		boolean isDocumentationComplete = true;
		try {
			JSONArray topArray = new JSONArray(knowledgeElementsAsJsonString);
			for (Object current : topArray) {

				JSONArray myCurrent = (JSONArray) current;
				boolean tempResult = checkIfDecisionsExists(myCurrent);
				if (!tempResult) {
					JSONObject firstKey = (JSONObject) myCurrent.get(0);
					jiraIssuesWithIncompleteDocumentation.add((String) firstKey.get("key"));
					isDocumentationComplete = false;
				}
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

	public String getProjectKeyFromJiraAndCheckWhichOneCouldBe(Iterable<Commit> commits, String branchId,
			String branchTitle) {
		Set<String> projectKeys = ApiLinkService.instance.getCurrentActiveJiraProjects();

		// check branch name, e.g. "CONDEC-1.create.a.great.plugin"
		String projectKey = CompletenessCheckHandler.retrieveProjectKey(branchId);
		if (isProjectKeyExisting(projectKey, projectKeys)) {
			return projectKey;
		}

		// check pull request title, e.g. "ConDec-1: Create a great plugin"
		String projectKeyInBranchName = CompletenessCheckHandler.retrieveProjectKey(branchTitle);
		if (isProjectKeyExisting(projectKeyInBranchName, projectKeys)) {
			return branchTitle;
		}

		// check commit messages
		for (Commit commit : commits) {
			String projectKeyInCommitMessage = CompletenessCheckHandler.retrieveProjectKey(commit.getMessage());
			if (isProjectKeyExisting(projectKeyInCommitMessage, projectKeys)) {
				return projectKeyInCommitMessage;
			}
		}

		return "";
	}

	private boolean isProjectKeyExisting(String projectKey, Set<String> projectKeys) {
		return !projectKey.isEmpty() && projectKeys.contains(projectKey);
	}

	public Set<String> getJiraIssuesWithIncompleteDocumentation() {
		return jiraIssuesWithIncompleteDocumentation;
	}
}

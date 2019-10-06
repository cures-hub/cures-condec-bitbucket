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

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.MergeCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.ApiLinkService;

/**
 * Class responsible for...?
 */
public class MergeCheckHandlerImpl implements MergeCheckHandler {

	private PullRequest pullRequest;
	public static String JIRA_QUERY;
	public static String PROJECT_KEY;
	private Set<String> jiraIssuesWithIncompleteDocumentation;

	public MergeCheckHandlerImpl(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
		this.jiraIssuesWithIncompleteDocumentation = new HashSet<String>();
	}

	public Iterable<Commit> getCommitsOfPullRequest() {
		CommitService commitService = ComponentLocator.getComponent(CommitService.class);
		CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(pullRequest);
		CommitsBetweenRequest commitsBetweenRequest = builder.build();
		Page<Commit> pageWithCommits = commitService.getCommitsBetween(commitsBetweenRequest,
				new PageRequestImpl(0, 1048476));
		return pageWithCommits.getValues();
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
	public boolean hasSufficientDecisions() {
		// find correct query out of projects, commitMessages and BranchId
		Iterable<Commit> commits = getCommitsOfPullRequest();
		String branchTitle = pullRequest.getTitle();
		PullRequestRef pullRequestRef = pullRequest.getFromRef();
		String branchId = pullRequestRef.getDisplayId();
		String queryWithJiraIssues = "?jql=key in " + getJiraCallQuery(commits, branchTitle, branchId);

		JIRA_QUERY = queryWithJiraIssues;
		PROJECT_KEY = getProjectKeyFromJiraAndCheckWhichOneCouldBe(commits, branchId, branchTitle);

		// get decision knowledge out of Jira
		String jsonString = ApiLinkService.instance.getDecisionKnowledgeFromJira(queryWithJiraIssues, PROJECT_KEY);

		boolean booleanResult = true;
		try {
			JSONArray topArray = new JSONArray(jsonString);
			for (Object current : topArray) {

				JSONArray myCurrent = (JSONArray) current;
				Boolean tempResult = checkIfDecisionsExists(myCurrent);
				if (!tempResult) {
					JSONObject firstKey = (JSONObject) myCurrent.get(0);
					jiraIssuesWithIncompleteDocumentation.add((String) firstKey.get("key"));
					booleanResult = false;
				}
			}
		} catch (Exception e) {
			booleanResult = false;
		}
		return booleanResult;
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
		String projects = ApiLinkService.instance.getCurrentActiveJiraProjects();
		String selectedProject = "";
		try {
			ArrayList<String> projectKeys = new ArrayList<String>();

			JSONArray projectArray = new JSONArray(projects);
			for (Object project : projectArray) {
				JSONObject projectMap = (JSONObject) project;
				String projectKey = (String) projectMap.get("key");
				projectKeys.add(projectKey.toLowerCase());
			}

			// check BranchId
			String eventuallyBranch = splitAndReturnWithoutToLower(branchId);
			if (notEmptyAndInProject(eventuallyBranch, projectKeys)) {
				selectedProject = eventuallyBranch.toUpperCase();
			}
			// check branchTitle
			else if (notEmptyAndInProject(branchTitle, projectKeys)) {
				selectedProject = branchTitle.toUpperCase();
			} else {
				for (Commit commit : commits) {
					String eventuallyProjectKey = splitAndReturnWithoutToLower(commit.getMessage());
					if (notEmptyAndInProject(eventuallyProjectKey, projectKeys)) {
						selectedProject = eventuallyProjectKey.toUpperCase();
					}
				}
			}

		} catch (Exception e) {
			return selectedProject;
		}
		return selectedProject;
	}

	private String splitAndReturnWithoutToLower(String toSplit) {
		if (toSplit.indexOf("-") > -1) {
			String splitted = toSplit.split("-")[0];
			return splitted.toLowerCase();
		} else {
			return "";
		}
	}

	private Boolean notEmptyAndInProject(String eventually, ArrayList<String> projectKeys) {
		return (!"".equals(eventually) && projectKeys.contains(eventually.toLowerCase()));
	}

	public Set<String> getJiraIssuesWithIncompleteDocumentation() {
		return jiraIssuesWithIncompleteDocumentation;
	}
}

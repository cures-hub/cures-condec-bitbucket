package de.uhd.ifi.se.decision.management.bitbucket.model;

import java.util.HashSet;
import java.util.Set;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.component.ComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

public class MyPullRequest {

	public PullRequest pullRequest;
	public static Set<String> JIRA_ISSUE_KEYS;

	public MyPullRequest(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
		JIRA_ISSUE_KEYS = retrieveJiraIssueKeys();
	}

	public MyPullRequest(PullRequestMergeHookRequest request) {
		this(request.getPullRequest());
	}

	public Set<String> retrieveJiraIssueKeys() {
		Set<String> jiraIssueKeysLinkedToPullRequest = new HashSet<String>();
		jiraIssueKeysLinkedToPullRequest.addAll(getJiraIssueKeysInCommitMessages());
		jiraIssueKeysLinkedToPullRequest.addAll(getJiraIssueKeysInTitle());
		jiraIssueKeysLinkedToPullRequest.addAll(getJiraIssueKeysInBranchName());
		return jiraIssueKeysLinkedToPullRequest;
	}

	public Set<String> getJiraIssueKeysInCommitMessages() {
		Set<String> jiraIssueKeysInCommitMessages = new HashSet<String>();
		Iterable<Commit> commits = getCommits();
		for (Commit commit : commits) {
			String message = commit.getMessage();
			jiraIssueKeysInCommitMessages.addAll(JiraClient.getJiraIssueKeys(message));
		}
		return jiraIssueKeysInCommitMessages;
	}

	public Iterable<Commit> getCommits() {
		CommitService commitService = ComponentLocator.getComponent(CommitService.class);
		CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(pullRequest);
		CommitsBetweenRequest commitsBetweenRequest = builder.build();
		Page<Commit> pageWithCommits = commitService.getCommitsBetween(commitsBetweenRequest,
				new PageRequestImpl(0, 1048476));
		return pageWithCommits.getValues();
	}

	public Set<String> getJiraIssueKeysInTitle() {
		String title = pullRequest.getTitle();
		return JiraClient.getJiraIssueKeys(title);
	}

	public Set<String> getJiraIssueKeysInBranchName() {
		PullRequestRef pullRequestRef = pullRequest.getFromRef();
		String branchName = pullRequestRef.getDisplayId();
		return JiraClient.getJiraIssueKeys(branchName);
	}

	public Set<String> getJiraIssueKeys() {
		return JIRA_ISSUE_KEYS;
	}

	public String getProjectKey() {
		return JiraClient.retrieveProjectKey(JIRA_ISSUE_KEYS);
	}
}

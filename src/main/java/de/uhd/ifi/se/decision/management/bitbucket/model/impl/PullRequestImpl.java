package de.uhd.ifi.se.decision.management.bitbucket.model.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.sal.api.component.ComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * Class for pull requests. Helps to get the associated commits, Jira project,
 * and Jira issues.
 */
public class PullRequestImpl implements PullRequest {

	private com.atlassian.bitbucket.pull.PullRequest internalPullRequest;
	public Set<String> jiraIssueKeys;

	public PullRequestImpl(com.atlassian.bitbucket.pull.PullRequest pullRequest) {
		this.internalPullRequest = pullRequest;
		if (pullRequest == null) {
			return;
		}
		jiraIssueKeys = retrieveJiraIssueKeys();
	}

	public PullRequestImpl(PullRequestMergeHookRequest request) {
		this(request.getPullRequest());
	}

	private Set<String> retrieveJiraIssueKeys() {
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

	@Override
	public Iterable<Commit> getCommits() {
		if (internalPullRequest == null) {
			return new ArrayList<Commit>();
		}
		CommitService commitService = ComponentLocator.getComponent(CommitService.class);
		CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(internalPullRequest);
		CommitsBetweenRequest commitsBetweenRequest = builder.build();
		Page<Commit> pageWithCommits = commitService.getCommitsBetween(commitsBetweenRequest,
				new PageRequestImpl(0, 1048476));
		return pageWithCommits.getValues();
	}

	public Set<String> getJiraIssueKeysInTitle() {
		return JiraClient.getJiraIssueKeys(getTitle());
	}

	public Set<String> getJiraIssueKeysInBranchName() {
		return JiraClient.getJiraIssueKeys(getBranchName());
	}

	@Override
	public com.atlassian.bitbucket.pull.PullRequest getInternalPullRequest() {
		return internalPullRequest;
	}

	@Override
	public Set<String> getJiraIssueKeys() {
		return jiraIssueKeys;
	}

	@Override
	public String getProjectKey() {
		return JiraClient.retrieveProjectKey(jiraIssueKeys);
	}

	@Override
	public String getTitle() {
		return internalPullRequest.getTitle();
	}

	@Override
	public String getBranchName() {
		PullRequestRef pullRequestRef = internalPullRequest.getFromRef();
		return pullRequestRef.getDisplayId();
	}
}

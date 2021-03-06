package de.uhd.ifi.se.decision.management.bitbucket.model;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.atlassian.bitbucket.commit.Commit;

import de.uhd.ifi.se.decision.management.bitbucket.model.impl.PullRequestImpl;

/**
 * Interface for pull requests. Helps to get the associated commits, Jira
 * project, and Jira issues.
 */
@JsonDeserialize(as = PullRequestImpl.class)
public interface PullRequest {

	/**
	 * Returns the commits associated with the pull request.
	 * 
	 * @return commits as an iterable collection.
	 */
	Iterable<Commit> getCommits();

	/**
	 * Returns the Jira issue keys associated with the pull request, i.e., mentioned
	 * in a commit message, branch name, or pull request title.
	 * 
	 * @return Jira issue keys as a set of strings.
	 */
	Set<String> getJiraIssueKeys();

	/**
	 * Returns the Jira project associated with the pull request.
	 * 
	 * @return Jira project key as a string.
	 */
	String getProjectKey();

	/**
	 * Returns the internal pull request.
	 * 
	 * @return internal pull request.
	 * @see com.atlassian.bitbucket.pull.PullRequest
	 */
	com.atlassian.bitbucket.pull.PullRequest getInternalPullRequest();

	/**
	 * Returns the title of the pull request.
	 * 
	 * @return title as a string.
	 */
	String getTitle();

	/**
	 * Returns the branch name (display id) of the branch (fromRef).
	 * 
	 * @return branch name as a string.
	 */
	String getBranchName();
}
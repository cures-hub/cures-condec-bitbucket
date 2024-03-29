package de.uhd.ifi.se.decision.management.bitbucket.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequestMergeHookRequest;

public class TestPullRequest {

	private static PullRequest pullRequest;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		pullRequest = new PullRequest(new MockPullRequest());
	}

	@Test
	public void testConstructor() {
		PullRequest pullRequest = new PullRequest(new MockPullRequestMergeHookRequest());
		assertEquals("CONDEC", pullRequest.getProjectKey());
	}

	@Test
	public void testGetCommits() {
		Iterable<Commit> commits = pullRequest.getCommits();
		assertEquals("ConDec-1: Initial commit", commits.iterator().next().getMessage());
	}

	@Test
	public void testGetCommitsInternalPullRequestNull() {
		PullRequest pullRequest = new PullRequest((com.atlassian.bitbucket.pull.PullRequest) null);
		Iterable<Commit> commits = pullRequest.getCommits();
		assertFalse(commits.iterator().hasNext());
	}

	@Test
	public void testJiraIssueKeysInCommitMessages() {
		Set<String> jiraIssueKeysInCommitMessages = pullRequest.getJiraIssueKeys();
		assertEquals("CONDEC-1", jiraIssueKeysInCommitMessages.iterator().next());
	}

	@Test
	public void testGetProjectKey() {
		String projectKey = pullRequest.getProjectKey();
		assertEquals("CONDEC", projectKey);
	}
	
	@Test
	public void testGetInternalPullRequest() {
		com.atlassian.bitbucket.pull.PullRequest internalPullRequest = pullRequest.getInternalPullRequest();
		assertEquals(internalPullRequest.getTitle(), pullRequest.getTitle());
	}
}

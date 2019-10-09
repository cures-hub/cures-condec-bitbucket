package de.uhd.ifi.se.decision.management.bitbucket.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;

public class TestPullRequest {

	private static MyPullRequest pullRequest;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		pullRequest = new MyPullRequest(new MockPullRequest());
	}

	@Test
	public void testGetCommits() {
		Iterable<Commit> commits = pullRequest.getCommits();
		assertEquals("ConDec-1: Initial commit", commits.iterator().next().getMessage());
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
}

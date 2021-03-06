package de.uhd.ifi.se.decision.management.bitbucket.merge.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.model.impl.PullRequestImpl;

public class TestCompletenessCheckHandler {

	private static CompletenessCheckHandler completenessCheckHandler;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		PullRequest pullRequest = new PullRequestImpl(new MockPullRequest());
		completenessCheckHandler = new CompletenessCheckHandlerImpl(pullRequest);
	}

	@Test
	public void testConstructorPullRequestNull() {
		assertNotNull(new CompletenessCheckHandlerImpl(null));
	}

	@Test
	public void testConstructorPullRequestValid() {
		assertNotNull(completenessCheckHandler);
	}

	@Test
	public void testIsDocumentationCompleteInPullRequest() {
		boolean isDocumentationComplete = completenessCheckHandler.isDocumentationComplete();
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testGetCommitsOfPullRequest() {
		Iterable<Commit> commits = ((CompletenessCheckHandlerImpl) completenessCheckHandler).pullRequest.getCommits();
		assertEquals(true, commits.iterator().hasNext());
	}

	@Test
	public void testIsDocumentationCompleteTrue() {
		String jsonString_true = "[{'type':'issue'}, {'type':'decision'}]";
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null)
				.isDocumentationComplete(jsonString_true);
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testIsDocumentationCompleteNonJsonArray() {
		String jsonString_true = "abc";
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null)
				.isDocumentationComplete(jsonString_true);
		assertFalse(isDocumentationComplete);
	}

	@Test
	public void testIsDocumentationCompleteFalse() {
		String jsonString_false = "[{'key':'CONDEC-1', 'type':'work item'}, {'type':'issue'}]";
		CompletenessCheckHandlerImpl completenessCheckHandler = new CompletenessCheckHandlerImpl(null);
		boolean isDocumentationComplete = completenessCheckHandler.isDocumentationComplete(jsonString_false);
		assertFalse(isDocumentationComplete);
		assertEquals("CONDEC-1", completenessCheckHandler.getJiraIssuesWithIncompleteDocumentation().iterator().next());
	}
}
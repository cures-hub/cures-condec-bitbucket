package de.uhd.ifi.se.decision.management.bitbucket.merge.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.atlassian.bitbucket.commit.Commit;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;

public class TestCompletenessCheckHandler {

	private static CompletenessCheckHandler completenessCheckHandler;

	@BeforeClass
	public static void setUp() {
		completenessCheckHandler = new CompletenessCheckHandlerImpl(new MockPullRequest());
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
	@Ignore
	public void testIsDocumentationCompleteInPullRequest() {
		// TODO Mock ComponententLocator, CommitService, and ApplicationLinkService
		boolean isDocumentationComplete = completenessCheckHandler.isDocumentationComplete();
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testGetCommitsOfPullRequest() {
		// TODO Mock ComponententLocator, CommitService, and ApplicationLinkService
		Iterable<Commit> commits = ((CompletenessCheckHandlerImpl) completenessCheckHandler).getCommitsOfPullRequest();
		assertEquals(false, commits.iterator().hasNext());
	}

	@Test
	public void testIsDocumentationCompleteTrue() {
		String jsonString_true = "[[{'type':'issue'}, {'type':'decision'}]]";
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null)
				.isDocumentationComplete(jsonString_true);
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testIsDocumentationCompleteFalse() {
		String jsonString_false = "[[{'key':'CONDEC-1', 'type':'work item'}, {'type':'issue'}]]";
		CompletenessCheckHandlerImpl completenessCheckHandler = new CompletenessCheckHandlerImpl(null);
		boolean isDocumentationComplete = completenessCheckHandler.isDocumentationComplete(jsonString_false);
		assertFalse(isDocumentationComplete);
		assertEquals("CONDEC-1", completenessCheckHandler.getJiraIssuesWithIncompleteDocumentation().iterator().next());
	}

	@Test
	public void parseProjectKey() {
		assertEquals("", CompletenessCheckHandler.retrieveProjectKey(""));
		assertEquals("CONDEC", CompletenessCheckHandler.retrieveProjectKey("Example message ConDec-1 ConDec-2"));
	}
}
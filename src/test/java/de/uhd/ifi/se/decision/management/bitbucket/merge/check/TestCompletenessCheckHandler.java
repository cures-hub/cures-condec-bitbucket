package de.uhd.ifi.se.decision.management.bitbucket.merge.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;

public class TestCompletenessCheckHandler {

	private static CompletenessCheckHandler completenessCheckHandler;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		PullRequest pullRequest = new PullRequest(new MockPullRequest());
		completenessCheckHandler = new CompletenessCheckHandler(pullRequest);
	}

	@Test
	public void testConstructorPullRequestNull() {
		assertNotNull(new CompletenessCheckHandler(null));
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
		Iterable<Commit> commits = ((CompletenessCheckHandler) completenessCheckHandler).pullRequest.getCommits();
		assertEquals(true, commits.iterator().hasNext());
	}

	@Test
	public void testIsDocumentationCompleteTrue() {
		JSONArray jsonString_true = new JSONArray("[{'type':'issue'}, {'type':'decision'}]");
		boolean isDocumentationComplete = new CompletenessCheckHandler(null).isDocumentationComplete(jsonString_true);
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testIsDocumentationCompleteFalse() {
		JSONArray jsonString_false = new JSONArray("[{'key':'CONDEC-1', 'type':'work item'}, {'type':'issue'}]");
		CompletenessCheckHandler completenessCheckHandler = new CompletenessCheckHandler(null);
		boolean isDocumentationComplete = completenessCheckHandler.isDocumentationComplete(jsonString_false);
		assertFalse(isDocumentationComplete);
	}
}
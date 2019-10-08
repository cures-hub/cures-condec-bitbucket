package de.uhd.ifi.se.decision.management.bitbucket.merge.check;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.bitbucket.hook.repository.RepositoryHookResult;
import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessMergeCheck;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLink;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequestMergeHookRequest;

public class TestCompletenessMergeCheck {

	private static CompletenessMergeCheck completenessMergeCheck;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		completenessMergeCheck = new CompletenessMergeCheck();
	}

	@Test
	public void testPreUpdateAccepted() {
		RepositoryHookResult result = completenessMergeCheck.preUpdate(null, new MockPullRequestMergeHookRequest());
		assertTrue(result.isAccepted());
	}

	@Test
	public void testPreUpdateRejected() {
		MockApplicationLink.IS_DOCUMENTATION_COMPLETE = false;
		RepositoryHookResult result = completenessMergeCheck.preUpdate(null, new MockPullRequestMergeHookRequest());
		assertTrue(result.isRejected());
		MockApplicationLink.IS_DOCUMENTATION_COMPLETE = true;
	}
}

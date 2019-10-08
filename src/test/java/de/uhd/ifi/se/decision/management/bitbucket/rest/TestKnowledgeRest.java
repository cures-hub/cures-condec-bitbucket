package de.uhd.ifi.se.decision.management.bitbucket.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequest;

public class TestKnowledgeRest {

	private static KnowledgeRest knowledgeRest;
	private static CompletenessCheckHandler completenessCheckHandler;

	@BeforeClass
	public static void setUpBeforClass() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		completenessCheckHandler = new CompletenessCheckHandlerImpl(new MockPullRequest());
		knowledgeRest = new KnowledgeRest();
	}

	@Test
	public void testGetDecisionKnowledgeFromJira() {
		completenessCheckHandler.isDocumentationComplete();
		Response response = knowledgeRest.getDecisionKnowledgeElement();
		assertEquals(200, response.getStatus());
	}

}

package de.uhd.ifi.se.decision.management.bitbucket.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockPullRequestService;

public class TestKnowledgeRest {

	private static KnowledgeRest knowledgeRest;

	@BeforeClass
	public static void setUpBeforClass() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService(),
				new MockPullRequestService());
		knowledgeRest = new KnowledgeRest();
	}

	@Test
	public void testGetDecisionKnowledgeFromJira() {
		Response response = knowledgeRest.getDecisionKnowledgeElement(1, 1);
		assertEquals(200, response.getStatus());
		assertEquals("[[{'type':'issue'}, {'type':'decision'}]]", response.getEntity());
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraPullRequestNull() {
		Response response = knowledgeRest.getDecisionKnowledgeElement(-1, -1);
		assertEquals(500, response.getStatus());
		assertEquals("The pull request cannot be found.", response.getEntity());
	}
}

package de.uhd.ifi.se.decision.management.bitbucket.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
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
		JSONArray expected = new JSONArray(
				"[[{'key' : 'CONDEC-1', 'type':'issue'}, {'key' : 'CONDEC-2', 'type':'decision'}]]");

		assertEquals(expected.toString(), response.getEntity().toString());
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraPullRequestNull() {
		Response response = knowledgeRest.getDecisionKnowledgeElement(-1, -1);
		assertEquals(500, response.getStatus());
		assertEquals("The pull request cannot be found.", response.getEntity());
	}
}

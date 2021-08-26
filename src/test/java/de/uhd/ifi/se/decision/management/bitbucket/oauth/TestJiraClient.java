package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;

public class TestJiraClient {

	private static JiraClient jiraClient;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockApplicationLinkService());
		jiraClient = new JiraClient();
	}

	@Test
	public void testConstructor() {
		assertNotNull(new JiraClient());
	}

	@Test
	public void testApplicationLinkNull() {
		jiraClient.jiraApplicationLink = null;
		assertEquals(0, jiraClient.getJiraProjects().size());
		assertTrue(jiraClient.getDecisionKnowledgeFromJiraAsJson("", "CONDEC", "CONDEC-1").isEmpty());
		jiraClient = new JiraClient();
	}

	@Test
	public void testGetCurrentActiveJiraProjects() {
		Set<String> projectKeys = jiraClient.getJiraProjects();
		assertEquals(1, projectKeys.size());
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraByFilterSettingsValid() {
		JSONArray decisionKnowledgeJsonString = jiraClient.getDecisionKnowledgeFromJiraAsJson("", "CONDEC", "CONDEC-1");
		JSONArray expected = new JSONArray(
				"[{'key' : 'CONDEC-1', 'type':'issue'}, {'key' : 'CONDEC-2', 'type':'decision'}]");
		assertEquals(expected.toString(), decisionKnowledgeJsonString.toString());
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraByKeysValid() {
		Set<String> jiraIssueKeys = new HashSet<>();
		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");
		JSONArray decisionKnowledgeJsonString = jiraClient.getDecisionKnowledgeFromJiraAsJson(jiraIssueKeys);

		assertEquals(2, decisionKnowledgeJsonString.length());
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraByKeysEmpty() {
		Set<String> jiraIssueKeys = new HashSet<>();
		JSONArray decisionKnowledgeJsonString = jiraClient.getDecisionKnowledgeFromJiraAsJson(jiraIssueKeys);
		assertTrue(decisionKnowledgeJsonString.isEmpty());
	}

	@Test
	public void testParseJiraProjectsJsonOneProject() {
		Set<String> projects = ((JiraClient) jiraClient).parseJiraProjectsJson("CONDEC");
		assertEquals("CONDEC", projects.iterator().next());
	}

	@Test
	public void testParseJiraProjectsJsonManyProjects() {
		Set<String> projects = ((JiraClient) jiraClient)
				.parseJiraProjectsJson("[ {'key' : 'TEST'}, {'key' : 'CONDEC'} ]");
		assertEquals(2, projects.size());
	}

	@Test
	public void testGetJiraCallQuery() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");
		assertEquals("%3Fjql%3Dkey+in+%28CONDEC-2%2CCONDEC-1%29", JiraClient.getJiraCallQuery(jiraIssueKeys));
	}

}

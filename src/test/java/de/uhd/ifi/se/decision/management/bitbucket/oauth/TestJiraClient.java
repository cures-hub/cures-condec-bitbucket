package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
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
	public void testGetCurrentActiveJiraProjects() {
		Set<String> projectKeys = jiraClient.getJiraProjects();
		assertEquals(1, projectKeys.size());
	}

	@Test
	@Ignore
	public void testGetDecisionKnowledgeFromJira() {
		// String decisionKnowledgeJsonString =
		// jiraClient.getDecisionKnowledgeFromJira("", "CONDEC");
		// assertEquals("[{'type':'issue'}, {'type':'decision'}]",
		// decisionKnowledgeJsonString);
	}

	@Test
	public void testGetDecisionKnowledgeFromJiraByKeys() {
		Set<String> jiraIssueKeys = new HashSet<>();
		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");

		String decisionKnowledgeJsonString = jiraClient.getDecisionKnowledgeFromJiraAsJsonString(jiraIssueKeys);
		assertEquals("[{'type':'issue'}, {'type':'decision'}]", decisionKnowledgeJsonString);
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
	public void testRetrieveProjectKeys() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		assertEquals("", JiraClient.retrieveProjectKey(jiraIssueKeys));
		jiraIssueKeys.add("UNKNOWNPROJECT-1");
		assertEquals("", JiraClient.retrieveProjectKey(jiraIssueKeys));

		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");
		assertEquals("CONDEC", JiraClient.retrieveProjectKey(jiraIssueKeys));
	}

	@Test
	public void testGetJiraCallQuery() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");
		assertEquals("%3Fjql%3Dkey+in+%28CONDEC-2%2CCONDEC-1%29", JiraClient.getJiraCallQuery(jiraIssueKeys));
	}

}

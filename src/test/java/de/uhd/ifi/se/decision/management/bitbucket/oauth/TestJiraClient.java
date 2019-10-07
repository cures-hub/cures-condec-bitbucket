package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockCommitService;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.impl.JiraClientImpl;

public class TestJiraClient {
	
	private static JiraClient jiraClient;

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockCommitService(), new MockApplicationLinkService());
		jiraClient = new JiraClientImpl();
	}

	@Test
	public void testConstructor() {
		assertNotNull(new JiraClientImpl());
	}
	
	@Test
	public void testGetCurrentActiveJiraProjects() {
		Set<String> projectKeys = jiraClient.getCurrentActiveJiraProjects();
		assertEquals(1, projectKeys.size());
	}

}

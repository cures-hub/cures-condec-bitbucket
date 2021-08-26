package de.uhd.ifi.se.decision.management.bitbucket.parser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;

public class TestRetrieveProjectKey {

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockApplicationLinkService());
	}

	@Test
	public void testMultipleJiraIssueKeys() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		jiraIssueKeys.add("CONDEC-1");
		jiraIssueKeys.add("CONDEC-2");
		assertEquals("CONDEC", JiraIssueKeyParser.retrieveProjectKey(jiraIssueKeys));
	}

	@Test
	public void testUnknownJiraIssueKey() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		jiraIssueKeys.add("UNKNOWNPROJECT-1");
		assertEquals("", JiraIssueKeyParser.retrieveProjectKey(jiraIssueKeys));
	}

	@Test
	public void testNoJiraIssueKey() {
		Set<String> jiraIssueKeys = new HashSet<String>();
		assertEquals("", JiraIssueKeyParser.retrieveProjectKey(jiraIssueKeys));
	}

	@Test
	public void testJiraIssueKeyNull() {
		assertEquals("", JiraIssueKeyParser.retrieveProjectKey(null));
	}

}

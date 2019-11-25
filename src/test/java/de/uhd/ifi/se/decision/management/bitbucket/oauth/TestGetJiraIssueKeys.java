package de.uhd.ifi.se.decision.management.bitbucket.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atlassian.sal.testresources.component.MockComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.mocks.MockApplicationLinkService;

public class TestGetJiraIssueKeys {

	@BeforeClass
	public static void setUp() {
		MockComponentLocator.create(new MockApplicationLinkService());
	}

	@Test
	public void testMultipleJiraIssueKeys() {
		Set<String> jiraIssueKeys = JiraClient
				.getJiraIssueKeys("ConDec-1: Initial commit ConDec-2 -hallo ConDec-3 -Great tool");
		Iterator<String> iterator = jiraIssueKeys.iterator();
		assertEquals("CONDEC-1", iterator.next());
		assertEquals("CONDEC-2", iterator.next());
		assertEquals("CONDEC-3", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testParseJiraIssueKeyStartingWithDifferentWord() {
		Set<String> jiraIssueKeys = JiraClient.getJiraIssueKeys("Feature/CONDEC-1 link to detail view");
		Iterator<String> iterator = jiraIssueKeys.iterator();
		assertEquals("CONDEC-1", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testParseJiraIssueKeysSeparatedWithHypen() {
		Set<String> jiraIssueKeys = JiraClient.getJiraIssueKeys("CONDEC-1-link-to-detail-view");
		Iterator<String> iterator = jiraIssueKeys.iterator();
		assertEquals("CONDEC-1", iterator.next());
		assertFalse(iterator.hasNext());
	}

}

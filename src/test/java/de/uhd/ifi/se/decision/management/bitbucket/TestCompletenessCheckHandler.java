package de.uhd.ifi.se.decision.management.bitbucket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.CompletenessCheckHandler;
import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;

public class TestCompletenessCheckHandler {

	@Test 
	public void testConstructor() {
		assertNotNull(new CompletenessCheckHandlerImpl(null));
	}

	@Test
	public void testIsDocumentationCompleteTrue() {
		String jsonString_true = "[[{'type':'issue'}, {'type':'decision'}]]";
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null)
				.isDocumentationComplete(jsonString_true);
		assertTrue(isDocumentationComplete);
	}

	@Test
	public void testIsDocumentationCompleteFalse() {
		String jsonString_false = "[[{'key':'CONDEC-1', 'type':'work item'}, {'type':'issue'}]]";
		CompletenessCheckHandlerImpl completenessCheckHandler = new CompletenessCheckHandlerImpl(null);
		boolean isDocumentationComplete = completenessCheckHandler
				.isDocumentationComplete(jsonString_false);
		assertFalse(isDocumentationComplete);
		assertEquals("CONDEC-1", completenessCheckHandler.getJiraIssuesWithIncompleteDocumentation().iterator().next());
	}
	
	@Test
	public void parseProjectKey() {
		assertEquals("", CompletenessCheckHandler.retrieveProjectKey(""));
		assertEquals("CONDEC", CompletenessCheckHandler.retrieveProjectKey("Example message ConDec-1 ConDec-2"));
	}

}
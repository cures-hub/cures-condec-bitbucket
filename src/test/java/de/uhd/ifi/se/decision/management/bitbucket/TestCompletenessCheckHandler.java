package de.uhd.ifi.se.decision.management.bitbucket;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;

public class TestCompletenessCheckHandler {
	
	@Test
	public void testConstructor() {
		assertNotNull(new CompletenessCheckHandlerImpl(null));
	}

}
package de.uhd.ifi.se.decision.management.bitbucket;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;

@Ignore
public class MergeCheckHandlerUnitTests {
//	MergeCheckHandler sut = new MergeCheckHandler();
//	String jsonString_true = "[[{'id':14910,'summary':'WI: Merge Elements Query Linked and Elements Query into one function','type':'issue','documentationLocation':'i','key':'CONDEC-3','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'},{'id':14174,'summary':'Should we really remove one entire function? We already did all the work, why remove it?\n','description':'Should we really remove one entire function? We already did all the work, why remove it?\n','type':'Issue','documentationLocation':'s','key':'CONDEC-3:14174','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'}],[{'id':14909,'summary':'WI: Disable recursive JQL filtering on getAllElementsMatchingQueryAndLinked to export all elements in tree instead of only elements in search term','type':'decision','documentationLocation':'i','key':'CONDEC-2','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14171,'summary':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','description':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','type':'Issue','documentationLocation':'s','key':'CONDEC-2:14171','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14173,'summary':'All issues\n','description':'All issues\n','type':'Decision','documentationLocation':'s','key':'CONDEC-2:14173','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14172,'summary':'All Open issues\n','description':'All Open issues\n','type':'Alternative','documentationLocation':'s','key':'CONDEC-2:14172','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'}]]";
//
//	String jsonString_false = "[[{'id':14910,'summary':'WI: Merge Elements Query Linked and Elements Query into one function','type':'Work Item','documentationLocation':'i','key':'CONDEC-3','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'},{'id':14174,'summary':'Should we really remove one entire function? We already did all the work, why remove it?\n','description':'Should we really remove one entire function? We already did all the work, why remove it?\n','type':'Issue','documentationLocation':'s','key':'CONDEC-3:14174','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'}],[{'id':14909,'summary':'WI: Disable recursive JQL filtering on getAllElementsMatchingQueryAndLinked to export all elements in tree instead of only elements in search term','type':'Work Item','documentationLocation':'i','key':'CONDEC-2','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14171,'summary':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','description':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','type':'Issue','documentationLocation':'s','key':'CONDEC-2:14171','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14173,'summary':'All issues\n','description':'All issues\n','type':'BUG','documentationLocation':'s','key':'CONDEC-2:14173','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14172,'summary':'All Open issues\n','description':'All Open issues\n','type':'Alternative','documentationLocation':'s','key':'CONDEC-2:14172','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'}]]";
//
//	@Test
//	public void checkMergeCondition_true() {
//		HashMap<String, String> result = sut.hasSufficientDecisions(jsonString_true);
//		assertEquals("The Condition should not be sufficient to merge!", "true", result.get("resultBoolean"));
//	}
//
//	@Test
//	public void checkMergeCondition_false() {
//		HashMap<String, String> result = sut.hasSufficientDecisions(jsonString_false);
//
//		assertEquals("The Condition should be sufficient to merge!", "false", result.get("resultBoolean"));
//	}
}
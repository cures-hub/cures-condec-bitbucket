package de.uhd.ifi.se.decision.management.bitbucket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.CompletenessCheckHandlerImpl;

public class TestCompletenessCheckHandler {

	private String jsonString_true = "[[{\"id\":27813,\"summary\":\"WI: Create automatic releases on GitHub when changing the plug-in version\",\"description\":\"Please find out how to create new releases on GitHub, when the version of the plug-in changes.\\r\\n * A release-tag should be created pointing to the respective commit on the master branch.\\r\\n * The jar file should be compiled and added to the release page.\\r\\n\\r\\nhttps://github.com/cures-hub/cures-decdoc-jira/releases\\r\\n\\r\\n{issue}How to create releases on GitHub?{issue}\",\"type\":\"Work Item\",\"documentationLocation\":\"i\",\"key\":\"CONDEC-3\",\"url\":\"https://jira-se.ifi.uni-heidelberg.de/browse/CONDEC-3\"},{\"id\":5645,\"summary\":\"How to create releases on GitHub?\",\"description\":\"How to create releases on GitHub?\",\"type\":\"Issue\",\"documentationLocation\":\"s\",\"key\":\"CONDEC-3:5645\",\"plainText\":true,\"tagged\":true,\"relevant\":true,\"validated\":true,\"url\":\"https://jira-se.ifi.uni-heidelberg.de/browse/CONDEC-3\"},{\"id\":2294,\"summary\":\"The release tag can be manually created by the developer.\",\"description\":\"The release tag can be manually created by the developer.\",\"type\":\"Decision\",\"documentationLocation\":\"s\",\"key\":\"CONDEC-3:2294\",\"plainText\":true,\"tagged\":true,\"relevant\":true,\"validated\":true,\"url\":\"https://jira-se.ifi.uni-heidelberg.de/browse/CONDEC-3\"}]]";
	private String jsonString_false = "[[{'id':14910,'summary':'WI: Merge Elements Query Linked and Elements Query into one function','type':'Work Item','documentationLocation':'i','key':'CONDEC-3','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'},{'id':14174,'summary':'Should we really remove one entire function? We already did all the work, why remove it?\n','description':'Should we really remove one entire function? We already did all the work, why remove it?\n','type':'Issue','documentationLocation':'s','key':'CONDEC-3:14174','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-3'}],[{'id':14909,'summary':'WI: Disable recursive JQL filtering on getAllElementsMatchingQueryAndLinked to export all elements in tree instead of only elements in search term','type':'Work Item','documentationLocation':'i','key':'CONDEC-2','url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14171,'summary':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','description':'We can let the user set an additional recursive filter for the linked elements, but which filter should we use as default\n','type':'Issue','documentationLocation':'s','key':'CONDEC-2:14171','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14173,'summary':'All issues\n','description':'All issues\n','type':'BUG','documentationLocation':'s','key':'CONDEC-2:14173','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'},{'id':14172,'summary':'All Open issues\n','description':'All Open issues\n','type':'Alternative','documentationLocation':'s','key':'CONDEC-2:14172','plainText':true,'tagged':true,'relevant':true,'validated':true,'url':'https://cures.ifi.uni-heidelberg.de/jira/browse/CONDEC-2'}]]";

	@Test
	public void testConstructor() {
		assertNotNull(new CompletenessCheckHandlerImpl(null));
	}
	
	@Test
	public void testIsDocumentationCompleteTrue() {
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null).isDocumentationComplete(jsonString_true);
		assertTrue(isDocumentationComplete);
	}
	
	@Test
	public void testIsDocumentationCompleteFalse() {
		boolean isDocumentationComplete = new CompletenessCheckHandlerImpl(null).isDocumentationComplete(jsonString_false);
		assertFalse(isDocumentationComplete);
	}

}
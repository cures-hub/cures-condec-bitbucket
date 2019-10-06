package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import java.util.Locale;
import java.util.Set;

import com.sun.jna.platform.win32.WinDef.WORD;

/**
 * Interface to check the completeness of the documentation of decision
 * knowledge related to a pull request.
 */
public interface CompletenessCheckHandler {

	/**
	 * Checks whether the documentation is complete.
	 * 
	 * @return true if the documentation is complete.
	 */
	boolean isDocumentationComplete();

	/**
	 * Returns a set of Jira issues with an incomplete documentation.
	 * 
	 * @return set of Jira issue keys.
	 */
	Set<String> getJiraIssuesWithIncompleteDocumentation();
	
	/**
	 * Looks for Jira issue key (e.g. CONDEC-1) in a String and returns a potential Jira project
	 * key (e.g. CONDEC).
	 * 
	 * @param text
	 *            that might contain a project key, e.g., a commit message, branch
	 *            name oder branch title.
	 * @return potential project key in upper case.
	 */
	static String retrieveProjectKey(String text) {		
		if (text.indexOf("-") == -1) {
			// there is no Jira issue key (e.g. CONDEC-1) in the text
			return "";
		}
		String splitted = text.split("-")[0];
		String[] words = splitted.split(" ");
		String projectKey = words[words.length-1];
		return projectKey.toUpperCase(Locale.ENGLISH);
	}

}
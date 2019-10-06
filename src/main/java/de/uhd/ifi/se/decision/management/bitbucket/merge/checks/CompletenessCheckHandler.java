package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import java.util.Set;

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
	boolean hasSufficientDecisions();

	/**
	 * Returns a set of Jira issues with an incomplete documentation.
	 * 
	 * @return set of Jira issue keys.
	 */
	Set<String> getJiraIssuesWithIncompleteDocumentation();

}
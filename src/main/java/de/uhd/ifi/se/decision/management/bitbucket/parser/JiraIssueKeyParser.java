package de.uhd.ifi.se.decision.management.bitbucket.parser;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * Util methods to identifiy Jira issue keys and project key within Strings
 * (commit messages, branch name, or pull request title).
 */
public interface JiraIssueKeyParser {

	/**
	 * @param message
	 *            that might contain a Jira issue key, e.g., a commit message,
	 *            branch name, or pull request title.
	 * @return set of all mentioned Jira issue keys in upper case letters (is
	 *         ordered by their appearance in the message).
	 * 
	 * @issue How to identify the Jira issue key(s) in a commit message?
	 * @decision Use the well known regex ((?<!([A-Z]{1,10})-?)[A-Z0-9]+-\\d+) to
	 *           identify all Jira issue keys in a commit message!
	 * @pro Works for all known cases.
	 * @alternative Assume that the Jira issue key is the first word in the commit
	 *              message and split the message using
	 *              commitMessage.split("[\\s,:]+").
	 * @con This assumption could be wrong for other projects than the ConDec
	 *      development projects.
	 */
	static Set<String> getJiraIssueKeys(String message) {
		Set<String> keys = new LinkedHashSet<String>();
		Pattern pattern = Pattern.compile("((?<!([A-Z]{1,10})-?)[A-Z0-9]+-\\d+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(message);
		while (matcher.find()) {
			keys.add(matcher.group(1).toUpperCase(Locale.ENGLISH));
		}
		return keys;
	}

	/**
	 * @param jiraIssueKeys
	 *            as a set of strings.
	 * @return potential Jira project key (e.g. CONDEC).
	 */
	static String retrieveProjectKey(Set<String> jiraIssueKeys) {
		Set<String> projectKeys = JiraClient.instance.getJiraProjects();
		if (jiraIssueKeys == null || jiraIssueKeys.isEmpty()) {
			return "";
		}
		for (String jiraIssueKey : jiraIssueKeys) {
			String potentialProjectKey = jiraIssueKey.split("-")[0];
			if (JiraIssueKeyParser.isProjectKeyExisting(potentialProjectKey, projectKeys)) {
				return potentialProjectKey;
			}
		}
		return "";
	}

	static boolean isProjectKeyExisting(String projectKey, Set<String> existingProjectKeys) {
		return !projectKey.isEmpty() && existingProjectKeys.contains(projectKey);
	}

}

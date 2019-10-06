package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import java.util.Set;

public interface MergeCheckHandler {

	boolean hasSufficientDecisions();

	Set<String> getJiraIssuesWithIncompleteDocumentation();

}
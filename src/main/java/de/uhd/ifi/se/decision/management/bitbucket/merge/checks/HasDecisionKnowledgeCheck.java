package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.hook.repository.PreRepositoryHookContext;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.hook.repository.RepositoryHookResult;
import com.atlassian.bitbucket.hook.repository.RepositoryMergeCheck;
import com.atlassian.bitbucket.pull.PullRequest;

import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.impl.MergeCheckHandlerImpl;

/**
 * Enforces that pull requests can only be accepted, i.e., the respective branch
 * can only be merged back to the maineline if the decision knowledge
 * documentation is complete.
 */
@Component("hasDecisionKnowledgeCheck")
public class HasDecisionKnowledgeCheck implements RepositoryMergeCheck {

	@Nonnull
	@Override
	public RepositoryHookResult preUpdate(@Nonnull PreRepositoryHookContext context,
			@Nonnull PullRequestMergeHookRequest request) {
		PullRequest pullRequest = request.getPullRequest();

		MergeCheckHandler mergeCheckDataHandler = new MergeCheckHandlerImpl(pullRequest);
		if (mergeCheckDataHandler.hasSufficientDecisions()) {
			return RepositoryHookResult.accepted();
		}
		String summaryMsg = "There are not enough decision knowledge elements linked to the pull request.";
		String detailedMsg = "Every Jira ticket has to have at least one decision problem (=issue) and one decision linked. "
				+ "Jira tickets with an incomplete documentation are:  ";
		for (String jiraIssueKey : mergeCheckDataHandler.getJiraIssuesWithIncompleteDocumentation()) {
			detailedMsg += jiraIssueKey + " ";
		}

		return RepositoryHookResult.rejected(summaryMsg, detailedMsg);
	}
}
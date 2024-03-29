package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.hook.repository.PreRepositoryHookContext;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.hook.repository.RepositoryHookResult;
import com.atlassian.bitbucket.hook.repository.RepositoryMergeCheck;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;

/**
 * Enforces that pull requests can only be accepted, i.e., the respective branch
 * can only be merged back to the maineline if the decision knowledge
 * documentation is complete. The completeness is determined in the
 * {@link CompletenessCheckHandler}.
 * 
 * @issue Should the developers be allowed to merge the branch with an
 *        incomplete decision knowledge documentation?
 * @decision It is configurable on a setting page whether the incomplete
 *           documentation enforces that the branch cannot be merged or not!
 */
@Component("completenessMergeCheck")
public class CompletenessMergeCheck implements RepositoryMergeCheck {

	@Nonnull
	@Override
	public RepositoryHookResult preUpdate(@Nonnull PreRepositoryHookContext context,
			@Nonnull PullRequestMergeHookRequest request) {
		PullRequest pullRequest = new PullRequest(request);

		CompletenessCheckHandler completenessCheckHandler = new CompletenessCheckHandler(pullRequest);
		if (completenessCheckHandler.isDocumentationComplete()) {
			return RepositoryHookResult.accepted();
		}
		String summaryMessage = "There are not enough decision knowledge elements linked to the pull request.";
		String detailMessage = "Every Jira ticket has to have at least one decision problem (=issue) and one decision linked. "
				+ "Jira tickets with an incomplete documentation are:  ";
		for (String jiraIssueKey : completenessCheckHandler.getJiraIssuesWithIncompleteDocumentation()) {
			detailMessage += jiraIssueKey + " ";
		}

		return RepositoryHookResult.rejected(summaryMessage, detailMessage);
	}
}
package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.hook.repository.PreRepositoryHookContext;
import com.atlassian.bitbucket.hook.repository.PullRequestMergeHookRequest;
import com.atlassian.bitbucket.hook.repository.RepositoryHookResult;
import com.atlassian.bitbucket.hook.repository.RepositoryMergeCheck;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestRef;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import de.uhd.ifi.se.decision.management.bitbucket.oAuth.ApiLinkService;

@Component("hasDecisionKnowledgeCheck")
public class HasDecisionKnowledgeCheck implements RepositoryMergeCheck {

	private final CommitService commitService;
	public static String JIRA_QUERY;
	public static String PROJECT_KEY;

	@Autowired
	public HasDecisionKnowledgeCheck(@ComponentImport CommitService commitService) {
		this.commitService = commitService;
	}

	@Nonnull
	@Override
	public RepositoryHookResult preUpdate(@Nonnull PreRepositoryHookContext context,
			@Nonnull PullRequestMergeHookRequest request) {

		// get pullRequest
		final PullRequest pullRequest = request.getPullRequest();

		// find correct query out of projects, commitMessages and BranchId
		MergeCheckDataHandler mergeCheckDataHandler = new MergeCheckDataHandler();
		Iterable<Commit> commits = mergeCheckDataHandler.getCommitsOfPullRequest(pullRequest, this.commitService);
		String branchTitle = pullRequest.getTitle();
		PullRequestRef pullRequestRef = pullRequest.getFromRef();
		String branchId = pullRequestRef.getDisplayId();
		String queryWithJiraIssues = "?jql=key in "
				+ mergeCheckDataHandler.getJiraCallQuery(commits, branchTitle, branchId);
		String projectString = ApiLinkService.getCurrentActiveJiraProjects();
		if (!mergeCheckDataHandler.checkIfJsonArray(projectString)) {
			return RepositoryHookResult.accepted();
		} else {
			JIRA_QUERY = queryWithJiraIssues;
			PROJECT_KEY = mergeCheckDataHandler.getProjectKeyFromJiraAndCheckWhichOneCouldBe(commits, projectString,
					branchId, branchTitle);

			// get Issues out of Jira
			String decisionKnowledgeIssueString = ApiLinkService.getDecisionKnowledgeFromJira(queryWithJiraIssues,
					PROJECT_KEY);
			HashMap<String, String> hasSufficientDecisions = mergeCheckDataHandler
					.hasSufficientDecisions(decisionKnowledgeIssueString);
			if (!Boolean.valueOf(hasSufficientDecisions.get("resultBoolean"))) {
				String summaryMsg = "There are not enough Decision Elements for each Commit";
				String detailedMsg = "Every Commit which is linked to a jira Ticket has to have at least one issue and one decision linked :"
						+ hasSufficientDecisions.get("resultString");

				return RepositoryHookResult.rejected(summaryMsg, detailedMsg);
			} else {
				return RepositoryHookResult.accepted();
			}
		}
	}

}

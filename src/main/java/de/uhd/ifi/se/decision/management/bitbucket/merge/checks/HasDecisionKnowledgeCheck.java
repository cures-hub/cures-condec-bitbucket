package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import com.atlassian.bitbucket.hook.repository.*;
import com.atlassian.bitbucket.i18n.I18nService;
import com.atlassian.bitbucket.permission.Permission;
import com.atlassian.bitbucket.permission.PermissionService;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.uhd.ifi.se.decision.management.bitbucket.oAuth.ApiLinkService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.SimpleCommit;

import javax.annotation.Nonnull;

import com.atlassian.bitbucket.util.PageRequestImpl;
import com.atlassian.bitbucket.util.Page;

import org.json.JSONArray;

import java.util.*;

@Component("hasDecisionKnowledgeCheck")
public class HasDecisionKnowledgeCheck implements RepositoryMergeCheck {

	private final I18nService i18nService;
	private final PermissionService permissionService;
	private final CommitService commitService;
	public static String JIRA_QUERY;
	public static String PROJECT_KEY;

	@Autowired
	public HasDecisionKnowledgeCheck(@ComponentImport I18nService i18nService,
									 @ComponentImport PermissionService permissionService,
									 @ComponentImport CommitService commitService) {
		this.i18nService = i18nService;
		this.permissionService = permissionService;
		this.commitService = commitService;
	}

	@Nonnull
	@Override
	public RepositoryHookResult preUpdate(@Nonnull PreRepositoryHookContext context,
										  @Nonnull PullRequestMergeHookRequest request) {
		final PullRequest pullRequest = request.getPullRequest();
		Repository repository = request.getPullRequest().getToRef().getRepository();
		Iterable<Commit> commits = getCommitsOfPullRequest(pullRequest);
		String queryWithJiraIssues = "?jql=key in " + getJiraCallQuery(commits);
		String projectString = ApiLinkService.getCurrentActiveJiraProjects();
		JIRA_QUERY = queryWithJiraIssues;
		PROJECT_KEY = getProjectKeyFromJiraAndCheckWhichOneCouldBe(commits, projectString);
		String decisionKnowledgeIssueString = ApiLinkService.getDecisionKnowledgeFromJira(queryWithJiraIssues, PROJECT_KEY);
		Boolean hasSufficientDecisions = this.hasSufficientDecisions(decisionKnowledgeIssueString);
		if (!hasSufficientDecisions) {
			String summaryMsg = i18nService.getMessage("mycompany.plugin.merge.check.notrepoadmin.summary",
				"There are not enough Decision Elements for each Commit");
			String detailedMsg = i18nService.getText("mycompany.plugin.merge.check.notrepoadmin.detailed",
				"Every Commit which is linked to a jira Ticket has to have at least one decision element linked");

			return RepositoryHookResult.rejected(summaryMsg, detailedMsg);
		} else {

			return RepositoryHookResult.accepted();
		}
	}

	public Iterable<Commit> getCommitsOfPullRequest(PullRequest pullRequest) {
		CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(pullRequest);
		CommitsBetweenRequest cbr = builder.build();
		Page<Commit> cs = this.commitService.getCommitsBetween(cbr, new PageRequestImpl(0, 1048476));
		return cs.getValues();
	}

	/**
	 * @param commits
	 * @return String "(TEST-5, TEST-7,etc...)"
	 */
	public String getJiraCallQuery(Iterable<Commit> commits) {
		String query = "(";
		ArrayList<String> messageList = new ArrayList<String>();
		for (Commit commit : commits) {
			String message = commit.getMessage();
			messageList.add(message);
		}
		for (String message : messageList) {
			//Split message after first space
			if (message.contains(" ")) {
				String[] parts = message.split(" ");
				query = query + parts[0] + ",";
			} else {
				// todo error exception
				return "";
			}
		}
		//remove last ,
		query = query.substring(0, query.length() - 1);
		// add )
		query += ")";
		return query;
	}

	private Boolean hasSufficientDecisions(String jsonString) {
		Boolean result = true;
		try {
			JSONArray topArray = new JSONArray(jsonString);
			for (Object current : topArray) {

				JSONArray myCurrent = (JSONArray) current;
				//child elements have to be more then 1
				if (myCurrent.length() < 2) {
					result = false;
				}
			}
		} catch (Exception e) {
			return result;
		}


		return result;
	}

	private String getProjectKeyFromJiraAndCheckWhichOneCouldBe(Iterable<Commit> commits, String projects) {
		String selectedProject = "TEST";
		try {
			ArrayList<String> projectKeys = new ArrayList<String>();

			JSONArray projectArray = new JSONArray(projects);
			for (Object project : projectArray) {
				JSONObject projectMap=(JSONObject)project;
				String projectKey=(String)projectMap.get("key");
				projectKeys.add(projectKey.toLowerCase());
			}
			for (Commit commit : commits) {
				String message = commit.getMessage();
				if (message.indexOf("-") > -1) {
					String eventuallyProjectKey = message.split("-")[0];
					if (projectKeys.contains(eventuallyProjectKey.toLowerCase())) {
						selectedProject = eventuallyProjectKey.toUpperCase();
					}
				}
			}
		} catch (Exception e) {
			return selectedProject;
		}
		return selectedProject;

	}

}

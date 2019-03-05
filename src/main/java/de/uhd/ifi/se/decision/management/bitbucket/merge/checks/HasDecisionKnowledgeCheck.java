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
		String branchId = pullRequest.getTitle();
		String queryWithJiraIssues = "?jql=key in " + getJiraCallQuery(commits, branchId);
		String projectString = ApiLinkService.getCurrentActiveJiraProjects();
		JIRA_QUERY = queryWithJiraIssues;
		PROJECT_KEY = getProjectKeyFromJiraAndCheckWhichOneCouldBe(commits, projectString);
		String decisionKnowledgeIssueString = ApiLinkService.getDecisionKnowledgeFromJira(queryWithJiraIssues, PROJECT_KEY);
		HashMap<String,String> hasSufficientDecisions = this.hasSufficientDecisions(decisionKnowledgeIssueString);
		if (!Boolean.valueOf(hasSufficientDecisions.get("resultBoolean"))) {
			String summaryMsg = i18nService.getMessage("mycompany.plugin.merge.check.notrepoadmin.summary",
				"There are not enough Decision Elements for each Commit");
			String detailedMsg = i18nService.getText("mycompany.plugin.merge.check.notrepoadmin.detailed",
				"Every Commit which is linked to a jira Ticket has to have at least one issue and one decision linked :"
					+ hasSufficientDecisions.get("resultString"));

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
	public String getJiraCallQuery(Iterable<Commit> commits, String branchId) {
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
				// just dont split the particular ticket
			}
		}

		//add Branch ID
		query += branchId;
		// add )
		query += ")";
		return query;
	}

	private HashMap<String,String> hasSufficientDecisions(String jsonString) {
		HashMap<String,String> result=new HashMap<String, String>();
		Boolean booleanResult = true;
		String stringResult = "";
		try {
			JSONArray topArray = new JSONArray(jsonString);
			for (Object current : topArray) {

				JSONArray myCurrent = (JSONArray) current;
				Boolean tempResult = checkIfDecisionsExists(myCurrent);
				if (!tempResult) {
					JSONObject firstKey = (JSONObject) myCurrent.get(0);
					stringResult += firstKey.get("key")+" ";
					booleanResult = false;
				}
			}
		} catch (Exception e) {
			booleanResult =false;
		}
		result.put("resultBoolean", booleanResult.toString());
		result.put("resultString", stringResult);
		return result;
	}

	private Boolean checkIfDecisionsExists(JSONArray decisions) {
		Boolean hasIssue = false;
		Boolean hasDecision = false;
		for (Object current : decisions) {
			JSONObject currentObject = (JSONObject) current;
			String type= (String)currentObject.get("type");
			//Issue
			if("issue".equals(type.toLowerCase())){
				hasIssue=true;
			}
			//Decision
			if("decision".equals(type.toLowerCase())){
				hasDecision=true;
			}
		}
		return hasIssue && hasDecision;
	}

	private String getProjectKeyFromJiraAndCheckWhichOneCouldBe(Iterable<Commit> commits, String projects) {
		String selectedProject = "TEST";
		try {
			ArrayList<String> projectKeys = new ArrayList<String>();

			JSONArray projectArray = new JSONArray(projects);
			for (Object project : projectArray) {
				JSONObject projectMap = (JSONObject) project;
				String projectKey = (String) projectMap.get("key");
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

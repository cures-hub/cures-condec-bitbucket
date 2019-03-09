package de.uhd.ifi.se.decision.management.bitbucket.merge.checks;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlassian.bitbucket.commit.Commit;
import com.atlassian.bitbucket.commit.CommitService;
import com.atlassian.bitbucket.commit.CommitsBetweenRequest;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequestImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class MergeCheckDataHandler {

	public Iterable<Commit> getCommitsOfPullRequest(PullRequest pullRequest, CommitService commitService ) {
		CommitsBetweenRequest.Builder builder = new CommitsBetweenRequest.Builder(pullRequest);
		CommitsBetweenRequest cbr = builder.build();
		Page<Commit> cs = commitService.getCommitsBetween(cbr, new PageRequestImpl(0, 1048476));
		return cs.getValues();
	}

	/**
	 * @param commits
	 * @param branchId
	 * @return String "(TEST-5, TEST-7,etc...)"
	 */
	public String getJiraCallQuery(Iterable<Commit> commits, String branchId, String branchTitle) {
		String query = "(";
		ArrayList<String> messageList = new ArrayList<String>();

		//add Branch ID
		messageList.add(branchId);
		//add Branch Title
		messageList.add(branchTitle);

		for (Commit commit : commits) {
			String message = commit.getMessage();
			messageList.add(message);
		}
		for (String message : messageList) {
			//Split message after first space
			if (message.contains(" ")) {
				String[] parts = message.split(" ");
				query = query + parts[0] + ",";
			}
		}
		//remove last ,
		query = query.substring(0, query.length() - 1);

		// add )
		query += ")";
		return query;
	}
	
 public HashMap<String,String> hasSufficientDecisions(String jsonString) {
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

	public Boolean checkIfDecisionsExists(JSONArray decisions) {
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

	public String getProjectKeyFromJiraAndCheckWhichOneCouldBe(Iterable<Commit> commits, String projects) {
		String selectedProject = "";
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

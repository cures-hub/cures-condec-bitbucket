package de.uhd.ifi.se.decision.management.bitbucket.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.atlassian.bitbucket.pull.PullRequestService;
import com.atlassian.sal.api.component.ComponentLocator;

import de.uhd.ifi.se.decision.management.bitbucket.model.PullRequest;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * REST resource: Enables importing decision knowledge from Jira.
 */
@Path("/knowledge")
public class KnowledgeRest {

	/**
	 * @issue The knowledge overview is not working anymore in Bitbucket version 7.
	 *        How can we extend the UI now?
	 */
	@Path("/decisionKnowledgeFromJira")
	@GET
	public Response getDecisionKnowledgeElement(@QueryParam("repositoryId") int repositoryId,
			@QueryParam("pullRequestId") long pullRequestId) {
		if (repositoryId < 0 || pullRequestId < 0) {
			return Response.serverError().entity("The pull request cannot be found.").build();
		}
		PullRequestService pullRequestService = ComponentLocator.getComponent(PullRequestService.class);
		PullRequest pullRequest = new PullRequest(pullRequestService.getById(repositoryId, pullRequestId));

		JSONArray jsonString = JiraClient.instance.getDecisionKnowledgeFromJiraAsJson(pullRequest);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
}
package de.uhd.ifi.se.decision.management.bitbucket.rest;

import static de.uhd.ifi.se.decision.management.bitbucket.model.MyPullRequest.JIRA_ISSUE_KEYS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.JiraClient;

/**
 * REST resource: Enables importing decision knowledge from Jira.
 */
@Path("/knowledge")
public class KnowledgeRest {

	@Path("/getDecisionKnowledgeFromJira")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDecisionKnowledgeElement() {
		if (JIRA_ISSUE_KEYS == null) {
			return Response.serverError().build();
		}
		String jsonString = JiraClient.instance.getDecisionKnowledgeFromJira(JIRA_ISSUE_KEYS);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
}
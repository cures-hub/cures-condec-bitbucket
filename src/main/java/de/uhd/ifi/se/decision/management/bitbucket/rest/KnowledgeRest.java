package de.uhd.ifi.se.decision.management.bitbucket.rest;

import static de.uhd.ifi.se.decision.management.bitbucket.merge.checks.HasDecisionKnowledgeCheck.JIRA_QUERY;
import static de.uhd.ifi.se.decision.management.bitbucket.merge.checks.HasDecisionKnowledgeCheck.PROJECT_KEY;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.ApiLinkService;
import de.uhd.ifi.se.decision.management.bitbucket.oauth.ApiLinkServiceImpl;

/**
 * REST resource: Enables importing decision knowledge from Jira
 */
@Path("/knowledge")
public class KnowledgeRest {

	@Path("/getDecisionKnowledgeFromJira")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDecisionKnowledgeElement() {
		if (JIRA_QUERY == null && PROJECT_KEY == null) {
			return Response.serverError().build();
		}
		String jsonString = ApiLinkService.instance.getDecisionKnowledgeFromJira(JIRA_QUERY, PROJECT_KEY);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
}
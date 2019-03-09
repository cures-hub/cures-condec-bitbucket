package de.uhd.ifi.se.decision.management.bitbucket.rest;


import de.uhd.ifi.se.decision.management.bitbucket.oAuth.ApiLinkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.uhd.ifi.se.decision.management.bitbucket.merge.checks.HasDecisionKnowledgeCheck.JIRA_QUERY;
import static de.uhd.ifi.se.decision.management.bitbucket.merge.checks.HasDecisionKnowledgeCheck.PROJECT_KEY;


@Path("/issueRest")
public class DecisionKnowledgeElementResource {

	@Path("/getIssuesFromJira")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getDecisionKnowledgeElement() {
		try {
			if ((JIRA_QUERY != null) && (PROJECT_KEY != null)) {
				String jsonString = ApiLinkService.getDecisionKnowledgeFromJira(JIRA_QUERY, PROJECT_KEY);
				return Response.status(Response.Status.OK).entity(jsonString).build();
			} else {
				return Response.serverError().build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
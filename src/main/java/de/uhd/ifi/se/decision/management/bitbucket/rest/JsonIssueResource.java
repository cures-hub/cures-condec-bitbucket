package de.uhd.ifi.se.decision.management.bitbucket.rest;


import de.uhd.ifi.se.decision.management.bitbucket.merge.checks.IsAdminMergeCheck;
import de.uhd.ifi.se.decision.management.bitbucket.oAuth.ApiLinkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.uhd.ifi.se.decision.management.bitbucket.merge.checks.IsAdminMergeCheck.jiraQuery;


@Path("/issueRest")
public class JsonIssueResource {

	@Path("/getIssuesFromJira")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getDecisionKnowledgeElement() {
		try {
			String jsonString= ApiLinkService.makeGetRequestToJira(jiraQuery);
			return Response.status(Response.Status.OK).entity(jsonString).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
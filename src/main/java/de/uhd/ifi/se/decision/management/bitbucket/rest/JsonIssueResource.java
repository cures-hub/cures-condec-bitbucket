package de.uhd.ifi.se.decision.management.bitbucket.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/issueRest")
public class JsonIssueResource {

	@Path("/getIssuesFromJira")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getDecisionKnowledgeElement() {
		try {
//			String jsonString=	de.uhd.ifi.se.decision.management.bitbucket.oAuth.ApiLinkService.makeGetRequestToJira(query, projectKey);
			String jsonString="[{\"id\":\"1\",\"text\":\"aasdfasdfsdf\"}]";
			return Response.status(Response.Status.OK).entity(jsonString).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
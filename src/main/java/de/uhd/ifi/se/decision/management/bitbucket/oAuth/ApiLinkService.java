package de.uhd.ifi.se.decision.management.bitbucket.oAuth;

import com.atlassian.applinks.api.*;
import com.atlassian.applinks.api.application.jira.JiraApplicationType;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.net.Request;
import com.atlassian.sal.api.net.Response;
import com.atlassian.sal.api.net.ResponseException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.*;


@Named("ComponentUtil")
public class ApiLinkService {

	@ComponentImport
	static private ApplicationLinkService applicationLinkService;

	@Inject
	public ApiLinkService(ApplicationLinkService oApplicationLinkService) {
		applicationLinkService = oApplicationLinkService;
	}

	static public String makeGetRequestToJira(String query) {
		//sanitise query
		String encodedQuery = encodeUserInputQuery(query);
		return getResponseFromJiraWithApplicationLink("rest/decisions/latest/decisions/getAllElementsMatchingQuery.json?resultType=ELEMENTS_QUERY_LINKED&query=" + encodedQuery);
	}

	private static String getResponseFromJiraWithApplicationLink(String jiraUrl) {
		String responseBody = "";
		try {
			ApplicationLink jiraApplicationLink = applicationLinkService.getPrimaryApplicationLink(JiraApplicationType.class);
			ApplicationLinkRequestFactory requestFactory = jiraApplicationLink.createAuthenticatedRequestFactory();
			ApplicationLinkRequest request = requestFactory.createRequest(Request.MethodType.GET,
				jiraUrl);
			request.addHeader("Content-Type", "application/json");

			responseBody = request.executeAndReturn(new ApplicationLinkResponseHandler<String>() {
				public String credentialsRequired(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}

				public String handle(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}
			});
		} catch (CredentialsRequiredException e) {
			responseBody=e.getMessage();
		} catch (ResponseException e) {
			responseBody=e.getMessage();
		}

		return responseBody;
	}

	public ApplicationLinkService getApplicationLinkService() {
		return applicationLinkService;
	}

	private static String encodeUserInputQuery(String query) {
		String encodedUrl = "";
		try {
			encodedUrl = URLEncoder.encode(query, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedUrl;

	}
}



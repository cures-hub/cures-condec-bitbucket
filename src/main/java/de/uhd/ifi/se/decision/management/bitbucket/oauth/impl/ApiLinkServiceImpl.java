package de.uhd.ifi.se.decision.management.bitbucket.oauth.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atlassian.applinks.api.ApplicationLink;
import com.atlassian.applinks.api.ApplicationLinkRequest;
import com.atlassian.applinks.api.ApplicationLinkRequestFactory;
import com.atlassian.applinks.api.ApplicationLinkResponseHandler;
import com.atlassian.applinks.api.ApplicationLinkService;
import com.atlassian.applinks.api.CredentialsRequiredException;
import com.atlassian.applinks.api.application.jira.JiraApplicationType;
import com.atlassian.sal.api.component.ComponentLocator;
import com.atlassian.sal.api.net.Request;
import com.atlassian.sal.api.net.Response;
import com.atlassian.sal.api.net.ResponseException;

import de.uhd.ifi.se.decision.management.bitbucket.oauth.ApiLinkService;

/**
 * Class responsible for the communication between Bitbucket and Jira via
 * application links.
 */
public class ApiLinkServiceImpl implements ApiLinkService {

	private ApplicationLink jiraApplicationLink;

	public ApiLinkServiceImpl() {
		ApplicationLinkService applicationLinkService = ComponentLocator.getComponent(ApplicationLinkService.class);
		// TODO
		// @issue There might be more than one application links to Jira. Currently, we
		// only support the first link. How can we support all links?
		this.jiraApplicationLink = applicationLinkService.getPrimaryApplicationLink(JiraApplicationType.class);
	}

	public Set<String> getCurrentActiveJiraProjects() {
		String projectsAsJsonString = getResponseFromJiraWithApplicationLink("rest/api/2/project");
		Set<String> projectKeys = new HashSet<String>();
		JSONArray projectArray = new JSONArray(projectsAsJsonString);
		for (Object project : projectArray) {
			JSONObject projectMap = (JSONObject) project;
			String projectKey = (String) projectMap.get("key");
			projectKeys.add(projectKey.toUpperCase());
		}
		return projectKeys;
	}

	private String getResponseFromJiraWithApplicationLink(String jiraUrl) {
		String responseBody = "false";
		if (jiraApplicationLink == null) {
			return responseBody;
		}
		try {
			ApplicationLinkRequestFactory requestFactory = jiraApplicationLink.createAuthenticatedRequestFactory();
			ApplicationLinkRequest request = requestFactory.createRequest(Request.MethodType.GET, jiraUrl);
			request.addHeader("Content-Type", "application/json");

			responseBody = request.executeAndReturn(new ApplicationLinkResponseHandler<String>() {
				public String credentialsRequired(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}

				public String handle(final Response response) throws ResponseException {
					return response.getResponseBodyAsString();
				}
			});
		} catch (CredentialsRequiredException | ResponseException e) {
			responseBody = e.getMessage();
		}
		return responseBody;
	}

	public String getDecisionKnowledgeFromJira(String query, String projectKey) {
		String encodedQuery = encodeUserInputQuery(query);
		return getResponseFromJiraWithApplicationLink(
				"rest/decisions/latest/decisions/getElements.json?allTrees=true&query=" + encodedQuery + "&projectKey="
						+ projectKey);
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

/*
 This module implements the communication with the ConDec Java REST API.

 Requires
 * nothing
    
 Is required by
 * condec.knowledge.overview.js
  
 Is referenced in HTML by
 * nowhere
 */
(function(global) {
	var ConDecAPI = function ConDecAPI() {
		this.restPrefix = AJS.contextPath() + "/rest/condec/latest";
	};

	/*
	 * external references: condec.knowledge.overview
	 */
	ConDecAPI.prototype.getDecisionKnowledgeFromJira = function getDecisionKnowledgeFromJira(callback) {
		console.log("conDecAPI getDecisionKnowledgeFromJira");
		var pullRequest = require("bitbucket/internal/model/page-state").getPullRequest();		
		console.log(pullRequest);

		var pullRequestId = pullRequest["id"];
		console.log(pullRequestId);
		var repositoryId =
			pullRequest.attributes.fromRef.attributes.repository.id;
		console.log(repositoryId);

		var url =
			this.restPrefix +
			"/knowledge/decisionKnowledgeFromJira?repositoryId=" +
			repositoryId +
			"&pullRequestId=" +
			pullRequestId;

		var response = getResponseAsReturnValue(url);
		console.log(response);
		return response;
	};

	function getResponseAsReturnValue(url) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, false);
		xhr.setRequestHeader(
			"Content-type",
			"application/json; charset=utf-8"
		);
		xhr.send();
		var status = xhr.status;
		if (status === 200) {
			try {
				console.log(xhr.response);
				var parsedResponse = JSON.parse(xhr.response);
				return parsedResponse;
			} catch (error) {
				console.log(error);
				return null;
			}
		}
		showFlag("error", xhr.response.error, status);
		return null;
	}

	function getJSON(url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, true);
		xhr.setRequestHeader(
			"Content-type",
			"application/json; charset=utf-8"
		);
		xhr.setRequestHeader("Accept", "application/json");
		xhr.responseType = "json";
		xhr.onload = function() {
			var status = xhr.status;
			if (status === 200) {
				callback(null, xhr.response);
			} else {
				showFlag("error", xhr.response.error, status);
				callback(status);
			}
		};
		xhr.send();
	}

	function showFlag(type, message, status) {
		if (status === null || status === undefined) {
			status = "";
		}
		AJS.flag({
			type: type,
			close: "auto",
			title:
				type.charAt(0).toUpperCase() +
				type.slice(1) +
				" " +
				status,
			body: message
		});
	}

	// export ConDecAPI
	global.conDecAPI = new ConDecAPI();
})(window);

/*
 This module implements the communication with the ConDec Java REST API.

 Requires
 * nothing
    
 Is required by
 * ???
  
 Is referenced in HTML by
 * nowhere
 */
(function(global) {

	var ConDecAPI = function ConDecAPI() {
		this.restPrefix = AJS.contextPath() + "/rest/condec/latest";
	};

	/*
	 * external references:
	 */
	ConDecAPI.prototype.getDecisionKnowledgeFromJira = function getDecisionKnowledgeFromJira(callback) {
		var url = this.restPrefix + "/knowledge/getDecisionKnowledgeFromJira";
		getJSON(
				url,
				function(error, decisionKnowledgeElements) {
					if (error === null) {
						callback(decisionKnowledgeElements);
					} else {
						showText("There seems to be a problem with the connection to Jira "
								+ "or none of the commit messages, branch-id or branch-title could be linked to a Jira issue.");
					}
				});
	};

	function showText(text) {
		$(".todo-list").replaceWith("<div class'todo-list'><p>" + text + "</p></div>");
	}

	function getJSON(url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, true);
		xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
		xhr.setRequestHeader("Accept", "application/json");
		xhr.onload = function() {
			var status = xhr.status;
			if (status === 200) {
				callback(null, xhr.responseText);
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
			type : type,
			close : "auto",
			title : type.charAt(0).toUpperCase() + type.slice(1) + " " + status,
			body : message
		});
	}

	// export ConDecAPI
	global.conDecAPI = new ConDecAPI();
})(window);
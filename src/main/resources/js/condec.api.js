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
	 * external references:
	 */
	ConDecAPI.prototype.getDecisionKnowledgeFromJira = function getDecisionKnowledgeFromJira(callback) {
		var url = this.restPrefix + "/knowledge/getDecisionKnowledgeFromJira";
		getJSON(url, function(error, knowledgeElements) {
			if (error === null) {
				callback(JSON.parse(knowledgeElements));
			} else {
				callback(null);
			}
		});
	};

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
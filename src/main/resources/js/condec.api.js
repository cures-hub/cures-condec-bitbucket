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
		return getResponseAsReturnValue(url);
	};

	function getResponseAsReturnValue(url) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, false);
		xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
		xhr.send();
		var status = xhr.status;
		if (status === 200) {
			try {
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
/**
 This module creates the decision knowledge overview that shows the related knowledge for the pull request.

 Requires
 * condec.api.js
 * condec.knowledge.overview.soy
    
 Is required by
 * atlassian-plugin.xml calls ConDecKnowledgeOverview.init in the client-web-panel
  
 Is referenced in HTML by
 * nowhere
 */
console.log("conDecKnowledgeOverview file");
var ConDecKnowledgeOverview = {
	/*
	 * Exposes the client-condition function. external references:
	 * client-web-panel in atlassian-plugin.xml
	 */
	init: function() {
		console.log("conDecKnowledgeOverview init");
		knowledgeElements = conDecAPI.getDecisionKnowledgeFromJira(
			null
		);
		console.log(knowledgeElements);
		if (knowledgeElements === null) {
			return {
				objects: null
			};
		}
		$("#showDecisionKnowledgeButton").click(function() {
			// Show dialog
			AJS.dialog2("#knowledge-overview-dialog").show();
		});
		$("#knowledge-overview-dialog-cancel-button").click(
			function() {
				AJS.dialog2("#knowledge-overview-dialog").hide();
			}
		);

		return {
			objects: knowledgeElements
		};
	}
};

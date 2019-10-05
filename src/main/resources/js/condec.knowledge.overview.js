/*
 This module creates the decision knowledge overview that shows the related knowledge for the pull request.

 Requires
 * condec.api.js
 * condec.knowledge.overview.soy
    
 Is required by
 * atlassian-plugin.xml calls ConDec.knowledgeOverview.init in the client-web-panel
  
 Is referenced in HTML by
 * nowhere
 */
(function($) {
	// Set up our namespace
	window.ConDec = window.ConDec || {};
	ConDec.knowledgeOverview = ConDec.knowledgeOverview || {};

	/*
	 * Exposes the client-condition function. external references:
	 * client-web-panel in atlassian-plugin.xml
	 */
	ConDec.knowledgeOverview.init = function init() {
		return {
			objects : []
		};
	};

	checkIfSoyTemplateIsRendered(0);

	function checkIfSoyTemplateIsRendered(iCounter) {
		// check if inital List exists
		if ($(".todo-list").length > 0) {
			getDecisionKnowledgeFromJira();
		} else {
			if (iCounter < 10)
				setTimeout(function() {
					// max 10 times, else an error occured
					checkIfSoyTemplateIsRendered(iCounter + 1);
				}, 3000);
		}
	}

	function getDecisionKnowledgeFromJira() {
		conDecAPI
				.getDecisionKnowledgeFromJira(function(knowledgeElements) {
					if (knowledgeElements === null) {
						showText("There seems to be a problem with the connection to Jira "
								+ "or none of the commit messages, branch-id or branch-title could be linked to a Jira issue.");
					}
					if (knowledgeElements.length === 0) {
						showText("None of the commit messages, branch-id or branch-title could be linked to a Jira issue.");
						return;
					}
					$(".todo-list").replaceWith(condec.todo.knowledgeElements({
						objects : knowledgeElements
					}));

					setEventListeners();
				});
	}

	function setEventListeners() {
		$("#showDecisionKnowledgeButton").click(function() {
			// Show dialog
			AJS.dialog2("#knowledge-overview-dialog").show();
		});

		$("#knowledge-overview-dialog-cancel-button").click(function() {
			AJS.dialog2("#knowledge-overview-dialog").hide();
		});
	}

	function showText(text) {
		$(".todo-list").replaceWith("<div class'todo-list'><p>" + text + "</p></div>");
	}

}(AJS.$));

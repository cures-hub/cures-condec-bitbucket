(function($) {
	// Set up our namespace
	window.UHD = window.UHD || {};
	UHD.ISSUES = UHD.ISSUES || {};

	/* Expose the client-condition function */
	function callInitIssues() {
		return {
			objects : []
		};
	}

	UHD.ISSUES.callInitIssues = callInitIssues;

	checkIfListIsLoaded(0);
	
	function showText(text) {
		$(".todo-list").replaceWith("<div class'todo-list'><p>" + text + "</p></div>");
	}

	function insertDecisionKnowledgeButton(oIssues) {
		$(".todo-list").replaceWith(
				"<button class='aui aui-button' id='showDecisionKnowledgeButton'>Show decision knowledge</button>");
		$("#showDecisionKnowledgeButton").click(function() {
			openDialog(oIssues);
		});
	}

	function openDialog(oIssues) {
		// Standard sizes are 400, 600, 800 and 960 pixels wide
		var dialog = new AJS.Dialog({
			width : 800,
			height : 500,
			id : "example-dialog",
			closeOnOutsideClick : true
		});
		dialog.addHeader("Decision Knowledge");
		dialog.addPanel("Panel 1", "<div id='decisionKnowledgeTableDiv'></div>", "panel-body");
		$("#decisionKnowledgeTableDiv").replaceWith(UHD.issues.issueList({
			objects : oIssues
		}));
		dialog.addButton("Close", function(dialog) {
			dialog.hide();
		});

		dialog.gotoPage(0);
		dialog.gotoPanel(0);
		dialog.show();
	}

	function getIssuesFromJira() {
		conDecAPI.getDecisionKnowledgeFromJira(function(data) {
			var oIssues = JSON.parse(data);
			if (oIssues.length === 0) {
				showText("None of the commit messages, branch-id or branch-title could be linked to a Jira issue.");
				return;
			}
			insertDecisionKnowledgeButton(oIssues);
		});
	}

	function checkIfListIsLoaded(iCounter) {
		// check if inital List exists
		if ($('.todo-list').length > 0) {
			getIssuesFromJira();
		} else {
			if (iCounter < 10)
				setTimeout(function() {
					// max 10 times, else an error occured
					checkIfListIsLoaded(iCounter + 1);
				}, 3000);
		}
	}

}(AJS.$));

(function ($) {
	// Set up our namespace
	window.UHD = window.UHD || {};
	UHD.ISSUES = UHD.ISSUES || {};

	/* Expose the client-condition function */
	function callInitIssues() {
		return {objects: []};
	}

	UHD.ISSUES.callInitIssues = callInitIssues;

	checkIfListIsLoaded(0);

	function getIssuesFromJira() {
		return {objects: [{id: 1, text: "lkj"}, {id: 2, text: "lkasdfj"}]};
	}

	function checkIfListIsLoaded(iCounter) {
		//check if inital List exists
		if ($('.todo-list').length > 0) {
			var issues = getIssuesFromJira();
			$('.todo-list').replaceWith(UHD.issues.issueList(issues));
		} else {
			if (iCounter < 10)
				setTimeout(function () {
					//max 10 times, else an error occured
					checkIfListIsLoaded(iCounter + 1);
				}, 3000);
		}
	}
}(AJS.$));

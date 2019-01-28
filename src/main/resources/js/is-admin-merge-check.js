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

	/*REST FUNCTIONS*/
	function getJSON(url, callback) {

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			if (this.readyState === 4 && this.status === 200) {
				callback(null, xhttp.responseText)
			}
		};
		xhttp.open("GET", url, true);
		xhttp.send();

	}

	function getIssuesFromJira() {
		var url = AJS.contextPath() + "/rest/jsonIssues/1.0/issueRest/getIssuesFromJira";
		getJSON(url, function (error, data) {
			if (error == null) {
				var oIssues = JSON.parse(data);
				if (oIssues.length > 0) {
					$('.todo-list').replaceWith(UHD.issues.issueList({objects: oIssues}));
				}
			}
		});
	}

	function checkIfListIsLoaded(iCounter) {
		//check if inital List exists
		if ($('.todo-list').length > 0) {
			getIssuesFromJira();
		} else {
			if (iCounter < 10)
				setTimeout(function () {
					//max 10 times, else an error occured
					checkIfListIsLoaded(iCounter + 1);
				}, 3000);
		}
	}

}(AJS.$));

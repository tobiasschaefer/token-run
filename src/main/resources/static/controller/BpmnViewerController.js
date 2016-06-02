(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', '$window', '$location', '$http', '$timeout', '$q', 'restUrlProcessDefinitions', function($scope, $window, $location, $http, $timeout, $q, restUrlProcessDefinitions) {

		var height = $window.innerHeight - 200;

		var BpmnNavigatedViewer  = window.BpmnJS;
		var viewer = new BpmnNavigatedViewer ({ container: angular.element('#js-canvas'), height });
		// connect to WebSocket
		var websocket = new WebSocket("ws://" + location.host +  "/webSocket");
		
		websocket.onopen = function(){  
	        console.log("WebSocket has been opened!");  
	    };
	    
	    $scope.currentPlayerName = "";
	    $scope.currentLevelScore = "";
	    
	    websocket.onmessage = function(message) {
	    	var data = JSON.parse(message.data);
	        console.log("Received data from websocket: ", data);
	        if(data.status == "started") {
	        	startTimer();
	        } else if(data.status == "stoped") {
	        	stopTimer();
	        	$scope.level.stopped = true;
	        }
	    };
		
		$scope.level = {
				key: "Test-Prozess",
				zeit: "00:00:00",
				instanceId: null,
				started: false,
				stopped: false
		};

		$scope.initLevel = function() {
			// 1. get process key from url parameters
			$scope.level.key = $location.search().processDefinitionKey;

			var url = restUrlProcessDefinitions + "/key/" + $scope.level.key + "/xml";

			// get xml from REST api
			$http({
				method: 'GET',
				url: url
			}).then(function successCallback(response) {
				viewer.importXML(response.data.bpmn20Xml, function(err) {
					if (err) {
						console.error(err);
					} else {
						viewer.get('canvas').zoom('fit-viewport');
					}
				});
			}, function errorCallback(response) {
				$scope.error = "TokenRun Error occured while accessing "+url+" - status: "+response.status;
			});
			
		}

		var today = new Date();
		var timeStart = null;
		var timeEnd = null;
		var tmPromise = null;
		
		$scope.startLevel = function() {
		
			sendRequest($scope.level.key);

			today = new Date();
			timeStart = today.getTime();
			
			// TODO: remove
			placeToken('UserTask_1');
		}

		$scope.abbruch = function() {
			$window.location.href="../";
		}

		$scope.stop = function() {
			stopTimer();
		}

		function checkTime(i) {
			i = (i < 1) ? 0 : i;
			if (i < 10) { i = "0" + i; }  // add zero in front of numbers < 10
			return i;
		}
		
		function startTimer() {

			var h, m, s, ms, today = new Date();
			// compute for the duration, 
			// normalize for the user
			timeEnd = today.getTime();
			ms = Math.floor((timeEnd - timeStart) / 1000);
			h =  checkTime(Math.floor(ms / 3600));
			ms = Math.floor(ms % 3600);
			m = checkTime(Math.floor(ms / 60));
			ms = Math.floor(ms % 60);
			s = checkTime(Math.floor(ms));
			// normalize time string
			$scope.level.zeit = h + ":" + m + ":" + s;

			// timer expired, restart timer
			tmPromise = $timeout(function () {
				startTimer();
			}, 500);
			
			$scope.level.started = true;
		}

		function stopTimer() {
			// stop timeout service
			$timeout.cancel(tmPromise);
			$scope.level.started = false;
		}
		
		function sendRequest(request) {
		      var defer = $q.defer();
		      console.log('Sending request', request);
		      websocket.send(JSON.stringify(request));
		      return defer.promise;
		    }
		
		function placeToken(id) {
			viewer.get('overlays').add(id, {
				position: {
					top: 0,
					left: 0
				},
				html: '<div style="width: 25px; height: 25px; border-radius: 50%; fill: #337ab7"></div>'
			});
		}
		
		$scope.openLevelScorePopup = function() {
			$('#levelscoreModal').modal('toggle');
		}
		
		$scope.commitLevelScoreAndClosePopup = function() {
			$http({
				method: 'POST',
				url: "/"+$scope.level.key+"/score",
				data: {
					playerName:$scope.currentPlayerName,
					time:$scope.currentLevelScore
				}
			}).then(function successCallback(response) {
				alert("your level score has been submitted!");
				//reset for next run
			    $scope.currentPlayerName = "";
			    $scope.currentLevelScore = "";
			    $('#levelscoreModal').modal('toggle');
			    
			}, function errorCallback(response) {
				alert("could not submit level score, please try again!");
				$('#levelscoreModal').modal('toggle');
			});
		}
		
		function removeToken(id) {
			overlays.remove(id);
		}
	}]);
}());
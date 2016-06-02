(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', '$window', '$location', '$http', '$timeout', 'restUrlProcessDefinitions', function($scope, $window, $location, $http, $timeout, restUrlProcessDefinitions) {

		var height = $window.innerHeight - 200;

		var BpmnNavigatedViewer  = window.BpmnJS;
		var viewer = new BpmnNavigatedViewer ({ container: angular.element('#js-canvas'), height });

		$scope.level = {
				key: "Test-Prozess",
				zeit: "00:00:00",
				instanceId: null,
				started: false
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

			// start process
			var url = restUrlProcessDefinitions + "key/" + $scope.level.key + "/start";
			$http({
				method: 'POST',
				url: url
			}).then(function successCallback(response) {
				$scope.level.instanceId = response.id;
			}, function errorCallback(response) {
				$scope.error = "TokenRun Error occured while accessing "+url+" - status: "+response.status;
			});

			today = new Date();
			timeStart = today.getTime();
			startTimer();
		}

		$scope.abbruch = function() {
			$window.location.href="index2.html";
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
	}]);
}());
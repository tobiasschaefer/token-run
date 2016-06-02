(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', '$window', '$location', '$http', '$timeout', '$q', '$modal', 'restUrlProcessDefinitions', function($scope, $window, $location, $http, $timeout, $q, $modal, restUrlProcessDefinitions) {

		var height = $window.innerHeight - 200;

		var BpmnNavigatedViewer  = window.BpmnJS;
		var viewer = new BpmnNavigatedViewer ({ container: angular.element('#js-canvas'), height });
		// connect to WebSocket
		var websocket = new WebSocket("ws://" + location.host +  "/webSocket");

		websocket.onopen = function(){  
			console.log("WebSocket has been opened!");  
		};

		websocket.onmessage = function(message) {
			var data = JSON.parse(message.data);
			console.log("Received data from websocket: ", data);
			if(data.status == "started") {
				startTimer();
			} else if(data.status == "completed") {
				stopTimer();
				$scope.level.completed = true;
				// open "finished"-popup
				$modal.open({
					templateUrl: 'FinishedLevelModal.html',
					controller: 'FinishedLevelModalController',
					size: 'sm',
					resolve: {
						processKey: function () {
							return $scope.level.key;
						},
						time: function () {
							return $scope.level.time;
						}
					}
				});
			} else if(data.status == "entered") {
				var humanTask = document.querySelector('[data-element-id="UserTask_1"]');
				humanTask.addEventListener('click', openModal);
			}
		};

		$scope.level = {
				key: "Test-Prozess",
				time: "00:00:00",
				instanceId: null,
				started: false,
				completed: false
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
						
						// for testing purposes of "human task"-popup
//						var humanTask = document.querySelector('[data-element-id="UserTask_1"]');
//						humanTask.addEventListener('click', openModal);
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

//			// start process
//			var url = restUrlProcessDefinitions + "/key/" + $scope.level.key + "/start";
//			$http({
//			method: 'POST',
//			url: url
//			}).then(function successCallback(response) {
//			$scope.level.instanceId = response.id;
//			}, function errorCallback(response) {
//			$scope.error = "TokenRun Error occured while accessing "+url+" - status: "+response.status;
//			});

			sendRequest($scope.level.key);

			today = new Date();
			timeStart = today.getTime();

			// TODO: remove
			placeToken('UserTask_1');
		}

		$scope.cancel = function() {
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
			$scope.level.time = h + ":" + m + ":" + s;

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

		function removeToken(id) {
			overlays.remove(id);
		}
		
		function openModal() {
			// TODO: get values for attributeList and key from data
			var attributeList = [{name: "Name"}, {name: "Vorname"}];
			var key = "UserTask_1";
			// open "human task"-popup
			$modal.open({
				templateUrl: 'HumanTaskModal.html',
				controller: 'HumanTaskModalController',
				size: 'lg',
				resolve: {
					key: function () {
						return key;
					},
					attributeList: function () {
						return attributeList;
					},
					websocket: function () {
						return websocket;
					},
					openModal: function () {
						return openModal;
					}
				}
			});
		}
	}]);
}());
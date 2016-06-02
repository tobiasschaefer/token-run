(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', '$window', '$location', '$http', 'restUrlProcessDefinitions', function($scope, $window, $location, $http, restUrlProcessDefinitions) {

		var height = $window.innerHeight - 200;
		
		var BpmnNavigatedViewer  = window.BpmnJS;
		var viewer = new BpmnNavigatedViewer ({ container: angular.element('#js-canvas'), height });

		$scope.level = {
				key: "Test-Prozess",
				zeit: "00:00:00",
				instanceId: null
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
			
			// TODO: start timer
			$scope.level.zeit = "00:00:01";
		}
		
		$scope.abbruch = function() {
			$window.location.href="index2.html";
		}
	}]);
}());
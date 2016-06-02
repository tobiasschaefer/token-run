(function(){
	'use strict';

	angular.module('TokenRunApp').controller('GameActionsController', ['restUrlProcessDefinitions', '$http', '$scope','$window','$timeout', function (restUrlProcessDefinitions, $http, $scope,$window,$timeout) {

		var defaultLevelSelection = {
				name:"choose level",
				id:-1
		};


		$scope.selectedProcessDefinition = defaultLevelSelection;
		$scope.levelHighscores = [];
		
		$scope.showLog = false;

		$scope.error = "";
		$scope.info = "";

		$scope.processDefinitions = [defaultLevelSelection];

		var resetStateInformation = function() {
			$scope.error = "";
			$scope.info = "";
		}
		
		var resetHighscores = function() {
			$scope.levelHighscores = [];
		}

		$scope.loadAvailableProcessDefinitions = function() {

			resetStateInformation();

			$http({
				method: 'GET',
				url: restUrlProcessDefinitions
			}).then(function successCallback(response) {
				console.debug("found "+response.data.length+" deployed process definitions");
				if(response.data.length>0) {
					for(var i in response.data) {
						$scope.processDefinitions.push({
							name:response.data[i].key,
							id:response.data[i].key
						});
					}
				} else {
					$scope.info="no level to play!";
				}
			}, function errorCallback(response) {
				$scope.error = "TokenRun Error occured while accessing "+restUrlProcessDefinitions+" - status: "+response.status;
			});
			
			$timeout(function() {
				$scope.showLog = true;
			}, 60000);
		}
		
		$scope.playLevel = function() {
			$window.location.href="BpmnViewer.html?processDefinitionKey="+$scope.selectedProcessDefinition.name;
		}
		
		$scope.showHighscoresForLevel = function() {
			
			resetStateInformation();
			resetHighscores();
			
			$http({
				method: 'GET',
				url: '/'+$scope.selectedProcessDefinition.name+'/scores/10'
			}).then(function successCallback(response) {
				console.log("found "+response.data.length+" level scores (max 10)");
				if(response.data.length>0) {
					$scope.levelHighscores = response.data;
					$('#highscoreModal').modal('toggle');
					
				} else {
					$scope.info = "No highscores for "+$scope.selectedProcessDefinition.name+" available";
				}
			}, function errorCallback(response) {
				$scope.error = "TokenRun Error occured while accessing "+restUrlProcessDefinitions+" - status: "+response.status;
			});
		}

	}]);
}());

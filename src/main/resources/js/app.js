var tokenRunApp  = angular.module('tokenRunApp', []);

tokenRunApp.constant('restUrlProcessDefinitions', '/api/engine/engine/default/process-definition');
tokenRunApp.controller('GameActionsController', ['restUrlProcessDefinitions', '$http', '$scope', function  GameActionsController(restUrlProcessDefinitions, $http, $scope) {

	var defaultLevelSelection = {
		  	name:"Level auswählen",
  			id:-1
  		};
	
  
  $scope.selectedProcessDefinition = defaultLevelSelection;
  
  $scope.error = "";
  $scope.info = "";

  $scope.processDefinitions = [defaultLevelSelection];
  
  var resetStateInformation = function() {
	  $scope.error = "";
	  $scope.info = "";
  }
  
  $scope.loadAvailableProcessDefinitions = function() {
	  
	  resetStateInformation();

	  $http({
		  method: 'GET',
		  url: restUrlProcessDefinitions
		}).then(function successCallback(response) {
		    console.log("found "+response.data.length+" deployed process definitions");
		    if(response.data.length>0) {
		    	for(var i in response.data) {
		    		$scope.processDefinitions.push({
		    			name:response.data[i].key,
		    			id:response.data[i].key
		    		});
		    	}
		    } else {
		    	$scope.info="Keine Level zum Spielen!";
		    }
		  }, function errorCallback(response) {
			$scope.error = "TokenRun Error occured while accessing "+restUrlProcessDefinitions+" - status: "+response.status;
		  });
  }
  
}]);

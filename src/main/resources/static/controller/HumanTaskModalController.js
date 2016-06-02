(function() {
	'use strict'

	angular.module('TokenRunApp').controller('HumanTaskModalController', ['$scope', '$modalInstance', '$window', 'key', 'attributeList', 'websocket', 'openModal', function($scope, $modalInstance, $window, key, attributeList, websocket, openModal) {

		$scope.init = function() {
			$scope.key = key;
			$scope.attributeList = attributeList;
			$scope.websocket = websocket;
		}

		$scope.confirm = function() {
			$modalInstance.close();
			sendRequest(attributeList);
			var humanTask = document.querySelector('[data-element-id="' + $scope.key + '"]');
			humanTask.removeEventListener('click', openModal);
		}

		function sendRequest(request) {
//			var defer = $q.defer();
			console.log('Sending request', request);
			websocket.send(JSON.stringify(request));
//			return defer.promise;
		}
	}]);
}());
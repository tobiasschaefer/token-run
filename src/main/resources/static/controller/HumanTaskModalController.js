(function() {
	'use strict'

	angular.module('TokenRunApp').controller('HumanTaskModalController', ['$scope', '$modalInstance', '$window', 'key', 'attributeList', 'websocket', 'openModal', function($scope, $modalInstance, $window, key, attributeList, websocket, openModal) {

		$scope.init = function() {
			$scope.key = key;
			$scope.attributeList = getList(attributeList);
			$scope.websocket = websocket;
		}

		$scope.confirm = function() {
			$modalInstance.close(getJSON($scope.attributeList));
			var humanTask = document.querySelector('[data-element-id="' + $scope.key + '"]');
			humanTask.removeEventListener('click', openModal);
		}

		function getList(json) {
			var list = [];
			angular.forEach(json, function(value, key) {
			  this.push({ name: key, value: value });
			}, list);
			return list;
		}
		
		function getJSON(list) {
			var json = {};
			angular.forEach(list, function(value, key) {
			  json[value.name] = value.value;
			});
			return json;
		}
	}]);
}());
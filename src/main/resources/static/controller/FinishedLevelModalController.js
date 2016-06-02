(function() {
	'use strict'

	angular.module('TokenRunApp').controller('FinishedLevelModalController', ['$scope', '$modalInstance', '$window', 'processKey', 'time', function($scope, $modalInstance, $window, processKey, time) {

		$scope.init = function() {
			$scope.level = {
					key: processKey,
					time: time
			}
		}

		$scope.cancel = function() {
			$modalInstance.close();
			$window.location.href="../";
		}
	}]);
}());
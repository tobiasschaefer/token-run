(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', '$window', function($scope, $window) {

		var height = $window.innerHeight - 200;
		
		var BpmnNavigatedViewer  = window.BpmnJS;
		var viewer = new BpmnNavigatedViewer ({ container: angular.element('#js-canvas'), height });

		var url = "../ressources/test-process.xml";
		
		$scope.level = {
				name: "Test-Prozess",
				zeit: "00:00:00"
		};
		
		$.ajax(url, { dataType : 'text' }).done(function(xml) {
			viewer.importXML(xml, function(err) {
				if (err) {
					console.error(err);
				} else {
					viewer.get('canvas').zoom('fit-viewport');
				}
			});
		});
		
		$scope.startLevel = function() {
			// TODO: xml aus REST-API holen
			// TODO: Prozess starten
			// TODO: Zeit starten
			$scope.level.zeit = "00:00:01";
		}
		
		$scope.abbruch = function() {
			// TODO: zurück auf Startseite
		}
	}]);
}());
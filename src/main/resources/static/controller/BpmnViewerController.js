(function(){
	'use strict';

	angular.module('TokenRunApp').controller('BpmnViewerController', ['$scope', function($scope) {

		var BpmnViewer = window.BpmnJS;
		var viewer = new BpmnViewer({ container: angular.element('#js-canvas'), height: 600 });

		var url = "../ressources/test-process.xml";
		
		$.ajax(url, { dataType : 'text' }).done(function(xml) {
			viewer.importXML(xml, function(err) {
				if (err) {
					console.error(err);
				} else {
					viewer.get('canvas').zoom('fit-viewport');
				}
			});
		});
	}]);
}());
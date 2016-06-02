(function(){
	'use strict';

	angular.module('TokenRunApp', [])
	.config(function($locationProvider) {
		$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});
	}) 
	.constant('restUrlProcessDefinitions', '/rest/process-definition');

//	hier können App-weite Einstellungen vorgenommen werden, indem Konstanten mit .const gesetzt werden

}());
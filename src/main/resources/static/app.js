(function(){
	'use strict';

	angular.module('TokenRunApp', ['ui.bootstrap'])
	.config(function($locationProvider) {
		$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});
	}) 
	.constant('restUrlProcessDefinitions', '/rest/process-definition?latestVersion=true');

}());
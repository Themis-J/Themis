'use strict';

angular.module('masterApp', 
	['ngRoute', 
		'masterApp.config', 
		'masterApp.filters', 
		'masterApp.services', 
		'masterApp.directives', 
		'masterApp.controllers', 
		'overallAbs.controllers', 
		'overallPercentage.controllers', 
		'departmentOp.controllers', 
		'departmentAbs.controllers', 
		'departmentOpComp.controllers', 
		'departmentPercentage.controllers']).
    config(['$routeProvider', function($routeProvider) {
    	$routeProvider.when('/report', {templateUrl: 'partials/master/edit.html', controller: 'editCtrl'});
        $routeProvider.otherwise({redirectTo: '/report'});
    }]);

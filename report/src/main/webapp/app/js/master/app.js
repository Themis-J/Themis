'use strict';

angular.module('masterApp', ['ngRoute', 'master.config', 'masterApp.filters', 'masterApp.services', 'masterApp.directives', 'masterApp.controllers', 'overallAbs.controllers', 'overallPercentage.controllers', 'departmentAbs.controllers']).
    config(['$routeProvider', function($routeProvider) {
    	//$routeProvider.when('/report', {templateUrl: 'partials/master/overallAbs.html', controller: 'editCtrl'});
    	$routeProvider.when('/report', {templateUrl: 'partials/master/edit.html', controller: 'editCtrl'});
        $routeProvider.otherwise({redirectTo: '/report'});
    }]);


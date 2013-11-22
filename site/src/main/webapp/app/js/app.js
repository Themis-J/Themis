'use strict';
angular.module('themis', ['ngRoute', 'themis.directives', 'themis.controllers', 'themis.services', 'themis.config', 'guest.controllers', 'ngSanitize',
        'branch.services', 'branch.controllers', 'lirun.controller', 'jingying.controller','fenhong.controller', 'sunyi.controller', 'kucun.controller', 'zhangkuan.controller', 'renyuan.controller', 'tax.controller',
        'masterApp.config', 'masterApp.services','masterApp.controllers', 
        'keyNetProfit.controllers', 
        'keyOpProfit.controllers', 
        'keyOpProfitPerRevenue.controllers', 
        'overallAbs.controllers', 
        'overallRevenue.controllers', 
        'overallMargin.controllers', 
		'overallPercentage.controllers', 
		'overallExpPercentage.controllers', 
		'overallHRAnalysis.controllers', 
		'departmentOp.controllers', 
		'departmentAbs.controllers', 
		'departmentOpComp.controllers', 
		'departmentPercentage.controllers',
		'salesAbs.controllers',
		'salesDepartmentAbs.controllers',
		'salesPercentage.controllers',
		'salesDepartmentPercentage.controllers',
        'admin.controllers']).
    config(['$routeProvider', function($routeProvider) {
    	$routeProvider.when('/head/report', {templateUrl: 'partials/head/edit.html', controller: 'headEditCtrl'});
    	//$routeProvider.when('/head/report', {templateUrl: 'partials/admin/admin.html', controller: 'adminCtrl'});
        $routeProvider.otherwise({redirectTo: '/head/report'});
    }]);
 
'use strict';

/* Services */
angular.module('masterApp.services', ['ngResource'])
	.factory('ReportRestClient', ['$resource', 'config', function ($resource, config) {
        return function(mode) {
        	var services = {
	        	remote: $resource(
		        	config.service.url + '/:path/:subpath', {}, {
		            queryOverallIncomeReport: {method: 'GET', params: {path: 'query', subpath:'overallIncomeReport'}, isArray: false},
		            queryOverallPercentageIncomeReport: {method: 'GET', params: {path: 'query', subpath:'overallIncomeReport'}, isArray: false},
		            queryDepartments: {method: 'GET', params: {path: '', subpath:'department'}, isArray: false},
		        }),
	        	local: $resource(
		        	config.service.localUrl + '/:path/:subpath', {}, {
		            queryOverallIncomeReport: {method: 'GET', params: {path: '', subpath:'queryYearlyIncomeReport.json'}, isArray: false},
		            queryOverallPercentageIncomeReport: {method: 'GET', params: {path: '', subpath:'queryYearlyPercentageIncomeReport.json'}, isArray: false},
		            queryDepartments: {method: 'GET', params: {path: '', subpath:'departments.json'}, isArray: false},
		        })
	        };
	        if ( mode == 'local' ) {
	        	return services.local;
	        }
        	return services.remote;
        };
    }
    ]).
    factory('ReportService', ['config', function(config){
    	var monthOfYear = -1;
    	var currentYear = -1;
    	var charts = [];
    	var isFullScreen = false;
        return {
        	setCurrentYear: function(year) {
        		currentYear = year;
        	},
        	getCurrentYear: function() {
        		return currentYear;
        	},
        	setMonthOfYear: function(month){
        		monthOfYear = month;
        	},
        	getMonthOfYear: function() {
        		return monthOfYear;
        	},
        	setFullScreen: function(flag){
        		isFullScreen = flag;
        	},
        	getFullScreen: function() {
        		return isFullScreen;
        	},
        	getDepartments: function(restClient, params, callback) {
        		restClient(params, function(data) {
        			var departments = [];
        			for ( var i = 1; i < data.items.length; i++ ) {
        				departments[i-1]=data.items[i];
        			}
        			callback(departments);
        		});
        	},
        	getMonthList: function() {
        		var months = [];
        		for (var i=0;i<12;i++) 
		  		{
		  			months[i] = {name: (i+1) + '月', id: i+1};
		  		}
		  		return months;
        	}, 
        	getYearList: function() {
        		var currentDate = new Date();
        		var years = [];
		  		var j = 0;
		  		for (var i=currentDate.getFullYear();i>1980;i--) 
		  		{
		  			years[j++] = {name: i + '年', id: i};
		  		}
		  		return years;
        	},
        };
    }])
    ;

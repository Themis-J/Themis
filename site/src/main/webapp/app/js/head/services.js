'use strict';

angular.module('masterApp.services', ['ngResource'])
	.factory('ReportRestClient', ['$resource', 'config', function ($resource, config) {		
		return function(mode) {
        	var services = {
	        	remote: $resource(
		        	config.service.url + '/:root/:path/:subpath/:subpath2', {}, {
		            queryOverallIncomeReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'overallIncomeReport', subpath2:''}, isArray: false},
		            queryOverallPercentageIncomeReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'overallIncomeReport', subpath2:''}, isArray: false},
		            queryDepartmentIncomeReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'departmentIncomeReport', subpath2:''}, isArray: false},
		            queryDealerSalesReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'salesReport', subpath2:''}, isArray: false},
		            queryDepartments: {method: 'GET', params: {root:'dealer', path: 'department', subpath:'', subpath2:''}, isArray: false},
		            queryDealers: {method: 'GET', params: {root:'dealer', path: 'list', subpath:'', subpath2:''}, isArray: false},
		            queryGeneralJournalItems: {method: 'GET', params: {root:'dealer', path: 'generalJournal', subpath:'items', subpath2:''}, isArray: false},
		            queryOverallExpPercentageReport: {method: 'GET', params: {root: 'report', path: 'query', subpath:'overallExpensePercentageReport', subpath2:''}, isArray: false},
		            queryDepartmentIncomePercentageReport: {method: 'GET', params: {root: 'report', path: 'query', subpath:'departmentIncomeReport', subpath2:''}, isArray: false},
		            queryDealerSalesPercentgeReport: {method: 'GET', params: {root: 'report', path: 'query', subpath:'salesReport', subpath2:''}, isArray: false},
		            queryPositions: {method: 'GET', params: {root:'dealer', path: 'hr', subpath:'allocation', subpath2:'items'}, isArray: false},
		            queryOverallHRAllocReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'overallHRAllocReport', subpath2:''}, isArray: false},
		        }),
	        	local: $resource(
		        	config.service.localUrl + '/:path/:subpath', {}, {
		            queryOverallIncomeReport: {method: 'GET', params: {path: '', subpath:'queryYearlyIncomeReport.json'}, isArray: false},
		            queryOverallPercentageIncomeReport: {method: 'GET', params: {path: '', subpath:'queryYearlyPercentageIncomeReport.json'}, isArray: false},
		            queryDepartmentIncomeReport: {method: 'GET', params: {path: '', subpath:'queryDepartmentIncomeReport.json'}, isArray: false},
		            queryDealerSalesReport: {method: 'GET', params: {path: '', subpath:'queryDealerSalesReport.json'}, isArray: false},
		            queryDepartments: {method: 'GET', params: {path: '', subpath:'departments.json'}, isArray: false},
		            queryDealers: {method: 'GET', params: {path: '', subpath:'dealers.json'}, isArray: false},
		            queryGeneralJournalItems: {method: 'GET', params: {path: '', subpath:'expenseItems.json'}, isArray: false},
		            queryOverallExpPercentageReport: {method: 'GET', params: {path: '', subpath:'queryOverallExpPercentageReport.json'}, isArray: false},
		            queryDepartmentIncomePercentageReport: {method: 'GET', params: {path: '', subpath:'queryDepartmentIncomePercentageReport.json'}, isArray: false},
		            queryDealerSalesPercentgeReport: {method: 'GET', params: {path: '', subpath:'queryDealerSalesPercentageReport.json'}, isArray: false},
		            queryPositions: {method: 'GET', params: {path: '', subpath:'positions.json'}, isArray: false},
		            queryOverallHRAllocReport: {method: 'GET', params: {path: '', subpath:'queryOverallHRAllocReport.json'}, isArray: false},
		        })
	        };
	        if ( mode == 'local' ) {
	        	return services.local;
	        }
        	return services.remote;
        };
    }
    ]).
    factory('ReportService', [function(){
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
        			var j = 0;
        			for ( var i = 0; i < data.items.length; i++) {
        				if (data.items[i].id != 0) {
        					departments[j++]=data.items[i];        					
        				}
        			}
        			callback(departments);
        		});
        	},
        	getDepartments2: function(restClient, params, callback) {
        		restClient(params, function(data) {
        			var departments = [];
        			var j = 0;
        			for ( var i = 0; i < data.items.length; i++) {
        				if (data.items[i].id != 0) {
        					departments[j++]=data.items[i];        					
        				} else {
        					departments[j++]={id: 0, name:'全部门'};
        				}
        			}
        			callback(departments);
        		});
        	},
        	getDealers: function(restClient, params, callback) {
        		restClient(params, function(data) {
        			callback(data.items);
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
        	getGeneralJournalItems: function(restClient, params, callback) {
        		restClient.queryGeneralJournalItems(params, function(data) {
        			var items = [];
        			for ( var i = 0; i < data.items.length; i++) {
        				items[i]=data.items[i];        					
        			}
        			callback(items);
        		});
        	},
        	getPositions: function(restClient, params, callback) {
        		restClient.queryPositions(params, function(data) {
        			var items = [];
        			var j = 0;
        			items[j++] = {id:0, name:'全职位'};
        			for ( var i = 0; i < data.items.length; i++) {
        				items[j++]=data.items[i];        					
        			}
        			callback(items);
        		});
        	},
        };
    }])
    ;

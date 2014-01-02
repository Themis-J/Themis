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
		            importData: {method: 'POST', params: {root:'report', path: 'import', subpath:'', subpath2:''}, isArray: false},
		            queryOverallIncomeWithGroupByReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'overallIncomeReport', subpath2:''}, isArray: false},
		            queryAccountReceivableReport: {method: 'GET', params:  {root:'report', path: 'query', subpath:'accountReceivableReport', subpath2:''}, isArray: false},
		            queryAccountReceivablePercentageReport: {method: 'GET', params:  {root:'report', path: 'query', subpath:'accountReceivablePercentageReport', subpath2:''}, isArray: false},
		            queryPostSalesOverallPercentageIncomeReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'postSalesOverallIncomeReport', subpath2:''}, isArray: false},
		            queryNonRecurrentPNLReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'nonRecurrentPNLReport', subpath2:''}, isArray: false},
		            queryNonSalesProfitReport: {method: 'GET', params: {root:'report', path: 'query', subpath:'nonSalesProfitReport', subpath2:''}, isArray: false},
		            queryPostSalesIncomeReport: {method: 'GET', params: {root: 'report', path: 'query', subpath: 'postSalesIncomeReport', subpath2: ''}, isArray: false},
		            queryPostSalesExpenseReport: {method: 'GET', params: {root: 'report', path: 'query', subpath: 'postSalesExpenseReport', subpath2: ''}, isArray: false},
		            queryMaintenanceIncomeReport: {method: 'GET', params: {root: 'report', path: 'query', subpath: 'maintenanceIncomeReport', subpath2: ''}, isArray: false},
		            querySparePartIncomeReport: {method: 'GET', params: {root: 'report', path: 'query', subpath: 'sparePartIncomeReport', subpath2: ''}, isArray: false},
		            querySheetSprayIncomeReport: {method: 'GET', params: {root: 'report', path: 'query', subpath: 'sheetSprayIncomeReport', subpath2: ''}, isArray: false}
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
		            importData: {method: 'POST', params: {path:'', subpath:'queryOverallHRAllocReport.json'}, isArray: false},
		            queryOverallIncomeWithGroupByReport: {method: 'GET', params: {path: '', subpath:'queryYearlyIncomeWithGroupByReport.json'}, isArray: false},
		            queryAccountReceivableReport: {method: 'GET', params: {path: '', subpath:'queryAccountReceivableReport.json'}, isArray: false},
		            queryAccountReceivablePercentageReport: {method: 'GET', params: {path: '', subpath:'queryAccountReceivablePercentageReport.json'}, isArray: false},
		            queryPostSalesOverallPercentageIncomeReport: {method: 'GET', params: {path: '', subpath:'queryPostSalesOverallPercentageIncomeReport.json'}, isArray: false},
		            queryNonRecurrentPNLReport: {method: 'GET', params: {path: '', subpath:'queryNonRecurrentPNLReport.json'}, isArray: false},
		            queryNonSalesProfitReport: {method: 'GET', params: {path: '', subpath:'queryNonSalesProfitReport.json'}, isArray: false},
		            queryPostSalesIncomeReport: {method: 'GET', params: {path: '', subpath: 'queryMonthlyPostSalesIncomeReport.json'}, isArray: false},
		            queryPostSalesExpenseReport: {method: 'GET', params: {path: '', subpath: 'queryMonthlyPostSalesExpenseReport.json'}, isArray: false},
		            queryMaintenanceIncomeReport: {method: 'GET', params: {path: '', subpath: 'queryMonthlyMaintenanceIncomeReport.json'}, isArray: false},
		            querySparePartIncomeReport: {method: 'GET', params: {path: '', subpath: 'queryMonthlySparePartIncomeReport.json'}, isArray: false},
		            querySheetSprayIncomeReport: {method: 'GET', params: {path: '', subpath: 'queryMonthlySheetSprayIncomeReport.json'}, isArray: false}
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
    	var isShowChart = false;
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
        	setShowChart: function(flag){
        		isShowChart = flag;
        	},
        	getShowChart: function() {
        		return isShowChart;
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
        	getPostSalesDepartments: function(restClient, params, callback) {
        		restClient(params, function(data) {
        			var departments = [];
        			var j = 0;
        			for ( var i = 0; i < data.items.length; i++) {
        				if (data.items[i].name == '维修部' || data.items[i].name == '备件部' || data.items[i].name == '钣喷部') {
        					departments[j++]=data.items[i];        					
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
        	setPrintGrid: function (gid,pid,pgTitle){
		        // print button title.
		        var btnTitle = 'Print Grid';
		        // setup print button in the grid top navigation bar.
		        $('#'+gid).jqGrid('navSeparatorAdd','#'+gid+'_toppager_left', {sepclass :'ui-separator'});
		        $('#'+gid).jqGrid('navButtonAdd','#'+gid+'_toppager_left', {caption: '', title: btnTitle, position: 'last', buttonicon: 'ui-icon-print', onClickButton: function() {    PrintGrid();    } });
		    
		        // setup print button in the grid bottom navigation bar.
		        $('#'+gid).jqGrid('navSeparatorAdd','#'+pid, {sepclass : "ui-separator"});
		        $('#'+gid).jqGrid('navButtonAdd','#'+pid, {caption: '', title: btnTitle, position: 'last', buttonicon: 'ui-icon-print', onClickButton: function() { PrintGrid();    } });
		    	
		        function PrintGrid(){
		        	// attach print container style and div to DOM.
		            $('head').append('<style type="text/css">.prt-hide {display:none;}</style>');
		            $('body').append('<div id="prt-container" class="prt-hide"></div>');
		    
		            // copy and append grid view to print div container.
		            $('#gview_'+gid).clone().appendTo('#prt-container').css({'page-break-after':'auto'});
		            // remove navigation divs.
		            $('#prt-container div').remove('.ui-jqgrid-toppager,.ui-jqgrid-titlebar,.ui-jqgrid-pager');
		            // print the contents of the print container.    
		            $('#prt-container').printElement({printMode:'popup', pageTitle:pgTitle, overrideElementCSS:[{href:'css/print-container.css',media:'print'}]});
		            
		            // remove print container style and div from DOM after printing is done.
		            $('head style').remove();
		            $('body #prt-container').remove();
		            
		        }
		    }
        };
    }])
    ;

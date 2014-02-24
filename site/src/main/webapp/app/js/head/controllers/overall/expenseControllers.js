'use strict';

angular.module('overallExpense.controllers', [])
	.controller('overallExpenseCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
		/**
		 * Global functions
		 */
		$scope.selectReportYear = function() {
    		reportService.setCurrentYear($scope.selectedYearOption.id);
    		$scope.showReport();
    	};
    	
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), groupBy:0};
        	$scope.draw(restClient(config.currentMode).queryOverallIncomeWithGroupByReport, params);
        };
        var chartData = {
				        		id: 'report_chart',
				        		title: '总费用',
				        		yAxisTitle: '总费用',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[] },
				        		gridData:[]
				        	}; 
        $scope.draw = function (restClient, params) {
	        	Highcharts.theme = config.highChartsTheme;
				Highcharts.setOptions(Highcharts.theme); 
	 			
	            restClient(params, function(data) {
	            	
	            	var chartCategories = [{ categories: null }];
	            	var dealers = [];
	            	chartCategories[0].categories = dealers;
					var currentDetail = data.detail[0].detail;
					var j = 0;
					for (var i=0; i<currentDetail.length; i++) {
						chartData.series.current[i] = currentDetail[i].expense.amount;
	            		chartData.series.currentPercentage[i] = currentDetail[i].expense.percentage * 100;
	            		chartData.series.currentReference[i] = currentDetail[i].expense.reference;
	            		
	            		for ( var k=0; k< currentDetail[i].detail.length; k++ ) {
	            			if ( currentDetail[i].detail[k].name != 'NA' &&  
	            					currentDetail[i].detail[k].name != '二手车部' && 
	            						currentDetail[i].detail[k].name != '其它部' &&
	            						currentDetail[i].detail[k].name != '租赁事业部' ) {
			            		chartData.gridData[j] = {id:null, departmentName:null, name:null, amount:null, totalAmount:null};
			            		chartData.gridData[j].id = currentDetail[i].code;
			            		chartData.gridData[j].name = currentDetail[i].name;
			            		chartData.gridData[j].departmentName = currentDetail[i].detail[k].name;
			            		chartData.gridData[j].amount = currentDetail[i].detail[k].expense.amount.toFixed(2);
			            		chartData.gridData[j].totalAmount = currentDetail[i].expense.amount.toFixed(2);
			            		j++;
		            		}
	            		}
	            	};
	            	
	            	var chartSubtitle = '年度对比';
	            	var chartColumnCurrent = '今年';
	            	var chartColumnCurrentRef = '今年参考值';
			        var chartWidth = $(window).width() * 0.60;
			        if ( reportService.getFullScreen() ) {
			        	chartWidth = $(window).width() * 0.90;
					}
			        var currentData = chartData;
			        jQuery("#report_list").jqGrid("GridUnload");
			        jQuery("#report_list").jqGrid({
					   	data:chartData.gridData,
						datatype: "local",
					   	colNames:['部门','经销商代码', '名称', '部门总费用', '经销商总费用'],
					   	colModel:[
					   		{name:'departmentName',index:'departmentName', width:55},
					   		{name:'id',index:'id', width:55},
					   		{name:'name',index:'name', sorttype: function (cellValus, rowData) {return (rowData.totalAmount + rowData.id).lpad('0', 100);}, width:100},
					   		{name:'amount',index:'amount', width:80, formatter:"number", sorttype:"float", align:"right", summaryType: "sum"},
					   		{name:'totalAmount',index:'totalAmount', formatter:"number", width:80, sorttype:"float", align:"right"}		
					   	],
					   	rowNum:300,
					   	pager: '#report_pager',
					   	loadError: function(xhr,status, err) { 
					   		try {
					   			jQuery.jgrid.info_dialog(jQuery.jgrid.errors.errcap,'<div class="ui-state-error">'+ xhr.responseText +'</div>', jQuery.jgrid.edit.bClose,{buttonalign:'right'});
					   		} catch(e) { 
					   			alert(xhr.responseText);
					   		}}, 
					   	viewrecords: true,
					    sortable: false,
						multiselect: false,
						width: chartWidth,
						height: "100%",
						grouping:true,
					   	groupingView : {
					   		groupField : ['name'],
					   		groupSummary : [true],
					   		groupColumnShow : [true],
					   		groupText : ['<b>{0}</b>'],
					   		groupCollapse : false,
					   		showSummaryOnHide: true,
							groupOrder: ['desc']
					   	},
						caption: "本年总费用",
						sortname : "departmentName"
					});
					jQuery("#report_list").jqGrid('navGrid','#report_pager',{"edit":false,"add":false,"del":false,"search":true,"refresh":true,"view":false,"excel":false,"pdf":false,"csv":false,"columns":false});
					
			  });
		};
		
        $scope.toggleFullScreen = function() {
            if (reportService.getFullScreen()) {
                $('#container_div').addClass('container');
                $('#container_div').removeClass('row-fluid');
                $("#header").removeClass('hide');
                $("#nav_div").removeClass('hide');
                $("#report_div").removeClass('span12');
                $("#report_div").addClass('span9');
                reportService.setFullScreen(false);
                $scope.showReport();
                
            } else {
                $('#container_div').removeClass('container');
                $('#container_div').addClass('row-fluid');
                $("#header").addClass('hide');
                $("#nav_div").addClass('hide');
                $("#report_div").removeClass('span9');
                $("#report_div").addClass('span12');
                reportService.setFullScreen(true);
                $scope.showReport();
               
            }
        };
		
		/**
		 * Global variables
		 */
		reportService.setFullScreen(false);
        $scope.setupReportDate();

		// called on page is loaded
		$scope.showReport();
  }]);

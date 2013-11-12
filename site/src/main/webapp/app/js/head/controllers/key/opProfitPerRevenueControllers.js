'use strict';

angular.module('keyOpProfitPerRevenue.controllers', [])
	.controller('keyOpProfitPerRevenueCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
		/**
		 * Global functions
		 */
		$scope.selectReportYear = function() {
    		reportService.setCurrentYear($scope.selectedYearOption.id);
    		$scope.showReport();
    	};
    	$scope.selectReportMonth = function() {
    		reportService.setCurrentMonth($scope.selectedMonthOption.id);
    		$scope.showReport();
    	};
    	
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), monthOfYear: reportService.getMonthOfYear(), denominator: 0};
        	$scope.draw(restClient(config.currentMode).queryOverallPercentageIncomeReport, params);
        };
        
        $scope.draw = function (restClient, params) {
	        	Highcharts.theme = config.highChartsTheme;
				Highcharts.setOptions(Highcharts.theme); 
	 			
	            restClient(params, function(data) {
	            	var chartData = {
				        		id: 'report_chart',
				        		title: '运营利润/销售金额',
				        		yAxisTitle: '运营利润/销售金额',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[] },
				        		gridData:[]
				        	}; 
	            	var chartCategories = [{ categories: null }];
	            	var dealers = [];
	            	var previousDetail = data.detail[0].detail;
	            	for ( var i in previousDetail ) {
	            		dealers[i] = previousDetail[i].code;
	            		chartData.series.previous[i] = previousDetail[i].opProfit.amount;
	            		chartData.series.previousReference[i] = previousDetail[i].opProfit.reference;
	            		chartData.gridData[i] = {id:null, name:null, amount:null};
	            		chartData.gridData[i].id = previousDetail[i].code;
	            		chartData.gridData[i].name = previousDetail[i].name;
	            		chartData.gridData[i].amount = previousDetail[i].opProfit.amount * 100;
	            	};
	            	
					chartCategories[0].categories = dealers;
					var currentDetail = data.detail[1].detail;
					for ( var i in currentDetail ) {
	            		chartData.series.current[i] = currentDetail[i].opProfit.amount;
	            		chartData.series.currentPercentage[i] = currentDetail[i].opProfit.percentage * 100;
	            		chartData.series.currentReference[i] = currentDetail[i].opProfit.reference;
	            	};
	            	var chartSubtitle = '年度对比';
	            	var chartColumnPrevious = '去年';
	            	var chartColumnCurrent = '今年';
	            	var chartColumnPreviousRef = '去年参考值';
	            	var chartColumnCurrentRef = '今年参考值';
			        var chartWidth = $(window).width() * 0.60;
			        if ( reportService.getFullScreen() ) {
			        	chartWidth = $(window).width() * 0.90;
					}
			        var currentData = chartData;
			        
			        jQuery("#report_list").jqGrid({
					   	data:chartData.gridData,
						datatype: "local",
					   	colNames:['经销商代码','名称', '百分比（％）'],
					   	colModel:[
					   		{name:'id',index:'id', width:55},
					   		{name:'name',index:'name', width:100},
					   		{name:'amount',index:'amount', width:80, sorttype:"float", formatter:"number", align:"right"}	
					   	],
					   	rowNum:30,
					   	pager: '#report_pager',
					   	sortname: 'amount',
					    viewrecords: true,
					    sortorder: "desc",
						multiselect: false,
						width: chartWidth,
						height: "100%",
						caption: "月均运营利润占销售金额百分比"
					});
					/*
					$('#report_chart').highcharts({
			                chart: {
			                	zoomType: 'xy',
			                    height:$(window).height()*0.60,
			                    width: chartWidth
			                },
			                title: {
			                    text: currentData.title
			                },
			                subtitle: {
			                    text: chartSubtitle
			                },
			                xAxis: chartCategories,
			                yAxis: [{
			                    title: {
			                        text: currentData.yAxisTitle
			                    },
			                    min:-10000
			                },
		                    {
		                        gridLineWidth: 0,
		                        title: {
		                            text: '增长百分比 (%)'
		                        },
		                        labels: {
		                            formatter: function() {
		                                return this.value +' %';
		                            }
		                        }
		                        ,
		                        opposite: true
		                    }
							],
			                tooltip: {
			                    formatter: function() {
			                        var tooltip = this.series.name +': '+ this.y +'<br/>';
			                        return  tooltip;
			                    },
			                    useHTML: true
			                },
			                plotOptions: {
			                    column: {
			                    	cursor: 'pointer'
			                    }
			                },
			                series: [
			                    {
			                        type: 'column',
			                        name: chartColumnPrevious,
			                        data: currentData.series.previous
			                    },
			                    {
			                        type: 'column',
			                        name: chartColumnCurrent,
			                        data: currentData.series.current
			                    },
			                    {
			                        type: 'spline',
			                        name: chartColumnPreviousRef,
			                        data: currentData.series.previousReference
			                    },
			                    {
			                        type: 'spline',
			                        name: chartColumnCurrentRef,
			                        data: currentData.series.currentReference
			                    },
			                    {
			                        type: 'spline',
			                        name: '增长比例(%)',
			                        yAxis: 1,
			                        data: currentData.series.currentPercentage
			                    }
			                ]
			        	});*/
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
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
		reportService.setMonthOfYear(currentDate.getMonth());
  		$scope.yearOptions = reportService.getYearList();
		$scope.selectedYearOption = $scope.yearOptions[0];
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];

		// called on page is loaded
		$scope.showReport();
  }]);

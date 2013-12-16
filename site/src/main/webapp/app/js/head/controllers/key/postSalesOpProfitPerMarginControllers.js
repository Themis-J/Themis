'use strict';

angular.module('keyPostSalesOpProfitPerMargin.controllers', [])
	.controller('keyPostSalesOpProfitPerMarginCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
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
    	$scope.selectReportDepartment = function() {
			$scope.showReport();
		};
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), monthOfYear: reportService.getMonthOfYear(), departmentID: $scope.selectedDepartmentOption.id, denominator: 1};
        	$scope.draw(restClient(config.currentMode).queryOverallPercentageIncomeReport, params);
        };
        
        $scope.draw = function (restClient, params) {
	        	Highcharts.theme = config.highChartsTheme;
				Highcharts.setOptions(Highcharts.theme); 
	 			
	            restClient(params, function(data) {
	            	var chartData = {
				        		id: 'report_chart',
				        		title: '运营利润/毛利',
				        		yAxisTitle: '运营利润/毛利',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[] },
				        		gridData:[]
				        	}; 
	            	var chartCategories = [{ categories: null }];
	            	var dealers = [];
	            	var previousDetail = data.detail[0].detail;
	            	for (var i=0; i<previousDetail.length; i++) {
						dealers[i] = previousDetail[i].code;
	            		chartData.series.previous[i] = previousDetail[i].opProfit.amount * 100;
	            		chartData.series.previousReference[i] = previousDetail[i].opProfit.reference * 100;
	            		chartData.gridData[i] = {id:null, name:null, amount:null};
	            		chartData.gridData[i].id = previousDetail[i].code;
	            		chartData.gridData[i].name = previousDetail[i].name;
	            		chartData.gridData[i].brand = previousDetail[i].brand;
	            		chartData.gridData[i].amount = previousDetail[i].opProfit.amount * 100;
	            	};
	            	
					chartCategories[0].categories = dealers;
					
	            	var chartSubtitle = '月均对比';
	            	var chartColumnCurrent = '月均';
	            	var chartColumnCurrentRef = '参考值';
			        var chartWidth = $(window).width() * 0.60;
			        if ( reportService.getFullScreen() ) {
			        	chartWidth = $(window).width() * 0.90;
					}
			        var currentData = chartData;
			        jQuery("#report_list").jqGrid("GridUnload");
			        jQuery("#report_list").jqGrid({
					   	data:chartData.gridData,
						datatype: "local",
					   	colNames:['经销商代码','名称', '品牌', '百分比（％）'],
					   	colModel:[
					   		{name:'id',index:'id', width:55},
					   		{name:'name',index:'name', width:100},
					   		{name:'brand',index:'brand', width:55},
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
						caption: "月均运营利润占毛利百分比"
					});
					jQuery("#report_list").jqGrid('navGrid','#report_pager',{"edit":false,"add":false,"del":false,"search":true,"refresh":true,"view":false,"excel":false,"pdf":false,"csv":false,"columns":false});
					if (  $scope.report_chart_display ) {
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
			                    }
			                }
							],
			                tooltip: {
			                    formatter: function() {
			                        var tooltip = this.series.name +': '+ this.y + '%' +'<br/>';
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
			                        name: chartColumnCurrent,
			                        data: currentData.series.previous
			                    },
			                    {
			                        type: 'spline',
			                        name: chartColumnCurrentRef,
			                        data: currentData.series.previousReference
			                    }
			                ]
			        	});
			        }
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

		$scope.showChart = function() {
            reportService.setShowChart(!reportService.getShowChart());
            $scope.report_chart_display = reportService.getShowChart();
			if ( $scope.report_chart_display ) {
				$scope.report_chart_button_text = "隐藏图表";
			} else {
				$scope.report_chart_button_text = "显示图表";
			}
			$scope.showReport();
        };
        
		/**
		 * Global variables
		 */
		reportService.setFullScreen(false);
		reportService.setShowChart(false);
		$scope.report_chart_display = reportService.getShowChart();
		$scope.report_chart_button_text = "显示图表";
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
		reportService.setMonthOfYear(currentDate.getMonth());
  		$scope.yearOptions = reportService.getYearList();
		$scope.selectedYearOption = $scope.yearOptions[0];
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];

		$scope.departmentOptions = [];
    	reportService.getPostSalesDepartments(restClient(config.currentMode).queryDepartments, {}, function(departments) {
    		$scope.departmentOptions = departments;
    		$scope.selectedDepartmentOption = $scope.departmentOptions[0];
			// called on page is loaded
			$scope.showReport();
		});
  }]);

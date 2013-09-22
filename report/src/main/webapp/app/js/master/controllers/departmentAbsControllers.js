'use strict';

angular.module('departmentAbs.controllers', [])
	.controller('departmentAbsCtrl', ['$scope', '$http', 'ReportRestClient', 'ReportService', 'config', function($scope, $http, restClient, reportService, config) {
		$scope.charts = [
			{text:'营业额', display:false},
    		{text:'毛利', display:false},
    		{text:'费用', display:false},
    		{text:'运营利润', display:true},
    		];
        
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
  		$scope.yearOptions = reportService.getYearList();
  		$scope.selectedYearOption = $scope.yearOptions[0];
		
		$scope.monthOptions = [];
		reportService.setMonthOfYear(currentDate.getMonth());
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];
    	
    	$scope.selectedTime = 0;
    	$scope.selectReportYear = function() {
    		reportService.setCurrentYear($scope.selectedYearOption.id);
    		$scope.selectTime($scope.selectedTime);
    	};
		$scope.selectReportMonth = function() {
    		reportService.setMonthOfYear($scope.selectedMonthOption.id);
    		$scope.selectTime($scope.selectedTime);
    	};
    	
    	$scope.departmentOptions = [];
    	reportService.getDepartments(restClient(config.currentMode).queryDepartments, {}, function(departments) {
    		$scope.departmentOptions = departments;
    		$scope.selectedDepartmentOption = $scope.departmentOptions[0];
			// called on page is loaded
			$scope.showReport();
		});
		
		$scope.selectReportDepartment = function() {
			$scope.showReport();
		};
		
    	$scope.showReport = function()
        {
        	var params = null;
        	if ( $scope.selectedTime == 0 ) {
        		params = {year: reportService.getCurrentYear(), departmentID: $scope.selectedDepartmentOption.id};
        	}
        	if ( $scope.selectedTime == 1 ) {
        		params = {year: reportService.getCurrentYear(), monthOfYear: reportService.getMonthOfYear(), departmentID: $scope.selectedDepartmentOption.id};
        	}
        	for ( var i=0; i< $scope.charts.length;i++ ) {
        		if ( $scope.charts[i].display == true ) {
        			$scope.draw(restClient(config.currentMode).queryOverallIncomeReport, params, i);
        		} 
        	}
        	
        };
        $scope.draw = function (restClient, params, index) {
			Highcharts.theme = config.highChartsTheme;
			
            // Apply the theme
            var highchartsOptions = Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var series = { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], };
            	var chartData = [
				        	{
				        		id: 'report_revenue',
				        		title: '营业额',
				        		yAxisTitle: '营业额',
				        		series: series
				        	}, 
				        	{
				        		id: 'report_margin',
				        		title: '毛利',
				        		yAxisTitle: '毛利',
				        		series: series
				        	},
				        	{
				        		id: 'report_expense',
				        		title: '费用',
				        		yAxisTitle: '费用',
				        		series: series
				        	}, 
				        	{
				        		id: 'report_opProfit',
				        		title: '运营利润',
				        		yAxisTitle: '运营利润',
				        		series: series
				        	},

				    ]; 
	            	var chartCategories = [{ categories: null }];
	            	var dealers = [];
	            	var previousDetail = data.detail[0].detail;
	            	var previousYear = data.detail[0].year;
	            	for ( var i in previousDetail ) {
	            		dealers[i] = previousDetail[i].code;
	            		chartData[0].series.previous[i] = {y: previousDetail[i].revenue.amount, drilldown:{name: previousDetail[i].id, type: 'revenue'}};
	            		chartData[0].series.previousReference[i] = previousDetail[i].revenue.reference;
	            		
	            		chartData[1].series.previous[i] = {y: previousDetail[i].margin.amount, drilldown:{name: previousDetail[i].id, type: 'margin'}};
	            		chartData[1].series.previousReference[i] = previousDetail[i].margin.reference;

	            		chartData[2].series.previous[i] = {y: previousDetail[i].expense.amount, drilldown:{name: previousDetail[i].id, type: 'expense'}};
	            		chartData[2].series.previousReference[i] = previousDetail[i].expense.reference;
	            		
	            		chartData[3].series.previous[i] = {y: previousDetail[i].opProfit.amount, drilldown:{name: previousDetail[i].id, type: 'opProfit'}};
	            		chartData[3].series.previousReference[i] = previousDetail[i].opProfit.reference;
	            	};
	            	
					chartCategories[0].categories = dealers;
					var currentDetail = data.detail[1].detail;
					for ( var i in currentDetail ) {
	            		chartData[0].series.current[i] = currentDetail[i].revenue.amount;
	            		chartData[0].series.currentReference[i] = currentDetail[i].revenue.reference;
	            		chartData[0].series.currentPercentage[i] = currentDetail[i].revenue.percentage * 100;
	            		
	            		chartData[1].series.current[i] = currentDetail[i].margin.amount;
	            		chartData[1].series.currentReference[i] = currentDetail[i].margin.reference;
	            		chartData[1].series.currentPercentage[i] = currentDetail[i].margin.percentage * 100;
	            		
	            		chartData[2].series.current[i] = currentDetail[i].expense.amount;
	            		chartData[2].series.currentReference[i] = currentDetail[i].expense.reference;
	            		chartData[2].series.currentPercentage[i] = currentDetail[i].expense.percentage * 100;
	            		
	            		chartData[3].series.current[i] = currentDetail[i].opProfit.amount;
	            		chartData[3].series.currentPercentage[i] = currentDetail[i].opProfit.percentage * 100;
	            		chartData[3].series.currentReference[i] = currentDetail[i].opProfit.reference;
	            	};
	            	
	            	var chartSubtitle = '年度对比';
	            	if ( $scope.selectedTime == 1 ) {
	            		chartSubtitle = '月对比';
	            	}
	            	
	            	var chartColumnPrevious = '去年';
	            	if ( $scope.selectedTime == 1 ) {
	            		chartColumnPrevious = '月均';
	            	}
	            	var chartColumnCurrent = '今年';
	            	if ( $scope.selectedTime == 1 ) {
	            		chartColumnCurrent = '当月';
	            	}
	            	var chartColumnPreviousRef = '去年参考值';
	            	if ( $scope.selectedTime == 1 ) {
	            		chartColumnPreviousRef = '月均参考值';
	            	}
	            	var chartColumnCurrentRef = '今年参考值';
	            	if ( $scope.selectedTime == 1 ) {
	            		chartColumnCurrentRef = '当月参考值';
	            	}
				        
			        var chartWidth = $(window).width() * 0.60;
			        if ( reportService.getFullScreen() ) {
			        	chartWidth = $(window).width();
					}
			                    		
			        chartData = [chartData[index]];
			        
		        	for (var i=0;i<=chartData.length;i++) 
	  				{
			        	var currentData = chartData[i];
			        	var chart = $('#' + currentData.id).highcharts({
			                chart: {
			                	zoomType: 'xy',
			                    height:$(window).height()*0.60,
			                    width: chartWidth,
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
		                    },
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
			                    	cursor: 'pointer',
			                    	point: {
				                        events: {
				                            click: function() {
				                            	var drilldown = this.drilldown;
				                                window.alert(drilldown.name);
				                                if (drilldown) { // drill down
				                                    // TODO: generate drilldown data
				                                    setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
				                                } else { // restore
				                                    setChart(name, categories, data);
				                                }
				                            }
				                        }
				                    },
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
			        	}).highcharts();
			        }
		        
			  });
		};

		$scope.times = [
    		{text:'月', value:1, isDefault: true}, 
    		{text:'年', value:0, isDefault: false},];
    	
    	$scope.selectTime = function(x) {
    		if ( x == 0 ) { // year
    			$scope.selectedTime = 0;
    			$scope.showReport();
    		}
    		if ( x == 1 ) { // month
    			$scope.selectedTime = 1;
    			$scope.showReport();
    		}
    	};
    	
        reportService.setFullScreen(false);

        $scope.toggleFullScreen = function()
        {
            if (reportService.getFullScreen())
            {
                $('#container_div').addClass('container');
                $('#container_div').removeClass('row-fluid');
                $("#header").removeClass('hide');
                $("#nav_div").removeClass('hide');
                $("#report_div").removeClass('span12');
                $("#report_div").addClass('span9');
                reportService.setFullScreen(false);
                $scope.showReport();
                
            }
            else
            {
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

  }]);

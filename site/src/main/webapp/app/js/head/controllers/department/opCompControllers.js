'use strict';

angular.module('departmentOpComp.controllers', [])
	.controller('departmentOpCompCtrl', ['$scope', '$http', 'ReportRestClient', 'ReportService', 'config', function($scope, $http, restClient, reportService, config) {
		$scope.selectReportDenominotor = function() {
    		$scope.showReport();
    	};
    	    	
    	$scope.selectReportDepartment = function() {
			$scope.showReport();
		};
		
    	$scope.showReport = function()
        {
        	var params = {year: reportService.getCurrentYear(), 
        					departmentID: $scope.selectedDepartmentOption.id, 
        					monthOfYear: reportService.getMonthOfYear(), 
        					denominator: $scope.selectedDenominatorOption.id};
        	for ( var i=0; i< $scope.charts.length;i++ ) {
        		if ( $scope.charts[i].display == true ) {
        			$scope.draw(restClient(config.currentMode).queryDepartmentIncomePercentageReport, params, i);
        		} 
        	}
        };
        
        $scope.draw = function (restClient, params, index) {
	        Highcharts.theme = config.highChartsTheme;
			Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var chartData = [
				        	{
				        		id: 'report_opProfit',
				        		title: '运营利润' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '运营利润',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	},
				        	{
				        		id: 'report_revenue',
				        		title: '营业额' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '营业额',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	}, 
				        	{
				        		id: 'report_margin',
				        		title: '毛利' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '毛利',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	}
				]; 
				    
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	var previousDetail = data.detail[0].detail;
	            	for ( var i in previousDetail ) {
	            		dealers[i] = previousDetail[i].code;
	            		chartData[0].series.previous[i] = previousDetail[i].opProfit.amount * 100;
	            		chartData[0].series.previousReference[i] = previousDetail[i].opProfit.reference * 100;
	            		
	            		chartData[1].series.previous[i] = previousDetail[i].revenue.amount * 100;
	            		chartData[1].series.previousReference[i] = previousDetail[i].revenue.reference * 100;
	            		
	            		chartData[2].series.previous[i] = previousDetail[i].margin.amount * 100;
	            		chartData[2].series.previousReference[i] = previousDetail[i].margin.reference * 100;
	            	};
	            	
					chartCategories[0].categories = dealers;
					var currentDetail = data.detail[1].detail;
					for ( var i in currentDetail ) {
	            		chartData[0].series.current[i] = currentDetail[i].opProfit.amount * 100;
	            		chartData[0].series.currentReference[i] = currentDetail[i].opProfit.reference * 100;
	            		
	            		chartData[1].series.current[i] = currentDetail[i].revenue.amount * 100;
	            		chartData[1].series.currentReference[i] = currentDetail[i].revenue.reference * 100;
	            		
	            		chartData[2].series.current[i] = currentDetail[i].margin.amount * 100;
	            		chartData[2].series.currentReference[i] = currentDetail[i].margin.reference * 100;
		            };
		            	
	            	var chartSubtitle = '月对比';
	            	var chartColumnPrevious = '月均';
	            	var chartColumnCurrent = '当月';
	            	var chartColumnPreviousRef = '月均参考值';
	            	var chartColumnCurrentRef = '当月参考值';
	            	
			        var chartWidth = $(window).width() * 0.60;
			        if ( reportService.getFullScreen() ) {
			        	chartWidth = $(window).width();
					}
			        
			        var currentData = chartData[index];
			        $('#' + currentData.id).highcharts({
			                chart: {
			                    zoomType: 'xy',
			                    height:$(window).height()*0.60,
			                    width:chartWidth,
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
			                        text: currentData.yAxisTitle + '百分比 (%)'
			                    },
			                },
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
			                    }
			                ]
			        	});
		        
			  });
		};

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
        
    	$scope.selectReportYear = function() {
    		reportService.setCurrentYear($scope.selectedYearOption.id);
    		$scope.showReport();
    	};
		$scope.selectReportMonth = function() {
    		reportService.setMonthOfYear($scope.selectedMonthOption.id);
    		$scope.showReport();
    	};
    	
        reportService.setFullScreen(false);

		$scope.charts = [
    		{id: 'report_opProfit', display:true},
    		{id: 'report_revenue', display:false},
    		{id: 'report_margin', display:false},
    		];
		
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
  		$scope.yearOptions = reportService.getYearList();
		$scope.selectedYearOption = $scope.yearOptions[0];
		
		reportService.setMonthOfYear(currentDate.getMonth());
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];
    	
    	$scope.denominatorOptions = [
    		{name:'部门毛利分析', id:1},
    		{name:'运营利润分析', id:0}];
    	$scope.selectedDenominatorOption = $scope.denominatorOptions[1];
    	
    	$scope.departmentOptions = [];
    	reportService.getDepartments(restClient(config.currentMode).queryDepartments, {}, function(departments) {
    		$scope.departmentOptions = departments;
    		$scope.selectedDepartmentOption = $scope.departmentOptions[0];
			$scope.showReport();
		});
		
  }]);

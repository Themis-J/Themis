'use strict';

angular.module('overallPercentage.controllers', [])
	.controller('overallPercentageCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
		$scope.selectReportDenominotor = function() {
    		if ($scope.selectedDenominatorOption.id == 0) {
    			$scope.charts[0].display = false;
    			$scope.charts[0].checkboxDisplay = false;
    			$scope.charts[1].display = false;
    			$scope.charts[1].checkboxDisplay = false;
    			$scope.charts[2].display = true;
    			$scope.charts[2].checkboxDisplay = true;
    			$scope.charts[3].display = true;
    			$scope.charts[3].checkboxDisplay = true;
    		} else {
    			$scope.charts[0].display = false;
    			$scope.charts[0].checkboxDisplay = true;
    			$scope.charts[1].display = false;
    			$scope.charts[1].checkboxDisplay = true;
    			$scope.charts[2].display = true;
    			$scope.charts[2].checkboxDisplay = true;
    			$scope.charts[3].display = true;
    			$scope.charts[3].checkboxDisplay = true;
    		}
    		$scope.showReport();
    	};
    	
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), monthOfYear: reportService.getMonthOfYear(), denominator: $scope.selectedDenominatorOption.id};
        	for ( var i=0; i< $scope.charts.length;i++ ) {
        		if ( $scope.charts[i].display == true ) {
        			$scope.draw(restClient(config.currentMode).queryOverallPercentageIncomeReport, params, i);
        		} 
        	}
        };
        
        $scope.draw = function (restClient, params, index) {
	        Highcharts.theme = config.highChartsTheme;
            Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var chartData = [
				        	{
				        		id: 'report_margin',
				        		title: '毛利' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '毛利',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	},
				        	{
				        		id: 'report_expense',
				        		title: '费用' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '费用',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	}, 
				        	{
				        		id: 'report_opProfit',
				        		title: '运营利润' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '运营利润',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	},
				        	{
				        		id: 'report_netProfit',
				        		title: '税前尽利润' + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: '税前尽利润',
				        		series: { previous:[], current:[], previousReference:[], currentReference:[], currentPercentage:[], }
				        	},
				        	
				]; 
				    
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	var previousDetail = data.detail[0].detail;
	            for ( var i in previousDetail ) {
	            		dealers[i] = previousDetail[i].code;
	            		
	            		chartData[0].series.previous[i] = previousDetail[i].margin.amount * 100;
	            		chartData[0].series.previousReference[i] = previousDetail[i].margin.reference * 100;
	            	
		            	chartData[1].series.previous[i] = previousDetail[i].expense.amount * 100;
	            		chartData[1].series.previousReference[i] = previousDetail[i].expense.reference * 100;
	            		
	            		chartData[2].series.previous[i] = previousDetail[i].opProfit.amount * 100;
	            		chartData[2].series.previousReference[i] = previousDetail[i].opProfit.reference * 100;
	            		
	            		chartData[3].series.previous[i] = previousDetail[i].netProfit.amount * 100;
	            		chartData[3].series.previousReference[i] = previousDetail[i].netProfit.reference * 100;
	            };
	            	
				chartCategories[0].categories = dealers;
				var currentDetail = data.detail[1].detail;
				for ( var i in currentDetail ) {
	            		chartData[0].series.current[i] = currentDetail[i].margin.amount * 100;
	            		chartData[0].series.currentReference[i] = currentDetail[i].margin.reference * 100;
		            
		            	chartData[1].series.current[i] = currentDetail[i].expense.amount * 100;
	            		chartData[1].series.currentReference[i] = currentDetail[i].expense.reference * 100;
	            		
	            		chartData[2].series.current[i] = currentDetail[i].opProfit.amount * 100;
	            		chartData[2].series.currentReference[i] = currentDetail[i].opProfit.reference * 100;
	            		
	            		chartData[3].series.current[i] = currentDetail[i].netProfit.amount * 100;
	            		chartData[3].series.currentReference[i] = currentDetail[i].netProfit.reference * 100;
	            };
		            
	            var chartSubtitle = '月对比';
	            var chartColumnPrevious = '月均';
	            var chartColumnCurrent = '当月';
	            var chartColumnPreviousRef = '月均参考值';
	            var chartColumnCurrentRef = '当月参考值';
	            	
			    var currentData = chartData[index];
			    var chartWidth = $(window).width() * 0.60;
			    if ( reportService.getFullScreen() ) {
			    	chartWidth = $(window).width();
				}
			        
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

		$scope.charts = [
    		{id: 'report_margin', text:'毛利', display:false, checkboxDisplay: false},
    		{id: 'report_expense', text:'费用', display:false, checkboxDisplay: false},
    		{id: 'report_opProfit', text:'运营利润', display:true, checkboxDisplay: true},
    		{id: 'report_netProfit', text:'税前净利润', display:true, checkboxDisplay: true},
    	];
    	
    	reportService.setFullScreen(false);
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
  		reportService.setMonthOfYear(currentDate.getMonth());
		$scope.yearOptions = reportService.getYearList();
		$scope.selectedYearOption = $scope.yearOptions[0];
		
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];
    	
    	$scope.currentDenominator = 0;
    	$scope.denominatorOptions = [
    		{name:'营业额', id:0},
    		{name:'毛利', id:1}];
    	$scope.selectedDenominatorOption = $scope.denominatorOptions[$scope.currentDenominator];
    	
		// called on page is loaded
		$scope.showReport();
  }]);

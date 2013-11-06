'use strict';

angular.module('overallExpPercentage.controllers', [])
	.controller('overallExpPercentageCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
		$scope.selectReportYear = function() {
    		reportService.setCurrentYear($scope.selectedYearOption.id);
    		$scope.showReport();
    	};
    	
		$scope.selectReportMonth = function() {
    		reportService.setMonthOfYear($scope.selectedMonthOption.id);
    		$scope.showReport();
    	};
		$scope.selectReportDenominotor = function() {
    		$scope.showReport();
    	};
    	$scope.selectReportCategory = function() {
    		reportService.getGeneralJournalItems(restClient(config.currentMode), {categoryID: $scope.selectedCategoryOption.id}, function(items) {
	    		$scope.itemOptions = items;
		    	$scope.selectedItemOption = $scope.itemOptions[0];
		    	
		    	$scope.showReport();
	    	});
    	};
    	$scope.selectReportItem = function() {
    		$scope.showReport();
    	};
    	
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					denominator: $scope.selectedDenominatorOption.id, 
        					itemCategory: $scope.selectedCategoryOption.name, 
        					};
        	if ( $scope.isQueryItem ) {
        		params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					denominator: $scope.selectedDenominatorOption.id, 
        					itemCategory: $scope.selectedCategoryOption.name, 
        					itemName: $scope.selectedItemOption.name, 
        					};
        	}
        	$scope.draw(restClient(config.currentMode).queryOverallExpPercentageReport, params);
        };
        
        $scope.draw = function (restClient, params) {
	        Highcharts.theme = config.highChartsTheme;
			Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var expTitle = $scope.selectedCategoryOption.name;
            	if ( $scope.isQueryItem ) {
            		expTitle = $scope.selectedItemOption.name;
            	}
            	var currentData = {
				        		id: 'chart',
				        		title: expTitle + '/' + $scope.selectedDenominatorOption.name,
				        		yAxisTitle: expTitle,
				        		series: { amount:[], currentMargin:[], previousMargin:[], currentPercentage:[], previousPercentage:[], }
				        	};
				    
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	var detail = data.detail[0].detail;
            	
	            for ( var i in detail ) {
	            	dealers[i] = detail[i].code;
	            	currentData.series.amount[i] = detail[i].amount.amount;
	            	currentData.series.currentMargin[i] = detail[i].currentMargin.amount;
	            	currentData.series.previousMargin[i] = detail[i].previousMargin.amount;
	            	currentData.series.currentPercentage[i] = detail[i].currentMargin.percentage * 100;
	            	currentData.series.previousPercentage[i] = detail[i].previousMargin.percentage * 100;
	            };
	            
				chartCategories[0].categories = dealers;

			    var chartWidth = $(window).width() * 0.60;
			    if ( reportService.getFullScreen() ) {
			    	chartWidth = $(window).width();
				}
				
			    $('#report_chart').highcharts({
			                chart: {
			                    zoomType: 'xy',
			                    height:$(window).height()*0.60,
			                    width:chartWidth,
			                },
			                title: {
			                    text: currentData.title
			                },
			                subtitle: {
			                    text: '月均对比'
			                },
			                xAxis: chartCategories,
			                yAxis: [{
			                    title: {
			                        text: currentData.yAxisTitle
			                    },
			                    min:0
			                },
			                {
		                        gridLineWidth: 0,
		                        title: {
		                            text: '百分比 (%)'
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
			                        name: '费用',
			                        data: currentData.series.amount
			                    },
			                    {
			                        type: 'column',
			                        name: '今年毛利',
			                        data: currentData.series.currentMargin
			                    },
			                    {
			                        type: 'column',
			                        name: '去年',
			                        data: currentData.series.previousMargin
			                    },
			                    {
			                        type: 'spline',
			                        name: '今年百分比',
			                        yAxis: 1,
			                        data: currentData.series.currentPercentage
			                    },
			                    {
			                        type: 'spline',
			                        name: '去年百分比',
			                        yAxis: 1,
			                        data: currentData.series.previousPercentage
			                    },
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

    	reportService.setFullScreen(false);
    	var currentDate = new Date();
  		reportService.setCurrentYear(currentDate.getFullYear());
  		reportService.setMonthOfYear(currentDate.getMonth());
		$scope.yearOptions = reportService.getYearList();
		$scope.selectedYearOption = $scope.yearOptions[0];
		$scope.monthOptions = reportService.getMonthList();
		$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear()-1];
    	
    	$scope.denominatorOptions = [
    		{name:'新车及二手车零售毛利', id:0},
    		{name:'经销商总毛利', id:1}];
    	$scope.selectedDenominatorOption = $scope.denominatorOptions[0];
    	
    	$scope.categoryOptions = [
    		{name:'变动费用', id:1},
    		{name:'销售费用', id:2},
    		{name:'人工费用', id:3},
    		{name:'半固定费用', id:4},
    		{name:'固定费用', id:5},
    		];
    	$scope.selectedCategoryOption = $scope.categoryOptions[0];
    	$scope.itemOptions = [];
    	$scope.selectedItemOption = null;
    	$scope.isQueryItem = false;
    	$scope.selectReportCategory();
  }]);

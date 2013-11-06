'use strict';

angular.module('overallHRAnalysis.controllers', [])
	.controller('overallHRAnalysisCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
		$scope.selectReportDepartment = function() {
			$scope.showReport();
		};
		$scope.selectReportPosition = function() {
			$scope.showReport();
		};
    	$scope.selectReportItem = function() {
    		$scope.showReport();
    	};
    	
    	$scope.showReport = function() {
        	var params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					};
        	if ( $scope.selectedDepartmentOption.name != '全部门' ) {
        		if ( $scope.selectedPositionOption.name != '全职位' ) {
        			params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					departmentID: $scope.selectedDepartmentOption.id,
        					positionID: $scope.selectedPositionOption.id,
        					groupBy:0,
        					};
        		} else {
        			params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					departmentID: $scope.selectedDepartmentOption.id,
        					groupBy:0,
        					};
        		}
        		
        	}
        	if ( $scope.selectedPositionOption.name != '全职位' ) {
        		if ( $scope.selectedDepartmentOption.name != '全部门' ) {
        			params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					departmentID: $scope.selectedDepartmentOption.id,
        					positionID: $scope.selectedPositionOption.id,
        					groupBy:0,
        					};
        		} else {
        			params = {year: reportService.getCurrentYear(), 
        					monthOfYear: reportService.getMonthOfYear(), 
        					positionID: $scope.selectedPositionOption.id,
        					groupBy:0,
        					};
        		}
        	}
        	$scope.draw(restClient(config.currentMode).queryOverallHRAllocReport, params);
        };
        
        $scope.draw = function (restClient, params) {
	        Highcharts.theme = config.highChartsTheme;
			Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var expTitle = $scope.selectedDepartmentOption.name+'/'+$scope.selectedPositionOption.name+' 人员统计';
            	var currentData = {
				        		id: 'report_chart',
				        		title: expTitle,
				        		yAxisTitle: expTitle,
				        		series: { allocation:[], }
				        	};
				
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	var detail = data.detail[0].detail;
            	
	            for ( var i in detail ) {
	            	dealers[i] = detail[i].code;
	            	currentData.series.allocation[i] = detail[i].allocation.amount;
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
			                        name: '人数',
			                        data: currentData.series.allocation
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
    	
    	$scope.departmentOptions = [];
    	$scope.selectedDepartmentOption = null;
    	$scope.selectedPositionOption = null;
    	reportService.getDepartments2(restClient(config.currentMode).queryDepartments, {}, function(departments) {
    		$scope.departmentOptions = departments;
    		$scope.selectedDepartmentOption = $scope.departmentOptions[0];
			reportService.getPositions(restClient(config.currentMode), {}, function(positions) {
	    		$scope.positionOptions = positions;
	    		$scope.selectedPositionOption = $scope.positionOptions[0];
				$scope.showReport();
			});
		});
  }]);

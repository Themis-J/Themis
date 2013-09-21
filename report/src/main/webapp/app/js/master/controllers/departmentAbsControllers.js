'use strict';

angular.module('departmentAbs.controllers', [])
	.controller('departmentAbsCtrl', ['$scope', '$http', 'ReportRestClient', 'ReportService', function($scope, $http, restClient, reportService) {
		$scope.charts = [
    		{text:'运营利润', display:true},
    		{text:'营业额', display:false},
    		{text:'费用', display:false},
    		{text:'毛利', display:false}];
        
        var services = [
        	{
        		queryDepartments: restClient.localQueryDepartments, 
        		queryReportService: restClient.localQueryYearlyOverallIncomeReport, 
        	},
        	{
        		queryDepartments: restClient.queryDepartments, 
        		queryReportService: restClient.queryOverallIncomeReport, 
        	},
        ];
        var local = services[0];
        var normal = services[1];
		// var restService = normal;
    	var restService = local;
    	
    	$scope.yearOptions = [];
  		var currentDate = new Date();
  		$scope.currentYear = currentDate.getFullYear();
  		var i = $scope.currentYear;
  		var j = 0;
  		for (var i=$scope.currentYear;i>1980;i--) 
  		{
  			$scope.yearOptions[j++] = {name: i + '年', id: i};
  		}
		$scope.selectedYearOption = $scope.yearOptions[0];
		
		$scope.monthOptions = [];
		$scope.currentMonth = currentDate.getMonth();
		for (var i=0;i<12;i++) 
  		{
  			$scope.monthOptions[i] = {name: (i+1) + '月', id: i+1};
  		}
		$scope.selectedMonthOption = $scope.monthOptions[$scope.currentMonth-1];
    	
    	$scope.selectedTime = 0;
    	
    	$scope.selectReportYear = function() {
    		$scope.currentYear = $scope.selectedYearOption.id;
    		$scope.selectTime($scope.selectedTime);
    	};
		$scope.selectReportMonth = function() {
    		$scope.currentMonth = $scope.selectedMonthOption.id;
    		$scope.selectTime($scope.selectedTime);
    	};
    	
    	$scope.departmentOptions = [];
    	reportService.getDepartments(restService.queryDepartments, {}, function(departments) {
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
        	reportService.setCurrentYear($scope.currentYear);
        	reportService.setFullScreen($scope.report.isFull);
        	var params = null;
        	if ( $scope.selectedTime == 0 ) {
        		params = {year: $scope.currentYear, departmentID: $scope.selectedDepartmentOption.id};
        		reportService.setMonthOfYear(-1);
        	}
        	if ( $scope.selectedTime == 1 ) {
        		params = {year: $scope.currentYear, monthOfYear: $scope.currentMonth, departmentID: $scope.selectedDepartmentOption.id};
        		reportService.setMonthOfYear($scope.currentMonth);
        	}
        	for ( var i=0; i< $scope.charts.length;i++ ) {
        		if ( $scope.charts[i].display == true ) {
        			reportService.drawAbsDepartmentIncomeReport(restService.queryReportService, params, i);
        		} 
        	}
        	
        };
        
        $scope.chartSelect = function()
        {
        	$scope.showReport();
        };

		$scope.times = [
    		{text:'年', value:0, isDefault: true},
    		{text:'月', value:1, isDefault: false}];
    	
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
    	
        $scope.report = {};
        $scope.report.width;
        $scope.report.height;
        $scope.report.isFull = false;

        $scope.toggleFullScreen = function()
        {
            if ($scope.report.isFull)
            {
                $('#container_div').addClass('container');
                $('#container_div').removeClass('row-fluid');
                $("#header").removeClass('hide');
                $("#nav_div").removeClass('hide');
                $("#report_div").removeClass('span12');
                $("#report_div").addClass('span9');
                $scope.report.isFull = false;
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
                 $scope.report.isFull = true;
                $scope.showReport();
               
            }
        };

  }])

  .controller('viewCtrl', ['$scope',function($scope) {

  }]);

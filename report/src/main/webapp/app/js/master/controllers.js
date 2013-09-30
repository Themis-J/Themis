'use strict';

angular.module('masterApp.controllers', [])
	.controller('editCtrl', ['$scope', '$http', '$route','$location', 'ReportRestClient', 'ReportService', 
	function($scope, $http, $route, $location, restClient, reportService) {
		$scope.items = ['overallAbs',
						'overallPercentage',
						'departmentOp', 
						'departmentAbs', 
						'departmentPercentage', 
						'departmentOpComp', ];
        
		$scope.goto = function(itemId)
        {
        	$scope.$apply(function(){
            });
            $scope.subpage = 'partials/master/' + $scope.items[itemId] + '.html';
        };
        
        $scope.bindEvent = function()
        {
            $('ul.nav.nav-pills li a').click(function() {
                $(this).parent().addClass('active').siblings().removeClass('active');
            });
        };

        $scope.$on('$includeContentLoaded', function () {
        });
        
        $scope.subpage = 'partials/master/' + $scope.items[4] + '.html';

  }])

  .controller('viewCtrl', ['$scope',function($scope) {

  }]);

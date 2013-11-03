'use strict';

angular.module('masterApp.controllers', [])
	.controller('headEditCtrl', ['$scope',
	function($scope) {
		$scope.items = [{name: '总运营－营业额、毛利、费用、运营利润、税前净利润', page:'overall/abs'}, 
						{name: '总运营－销售、毛利占百分比', page:'overall/percentage'},
						{name: '总运营－费用占百分比', page:'overall/expPercentage'},
						{name: '总运营－人员分析', page:'overall/hranalysis'},
						{name: '分运营－经销商部门收入、毛利、费用、运营利润', page:'department/abs'},
						{name: '分运营－部门收入、毛利、费用、运营利润', page:'department/op'},
						{name: '分运营－各经销商部门收入、毛利、费用、运营利润', page:'department/percentage'},
						{name: '分运营－各部门毛利、运营利润占百分比', page:'department/opComp'},
						{name: '销售部－销量 – 总量', page:'sales/abs'},
						{name: '销售部－销量', page:'sales/departmentAbs'},
						{name: '销售部－总销量占百分比', page:'sales/percentage'},
						{name: '销售部－部门销量占百分比', page:'sales/departmentPercentage'},
						];
        
		$scope.goto = function(item)
        {
        	for ( var i in $scope.items ) {
        		if ($scope.items[i].name == item) {
        			$scope.subpage = 'partials/head/' + $scope.items[i].page + '.html';
        			break;
        		}
        	}
        	
        };
        $scope.bindEvent = function()
        {
            $('ul.nav.nav-pills li a').click(function() {
            	$(this).parent().addClass('active').siblings().removeClass('active');
            });
        };
        $scope.subpage = 'partials/head/' + $scope.items[0].page + '.html';
  	}])
    .controller('branchHeadCtrl', [ '$scope', '$route', '$location', 'DealerService','UserService','Auth', function($scope, $route,$location,DealerService,UserService,Auth)
    {
        $scope.signout = function() {
            Auth.logout({}, function () {
                UserService.setupUser(null);
                $location.path('/guest/login');
            });
        };
    }
    ]);

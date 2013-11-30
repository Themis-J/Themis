'use strict';

angular.module('masterApp.controllers', [])
	.controller('headEditCtrl', ['$scope',
	function($scope) {
		$scope.items = [{name: '关键指标总览－本年度税前净利润', page:'key/netProfit'}, 
						{name: '关键指标总览－本年度总运营利润', page:'key/opProfit'}, 
						{name: '关键指标总览－月均运营利润占销售金额百分比', page:'key/opProfitPerRevenue'}, 
						{name: '关键指标总览－月均运营利润占毛利百分比', page:'key/opProfitPerMargin'}, 
						{name: '关键指标总览－售后部门运营利润毛利润百分比', page:'key/postSalesOpProfitPerMargin'}, 
						{name: '关键指标总览－售后部门销售金额毛利润百分比', page:'key/postSalesRevenuePerMargin'}, 
						{name: '总运营－(本年度)总营业额', page:'overall/revenue'}, 
						{name: '总运营－(本年度)经销商总毛利', page:'overall/margin'}, 
						{name: '总运营－(本年度)经销商总费用', page:'overall/expense'}, 
						{name: '总运营－营业额、毛利、费用、运营利润、税前净利润', page:'overall/abs'}, 
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
						{name: '应收账款－应收帐款总额', page:'ard/overall'},
						{name: '应收账款－售前应收总额', page:'ard/sales'},
						{name: '应收账款－售后应收总额', page:'ard/postSales'},
						{name: '应收账款－厂家应收总额', page:'ard/factory'}
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
    .controller('headHeaderCtrl', [ '$scope', '$route', '$location', 'DealerService','UserService','Auth', function($scope, $route,$location,DealerService,UserService,Auth)
    {
        $scope.signout = function() {
            Auth.logout({}, function () {
                UserService.setupUser(null);
                $location.path('/guest/login');
            });
        };
    }
    ]);

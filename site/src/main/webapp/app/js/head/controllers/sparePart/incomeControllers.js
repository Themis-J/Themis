'use strict';

angular.module('sparePartIncome.controllers', []).controller('sparePartIncomeCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
	/**
	 * Global functions
	 */
	$scope.selectReportYear = function() {
		reportService.setCurrentYear($scope.selectedYearOption.id);
		$scope.showReport();
	};
	$scope.selectReportMonth = function() {
		reportService.setMonthOfYear($scope.selectedMonthOption.id);
		$scope.showReport();
	};
	$scope.selectReportCategory = function() {
		$scope.showReport();
	};
	
	$scope.showReport = function() {
		var params = {
			year: reportService.getCurrentYear(),
			monthOfYear: reportService.getMonthOfYear(),
			itemName : $scope.selectedCategoryOption.name
		};
		$scope.draw(restClient(config.currentMode).querySparePartIncomeReport, params);
	};
	
	$scope.draw = function (restClient, params) {
		Highcharts.theme = config.highChartsTheme;
		Highcharts.setOptions(Highcharts.theme);
		
		restClient(params, function(data) {
			var chartData = {
				id : 'report_spare_part_income',
				title : $scope.selectedCategoryOption.name,
				yAxisTitle : $scope.selectedCategoryOption.name,
				series : {
					current : [],
					currentReference : [],
					currentPercentage : []
				},
				gridData : []
			}
			
			var chartCategories = [
			    {
			    	categories : null
			    }
			];
			var dealers = [];
			chartCategories[0].categories = dealers;
			var currentDetail = data.detail[0].detail;
			for (var i = 0; i < currentDetail.length; i++) {
				chartData.gridData[i] = {
					id : null,
					name : null,
					revenue : null,
					marginRate : null
				};
				chartData.gridData[i].id = currentDetail[i].code;
				chartData.gridData[i].name = currentDetail[i].name;
				chartData.gridData[i].revenue = currentDetail[i].revenue.amount;
				chartData.gridData[i].marginRate = currentDetail[i].margin.amount / currentDetail[i].revenue.amount * 100;
				
				var chartSubtitle = '月均对比';
				var chartColumnCurrent = '月均';
				var chartColumnCurrentRef = '参考值';
				var chartWidth = $(window).width() * 0.60;
				if (reportService.getFullScreen()) {
					chartWidth = $(window).width() * 0.90;
				}
				var currentData = chartData;
				jQuery("#report_list").jqGrid("GridUnload");
				jQuery("#report_list").jqGrid({
					data : chartData.gridData,
					datatype : "local",
					colNames : [
								'经销商代码',
								'名称',
								$scope.selectedCategoryOption.name + "销售",
								$scope.selectedCategoryOption.name + "毛利率"
							],
					colModel : [
							       {
										name : 'id',
										index : 'id',
										width : 55
									},
									{
										name : 'name',
										index : 'name',
										sorttype : function(cellValues, rowData) {
											return rowData.totalAmount;
										},
										width : 100
									},
									{
										name : 'revenue',
										index : 'revenue',
										width : 80,
										formatter : "number",
										align : "right",
										sorttype : "float",
										summaryType : "sum"
									},
									{
										name : 'marginRate',
										index : 'marginRate',
										width : 80,
										formatter : "number",
										align : "right",
										sorttype : "float",
										summaryType : "sum"
									}
								],
					rowNum : 30,
					pager : '#report_pager',
					loadError : function(xhr,status, err) { 
					   	try {
					   		jQuery.jgrid.info_dialog(jQuery.jgrid.errors.errcap,'<div class="ui-state-error">'+ xhr.responseText +'</div>', jQuery.jgrid.edit.bClose,{buttonalign:'right'});
					   	} catch(e) { 
					   		alert(xhr.responseText);
					   	}
					},
					viewrecords : true,
					sortable : false,
					multiselect : false,
					width : chartWidth,
					height : "100%",
					caption : "月均备件部收入"
				});
				jQuery("#report_list").jqGrid('navGrid','#report_pager',{"edit":false,"add":false,"del":false,"search":true,"refresh":true,"view":false,"excel":false,"pdf":false,"csv":false,"columns":false});
				if ($scope.report_chart_display) {
					$('#report_chart').highcharts({
						chart : {
							zoomType : 'xy',
							height : $(window).height() * 0.60,
							width : chartWidth
						},
						title : {
							text : currentData.title
						},
						subtitle : {
							text : chartSubtitle
						},
						xAxis : chartCategories,
						yAxis : [
						    {
						    	title : {
						    		text : currentData.yAxisTitle
								},
								min : -10000
							}
						],
						tooltip : {
							formatter : function() {
								var tooltip = this.series.name + ': ' + this.y + '<br/>';
								return tooltip;
							},
							useHTML : true
						},
						plotOptions : {
							column : {
								cursor : 'pointer'
							}
						},
						series : [
						    {
						    	type : 'column',
						    	name : chartColumnCurrent,
						    	data : currentData.series.current
						    },
						    {
						    	type : 'spline',
						    	name : chartColumnCurrentRef,
						    	data : currentData.series.currentReference
						    }
						]
					});
				}
			}
		});
	}
	
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
		if ($scope.report_chart_display) {
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
	$scope.categoryOptions = [
 	    {
 	    	name : '客户－维修部',
 	    	id : 1
		},
		{
			name : '保修',
			id : 2
		},
		{
			name : '内部',
			id : 3
		},
		{
			name : '精品',
			id : 4
		},
		{
			name : '客户－钣喷部',
			id : 5
		},
		{
			name : '批发',
			id : 6
		},
		{
			name : '柜台零售',
			id : 7
		},
		{
			name : '其它营业外收入',
			id : 8
		},
		{
			name : '油品',
			id : 9
		},
		{
			name : '轮胎',
			id : 10
		}
	];
	$scope.selectedCategoryOption = $scope.categoryOptions[0];
    $scope.setupReportDate();

	$scope.showReport();
}]);

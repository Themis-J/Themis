'use strict';

angular.module('postSalesExpense.controllers', []).controller('postSalesExpenseCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
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
			year : reportService.getCurrentYear(),
			monthOfYear : reportService.getMonthOfYear(),
			itemCategory : $scope.selectedCategoryOption.name,
			groupBy : 0
		};
		$scope.draw(restClient(config.currentMode).queryPostSalesExpenseReport, params);
	};
	
	$scope.draw = function (restClient, params) {
		Highcharts.theme = config.highChartsTheme;
		Highcharts.setOptions(Highcharts.theme);
		
		restClient(params, function(data) {
			var chartData = {
				id : 'report_post_sales_expense',
				title : $scope.selectedCategoryOption.name,
				yAxisTitle : $scope.selectedCategoryOption.name,
				series : {
					current : [],
					currentReference : [],
					currentPercentage : []
				},
				gridData : []
			}
			
			var chartCategories = [{
			    	categories : null
			}];
			var dealers = [];
			chartCategories[0].categories = dealers;
			var currentDetail = data.detail[0].detail;
			for (var i = 0; i < currentDetail.length; i++) {
				chartData.series.current[i] = currentDetail[i].expense.amount;
				chartData.series.currentPercentage[i] = currentDetail[i].expense.percentage * 100;
				chartData.series.currentReference[i] = currentDetail[i].expense.reference;

				chartData.gridData[i] = {
					id : null,
					name : null,
					maintenanceAmount : null,
					sparePartAmount : null,
					sheetSprayAmount : null,
					totalAmount : null
				};
				chartData.gridData[i].id = currentDetail[i].code;
				chartData.gridData[i].name = currentDetail[i].name;
				chartData.gridData[i].totalAmount = currentDetail[i].expense.amount;
				for (var k = 0; k < currentDetail[i].detail.length; k++) {
					if (currentDetail[i].detail[k].name == "维修部") {
						chartData.gridData[i].maintenanceAmount = currentDetail[i].detail[k].expense.amount;
					} else if (currentDetail[i].detail[k].name == "备件部") {
						chartData.gridData[i].sparePartAmount = currentDetail[i].detail[k].expense.amount;
					} else if (currentDetail[i].detail[k].name == "钣喷部") {
						chartData.gridData[i].sheetSprayAmount = currentDetail[i].detail[k].expense.amount;
					}
				}
				
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
						'维修部售后' + $scope.selectedCategoryOption.name,
						'备件部售后' + $scope.selectedCategoryOption.name,
						'钣喷部售后' + $scope.selectedCategoryOption.name,
						'经销商售后' + $scope.selectedCategoryOption.name
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
							name : 'maintenanceAmount',
							index : 'maintenanceAmount',
							width : 80,
							formatter : "number",
							align : "right",
							sorttype : "float",
							summaryType : "sum"
						},
						{
							name : 'sparePartAmount',
							index : 'sparePartAmount',
							width : 80,
							formatter : "number",
							align : "right",
							sorttype : "float",
							summaryType : "sum"
						},
						{
							name : 'sheetSprayAmount',
							index : 'sheetSprayAmount',
							width : 80,
							formatter : "number",
							align : "right",
							sorttype : "float",
							summaryType : "sum"
						},
						{
							name : 'totalAmount',
							index : 'totalAmount',
							width : 80,
							formatter : "number",
							sorttype : "float",
							align : "right"
						}
					],
					rowNum : 30,
					pager : '#report_pager',
					loadError : function(xhr, status, err) { 
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
					caption : "月均售后费用"
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
	var currentDate = new Date();
	reportService.setCurrentYear(currentDate.getFullYear());
	reportService.setMonthOfYear(currentDate.getMonth() + 1);
	$scope.yearOptions = reportService.getYearList();
	$scope.selectedYearOption = $scope.yearOptions[0];
	$scope.monthOptions = reportService.getMonthList();
	$scope.selectedMonthOption = $scope.monthOptions[reportService.getMonthOfYear() - 1];
	$scope.categoryOptions = [
 	    {
 	    	name : '销售费用',
 	    	id : 1
		},
		{
			name : '人工费用',
			id : 2
		},
		{
			name : '半固定费用',
			id : 3
		},
		{
			name : '固定费用',
			id : 4
		}
	];
	$scope.selectedCategoryOption = $scope.categoryOptions[0];
	$scope.showReport();
}]);
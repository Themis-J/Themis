'use strict';

angular.module('postSalesMargin.controllers', []).controller('postSalesMarginCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function($scope, restClient, reportService, config) {
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
	
	$scope.showReport = function() {
		var params = {
			year : reportService.getCurrentYear(),
			monthOfYear : reportService.getMonthOfYear(),
			groupBy : 0
		};
		$scope.draw(restClient(config.currentMode).queryPostSalesIncomeReport, params);
	};
	
	$scope.draw = function (restClient, params) {
		Highcharts.theme = config.highChartsTheme;
		Highcharts.setOptions(Highcharts.theme);
		
		restClient(params, function(data) {
			var chartData = {
				id: 'report_chart',
				title: '售后毛利',
				yAxisTitle: '售后毛利',
				series: {
					current : [],
					currentReference : [],
					currentPercentage : []
				},
				gridData : []
			};
			
			var chartCategories = [{
				categories : null
			}];
			var dealers = [];
			chartCategories[0].categories = dealers;
			var currentDetail = data.detail[0].detail;
			var j = 0;
			for (var i = 0; i < currentDetail.length; i++) {
				chartData.series.current[i] = currentDetail[i].margin.amount;
				chartData.series.currentPercentage[i] = currentDetail[i].margin.percentage * 100;
				chartData.series.currentReference[i] = currentDetail[i].margin.reference;
				for (var k = 0; k < currentDetail[i].detail.length; k++) {
					chartData.gridData[j] = {
						id : null,
						departmentName : null,
						name : null,
						amount : null,
						totalAmount : null
					};
					chartData.gridData[j].id = currentDetail[i].code;
					chartData.gridData[j].name = currentDetail[i].name;
					chartData.gridData[j].departmentName = currentDetail[i].detail[k].name;
					chartData.gridData[j].amount = currentDetail[i].detail[k].margin.amount.toFixed(2);
					chartData.gridData[j].totalAmount = currentDetail[i].margin.amount.toFixed(2);
					j++;
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
				    '部门',
				    '经销商代码',
				    '名称',
				    '部门售后毛利',
				    '经销商售后毛利'
				],
				colModel : [
				    {
				    	name : 'departmentName',
				    	index : 'departmentName',
				    	width : 55
				    },
				    {
				    	name : 'id',
				    	index : 'id',
				    	width : 55
				    },
				    {name:'name',index:'name', sorttype: function (cellValus, rowData) {return (rowData.totalAmount + rowData.id).lpad('0', 100);}, width:100},
				    {
				    	name : 'amount',
				    	index : 'amount',
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
				rowNum : 300,
				pager : '#report_pager',
				loadError : function(xhr, status, err) {
					try {
						jQuery.jgrid.info_dialog(jQuery.jgrid.errors.errcap,'<div class="ui-state-error">'+ xhr.responseText +'</div>', jQuery.jgrid.edit.bClose,{buttonalign:'right'});
					} catch(e) {
						alert(xhr.responseText);
					}},
				viewrecords : true,
				sortable : false,
				multiselect : false,
				width : chartWidth,
				height : "100%",
				grouping : true,
				groupingView : {
					groupField : ['name'],
					groupSummary : [true],
					groupColumnShow : [true],
					groupText : ['<b>{0}</b>'],
					groupCollapse : false,
					showSummaryOnHide : true,
					groupOrder : 'desc',
					groupDataSorted : true
				},
				caption : "月均售后毛利",
				sortname : "departmentName"
			});
			jQuery("#report_list").jqGrid('navGrid','#report_pager',{"edit":false,"add":false,"del":false,"search":true,"refresh":true,"view":false,"excel":false,"pdf":false,"csv":false,"columns":false});
			if ($scope.report_chart_display) {
				$('#report_chart').highcharts({
					chart : {
						zoomType : 'xy',
						height : $(window).height()*0.60,
						width : chartWidth
					},
					title : {
						text : currentData.title
					},
					subtitle : {
						text : chartSubtitle
					},
					xAxis : chartCategories,
					yAxis : [{
						title : {
							text : currentData.yAxisTitle
						},
						min:-10000
					}],
					tooltip : {
						formatter : function() {
							var tooltip = this.series.name + ': ' + this.y + '<br/>';
							return  tooltip;
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
    
    $scope.showChart = function() {
        reportService.setShowChart(!reportService.getShowChart());
        $scope.report_chart_display = reportService.getShowChart();
		if ( $scope.report_chart_display ) {
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
	
	// called on page is loaded
	$scope.showReport();
}]);

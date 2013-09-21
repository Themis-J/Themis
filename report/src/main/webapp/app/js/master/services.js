'use strict';

/* Services */
angular.module('masterApp.services', ['ngResource'])
    .factory('ReportRestClient', ['$resource', 'config', function ($resource, config) {
        return $resource(
        	//config.service.url + '/:path/:subpath', {}, {
            config.service.localUrl + '/:path/:subpath', {}, {
            queryOverallIncomeReport: {method: 'GET', params: {path: 'query', subpath:'overallIncomeReport'}, isArray: false},
            localQueryMonthlyOverallIncomeReport: {method: 'GET', params: {path: '', subpath:'queryMonthlyIncomeReport.json'}, isArray: false},
            localQueryYearlyOverallIncomeReport: {method: 'GET', params: {path: '', subpath:'queryYearlyIncomeReport.json'}, isArray: false},
            queryDepartments: {method: 'GET', params: {path: '', subpath:'department'}, isArray: false},
            localQueryDepartments: {method: 'GET', params: {path: '', subpath:'departments.json'}, isArray: false},
        });
    }
    ]).
    factory('ReportService', [function(){
    	var monthOfYear = -1;
    	var currentYear = -1;
    	var charts = [];
    	var isFullScreen = false;
        return {
        	setCurrentYear: function(year) {
        		currentYear = year;
        	},
        	setMonthOfYear: function(month){
        		monthOfYear = month;
        	},
        	setFullScreen: function(flag){
        		isFullScreen = flag;
        	},
        	getDepartments: function(restClient, params, callback) {
        		restClient(params, function(data) {
        			var departments = [];
        			for ( var i = 1; i < data.items.length; i++ ) {
        				departments[i-1]=data.items[i];
        			}
        			callback(departments);
        		});
        	},
            drawAbsOverallIncomeReport: function (restClient, params, index)
			{
				charts = [];
	        	Highcharts.theme = {
	                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
	                chart: {
	                    backgroundColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
	                        stops: [
	                            [0, 'rgb(255, 255, 255)'],
	                            [1, 'rgb(240, 240, 255)']
	                        ]
	                    },
	                    borderWidth: 0,
	                    plotBackgroundColor: 'rgba(255, 255, 255, .9)',
	                    plotShadow: true,
	                    plotBorderWidth: 1
	                },
	                title: {
	                    style: {
	                        color: '#000',
	                        font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                subtitle: {
	                    style: {
	                        color: '#666666',
	                        font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                xAxis: {
	                    gridLineWidth: 1,
	                    lineColor: '#000',
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	
	                        }
	                    }
	                },
	                yAxis: {
	                    minorTickInterval: 'auto',
	                    lineColor: '#000',
	                    lineWidth: 1,
	                    tickWidth: 1,
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	                        }
	                    }
	                },
	                legend: {
	                    itemStyle: {
	                        font: '9pt Trebuchet MS, Verdana, sans-serif',
	                        color: 'black'
	
	                    },
	                    itemHoverStyle: {
	                        color: '#039'
	                    },
	                    itemHiddenStyle: {
	                        color: 'gray'
	                    }
	                },
	                labels: {
	                    style: {
	                        color: '#99b'
	                    }
	                },
	
	                navigation: {
	                    buttonOptions: {
	                        theme: {
	                            stroke: '#CCCCCC'
	                        }
	                    }
	                }
	            };

            // Apply the theme
            var highchartsOptions = Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	// previous year
            	var previousDetail = data.detail[0].detail;
            	var previousRevenue = [];
            	var previousExpense = [];
            	var previousRevenueReference = [];
				var previousExpenseReference = [];
				var previousMargin = [];
            	var previousMarginReference = [];
            	var previousOpProfit = [];
            	var previousOpProfitReference = [];
            	var previousNetProfit = [];
            	var previousNetProfitReference = [];
				for ( var i in previousDetail ) {
            		dealers[i] = previousDetail[i].code;
            		previousRevenue[i] = previousDetail[i].revenue.amount;
            		previousRevenueReference[i] = previousDetail[i].revenue.reference;
            		previousExpense[i] = previousDetail[i].expense.amount;
            		previousExpenseReference[i] = previousDetail[i].expense.reference;
            		previousMargin[i] = previousDetail[i].margin.amount;
            		previousMarginReference[i] = previousDetail[i].margin.reference;
            		previousOpProfit[i] = previousDetail[i].opProfit.amount;
            		previousOpProfitReference[i] = previousDetail[i].opProfit.reference;
            		previousNetProfit[i] = previousDetail[i].netProfit.amount;
            		previousNetProfitReference[i] = previousDetail[i].netProfit.reference;
            	};
				chartCategories[0].categories = dealers;
				
            	// current year
            	var currentDetail = data.detail[1].detail;
            	var currentRevenue = [];
            	var currentExpense = [];
            	var currentMargin = [];
            	var currentRevenuePercentage = [];
            	var currentExpensePercentage = [];
            	var currentMarginPercentage = [];
            	var currentExpenseReference = [];
				var currentRevenueReference = [];
				var currentMarginReference = [];
				var currentOpProfit = [];
            	var currentOpProfitPercentage = [];
            	var currentOpProfitReference = [];
				var currentNetProfit = [];
            	var currentNetProfitPercentage = [];
            	var currentNetProfitReference = [];
				for ( var i in currentDetail ) {
            		currentRevenue[i] = currentDetail[i].revenue.amount;
            		currentRevenueReference[i] = currentDetail[i].revenue.reference;
            		currentExpense[i] = currentDetail[i].expense.amount;
            		currentExpenseReference[i] = currentDetail[i].expense.reference;
            		currentMargin[i] = currentDetail[i].margin.amount;
            		currentRevenuePercentage[i] = currentDetail[i].revenue.percentage * 100;
            		currentExpensePercentage[i] = currentDetail[i].expense.percentage * 100;
            		currentMarginPercentage[i] = currentDetail[i].margin.percentage * 100;
            		currentMarginReference[i] = currentDetail[i].margin.reference;
            		currentOpProfit[i] = currentDetail[i].opProfit.amount;
            		currentOpProfitPercentage[i] = currentDetail[i].opProfit.percentage * 100;
            		currentOpProfitReference[i] = currentDetail[i].opProfit.reference;
            		currentNetProfit[i] = currentDetail[i].netProfit.amount;
            		currentNetProfitPercentage[i] = currentDetail[i].netProfit.percentage * 100;
            		currentNetProfitReference[i] = currentDetail[i].netProfit.reference;
            	};
            	var chartSubtitle = '年度对比';
            	if ( monthOfYear != -1 ) {
            		chartSubtitle = '月对比';
            	}
            	
            	var chartColumnPrevious = '去年';
            	if ( monthOfYear != -1 ) {
            		chartColumnPrevious = '月均';
            	}
            	var chartColumnCurrent = '今年';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrent = '当月';
            	}
            	var chartColumnPreviousRef = '去年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnPreviousRef = '月均参考值';
            	}
            	var chartColumnCurrentRef = '今年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrentRef = '当月参考值';
            	}
		        var chartData = [
		        	{
		        		id: 'report_opProfit',
		        		title: '运营利润',
		        		yAxisTitle: '运营利润',
		        		series: [
		                    {
		                        data: previousOpProfit
		                    },
		                    {
		                        data: currentOpProfit
		                    },
		                    {
		                        data: previousOpProfitReference
		                    },
		                    {
		                        data: currentOpProfitReference
		                    },
		                    {
		                        data: currentOpProfitPercentage
		                    }
		                ]
		        	},
		        	{
		        		id: 'report_netProfit',
		        		title: '税前尽利润',
		        		yAxisTitle: '税前尽利润',
		        		series: [
		                    {
		                        data: previousNetProfit
		                    },
		                    {
		                        data: currentNetProfit
		                    },
		                    {
		                        data: previousNetProfitReference
		                    },
		                    {
		                        data: currentNetProfitReference
		                    },
		                    {
		                        data: currentNetProfitPercentage
		                    }
		                ]
		        	},
		        	{
		        		id: 'report_revenue',
		        		title: '营业额',
		        		yAxisTitle: '营业额',
		        		series: [
		                    {
		                        data: previousRevenue
		                    },
		                    {
		                        data: currentRevenue
		                    },
		                    {
		                        data: previousRevenueReference
		                    },
		                    {
		                        data: currentRevenueReference
		                    },
		                    {
		                        data: currentRevenuePercentage
		                    }
		                ]
		        	}, 
		        	{
		        		id: 'report_expense',
		        		title: '费用',
		        		yAxisTitle: '费用',
		        		series: [
		                    {
		                        data: previousExpense
		                    },
		                    {
		                        data: currentExpense
		                    },
		                    {
		                        data: previousExpenseReference
		                    },
		                    {
		                        data: currentExpenseReference
		                    },
		                    {
		                        data: currentExpensePercentage
		                    }
		                ]
		        	}, 
		        	{
		        		id: 'report_margin',
		        		title: '毛利',
		        		yAxisTitle: '毛利',
		        		series: [
		                    {
		                        data: previousMargin
		                    },
		                    {
		                        data: currentMargin
		                    },
		                    {
		                        data: previousMarginReference
		                    },
		                    {
		                        data: currentMarginReference
		                    },
		                    {
		                        data: currentMarginPercentage
		                    }
		                ]
		        	}
		        ]; 
		        var chartWidth = $(window).width() * 0.60;
		        if ( isFullScreen ) {
		        	chartWidth = $(window).width();
				}
		                    		
		        chartData = [chartData[index]];
		        for (var i=0;i<=chartData.length;i++) 
  				{
		        	var currentData = chartData[i];
		        
            		var chart = $('#' + currentData.id).highcharts({
		                chart: {
		                	zoomType: 'xy',
		                    height:$(window).height()*0.60,
		                    width: chartWidth,
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
		                        text: currentData.yAxisTitle
		                    },
		                    min:-10000
		                },
	                    {
	                        gridLineWidth: 0,
	                        title: {
	                            text: '增长百分比 (%)'
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
		                        name: chartColumnPrevious,
		                        data: currentData.series[0].data
		                    },
		                    {
		                        type: 'column',
		                        name: chartColumnCurrent,
		                        data: currentData.series[1].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnPreviousRef,
		                        data: currentData.series[2].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnCurrentRef,
		                        data: currentData.series[3].data
		                    },
		                    {
		                        type: 'spline',
		                        name: '增长比例(%)',
		                        yAxis: 1,
		                        data: currentData.series[4].data
		                    }
		                ]
		        	}).highcharts();
		        }
		        
			  });
			}, 
            drawPercentageOverallIncomeReport: function (restClient, params, index)
			{
	        	Highcharts.theme = {
	                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
	                chart: {
	                    backgroundColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
	                        stops: [
	                            [0, 'rgb(255, 255, 255)'],
	                            [1, 'rgb(240, 240, 255)']
	                        ]
	                    },
	                    borderWidth: 0,
	                    plotBackgroundColor: 'rgba(255, 255, 255, .9)',
	                    plotShadow: true,
	                    plotBorderWidth: 1
	                },
	                title: {
	                    style: {
	                        color: '#000',
	                        font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                subtitle: {
	                    style: {
	                        color: '#666666',
	                        font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                xAxis: {
	                    gridLineWidth: 1,
	                    lineColor: '#000',
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	
	                        }
	                    }
	                },
	                yAxis: {
	                    minorTickInterval: 'auto',
	                    lineColor: '#000',
	                    lineWidth: 1,
	                    tickWidth: 1,
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	                        }
	                    }
	                },
	                legend: {
	                    itemStyle: {
	                        font: '9pt Trebuchet MS, Verdana, sans-serif',
	                        color: 'black'
	
	                    },
	                    itemHoverStyle: {
	                        color: '#039'
	                    },
	                    itemHiddenStyle: {
	                        color: 'gray'
	                    }
	                },
	                labels: {
	                    style: {
	                        color: '#99b'
	                    }
	                },
	
	                navigation: {
	                    buttonOptions: {
	                        theme: {
	                            stroke: '#CCCCCC'
	                        }
	                    }
	                }
	            };

            // Apply the theme
            var highchartsOptions = Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	// previous year
            	var previousDetail = data.detail[0].detail;
            	var previousRevenue = [];
            	var previousExpense = [];
            	var previousRevenueReference = [];
				var previousExpenseReference = [];
				var previousMargin = [];
            	var previousMarginReference = [];
            	var previousOpProfit = [];
            	var previousOpProfitReference = [];
            	var previousNetProfit = [];
            	var previousNetProfitReference = [];
				for ( var i in previousDetail ) {
            		dealers[i] = previousDetail[i].code;
            		previousRevenue[i] = previousDetail[i].revenue.amount;
            		previousRevenueReference[i] = previousDetail[i].revenue.reference;
            		previousExpense[i] = previousDetail[i].expense.amount;
            		previousExpenseReference[i] = previousDetail[i].expense.reference;
            		previousMargin[i] = previousDetail[i].margin.amount;
            		previousMarginReference[i] = previousDetail[i].margin.reference;
            		previousOpProfit[i] = previousDetail[i].opProfit.amount;
            		previousOpProfitReference[i] = previousDetail[i].opProfit.reference;
            		previousNetProfit[i] = previousDetail[i].netProfit.amount;
            		previousNetProfitReference[i] = previousDetail[i].netProfit.reference;
            	};
				chartCategories[0].categories = dealers;
				
            	// current year
            	var currentDetail = data.detail[1].detail;
            	var currentRevenue = [];
            	var currentExpense = [];
            	var currentMargin = [];
            	var currentExpenseReference = [];
				var currentRevenueReference = [];
				var currentMarginReference = [];
				var currentOpProfit = [];
            	var currentOpProfitReference = [];
				var currentNetProfit = [];
            	var currentNetProfitReference = [];
				for ( var i in currentDetail ) {
            		currentRevenue[i] = currentDetail[i].revenue.amount;
            		currentRevenueReference[i] = currentDetail[i].revenue.reference;
            		currentExpense[i] = currentDetail[i].expense.amount;
            		currentExpenseReference[i] = currentDetail[i].expense.reference;
            		currentMargin[i] = currentDetail[i].margin.amount;
            		currentMarginReference[i] = currentDetail[i].margin.reference;
            		currentOpProfit[i] = currentDetail[i].opProfit.amount;
            		currentOpProfitReference[i] = currentDetail[i].opProfit.reference;
            		currentNetProfit[i] = currentDetail[i].netProfit.amount;
            		currentNetProfitReference[i] = currentDetail[i].netProfit.reference;
            	};
            	var chartSubtitle = '年度对比';
            	if ( monthOfYear != -1 ) {
            		chartSubtitle = '月对比';
            	}
            	
            	var chartColumnPrevious = '去年';
            	if ( monthOfYear != -1 ) {
            		chartColumnPrevious = '月均';
            	}
            	var chartColumnCurrent = '今年';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrent = '当月';
            	}
            	var chartColumnPreviousRef = '去年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnPreviousRef = '月均参考值';
            	}
            	var chartColumnCurrentRef = '今年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrentRef = '当月参考值';
            	}
		        var chartData = [
		        	{
		        		id: 'report_opProfit',
		        		title: '运营利润',
		        		yAxisTitle: '运营利润',
		        		series: [
		                    {
		                        data: previousOpProfit
		                    },
		                    {
		                        data: currentOpProfit
		                    },
		                    {
		                        data: previousOpProfitReference
		                    },
		                    {
		                        data: currentOpProfitReference
		                    },
		                ]
		        	},
		        	{
		        		id: 'report_netProfit',
		        		title: '税前尽利润',
		        		yAxisTitle: '税前尽利润',
		        		series: [
		                    {
		                        data: previousNetProfit
		                    },
		                    {
		                        data: currentNetProfit
		                    },
		                    {
		                        data: previousNetProfitReference
		                    },
		                    {
		                        data: currentNetProfitReference
		                    },
		                ]
		        	},
		        	{
		        		id: 'report_expense',
		        		title: '费用',
		        		yAxisTitle: '费用',
		        		series: [
		                    {
		                        data: previousExpense
		                    },
		                    {
		                        data: currentExpense
		                    },
		                    {
		                        data: previousExpenseReference
		                    },
		                    {
		                        data: currentExpenseReference
		                    },
		                ]
		        	}, 
		        	{
		        		id: 'report_margin',
		        		title: '毛利',
		        		yAxisTitle: '毛利',
		        		series: [
		                    {
		                        data: previousMargin
		                    },
		                    {
		                        data: currentMargin
		                    },
		                    {
		                        data: previousMarginReference
		                    },
		                    {
		                        data: currentMarginReference
		                    },
		                ]
		        	}
		        ]; 
		        chartData = [chartData[index]];
		        var chartWidth = $(window).width() * 0.60;
		        if ( isFullScreen ) {
		        	chartWidth = $(window).width();
				}
		        for (var i=0;i<=chartData.length;i++) 
  				{
		        	var currentData = chartData[i];
		        
            		var chart = $('#' + currentData.id).highcharts({
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
		                        text: currentData.yAxisTitle
		                    },
		                    min:-10000
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
		                        name: chartColumnPrevious,
		                        data: currentData.series[0].data
		                    },
		                    {
		                        type: 'column',
		                        name: chartColumnCurrent,
		                        data: currentData.series[1].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnPreviousRef,
		                        data: currentData.series[2].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnCurrentRef,
		                        data: currentData.series[3].data
		                    },
		                ]
		        	}).highcharts();
	            
		        }
		        
			  });
			},
            drawAbsDepartmentIncomeReport: function (restClient, params, index)
			{
				charts = [];
	        	Highcharts.theme = {
	                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
	                chart: {
	                    backgroundColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
	                        stops: [
	                            [0, 'rgb(255, 255, 255)'],
	                            [1, 'rgb(240, 240, 255)']
	                        ]
	                    },
	                    borderWidth: 0,
	                    plotBackgroundColor: 'rgba(255, 255, 255, .9)',
	                    plotShadow: true,
	                    plotBorderWidth: 1
	                },
	                title: {
	                    style: {
	                        color: '#000',
	                        font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                subtitle: {
	                    style: {
	                        color: '#666666',
	                        font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
	                    }
	                },
	                xAxis: {
	                    gridLineWidth: 1,
	                    lineColor: '#000',
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	
	                        }
	                    }
	                },
	                yAxis: {
	                    minorTickInterval: 'auto',
	                    lineColor: '#000',
	                    lineWidth: 1,
	                    tickWidth: 1,
	                    tickColor: '#000',
	                    labels: {
	                        style: {
	                            color: '#000',
	                            font: '11px Trebuchet MS, Verdana, sans-serif'
	                        }
	                    },
	                    title: {
	                        style: {
	                            color: '#333',
	                            fontWeight: 'bold',
	                            fontSize: '12px',
	                            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
	                        }
	                    }
	                },
	                legend: {
	                    itemStyle: {
	                        font: '9pt Trebuchet MS, Verdana, sans-serif',
	                        color: 'black'
	
	                    },
	                    itemHoverStyle: {
	                        color: '#039'
	                    },
	                    itemHiddenStyle: {
	                        color: 'gray'
	                    }
	                },
	                labels: {
	                    style: {
	                        color: '#99b'
	                    }
	                },
	
	                navigation: {
	                    buttonOptions: {
	                        theme: {
	                            stroke: '#CCCCCC'
	                        }
	                    }
	                }
	            };

            // Apply the theme
            var highchartsOptions = Highcharts.setOptions(Highcharts.theme); 
 			
            restClient(params, function(data) {
            	var chartCategories = [{ categories: null }];
            	var dealers = [];
            	// previous year
            	var previousDetail = data.detail[0].detail;
            	var previousRevenue = [];
            	var previousExpense = [];
            	var previousRevenueReference = [];
				var previousExpenseReference = [];
				var previousMargin = [];
            	var previousMarginReference = [];
            	var previousOpProfit = [];
            	var previousOpProfitReference = [];
				for ( var i in previousDetail ) {
            		dealers[i] = previousDetail[i].code;
            		previousRevenue[i] = previousDetail[i].revenue.amount;
            		previousRevenueReference[i] = previousDetail[i].revenue.reference;
            		previousExpense[i] = previousDetail[i].expense.amount;
            		previousExpenseReference[i] = previousDetail[i].expense.reference;
            		previousMargin[i] = previousDetail[i].margin.amount;
            		previousMarginReference[i] = previousDetail[i].margin.reference;
            		previousOpProfit[i] = previousDetail[i].opProfit.amount;
            		previousOpProfitReference[i] = previousDetail[i].opProfit.reference;
            	};
				chartCategories[0].categories = dealers;
				
            	// current year
            	var currentDetail = data.detail[1].detail;
            	var currentRevenue = [];
            	var currentExpense = [];
            	var currentMargin = [];
            	var currentRevenuePercentage = [];
            	var currentExpensePercentage = [];
            	var currentMarginPercentage = [];
            	var currentExpenseReference = [];
				var currentRevenueReference = [];
				var currentMarginReference = [];
				var currentOpProfit = [];
            	var currentOpProfitPercentage = [];
            	var currentOpProfitReference = [];
				for ( var i in currentDetail ) {
            		currentRevenue[i] = currentDetail[i].revenue.amount;
            		currentRevenueReference[i] = currentDetail[i].revenue.reference;
            		currentExpense[i] = currentDetail[i].expense.amount;
            		currentExpenseReference[i] = currentDetail[i].expense.reference;
            		currentMargin[i] = currentDetail[i].margin.amount;
            		currentRevenuePercentage[i] = currentDetail[i].revenue.percentage * 100;
            		currentExpensePercentage[i] = currentDetail[i].expense.percentage * 100;
            		currentMarginPercentage[i] = currentDetail[i].margin.percentage * 100;
            		currentMarginReference[i] = currentDetail[i].margin.reference;
            		currentOpProfit[i] = currentDetail[i].opProfit.amount;
            		currentOpProfitPercentage[i] = currentDetail[i].opProfit.percentage * 100;
            		currentOpProfitReference[i] = currentDetail[i].opProfit.reference;
            	};
            	var chartSubtitle = '年度对比';
            	if ( monthOfYear != -1 ) {
            		chartSubtitle = '月对比';
            	}
            	
            	var chartColumnPrevious = '去年';
            	if ( monthOfYear != -1 ) {
            		chartColumnPrevious = '月均';
            	}
            	var chartColumnCurrent = '今年';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrent = '当月';
            	}
            	var chartColumnPreviousRef = '去年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnPreviousRef = '月均参考值';
            	}
            	var chartColumnCurrentRef = '今年参考值';
            	if ( monthOfYear != -1 ) {
            		chartColumnCurrentRef = '当月参考值';
            	}
		        var chartData = [
		        	{
		        		id: 'report_opProfit',
		        		title: '运营利润',
		        		yAxisTitle: '运营利润',
		        		series: [
		                    {
		                        data: previousOpProfit
		                    },
		                    {
		                        data: currentOpProfit
		                    },
		                    {
		                        data: previousOpProfitReference
		                    },
		                    {
		                        data: currentOpProfitReference
		                    },
		                    {
		                        data: currentOpProfitPercentage
		                    }
		                ]
		        	},
		        	{
		        		id: 'report_revenue',
		        		title: '营业额',
		        		yAxisTitle: '营业额',
		        		series: [
		                    {
		                        data: previousRevenue
		                    },
		                    {
		                        data: currentRevenue
		                    },
		                    {
		                        data: previousRevenueReference
		                    },
		                    {
		                        data: currentRevenueReference
		                    },
		                    {
		                        data: currentRevenuePercentage
		                    }
		                ]
		        	}, 
		        	{
		        		id: 'report_expense',
		        		title: '费用',
		        		yAxisTitle: '费用',
		        		series: [
		                    {
		                        data: previousExpense
		                    },
		                    {
		                        data: currentExpense
		                    },
		                    {
		                        data: previousExpenseReference
		                    },
		                    {
		                        data: currentExpenseReference
		                    },
		                    {
		                        data: currentExpensePercentage
		                    }
		                ]
		        	}, 
		        	{
		        		id: 'report_margin',
		        		title: '毛利',
		        		yAxisTitle: '毛利',
		        		series: [
		                    {
		                        data: previousMargin
		                    },
		                    {
		                        data: currentMargin
		                    },
		                    {
		                        data: previousMarginReference
		                    },
		                    {
		                        data: currentMarginReference
		                    },
		                    {
		                        data: currentMarginPercentage
		                    }
		                ]
		        	}
		        ]; 
		        var chartWidth = $(window).width() * 0.60;
		        if ( isFullScreen ) {
		        	chartWidth = $(window).width();
				}
		                    		
		        chartData = [chartData[index]];
		        for (var i=0;i<=chartData.length;i++) 
  				{
		        	var currentData = chartData[i];
		        
            		var chart = $('#' + currentData.id).highcharts({
		                chart: {
		                	zoomType: 'xy',
		                    height:$(window).height()*0.60,
		                    width: chartWidth,
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
		                        text: currentData.yAxisTitle
		                    },
		                    min:-10000
		                },
	                    {
	                        gridLineWidth: 0,
	                        title: {
	                            text: '增长百分比 (%)'
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
		                        var tooltip = this.series.name +': '+ this.y +'<br/>';
		                        return  tooltip;
		                    },
		                    useHTML: true
		                },
		                plotOptions: {
		                    column: {
		                    	cursor: 'pointer',
		                    	point: {
			                        events: {
			                            click: function() {
			                            	window.alert('drilldown is not implemented yet');
			                                var drilldown = this.drilldown;
			                                if (drilldown) { // drill down
			                                    setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
			                                } else { // restore
			                                    setChart(name, categories, data);
			                                }
			                            }
			                        }
			                    },
		                    }
		                },
		                series: [
		                    {
		                        type: 'column',
		                        name: chartColumnPrevious,
		                        data: currentData.series[0].data
		                    },
		                    {
		                        type: 'column',
		                        name: chartColumnCurrent,
		                        data: currentData.series[1].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnPreviousRef,
		                        data: currentData.series[2].data
		                    },
		                    {
		                        type: 'spline',
		                        name: chartColumnCurrentRef,
		                        data: currentData.series[3].data
		                    },
		                    {
		                        type: 'spline',
		                        name: '增长比例(%)',
		                        yAxis: 1,
		                        data: currentData.series[4].data
		                    }
		                ]
		        	}).highcharts();
		        }
		        
			  });
			},
        };
    }])
    ;

'use strict';

angular.module('ardSales.controllers', [])
    .controller('ardSalesCtrl', ['$scope', 'ReportRestClient', 'ReportService', 'config', function ($scope, restClient, reportService, config) {
        /**
         * Global functions
         */
        $scope.selectReportYear = function () {
            reportService.setCurrentYear($scope.selectedYearOption.id);
            $scope.showReport();
        };
        $scope.selectReportMonth = function () {
            reportService.setMonthOfYear($scope.selectedMonthOption.id);
            $scope.showReport();
        };

        $scope.showReport = function () {
            var params = {year: reportService.getCurrentYear(), monthOfYear: reportService.getMonthOfYear(), itemName: '售前部门应收账款'};
            $scope.draw(restClient(config.currentMode).queryAccountReceivableReport, params);
        };

        $scope.draw = function (restClient, params) {
            Highcharts.theme = config.highChartsTheme;
            Highcharts.setOptions(Highcharts.theme);

            restClient(params, function (data) {
                var chartData = {
                    id: 'report_chart',
                    title: '售前应收总额',
                    yAxisTitle: '售前应收总额',
                    series: { previous: [], current: [], previousReference: [], currentReference: [], currentPercentage: [] },
                    gridData: []
                };
                var chartCategories = [
                    { categories: null }
                ];
                var dealers = [];

                chartCategories[0].categories = dealers;
                var currentDetail = data.detail[0].detail;
                for (var i = 0; i < currentDetail.length; i++) {
                    chartData.series.current[i] = currentDetail[i].amount.amount;
                    chartData.series.currentReference[i] = currentDetail[i].amount.reference;
                    chartData.gridData[i] = {id: null, name: null, amount: null};
                    chartData.gridData[i].id = currentDetail[i].code;
                    chartData.gridData[i].name = currentDetail[i].name;
                    chartData.gridData[i].brand = currentDetail[i].brand;
                    chartData.gridData[i].amount = currentDetail[i].amount.amount;
                }
                ;
                var chartSubtitle = '';
                var chartColumnCurrent = '当月';
                var chartColumnCurrentRef = '参考值';
                var chartWidth = $(window).width() * 0.60;
                if (reportService.getFullScreen()) {
                    chartWidth = $(window).width() * 0.90;
                }
                var currentData = chartData;

                jQuery("#report_list").jqGrid("GridUnload");
                jQuery("#report_list").jqGrid({
                    data: chartData.gridData,
                    datatype: "local",
                    colNames: ['经销商代码', '名称', '品牌', '售前应收总额'],
                    colModel: [
                        {name: 'id', index: 'id', width: 55},
                        {name: 'name', index: 'name', width: 100},
                        {name: 'brand', index: 'brand', width: 55},
                        {name: 'amount', index: 'amount', width: 80, sorttype: "float", formatter: "number", align: "right"}
                    ],
                    rowNum: 30,
                    pager: '#report_pager',
                    loadError: function (xhr, status, err) {
                        try {
                            jQuery.jgrid.info_dialog(jQuery.jgrid.errors.errcap, '<div class="ui-state-error">' + xhr.responseText + '</div>', jQuery.jgrid.edit.bClose, {buttonalign: 'right'});
                        } catch (e) {
                            alert(xhr.responseText);
                        }
                    },
                    sortname: 'amount',
                    viewrecords: true,
                    sortorder: "desc",
                    multiselect: false,
                    width: chartWidth,
                    height: "100%",
                    caption: "售前应收总额"
                });
                jQuery("#report_list").jqGrid('navGrid', '#report_pager', {"edit": false, "add": false, "del": false, "search": true, "refresh": true, "view": false, "excel": false, "pdf": false, "csv": false, "columns": false});
                if ($scope.report_chart_display) {
                    $('#report_chart').highcharts({
                        chart: {
                            zoomType: 'xy',
                            height: $(window).height() * 0.60,
                            width: chartWidth
                        },
                        title: {
                            text: currentData.title
                        },
                        subtitle: {
                            text: chartSubtitle
                        },
                        xAxis: chartCategories,
                        yAxis: [
                            {
                                title: {
                                    text: currentData.yAxisTitle
                                },
                                min: -10000
                            }
                        ],
                        tooltip: {
                            formatter: function () {
                                var tooltip = this.series.name + ': ' + this.y + '<br/>';
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
                                name: chartColumnCurrent,
                                data: currentData.series.current
                            },
                            {
                                type: 'spline',
                                name: chartColumnCurrentRef,
                                data: currentData.series.currentReference
                            }
                        ]
                    });
                }
            });
        };

        $scope.toggleFullScreen = function () {
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

        $scope.showChart = function () {
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

        $scope.setupReportDate();

        // called on page is loaded
        $scope.showReport();
    }]);

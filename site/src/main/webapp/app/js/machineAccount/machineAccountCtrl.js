'use strict';

angular.module('commonControllers', []).
    controller('machineAccountCtrl', ['$scope', '$route', '$location', '$routeParams', 'DealerService', 'Dealer', 'machineAccountService', 'UserService', '$timeout',
        function ($scope, $route, $location, $routeParams, DealerService, Dealer, machineAccountService, UserService, $timeout) {

            $scope.$on('$includeContentLoaded', function () {
            });
            $scope.userService = UserService;
            // vehicle or postSales
            $scope.type = "vehicle";
            $scope.typeString = "新车销售台账";

            $scope.metadataStore = {};
            $scope.metadata = {};
            $scope.data = {};
            $scope.summary = {};
            $scope.newData = {};
            $scope.list = [];
            $scope.message = {};
            $scope.vehicleQuery = {};
            $scope.postQuery = {};
            $scope.dealers = {};
            $scope.dealer = {};
            $scope.state = {
                listType: '',
                loading: false,
                hasMore: false,
                perPage: 10
            };
            $scope.header = 'partials/branch/header.html';

            if (UserService.validateRoleHead()) {
                $scope.header = 'partials/head/header.html';
            }

            var dealers = Dealer.list({}, function () {
                $scope.dealers = dealers.items;
                $scope.dealer = $scope.dealers[0];
            });

            $scope.refreshList = function (type) {
                if (type) {
                    $scope.type = type;
                }
                var dealerID = null;
                if ($scope.dealer.id) {
                    dealerID = $scope.dealer.id;
                }
                else {
                    dealerID = DealerService.getDealerId();
                }

                $scope.state.listType = 'normal';
                if ($scope.type == "vehicle") {
                    $scope.list = [];
                    $scope.state.loading = true;
                    machineAccountService.queryVehicleSalesLedgerData({dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                        function (data) {
                            $scope.state.loading = false;
                            $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                            $scope.list = data.summaries;
                        });
                }
                if ($scope.type == "postSales") {
                    $scope.list = [];
                    $scope.loading = true;
                    machineAccountService.queryPostSalesLedgerData({dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                        function (data) {
                            $scope.loading = false;
                            $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                            $scope.list = data.summaries;
                        });
                }
                $scope.panel = "partials/common/machineAccount/list.html";
            }

            $scope.showMore = function () {
                var dealerID = null;
                if ($scope.dealer.id) {
                    dealerID = $scope.dealer.id;
                }
                else {
                    dealerID = DealerService.getDealerId();
                }

                if ('normal' == $scope.state.listType) {
                    if ($scope.type == "vehicle") {
                        $scope.state.loading = true;
                        machineAccountService.queryVehicleSalesLedgerData({dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                            function (data) {
                                $scope.state.loading = false;
                                $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                                $scope.list = $scope.list.concat(data.summaries);
                            });
                    }
                    if ($scope.type == "postSales") {
                        $scope.state.loading = true;
                        machineAccountService.queryPostSalesLedgerData({dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                            function (data) {
                                $scope.state.loading = false;
                                $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                                $scope.list = $scope.list.concat(data.summaries);
                            });
                    }
                }
                else {
                    if ('queryV' == $scope.state.listType) {
                        $scope.state.loading = true;
                        machineAccountService.queryVehicleSalesLedgerData({contractNo: $scope.vehicleQuery.contractNo,
                                model: $scope.vehicleQuery.model, type: $scope.vehicleQuery.type, color: $scope.vehicleQuery.color,
                                lpNumber: $scope.vehicleQuery.lpNumber, frameNo: $scope.vehicleQuery.frameNo, manufacturerDebitDate: $scope.vehicleQuery.manufacturerDebitDate,
                                warehousingDate: $scope.vehicleQuery.warehousingDate, saleDate: $scope.vehicleQuery.saleDate, guidingPrice: $scope.vehicleQuery.guidingPrice,
                                customerName: $scope.vehicleQuery.customerName, identificationNo: $scope.vehicleQuery.identificationNo, salesConsultant: $scope.vehicleQuery.salesConsultant,
                                customerType: $scope.vehicleQuery.customerType, dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                            function (data) {
                                $scope.state.loading = false;
                                $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                                $scope.list = $scope.list.concat(data.summaries);
                            });
                    }

                    if ('queryP' == $scope.state.listType) {
                        $scope.state.loading = true;
                        machineAccountService.queryPostSalesLedgerData({workOrderNo: $scope.postQuery.workOrderNo, saleDate: $scope.postQuery.saleDate,
                                mileage: $scope.postQuery.mileage, lpNumber: $scope.postQuery.lpNumber, customerName: $scope.postQuery.customerName,
                                color: $scope.postQuery.color, frameNo: $scope.postQuery.frameNo, model: $scope.postQuery.model, enterFactoryDate: $scope.postQuery.enterFactoryDate,
                                exitFactoryDate: $scope.postQuery.exitFactoryDate, customerType: $scope.postQuery.customerType, insuranceAgency: $scope.postQuery.insuranceAgency,
                                insuranceDueDate: $scope.postQuery.insuranceDueDate, insuranceClaimNumber: $scope.postQuery.insuranceClaimNumber, dealerID: dealerID,
                                marker: $scope.list.length, limit: $scope.state.perPage},
                            function (data) {
                                $scope.state.loading = false;
                                $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                                $scope.list = $scope.list.concat(data.summaries);
                            });
                    }
                }
            }

            $scope.type = $routeParams.type;
            $scope.typeString = ($routeParams.type == "vehicle")? "新车销售台账": "售后台账";

            if ($routeParams.id) {

                if ($routeParams.type == "vehicle") {
                    $scope.data = {};
                    machineAccountService.getVehicleSalesLedgerData({contractNo: $routeParams.id},
                        function (data) {
                            if (data.vehicleSalesLedger && data.vehicleSalesLedger.length > 0) {
                                angular.forEach(data.vehicleSalesLedger[0], function (value, key) {
                                    $scope.data[key] = value;
                                });
                            }
                        });
                }
                if ($routeParams.type == "postSales") {
                    $scope.data = {};
                    machineAccountService.getPostSalesLedgerData({workOrderNo: $routeParams.id},
                        function (data) {
                            if (data.postSalesLedger && data.postSalesLedger.length > 0) {
                                angular.forEach(data.postSalesLedger[0], function (value, key) {
                                    $scope.data[key] = value;
                                });
                            }
                        });
                }

                $scope.panel = "partials/common/machineAccount/detail.html";
            }
            else {
                if ($routeParams.action == 'add') {
                    $scope.data = {};
                    $scope.panel = "partials/common/machineAccount/detail.html";
                }
                else {
                    if ($routeParams.action == 'list')
                    {
                        $scope.refreshList();
                    }
                }
            }

            machineAccountService.getLedgerMetadata({dealerID: DealerService.getDealerId()}, function (metadata) {
                $scope.metadataStore = metadata;

                if ($scope.type == 'vehicle') {
                    $scope.metadata = {};
                    angular.forEach($scope.metadataStore['vehicleSalesLedger'], function (meta, index) {
                        $scope.metadata[meta.name] = meta.options;
                    });
                }
                if ($scope.type == 'postSales') {
                    $scope.metadata = {};
                    angular.forEach($scope.metadataStore['postSalesLedger'], function (meta, index) {
                        $scope.metadata[meta.name] = meta.options;
                    });
                }
            });

            $scope.queryVehices = function () {
                var dealerID = null;
                if ($scope.dealer.id) {
                    dealerID = $scope.dealer.id;
                }
                else {
                    dealerID = DealerService.getDealerId();
                }
                $scope.state.loading = true;
                $scope.state.listType = 'queryV';
                $scope.list = [];
                machineAccountService.queryVehicleSalesLedgerData({contractNo: $scope.vehicleQuery.contractNo,
                        model: $scope.vehicleQuery.model, type: $scope.vehicleQuery.type, color: $scope.vehicleQuery.color,
                        lpNumber: $scope.vehicleQuery.lpNumber, frameNo: $scope.vehicleQuery.frameNo, manufacturerDebitDate: $scope.vehicleQuery.manufacturerDebitDate,
                        warehousingDate: $scope.vehicleQuery.warehousingDate, saleDate: $scope.vehicleQuery.saleDate, guidingPrice: $scope.vehicleQuery.guidingPrice,
                        customerName: $scope.vehicleQuery.customerName, identificationNo: $scope.vehicleQuery.identificationNo, salesConsultant: $scope.vehicleQuery.salesConsultant,
                        customerType: $scope.vehicleQuery.customerType, dealerID: dealerID, marker: $scope.list.length, limit: $scope.state.perPage},
                    function (data) {
                        $scope.state.loading = false;
                        $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                        $scope.list = data.summaries;
                    });

                $('#search_vehicle_modal').modal('hide');
                $scope.panel = "partials/common/machineAccount/list.html";
            }

            $scope.queryPosts = function () {
                var dealerID = null;
                if ($scope.dealer.id) {
                    dealerID = $scope.dealer.id;
                }
                else {
                    dealerID = DealerService.getDealerId();
                }
                $scope.state.loading = true;
                $scope.state.listType = 'queryP';
                $scope.list = [];
                machineAccountService.queryPostSalesLedgerData({workOrderNo: $scope.postQuery.workOrderNo, saleDate: $scope.postQuery.saleDate,
                        mileage: $scope.postQuery.mileage, lpNumber: $scope.postQuery.lpNumber, customerName: $scope.postQuery.customerName,
                        color: $scope.postQuery.color, frameNo: $scope.postQuery.frameNo, model: $scope.postQuery.model, enterFactoryDate: $scope.postQuery.enterFactoryDate,
                        exitFactoryDate: $scope.postQuery.exitFactoryDate, customerType: $scope.postQuery.customerType, insuranceAgency: $scope.postQuery.insuranceAgency,
                        insuranceDueDate: $scope.postQuery.insuranceDueDate, insuranceClaimNumber: $scope.postQuery.insuranceClaimNumber, dealerID: dealerID,
                        marker: $scope.list.length, limit: $scope.state.perPage},
                    function (data) {
                        $scope.state.loading = false;
                        $scope.state.hasMore = (data.summaries.length == $scope.state.perPage) ? true : false;
                        $scope.list = data.summaries;
                    });

                $('#search_post_modal').modal('hide');
                $scope.panel = "partials/common/machineAccount/list.html";
            }

            $scope.refeshPath = function () {
                if ($scope.type == 'vehicle') {
                    $scope.metadata = {};
                    angular.forEach($scope.metadataStore['vehicleSalesLedger'], function (meta, index) {
                        $scope.metadata[meta.name] = meta.options;
                    });
                }
                if ($scope.type == 'postSales') {
                    $scope.metadata = {};
                    angular.forEach($scope.metadataStore['postSalesLedger'], function (meta, index) {
                        $scope.metadata[meta.name] = meta.options;
                    });
                }
                $scope.list = [];

                $scope.contentPath = "partials/common/machineAccount/" + $scope.type + "/page.html";
            }

            $scope.refeshPath();

            $scope.save = function () {
                if ($scope.dealer.id) {
                    $scope.data.dealerID = $scope.dealer.id;
                }
                else {
                    $scope.data.dealerID = DealerService.getDealerId();
                }
                $scope.data.validDate = DealerService.getValidDate();
                $scope.data.updatedBy = UserService.getCurUserAlias();

                if ($scope.type == 'vehicle') {
                    machineAccountService.saveVehicleSalesLedgerData({}, $scope.data, function () {
                        $scope.message.text = "台账信息保存成功!";
                        $scope.message.class = "text-success";
                    }, function () {
                        $scope.message.text = "台账信息保存失败。";
                        $scope.message.class = "text-warning";
                    });
                }
                if ($scope.type == 'postSales') {
                    machineAccountService.savePostSalesLedgerData({}, $scope.data, function () {
                        $scope.message.text = "台账信息保存成功!";
                        $scope.message.class = "text-success";
                    }, function () {
                        $scope.message.text = "台账信息保存失败。";
                        $scope.message.class = "text-warning";
                    });
                }

                $("#new_modal").modal('hide');
            }

            $scope.showSearchForm = function () {
                if ($scope.type == 'vehicle') {
                    $("#search_vehicle_modal").modal({
                        'backdrop': false
                    });
                }

                if ($scope.type == 'postSales') {
                    $("#search_post_modal").modal({
                        'backdrop': false
                    });
                }
            }

            $scope.parseNumber = function (str) {
                if (!str) {
                    return 0;
                }
                return parseFloat(str.replace(/[^\d\.\-]/g, ""));
            }

            $scope.toggleRepairTypes = function(maintenanceType) {
                if ($scope.data['maintenanceType'])
                {
                    $scope.data['maintenanceType'] = $scope.data['maintenanceType'].split(",");
                }
                else
                {
                    $scope.data['maintenanceType'] = [];
                }

                var idx = $scope.data['maintenanceType'].indexOf(maintenanceType);

                // is currently selected
                if (idx > -1) {
                    $scope.data['maintenanceType'].splice(idx, 1);
                }

                // is newly selected
                else {
                    $scope.data['maintenanceType'].push(maintenanceType);
                }

                $scope.data['maintenanceType'] += "";
            };
        }])
    .controller('searchMachineAccountCtrl', ['$scope', '$route', '$location', '$routeParams', 'DealerService', 'Dealer', 'machineAccountService', 'UserService', '$timeout',
        function ($scope, $route, $location, $routeParams, DealerService, Dealer, machineAccountService, UserService, $timeout) {

        }]);
angular.module('lirun.controller', [])
    .controller('lirunCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', '$q', '$timeout',
        function ($scope, Dealer, DealerService, $filter, $q, $timeout) {
            $scope.$on('$destroy', function () {
                $("#page_nav").empty();
            });

            $scope.vehicleSummary = [];
            $scope.salesSummary = [];
            $scope.generalSummary = [];

            $scope.autoSaveVehivleRevenue = function (vehivleRevenue) {
                if (!this.form.$invalid) {
                    var postData = {};
                    postData.dealerID = DealerService.getDealerId();
                    postData.departmentID = DealerService.getSelectedDept();
                    postData.validDate = DealerService.getValidDate();
                    postData.updateBy = DealerService.getUserName();
                    postData.detail = [];
                    postData.detail.push({
                        vehicleID: vehivleRevenue.id,
                        amount: vehivleRevenue.amount,
                        margin: vehivleRevenue.margin,
                        count: vehivleRevenue.count
                    });

                    var success = function () {
                        vehivleRevenue.sign = "icon-check-sign green";
                        var currentDate = new Date();
                        $scope.autoSaveTime = "上次自动保存于" + currentDate.getHours() + "点" + currentDate.getMinutes() + "分" + currentDate.getSeconds() + "秒";
                        $scope.autoSaveClass = "text-success";
                        $scope.markEdit();
                    };

                    var failed = function () {
                        vehivleRevenue.sign = "icon-remove-sign red";
                        $scope.autoSaveTime = "自动保存失败";
                        $scope.autoSaveClass = "text-error";
                    };

                    Dealer.saveVehicleRevenue({}, postData, $.proxy(success, this), $.proxy(failed, this));
                }
            }

            $scope.autoSaveSalesRevenue = function (sale) {
                if (!this.form.$invalid) {
                    var postData = {};
                    postData.dealerID = DealerService.getDealerId();
                    postData.departmentID = DealerService.getSelectedDept();
                    postData.validDate = DealerService.getValidDate();
                    postData.updateBy = DealerService.getUserName();
                    postData.detail = [];
                    postData.detail.push({
                        itemID: sale.id,
                        amount: sale.amount,
                        margin: sale.margin,
                        count: sale.count
                    });

                    function success() {
                        sale.sign = "icon-check-sign green";
                        var currentDate = new Date();
                        $scope.autoSaveTime = "上次自动保存于" + currentDate.getHours() + "点" + currentDate.getMinutes() + "分" + currentDate.getSeconds() + "秒";
                        $scope.autoSaveClass = "text-success";
                        $scope.markEdit();
                    };

                    function failed() {
                        sale.sign = "icon-remove-sign red";
                        $scope.autoSaveTime = "自动保存失败";
                        $scope.autoSaveClass = "text-error";
                    };

                    Dealer.saveSalesRevenue({}, postData, $.proxy(success, this), $.proxy(failed, this));
                }
            }

            $scope.sales = [];
            $scope.vehicleRevenues = [];
            $scope.generalSales = [];
            $scope.promises = []

            var salesSet = [];
            var salesDefer = $q.defer();
            $scope.promises.push(salesDefer.promise);
            var saleItems = Dealer.getSales({}, function () {
                $.each(saleItems.items, function (index, saleItem) {
                    saleItem.sign = "";
                    salesSet[saleItem.id] = saleItem;
                    $scope.salesSummary[saleItem.categoryID] = 0;
                });

                var saleRevenues = Dealer.getSalesRevenue({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                    function () {
                        $.each(saleRevenues.detail, function (index, saleRevenue) {
                            var oneSale = salesSet[saleRevenue.itemID];
                            oneSale.count = saleRevenue.count;
                            oneSale.amount = saleRevenue.amount;
                            oneSale.margin = saleRevenue.margin;
                            if (!isNaN(oneSale.amount)) {
                                $scope.salesSummary[oneSale.categoryID] += Number(oneSale.amount);
                            }
                        });

                        $.each(salesSet, function (index, sale) {
                            if (sale && sale.id) {
                                $scope.sales.push(sale);
                            }
                        });
                        salesDefer.resolve();
                    })
            });

            if (DealerService.getSelectedDept() == 1) {
                var vehicleDefer = $q.defer();
                $scope.promises.push(vehicleDefer.promise);
                var vehicleSet = [];
                var vehicles = Dealer.getVehicles({}, function () {
                    $.each(vehicles.items, function (index, vehicle) {
                        vehicle.sign = "";
                        vehicleSet[vehicle.id] = vehicle;
                        $scope.vehicleSummary[vehicle.categoryID] = 0;
                    });

                    var vehiclesRevenue = Dealer.getVehicleRevenue({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                        function () {
                            $.each(vehiclesRevenue.detail, function (index, vehicle) {
                                var oneVehicle = vehicleSet[vehicle.vehicleID];
                                oneVehicle.count = vehicle.count;
                                oneVehicle.amount = vehicle.amount;
                                oneVehicle.margin = vehicle.margin;
                                if (!isNaN(oneVehicle.amount)) {
                                    $scope.vehicleSummary[vehicle.categoryID] += Number(oneVehicle.amount);
                                }
                            });

                            $.each(vehicleSet, function (index, vehicle) {
                                if (vehicle && vehicle.id) {
                                    $scope.vehicleRevenues.push(vehicle);
                                }
                            });

                            $timeout(function () {
                                $scope.setupTooltip();
                            });

                            vehicleDefer.resolve();
                        }
                    );
                });
            }

            if (DealerService.getSelectedDept() == 7) {
                var generalSet = [];
                var generalDefer = $q.defer();
                $scope.promises.push(generalDefer.promise);
                var genetalItems = Dealer.getGeneral({}, function () {
                    $.each(genetalItems.items, function (index, saleItem) {
                        saleItem.sign = "";
                        generalSet[saleItem.id] = saleItem;
                        $scope.generalSummary[saleItem.categoryID] = 0;
                    });

                    var generalRevenues = Dealer.getGeneralJournal({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                        function () {
                            $.each(generalRevenues.detail, function (index, saleRevenue) {
                                var oneSale = generalSet[saleRevenue.itemID];
                                oneSale.amount = saleRevenue.amount;
                                if (!isNaN(oneSale.amount)) {
                                    $scope.generalSummary[oneSale.categoryID] += Number(oneSale.amount);
                                }
                            });

                            $.each(generalSet, function (index, sale) {
                                if (sale && sale.id) {
                                    $scope.generalSales.push(sale);
                                }
                            });

                            generalDefer.resolve();
                        })
                });
            }

            $q.all($scope.promises)
                .then(function (values) {
                    $timeout(function () {
                        $scope.setupTooltip();
                        $scope.setupNav();
                    });
                });
        }]);
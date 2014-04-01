angular.module('fenhong.controller', [])
    .controller('fenhongCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', '$timeout',
        function ($scope, Dealer, DealerService, $filter, $timeout) {
            $scope.generalSummary = [];
            $scope.generalSales = [];

            $scope.$on('$destroy', function () {
                $("#page_nav").empty();
            });

            var salesSet = [];
            var saleItems = Dealer.getGeneral({}, function () {
                $.each(saleItems.items, function (index, saleItem) {
                    saleItem.sign = "";
                    salesSet[saleItem.id] = saleItem;
                    $scope.generalSummary[saleItem.categoryID] = 0;
                });

                var saleRevenues = Dealer.getGeneralJournal({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                    function () {
                        $.each(saleRevenues.detail, function (index, saleRevenue) {
                            var oneSale = salesSet[saleRevenue.itemID];
                            oneSale.amount = saleRevenue.amount;
                            if (!isNaN(oneSale.amount)) {
                                $scope.generalSummary[oneSale.categoryID] += Number(oneSale.amount);
                            }
                        });

                        $.each(salesSet, function (index, sale) {
                            if (sale && sale.id) {
                                $scope.generalSales.push(sale);
                            }
                        });

                        $timeout(function () {
                            $scope.setupTooltip();
                            $scope.setupNav();
                        });
                    })
            });
        }]);
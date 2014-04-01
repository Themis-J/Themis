angular.module('sunyi.controller', [])
    .controller('sunyiCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', '$timeout',
        function ($scope, Dealer, DealerService, $filter, $timeout) {
            $scope.$on('$destroy', function () {
                $("#page_nav").empty();
            });

            $scope.isJinxiangActive = true;
            $scope.isXiaoxiangActive = true;

            $scope.generalSummary = [];
            $scope.generalSales = [];

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

            $scope.toggleEdit = function () {
                var postData = {};
                postData.dealerID = DealerService.getDealerId();
                postData.itemID = DealerService.getSelectedMenu();
                postData.validDate = DealerService.getValidDate();
                postData.updateBy = DealerService.getUserName();

                Dealer.saveStatus({}, postData, function () {
                    var navLink = $("#" + DealerService.getSelectedMenu());
                    navLink.children().remove();
                    $scope.$parent.$parent.editMenus.push(parseInt(DealerService.getSelectedMenu()));
                    navLink.append($('<i class="icon-edit" style="color:green;display:inline"></i>'));
                });
            }
        }]);
angular.module('kucun.controller', [])
    .controller('kucunCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', '$timeout', 'UserService',
        function ($scope, Dealer, DealerService, $filter, $timeout, UserService) {

            $scope.$on('$destroy', function () {
                $("#page_nav").empty();
            });

            $scope.accountReceivables = [];
            var salesSet = [];
            var accountItems = Dealer.getInventory({}, function () {
                $.each(accountItems.items, function (index, accountItem) {
                    accountItem.data = [];
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "0-30 天",
                        durationID : 1
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "31-60 天",
                        durationID : 2
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "61-90 天",
                        durationID : 3
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "超过90 天",
                        durationID : 4
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "0-6 月",
                        durationID : 5
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "7-9 月",
                        durationID : 6
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "10-12 月",
                        durationID : 7
                    });
                    accountItem.data.push({
                        active : false,
                        id : accountItem.id,
                        durationDesc : "超过12 月",
                        durationID : 8
                    });

                    salesSet[accountItem.id] = accountItem;
                });

                var accountReceivables = Dealer.getInventoryDuration({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                    function () {
                        $.each(accountReceivables.detail, function (index, accountReceivable) {
                            var oneSale = salesSet[accountReceivable.itemID];
                            oneSale.data[accountReceivable.durationID - 1].amount = accountReceivable.amount;
                        });

                        $.each(salesSet, function (index, sale) {
                            if (sale && sale.id) {
                                $scope.accountReceivables.push(sale);
                            }
                        });

                        $timeout(function () {
                            $scope.setupTooltip();
                            $scope.setupNav();
                        });
                    })
            });

            $scope.autoSaveAccountReceivables = function (accountInRange) {
                if (this.form.$invalid) {
                    return;
                }
                var postData = {};
                postData.dealerID = DealerService.getDealerId();
                postData.departmentID = DealerService.getSelectedDept();
                postData.validDate = DealerService.getValidDate();
                postData.updateBy = UserService.getCurUserAlias();
                postData.detail = [];
                postData.detail.push({
                    itemID: accountInRange.id,
                    amount: accountInRange.amount,
                    durationID: accountInRange.durationID
                });

                var success = function () {
                    accountInRange.sign = "icon-check-sign green";
                    var currentDate = new Date();
                    $scope.autoSaveTime = "上次自动保存于" + currentDate.getHours() + "点" + currentDate.getMinutes() + "分" + currentDate.getSeconds() + "秒";
                    $scope.autoSaveClass = "text-success";
                    $scope.markEdit();
                };

                var failed = function () {
                    accountInRange.sign = "icon-remove-sign red";
                    $scope.autoSaveTime = "自动保存失败";
                    $scope.autoSaveClass = "text-error";
                }

                Dealer.saveInventoryDuration({}, postData, $.proxy(success, this), $.proxy(failed, this));
            }
        }]);
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
                    accountItem.data[1] = {};
                    accountItem.data[1].active = false;
                    accountItem.data[1].id = accountItem.id;
                    accountItem.data[1].durationDesc = "0-30 天";
                    accountItem.data[1].durationID = 1;
                    accountItem.data[2] = {};
                    accountItem.data[2].active = false;
                    accountItem.data[2].durationDesc = "31-60 天";
                    accountItem.data[2].durationID = 2;
                    accountItem.data[2].id = accountItem.id;
                    accountItem.data[3] = {};
                    accountItem.data[3].active = false;
                    accountItem.data[3].durationDesc = "61-90 天";
                    accountItem.data[3].durationID = 3;
                    accountItem.data[3].id = accountItem.id;
                    accountItem.data[4] = {};
                    accountItem.data[4].active = false;
                    accountItem.data[4].durationDesc = "超过90 天";
                    accountItem.data[4].durationID = 4;
                    accountItem.data[4].id = accountItem.id;
                    accountItem.data[5] = {};
                    accountItem.data[5].active = false;
                    accountItem.data[5].durationDesc = "0-6 月";
                    accountItem.data[5].durationID = 5;
                    accountItem.data[5].id = accountItem.id;
                    accountItem.data[6] = {};
                    accountItem.data[6].active = false;
                    accountItem.data[6].durationDesc = "7-9 月";
                    accountItem.data[6].durationID = 6;
                    accountItem.data[6].id = accountItem.id;
                    accountItem.data[7] = {};
                    accountItem.data[7].active = false;
                    accountItem.data[7].durationDesc = "10-12 月";
                    accountItem.data[7].durationID = 7;
                    accountItem.data[7].id = accountItem.id;
                    accountItem.data[8] = {};
                    accountItem.data[8].active = false;
                    accountItem.data[8].durationDesc = "超过12 月";
                    accountItem.data[8].durationID = 8;
                    accountItem.data[8].id = accountItem.id;

                    salesSet[accountItem.id] = accountItem;
                });

                var accountReceivables = Dealer.getInventoryDuration({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                    function () {
                        $.each(accountReceivables.detail, function (index, accountReceivable) {
                            var oneSale = salesSet[accountReceivable.itemID];
                            oneSale.data[accountReceivable.durationID].durationID = accountReceivable.durationID;
                            oneSale.data[accountReceivable.durationID].amount = accountReceivable.amount;
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
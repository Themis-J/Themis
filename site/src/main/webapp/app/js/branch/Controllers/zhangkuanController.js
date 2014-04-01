angular.module('zhangkuan.controller', [])
    .controller('zhangkuanCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', '$timeout',
        function ($scope, Dealer, DealerService, $filter, $timeout) {
            $scope.$on('$destroy', function () {
                $("#page_nav").empty();
            });

            $scope.accountReceivables = [];

            $scope.calSummary = function (accountReceivable) {
                var summary = 0;
                $.each(accountReceivable.data, function (index, data) {
                    if (data && data.amount) {
                        summary += Number(data.amount);
                    }
                });
                accountReceivable.summary = summary;
            }

            $scope.autoSaveAccountReceivables = function (accountInRange) {
                if (isNaN(accountInRange.amount)) {
                    return;
                }
                var postData = {};
                postData.dealerID = DealerService.getDealerId();
                postData.validDate = DealerService.getValidDate();
                postData.updateBy = DealerService.getUserName();
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

                Dealer.saveAccountDuration({}, postData, $.proxy(success, this), $.proxy(failed, this));
            }

            var salesSet = [];
            var accountItems = Dealer.getAccountItems({}, function () {
                $.each(accountItems.items, function (index, accountItem) {
                    accountItem.data = [];
                    accountItem.data[1] = {};
                    accountItem.data[1].sign = "";
                    accountItem.data[1].id = accountItem.id;
                    accountItem.data[1].durationDesc = "0-30 天";
                    accountItem.data[1].durationID = 1;
                    accountItem.data[2] = {};
                    accountItem.data[2].sign = "";
                    accountItem.data[2].durationDesc = "31-60 天";
                    accountItem.data[2].durationID = 2;
                    accountItem.data[2].id = accountItem.id;
                    accountItem.data[3] = {};
                    accountItem.data[3].sign = "";
                    accountItem.data[3].durationDesc = "61-90 天";
                    accountItem.data[3].durationID = 3;
                    accountItem.data[3].id = accountItem.id;
                    accountItem.data[4] = {};
                    accountItem.data[4].sign = "";
                    accountItem.data[4].durationDesc = "超过90 天";
                    accountItem.data[4].durationID = 4;
                    accountItem.data[4].id = accountItem.id;
                    accountItem.summary = 0;
                    salesSet[accountItem.id] = accountItem;
                });

                var accountReceivables = Dealer.getAccountDuration({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()},
                    function () {
                        $.each(accountReceivables.detail, function (index, accountReceivable) {
                            var oneSale = salesSet[accountReceivable.itemID];
                            oneSale.data[accountReceivable.durationID].durationID = accountReceivable.durationID;
                            oneSale.data[accountReceivable.durationID].amount = accountReceivable.amount;
                        });

                        $.each(salesSet, function (index, receivable) {
                            if (receivable && receivable.id) {
                                var summary = 0;
                                $.each(receivable.data, function (index, data) {
                                    if (data && data.amount) {
                                        summary += data.amount;
                                    }
                                })
                                receivable.summary = summary;
                            }
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
        }]);
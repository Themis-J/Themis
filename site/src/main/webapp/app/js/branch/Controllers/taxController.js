angular.module('tax.controller', [])
    .controller('taxCtrl', ['$scope', 'Dealer', 'DealerService', '$timeout', 'UserService',
        function ($scope, Dealer, DealerService, $timeout, UserService) {
        $scope.$on('$destroy', function () {
            $("#page_nav").empty();
        });

        $scope.tax = null;
        $scope.active = false;
        var tax = Dealer.getTaxs({dealerID: DealerService.getDealerId(), validDate: DealerService.getValidDate(), departmentID: DealerService.getSelectedDept()}, function () {
            $scope.tax = tax.tax;
            $scope.tooltip = tax.tooltip;

            $timeout(function () {
                $scope.setupTooltip();
                $scope.setupNav();
            });
        });

        $scope.autoSaveTax = function () {
            if (this.form.$invalid) {
                return;
            }
            var postData = {};
            postData.dealerID = DealerService.getDealerId();
            postData.validDate = DealerService.getValidDate();
            postData.updateBy = UserService.getCurUserAlias();
            postData.tax = $scope.tax;

            var success = function () {
                $scope.sign = "icon-check-sign green";
                var currentDate = new Date();
                $scope.autoSaveTime = "上次自动保存于" + currentDate.getHours() + "点" + currentDate.getMinutes() + "分" + currentDate.getSeconds() + "秒";
                $scope.autoSaveClass = "text-success";
                $scope.markEdit();
            };

            var failed = function () {
                $scope.sign = "icon-remove-sign red";
                $scope.autoSaveTime = "自动保存失败";
                $scope.autoSaveClass = "text-error";
            }

            Dealer.saveTax({}, postData, $.proxy(success, this), $.proxy(failed, this));
        }
    }]);
angular.module('branch.base.controller', [])
    .controller('branchBaseCtrl', ['$scope', 'Dealer', 'DealerService', '$filter', function ($scope, Dealer, DealerService, $filter) {

        $scope.autoSaveGeneralRevenue = function (generalSale) {
            if (!this.form.$invalid) {
                var postData = {};
                postData.dealerID = DealerService.getDealerId();
                postData.departmentID = DealerService.getSelectedDept();
                postData.validDate = DealerService.getValidDate();
                postData.updateBy = DealerService.getUserName();
                postData.detail = [];
                postData.detail.push({
                    itemID: generalSale.id,
                    amount: generalSale.amount
                });

                var success = function () {
                    generalSale.sign = "icon-check-sign green";
                    var currentDate = new Date();
                    $scope.autoSaveTime = "上次自动保存于" + currentDate.getHours() + "点" + currentDate.getMinutes() + "分" + currentDate.getSeconds() + "秒";
                    $scope.autoSaveClass = "text-success";
                    $scope.markEdit();
                };

                var failed = function () {
                    generalSale.sign = "icon-remove-sign red";
                    $scope.autoSaveTime = "自动保存失败";
                    $scope.autoSaveClass = "text-error";
                }

                Dealer.saveGeneralJournal({}, postData, $.proxy(success, this), $.proxy(failed, this));
            }
        }

        $scope.amountChange = function (summary, list, curOne) {
            if (!this.form.amount.$invalid) {
                var lists = eval(list);
                var newSummary = 0;
                var filter = {categoryID: curOne.categoryID};
                var filtered = $filter('filter')(lists, filter);
                $.each(filtered, function (index, filteredOne) {
                    if (!isNaN(filteredOne.amount)) {
                        newSummary += Number(filteredOne.amount);
                    }
                });
                var summary = eval(summary);
                summary[curOne.categoryID] = newSummary;
            }
        }

        $scope.markEdit = function () {
            var postData = {};
            postData.dealerID = DealerService.getDealerId();
            postData.itemID = DealerService.getSelectedMenu();
            postData.validDate = DealerService.getValidDate();
            postData.updateBy = DealerService.getUserName();

            Dealer.saveStatus({}, postData, function () {
                var navLink = $("#" + DealerService.getSelectedMenu());
                navLink.children().remove();
                $scope.editMenus.push(parseInt(DealerService.getSelectedMenu()));
                navLink.append($('<i class="icon-edit" style="color:green;display:inline"></i>'));

                if ($('#collapseOne').find('i.icon-edit').size() == 7) {
                    $('#one').children('i').remove();
                    $('#one').append($('<i class="icon-edit" style="color:green;display:inline"></i>'));
                }

                if ($('#collapseTwo').find('i.icon-edit').size() == 7) {
                    $('#two').children('i').remove();
                    $('#two').append($('<i class="icon-edit" style="color:green;display:inline"></i>'));
                }

                if ($('#collapsFive').find('i.icon-edit').size() == 3) {
                    $('#five').children('i').remove();
                    $('#five').append($('<i class="icon-edit" style="color:green;display:inline"></i>'));
                }
                if ($('#collapsSix').find('i.icon-edit').size() == 2) {
                    $('#six').children('i').remove();
                    $('#six').append($('<i class="icon-edit" style="color:green;display:inline"></i>'));
                }
            });
        }

        $scope.setupTooltip = function () {
            $('.hasTooltip').each(function () {
                $(this).qtip({
                    content: {
                        text: $(this).next('div')
                    },
                    hide: {
                        event: 'unfocus'
                    },
                    position: {
                        at: 'bottom left',
                        target: $(this)
                    },
                    style: {
                        def: false,
                        classes: 'tip qtip-rounded qtip-bootstrap'
                    }
                });
            });
        }

        $scope.setupNav = function () {
            $("#edit_panel").scrollNav({
                sections: 'h4',
                sectionElem: 'section',
                insertTarget: $("#page_nav"),
                insertLocation: 'appendTo',
                fixedMargin: 50,
                showTopLink: false,
                showHeadline: false,
                arrowKeys: true,
                animated: true
            });
        }
    }]);
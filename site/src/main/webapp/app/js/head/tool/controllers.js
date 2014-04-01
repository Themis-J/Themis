'use strict';

angular.module('head.tool.controllers', [])
    .controller('headToolCtrl', ['$scope', '$location', 'Auth', 'UserService', function ($scope, $location, Auth, UserService) {
        $scope.innerPage = 'partials/head/tool/changePass.html';

        $scope.jumpTo = function (page) {
            $scope.innerPage = 'partials/head/tool/' + page + '.html';
        }
    }])
    .controller('headChangePassCtrl', ['$scope', '$location', 'Auth', 'UserService', 'Dealer', 'User', function ($scope, $location, Auth, UserService, Dealer, User) {
        $scope.userAlias = '';
        $scope.curPassword = "";
        $scope.password1 = "";
        $scope.password2 = "";

        $scope.msg = "";
        $scope.msgClass = "";

        $scope.resetPass = function () {
            var user = {};
            user.username = UserService.getCurUserAlias();
            user.oldPassword = $scope.curPassword;
            user.newPassword = $scope.password1;
            User.resetPass({}, user, function () {
                $scope.msg = "密码修改成功！";
                $scope.msgClass = "text-success";
            }, function (data) {
                $scope.msg = "密码修改失败，请稍后再试或联系管理员。";
                $scope.msgClass = "text-error";
            });
        }

    }])
;
'use strict';

/* Directives */


angular.module('themis.directives', []).
    directive('passEqual', [function () {
        return {
            require: 'ngModel',
            link: function (scope, ele, attrs, c) {
                scope.$watch(attrs.ngModel, function () {
                    if (scope.password1 && scope.password2) {
                        if (scope.password1 == scope.password2) {
                            scope.the_form.inputPassword1.$setValidity('equal', true);
                            scope.the_form.inputPassword2.$setValidity('equal', true);
                        }
                        else {
                            c.$setValidity('equal', false);
                        }
                    }
                });
            }
        }
    }])
    .directive('bindHtmlUnsafe', function ($compile) {
        return function ($scope, $element, $attrs) {
            var compile = function (newHTML) { // Create re-useable compile function
                newHTML = $compile(newHTML)($scope); // Compile html
                $element.html('').append(newHTML); // Clear and append it
            };

            var htmlName = $attrs.bindHtmlUnsafe; // Get the name of the variable
            // Where the HTML is stored

            $scope.$watch(htmlName, function (newHTML) { // Watch for changes to
                // the HTML
                if (!newHTML) return;
                compile(newHTML);   // Compile it
            });

        }
    })
    .directive('pagination', function ($parse, $q) {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: 'partials/directives/pagination.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.data = $parse(attrs.data)(scope.$parent);
                scope.loadFunc = $parse(attrs.loadFunc)(scope.$parent);
                scope.countAllFunc = $parse(attrs.countAllFunc)(scope.$parent);
                scope.countAllFunc().then(function (data) {
                    return scope.allNum = data;
                });
                scope.loadFunc(1).then(function () {
                    return scope.data = $parse(attrs.data)(scope.$parent);
                });
                ;

                scope.has_more = function () {
                    return scope.data.length < scope.allNum;
                };
                scope.loaded = true;
                return scope.show_more = function () {
                    var countPromise, loadPromise,
                        _this = this;
                    scope.loaded = false;
                    loadPromise = scope.loadFunc(scope.data.length + 1).then(function () {
                        return scope.data = $parse(attrs.data)(scope.$parent);
                    });
                    countPromise = scope.countAllFunc().then(function (data) {
                        return scope.allNum = data;
                    });
                    return $q.all([loadPromise, countPromise]).then(function () {
                        return scope.loaded = true;
                    });
                };
            }
        };
    })
    .directive('d20', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.summary = $parse(attrs.summary)(scope.$parent);
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.title = attrs.title;
                scope.category = attrs.category;
                scope.amountChange = $parse(attrs.amountchange)(scope.$parent);
                scope.autoSaveGeneralRevenue = $parse(attrs.autosavegeneralrevenue)(scope.$parent);
            }
        };
    })
    .directive('d20v', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20v.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.summary = $parse(attrs.summary)(scope.$parent);
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.title = attrs.title;
                scope.category = attrs.category;
                scope.amountChange = $parse(attrs.amountchange)(scope.$parent);
                scope.autoSaveVehivleRevenue = $parse(attrs.autosavevehivlerevenue)(scope.$parent);
            }
        };
    })
    .directive('d20s', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20s.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.summary = $parse(attrs.summary)(scope.$parent);
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.title = attrs.title;
                scope.category = attrs.category;
                scope.amountChange = $parse(attrs.amountchange)(scope.$parent);
                scope.autoSaveSalesRevenue = $parse(attrs.autosavesalesrevenue)(scope.$parent);
            }
        };
    })
    .directive('d20a', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20a.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.category = attrs.category;
                scope.calSummary = $parse(attrs.calsummary)(scope.$parent);
                scope.autoSaveAccountReceivables = $parse(attrs.autosaveaccountreceivables)(scope.$parent);
            }
        };
    })
    .directive('d20e', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20e.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.title = attrs.title;
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.category = attrs.category;
                scope.autoSaveFee = $parse(attrs.autosavefee)(scope.$parent);
            }
        };
    })
    .directive('d20es', function ($parse, $q) {
        return {
            restrict: 'A',
            scope: {},
            templateUrl: 'partials/directives/d20es.html',
            link: function (scope, element, attrs) {
                var _this = this;
                scope.list = $parse(attrs.list)(scope.$parent);
                scope.category = attrs.category;
                scope.autoSaveFeeSummary = $parse(attrs.autosavefeesummary)(scope.$parent);
            }
        };
    })
    .directive('scrollNav', ['$timeout', function (timer) {
        return {
            restrict: 'A',
            link: function (scope, ele, attrs, c) {
                var setupScroll = function () {
                    $(ele).scrollNav({
                        sections: 'h4',
                        sectionElem: 'section',
                        insertTarget: $("#page_nav"),
                        insertLocation: 'appendTo',
                        fixedMargin: 50,
                        scrollOffset: 100,
                        showTopLink: false,
                        showHeadline: false,
                        arrowKeys: true,
                        animated: true
                    });
                }
                timer(setupScroll, 0);
            }
        }
    }]);

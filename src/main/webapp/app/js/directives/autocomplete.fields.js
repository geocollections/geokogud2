angular.module('geoApp').directive('autocompleteField', function () {
    return {
        template: "<input type='text' class='form-control input-md' data-ng-model='field' " +
        "placeholder='{{placeholderText}}' " +
        "data-uib-typeahead='entity[localizedValue] for entity in factory.autocompleteSearch(table,$viewValue,sortBy,localizedValue)' " +
        "data-typeahead-min-length='1' data-typeahead-on-select='entitySelected($item, $model)' typeahead-loading='isLoading'/>" +
        "<span ng-if='!!isLoading'><img src='img/loader.gif' width='20' height='20' /></span>",
        restrict: 'AE',
        scope: {
            table: '@',
            field: '=?ngModel',
            sortBy: '@',
            et: '@',
            en: '@'
        },
        controller: ['$scope','$rootScope', '$translate', 'ApplicationService', 'SearchFactory', function ($scope, $rootScope,
                                                                                                        $translate, ApplicationService, SearchFactory) {
            $scope.factory = SearchFactory;
            $scope.$watch('[et, en]', function(newValue) {
                $scope.placeholderText = $translate.use() === 'et' ? "otsi..." : "search...";
                if($scope.et !== 'null' && $scope.en !== 'null') {
                    $scope.localizedValue = $translate.use() === 'et' ? newValue[0] : newValue[1];
                } else {
                    $scope.localizedValue = newValue[0] === null ? newValue[1] : newValue[0];
                }
            }, true);

            $rootScope.$on('$translateChangeSuccess', function() {
                $scope.placeholderText = $translate.use() === 'et' ? "otsi..." : "search...";
                if($scope.et !== 'null' && $scope.en !== 'null') {
                    $scope.localizedValue = $translate.use() === 'et' ? $scope.et : $scope.en;
                } else {
                    $scope.localizedValue = $scope.et === null ? $scope.en : $scope.et;
                }
            });

            $scope.entitySelected = function (item) {
                $scope.field = item[$scope.localizedValue];
            };

        }]
    };
});
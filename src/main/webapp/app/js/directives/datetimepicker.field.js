angular.module('geoApp').directive('commonDatetimepicker', function () {
    return {
        // TODO: Show date correctly, not in placeholder.
        template: '<span class=\'col-md-6 form-group\'><input type=\"text\" class=\"form-control\" placeholder=\"\{{field.name}}"' +
        ' data-ng-model=\"field.name\" data-uib-datepicker-popup=\"{{dateFormat}}\" ' +
        'data-is-open=\"fromDate.open\" data-datepicker-options=\"datePickerOptions\" ' +
        'data-ng-click=\"fromDate.open = true\"/></span> ',
        restrict: 'AE',
        scope: {
            field: '=?ngModel',
            lookup: '@',
            placeholder: '@'
        },
        controller: ['$scope', function ($scope) {
            $scope.$watch('field.name', function(newValue){
                if(newValue) {
                    console.log(newValue);
                    var date = new Date(newValue);
                    var yyyy = date.getFullYear();
                    var MM = date.getMonth() + 1;
                    var dd = date.getDate();
                    $scope.field.name = yyyy + "-" + MM + "-" + dd;
                    // $scope.field.name = new Date(yyyy + "-" + MM + "-" + dd);
                    console.log(typeof ($scope.field.name));
                    $scope.field.lookUpType = $scope.lookup;
                } else {
                    $scope.field = {lookUpType: $scope.lookup};
                }
            });

        }]
    }
});
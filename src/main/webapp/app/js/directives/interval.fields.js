 angular.module('geoApp').directive('intervalField', function () {
    return {
        template: "<span class='col-md-6 form-group'><input type='number' placeholder='{{placeholder}}' ng-model='field.name' class='form-control'/></span>",
        restrict: 'AE',
        scope: {
            field: '=?ngModel',
            lookup: '@',
            placeholder: '@'
        },
        controller: ['$scope', function ($scope) {
            $scope.$watch('field.name', function(newValue){
                if(newValue) {
                    $scope.field.lookUpType = $scope.lookup;
                } else {
                    $scope.field = {lookUpType: $scope.lookup, name:null};
                }
            });

        }]
    };
}).directive('checkboxField', function () {
    return {
        template: "<label><input type='checkbox' data-ng-click='checked()' value='value' ng-model='field.name'><span class=\"cr\"><i class=\"cr-icon glyphicon glyphicon-ok\"></i>" +
        "</span>{{ name | translate }}</label>",
        restrict: 'AE',
        scope: {
            field: '=?ngModel',
            name:'@'
        },
        controller: ['$scope', function ($scope) {
            $scope.$watch('field.name', function(newValue, oldValue){
                if(!newValue) $scope.field = {lookUpType : 'exact', name:null};
                else $scope.field = {lookUpType : 'exact', name:newValue};
            });
        }]
    };
}).directive('checkboxFieldPhoto', function () {
     return {
         template: "<label><input type='checkbox' data-ng-click='checked()' value='value' ng-model='field.name'><span class=\"cr\"><i class=\"cr-icon glyphicon glyphicon-ok\"></i>" +
         "</span>{{ name | translate }}</label>",
         restrict: 'AE',
         scope: {
             field: '=?ngModel',
             name:'@'
         },
         controller: ['$scope', function ($scope) {
             $scope.$watch('field.name', function(newValue, oldValue){
                 if (newValue == null && oldValue == null) {
                     $scope.field = {lookUpType : 'exact', name:true};
                 }
                 if (newValue == true && oldValue == false) {
                     $scope.field = {lookUpType : 'exact', name:true};

                 }
                 if (newValue == false && oldValue == true) {
                     $scope.field = {lookUpType : 'exact', name:false};
                 }
                 // console.log("new " + newValue);
                 // console.log("old " + oldValue);
             });
         }]
     };
 });
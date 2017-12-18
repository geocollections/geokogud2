var module = angular.module("geoApp");

var constructor = function ($scope, $stateParams, configuration, $http, applicationService, $window, $translate, SearchFactory, errorService, bsLoadingOverlayService) {
    var vm = this;
    vm.service = applicationService;
    vm.factory = SearchFactory;
    vm.searchLoadingHandler = bsLoadingOverlayService.createHandler({
        referenceId: "searchView"
    });

    var search = vm.service.getTranslationRoot($stateParams.type);
    $scope.searchParameters = {};

    $scope.isIdentifierFieldsCollapsed = false;
    $scope.isLocationFieldsCollapsed = true;
    $scope.isAnalysesFieldsCollapsed = true;
    $scope.isInstitutionsCollapsed = true;

    function onSearchData(result) {
        console.log(result);
        $scope.pageSize = 30;
        $scope.totalItems = result.data.count;

        if((['specimens', 'localities'].indexOf($stateParams.type) > -1)) $scope.images = composeImageStructure(result.data);
        if((['localities'].indexOf($stateParams.type) > -1)) $scope.images = composeImageStructure(result.data);
        if((['photoArchive'].indexOf($stateParams.type) > -1)) $scope.images = composeImageArchiveStructure(result.data);

        $scope.windowWidth = "innerWidth" in window ? window.innerWidth : document.documentElement.offsetWidth;
        if ($scope.windowWidth > 400) {
            $scope.searchParameters.maxSize = 5;
        } else {
            $scope.searchParameters.maxSize = 2;
        }
        // Window resize event
        var w = angular.element($window);
        w.bind('resize', function () {

            $scope.windowWidth = "innerWidth" in window ? window.innerWidth : document.documentElement.offsetWidth;
            if ($scope.windowWidth > 400) {
                $scope.searchParameters.maxSize = 5;
            } else {
                $scope.searchParameters.maxSize = 2;
            }
            $scope.$apply();
        });
        $scope.response = result.data;

        if ($scope.isMapHidden) {
            $scope.getLocalities($scope.response);
        }
        vm.searchLoadingHandler.stop();

        getSearchDataFromLocalStorage();

        /* $('html, body').animate({
             scrollTop: ($("#searches").offset().top - 50)
         }, 'fast');*/
    }
     function returnImageObject(entity) {
         switch ($stateParams.type) {
             case "specimens": return entity.specimen_image_thumbnail;
             case "localities": return entity.locality_image_thumbnail;
             default : break;
         }
     }
    function composeImageArchiveStructure  (response) {
        var imageStructure  = {rows : [],total : 0}, countImage = 0, currentRow  = [];
       angular.forEach(response.results, function (image){
            if(countImage < 30) {
                currentRow.push({image :image});
                countImage ++;
                if(countImage % 5 === 0) {
                    imageStructure.rows.push(currentRow);
                    currentRow = [];
                }
            }
        });

        imageStructure.rows.push(currentRow);
        imageStructure.total = countImage;
        return imageStructure;
    }

    function composeImageStructure (response) {
        var imageStructure  = {rows : [],total : 0}, countImage = 0, currentRow  = [];

        angular.forEach(response.results, function (entity){
            var imageObject = returnImageObject(entity);
            if(imageObject && imageObject.count > 0) {
                angular.forEach(imageObject.results, function (image){
                    if(countImage < 30) {
                        currentRow.push({image :image, object : entity});
                        countImage ++;
                        if(countImage % 5 === 0) {
                            imageStructure.rows.push(currentRow);
                            currentRow = [];
                        }
                    }
                });
            }
        });
        imageStructure.rows.push(currentRow);
        imageStructure.total = countImage;
        return imageStructure;
    }

    $scope.search = function () {
        $scope.showImages = $scope.searchParameters.searchImages && $scope.searchParameters.searchImages.name ? true : false;
        vm.searchLoadingHandler.start();
        addSearchDataToLocalStorage($scope.searchParameters);
        applicationService.getList($stateParams.type, $scope.searchParameters, onSearchData, onSearchError);
    };

    $scope.searchWithoutLocalStorage = function () {
        $scope.showImages = $scope.searchParameters.searchImages && $scope.searchParameters.searchImages.name ? true : false;
        vm.searchLoadingHandler.start();
        applicationService.getList($stateParams.type, $scope.searchParameters, onSearchData, onSearchError);
    };

    function onSearchError(error) {
        if(configuration.pageSetUp.debugMode) errorService.commonErrorHandler(error);
        setEmptyResponse();
        vm.searchLoadingHandler.stop();
    }
    function setEmptyResponse() {
        $scope.response = {count : 0, results : null};
        $scope.totalItems = 0;
    }

    $scope.searchDefault = function () {
        if ((['photoArchive'].indexOf($stateParams.type) > -1)) {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                searchImages: {lookUpType: "exact", name: true},
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        } else {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        }
        $scope.sortByAsc = true;
        $scope.search();
    };

    $scope.resetSearch = function () {
        // TODO: Reset is taking too long in some cases
        if ((['photoArchive'].indexOf($stateParams.type) > -1)) {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                searchImages: {lookUpType: "exact", name: true},
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        } else {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        }
        $scope.sortByAsc = true;

        localStorage.removeItem($stateParams.type);
        $scope.searchWithoutLocalStorage();
    };

    $scope.addRemoveInstitution = function (institution) {
        var index = $scope.searchParameters.dbs.indexOf(institution);
        if (index == -1) {
            vm.service.toggle(institution, $scope.searchParameters.dbs);
        } else {
            $scope.searchParameters.dbs.splice(index, 1);
        }
    };

    $scope.searchDefault();

    $scope.showHint = function () {
        $scope.isHintHidden = !$scope.isHintHidden;
    };

    $scope.showMap = function () {
        $scope.isMapHidden = !$scope.isMapHidden;
        $scope.getLocalities($scope.response.results);
    };

    $scope.getLocalities = function (list) {
        $scope.localities = [];
        if (list) {
            angular.forEach(list, function (el) {
                if (el.locality__latitude != null && el.locality__longitude != null && el.locality__locality_en != null && el.locality__locality != null && el.locality_id != null) {
                    //console.log("in if");
                    addToLocalities(el.locality__latitude, el.locality__longitude, el.locality__locality_en, el.locality__locality, el.locality_id);
                } else if (noneIsNull(el.latitude, el.longitude, el.locality_en, el.locality, el.id)) {
                  //  console.log("else if")
                    addToLocalities(el.latitude, el.longitude, el.locality_en, el.locality, el.id);
                } else if(noneIsNull(el.latitude, el.longitude, '', '', '')) {
                    addToLocalities(el.latitude, el.longitude, '', '', null);
                }
            })
        }
        return $scope.localities;
    };

    function noneIsNull(latitude, longitude, locality_en, locality, locality_id) {
        return latitude != null && longitude != null && locality_en != null && locality != null && locality_id != null;
    }

    function addToLocalities(latitude, longitude, locality_en, locality, locality_id) {
        $scope.localities.push({
            latitude: latitude,
            longitude: longitude,
            localityEng: locality_en,
            localityEt: locality,
            fid: locality_id
        });
    }

    function addSearchDataToLocalStorage(searchParameters) {
        if (typeof(localStorage) !== 'undefined') {
            var stringifiedParameters = JSON.stringify(searchParameters);
            var defaultParameters = '{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"]}';
            var defaultPhotoArchiveParameters = '{"sortField":{"sortBy":"id","order":"DESCENDING"},"searchImages":{"lookUpType":"exact","name":true},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"]}';

            if([$stateParams.type].indexOf($stateParams.type) > -1 && $stateParams.type === "photoArchive") {
                if(stringifiedParameters !== defaultPhotoArchiveParameters) {
                    localStorage.setItem($stateParams.type, JSON.stringify(searchParameters));
                }
            } else if([$stateParams.type].indexOf($stateParams.type) > -1) {
                if(stringifiedParameters !== defaultParameters) {
                    localStorage.setItem($stateParams.type, JSON.stringify(searchParameters));
                }
            }
        }
    }

    function getSearchDataFromLocalStorage() {
        if (typeof(localStorage) !== 'undefined') {
            console.log("Getting from localStorage: " + $stateParams.type);

            if([$stateParams.type].indexOf($stateParams.type) > -1 && localStorage[$stateParams.type] != null) {
                $scope.searchParameters = JSON.parse(localStorage.getItem($stateParams.type));
            }
        }
    }

};

constructor.$inject = ["$scope","$stateParams", "configuration", "$http", 'ApplicationService', '$window', '$translate', 'SearchFactory', 'ErrorService', 'bsLoadingOverlayService'];

module.controller("SearchController", constructor);

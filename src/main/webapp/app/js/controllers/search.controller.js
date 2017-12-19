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

        // Session storage overrides localStorage data, because of order.
        getSearchDataFromLocalStorage();
        getSearchDataFromSessionStorage();

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

    // Resets searchParameters to default search results.
    $scope.resetSearch = function () {
        resetSearchParametersToDefault();
        sessionStorage.removeItem($stateParams.type + "Search");
        localStorage.removeItem($stateParams.type);
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

    function getSearchDataFromSessionStorage() {
        if (typeof(sessionStorage) !== 'undefined') {
            if (sessionStorage[$stateParams.type + "Search"] != null) {
                $scope.searchParameters = JSON.parse(sessionStorage.getItem($stateParams.type + "Search"));
            }
        }
    }

    function resetSearchParametersToDefault() {
        if (['specimens'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"searchImages":{"lookUpType":"exact","name":null},"specimenNumber":{"lookUpType":"icontains"},"collectionNumber":{"lookUpType":"iexact"},"classification":{"lookUpType":"icontains"},"fossilName":{"lookUpType":"icontains"},"mineralRock":{"lookUpType":"icontains"},"adminUnit":{"lookUpType":"icontains"},"locality":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"id":{"lookUpType":"iexact"},"fossilMineralRock":{"lookUpType":"icontains"},"collector":{"lookUpType":"icontains"},"reference":{"lookUpType":"icontains"},"typeStatus":{"lookUpType":"icontains"},"partOfFossil":{"lookUpType":"icontains"},"keyWords":{"lookUpType":"icontains"},"maxSize":5}');
        }
        if (['samples'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"sampleNumber":{"lookUpType":"icontains"},"locality":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"stratigraphyBed":{"lookUpType":"icontains"},"agent":{"lookUpType":"icontains"},"id":{"lookUpType":"iexact"},"country":{"lookUpType":"icontains"},"location":{"lookUpType":"icontains"},"taxon":{"lookUpType":"icontains"},"frequency":{"lookUpType":"iexact"},"analysisMethod":{"lookUpType":"icontains"},"component":{"lookUpType":"icontains"},"content":{"lookUpType":"iexact"},"maxSize":5}');
        }
        if (['drillCores'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"drillcore":{"lookUpType":"icontains"},"country":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"storageLocation":{"lookUpType":"icontains"},"id":{"lookUpType":"iexact"},"adminUnit":{"lookUpType":"icontains"},"maxSize":5}');
        }
        if (['localities'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"searchImages":{"lookUpType":"exact","name":null},"locality":{"lookUpType":"icontains"},"number":{"lookUpType":"icontains"},"country":{"lookUpType":"icontains"},"adminUnit":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"reference":{"lookUpType":"icontains"},"id":{"lookUpType":"iexact"},"maPaId":{"lookUpType":"iexact"},"latitude":{"lookUpType":"iexact"},"longitude":{"lookUpType":"iexact"},"maxSize":5}');
        }
        if (['references'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"id":{"lookUpType":"iexact"},"author":{"lookUpType":"icontains"},"title":{"lookUpType":"icontains"},"journal":{"lookUpType":"icontains"},"book":{"lookUpType":"iexact"},"maxSize":5}');
        }
        if (['stratigraphy'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"id":{"lookUpType":"iexact"},"stratigraphy":{"lookUpType":"icontains"},"index":{"lookUpType":"icontains"},"ageMinY":{"lookUpType":"iexact"},"mainLithology":{"lookUpType":"icontains"},"author":{"lookUpType":"icontains"},"sortField":{"sortBy":"id","order":"DESCENDING"},"maxSize":5}');
        }
        if (['analyses'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"id":{"lookUpType":"iexact"},"locality":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"stratigraphyBed":{"lookUpType":"icontains"},"analysisMethod":{"lookUpType":"icontains"},"XXX":{"lookUpType":"icontains"},"methodDetails":{"lookUpType":"iexact"},"sample":{"lookUpType":"icontains"},"adminUnit":{"lookUpType":"icontains"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"sortField":{"sortBy":"id","order":"DESCENDING"},"maxSize":5}');
        }
        if (['preparations'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"number":{"lookUpType":"icontains"},"locality":{"lookUpType":"icontains"},"stratigraphy":{"lookUpType":"icontains"},"collector":{"lookUpType":"icontains"},"description":{"lookUpType":"icontains"},"speciesRecovered":{"lookUpType":"icontains"},"speciesFrequency":{"lookUpType":"iexact"},"sortField":{"sortBy":"id","order":"DESCENDING"},"maxSize":5}');
        }
        if (['photoArchive'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse('{"sortField":{"sortBy":"id","order":"DESCENDING"},"searchImages":{"lookUpType":"exact","name":true},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"locality":{"lookUpType":"icontains"},"people":{"lookUpType":"icontains"},"keywords":{"lookUpType":"icontains"},"adminUnit":{"lookUpType":"icontains"},"imageNumber":{"lookUpType":"icontains"},"authorAgent":{"lookUpType":"icontains"},"maxSize":5}');
        }
        if (['doi'].indexOf($stateParams.type > -1)) {
            $scope.searchParameters = JSON.parse('{"doi":{"lookUpType":"icontains"},"title":{"lookUpType":"icontains"},"publishedBy":{"lookUpType":"icontains"},"author":{"lookUpType":"icontains"},"abstractText":{"lookUpType":"icontains"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],"sortField":{"sortBy":"id","order":"DESCENDING"},"maxSize":5}');
        }
    }

};

constructor.$inject = ["$scope","$stateParams", "configuration", "$http", 'ApplicationService', '$window', '$translate', 'SearchFactory', 'ErrorService', 'bsLoadingOverlayService'];

module.controller("SearchController", constructor);

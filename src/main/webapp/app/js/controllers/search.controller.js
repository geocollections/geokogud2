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
        $scope.totalItems = result.data.count;

        // if((['specimens', 'localities'].indexOf($stateParams.type) > -1)) $scope.images = composeImageStructure(result.data);
        // if((['localities'].indexOf($stateParams.type) > -1)) $scope.images = composeImageStructure(result.data);
        // if((['photoArchive'].indexOf($stateParams.type) > -1)) $scope.images = composeImageArchiveStructure(result.data);

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


        // console.log("THIS IS HINT: " + $scope.isHintHidden);
        // console.log("THIS IS MAP: " + $scope.isMapHidden);
        if ($scope.isMapHidden) {
            console.log("im here");
            $scope.getLocalities($scope.response.results);
        }

        vm.searchLoadingHandler.stop();

        // Session storage overrides localStorage data, because of order.
        // getSearchDataFromLocalStorage();
        // getSearchDataFromSessionStorage();
        // This here populates input fields

        var searchParamsSession = getSearchDataFromSessionStorage();
        if (typeof(searchParamsSession) !== 'undefined') {
            $scope.searchParameters = searchParamsSession;
            areInstitutionsChecked();
            isAdditionalCriteriaUsed();
        }

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

        $scope.pageSize = $scope.searchParameters.paginateBy; // Essential for pagination

        fixPaginationIfNeeded();

        applicationService.getList($stateParams.type, $scope.searchParameters, onSearchData, onSearchError);
    };

    /**
     * Checks if user has entered valid parameters
     * if not then sets to default.
     */
    function fixPaginationIfNeeded() {
        if ($scope.searchParameters.paginateBy <= 0) {
            $scope.searchParameters.paginateBy = 25;
            $scope.pageSize = 25;
        }
        if ($scope.searchParameters.paginateBy > 1000) {
            $scope.searchParameters.paginateBy = 1000;
            $scope.pageSize = 1000;
        }
    }

    /**
     * Gets called if there is some kind of error.
     * @param error Full error response from server
     */
    function onSearchError(error) {
        console.log(error);
        // if(configuration.pageSetUp.debugMode) errorService.commonErrorHandler(error);
        setEmptyResponse();
        vm.searchLoadingHandler.stop();
    }

    /**
     * Sets respone to empty if search has some kind of error.
     */
    function setEmptyResponse() {
        $scope.response = {count : 0, results : null};
        $scope.totalItems = 0;
    }

    $scope.searchDefault = function () {
        if ((['photoArchive'].indexOf($stateParams.type) > -1)) {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                paginateBy: 25,
                searchImages: {lookUpType: "exact", name: true},
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        } else {
            $scope.searchParameters = {
                sortField: {sortBy: "id", order: "DESCENDING"},
                paginateBy: 25,
                dbs: vm.service.departments.map(function (department) {
                    return department.code;
                })
            };
        }

        console.log(location.href);
        /*** Get parameters from session storage START ***/
        var searchParamsSession = getSearchDataFromSessionStorage();
        if (typeof(searchParamsSession) !== 'undefined') {
            $scope.searchParameters = searchParamsSession;
        }
        /*** Get parameters from session storage END ***/
        console.log(location.href);

        $scope.sortByAsc = true;
        $scope.search();
    };

    // Resets searchParameters to default search results.
    $scope.resetSearch = function () {
        resetSearchParametersToDefault();
        sessionStorage.removeItem($stateParams.type);
        // localStorage.removeItem($stateParams.type);
    };

    $scope.addRemoveInstitution = function (institution) {
        if ($scope.searchParameters.dbs)
        var index = $scope.searchParameters.dbs.indexOf(institution);
        if (index === -1) {
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
        console.log($scope.isMapHidden);
        $scope.isMapHidden = !$scope.isMapHidden;
        console.log($scope.isMapHidden);
        $scope.getLocalities($scope.response.results);
    };

    $scope.getLocalities = function (list) {
        console.log(list);
        $scope.localities = [];

        console.log($scope.localities);
        if (list && list != null) {
            angular.forEach(list, function (el) {
                if (['localities'].indexOf($stateParams.type) > -1) {
                    if (el.latitude != null && el.longitude != null) {
                        addToLocalities(el.latitude, el.longitude, el.locality_en, el.locality, el.id);
                    }
                } else {
                    // Specimens, samples, drillCores
                    if (el.locality__longitude != null && el.locality__latitude != null) {
                        addToLocalities(el.locality__latitude, el.locality__longitude, el.locality__locality_en, el.locality__locality, el.locality_id);
                    }
                }
            })
        }
        return $scope.localities;
    };

    function addToLocalities(latitude, longitude, locality_en, locality, locality_id) {
        console.log("lat: " + latitude
            + " lon: " + longitude
            + " loc: " + locality_en
            + " loc_en: " + locality
            + " id: " + locality_id);
        $scope.localities.push({
            latitude: latitude,
            longitude: longitude,
            localityEng: locality_en,
            localityEt: locality,
            fid: locality_id
        });
    }

    function addSearchDataToSession(searchParameters) {
        if (typeof(sessionStorage) !== 'undefined') {
            var stringifiedParameters = JSON.stringify(searchParameters);
            console.log(stringifiedParameters);
            var defaultParameters = '{"sortField":{"sortBy":"id","order":"DESCENDING"},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"]}';
            var defaultPhotoArchiveParameters = '{"sortField":{"sortBy":"id","order":"DESCENDING"},"searchImages":{"lookUpType":"exact","name":true},"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"]}';

            if([$stateParams.type].indexOf($stateParams.type) > -1 && $stateParams.type === "photoArchive") {
                if(stringifiedParameters !== defaultPhotoArchiveParameters) {
                    sessionStorage.setItem($stateParams.type, stringifiedParameters);
                }
            } else if([$stateParams.type].indexOf($stateParams.type) > -1) {
                if(stringifiedParameters !== defaultParameters) {
                    sessionStorage.setItem($stateParams.type, stringifiedParameters);
                }
            }
        }
    }

    /**
     * Gets last searched data from session storage and returns it.
     * @returns Data in JSON format.
     */
    function getSearchDataFromSessionStorage() {
        if (typeof(sessionStorage) !== 'undefined') {
            var tableName = $stateParams.type;
            if([tableName].indexOf(tableName) > -1 && sessionStorage[tableName] != null) {
                return JSON.parse(sessionStorage.getItem(tableName));
            }
        }
    }

    /**
     * Resets searchParameters to default state.
     * Resets lookUpTypes and removes name aka input value
     */
    function resetSearchParametersToDefault() {
        if (['specimens'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"searchImages":{"lookUpType":"exact","name":null},' +
                '"specimenNumber":{"lookUpType":"icontains"},' +
                '"collectionNumber":{"lookUpType":"icontains"},' +
                '"classification":{"lookUpType":"icontains"},' +
                '"fossilName":{"lookUpType":"icontains"},' +
                '"mineralRock":{"lookUpType":"icontains"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"id":{"lookUpType":"iexact"},' +
                '"depthSince":{"lookUpType":"gte"},' +
                '"depthTo":{"lookUpType":"lte"},' +
                '"collector":{"lookUpType":"icontains"},' +
                '"reference":{"lookUpType":"icontains"},' +
                '"typeStatus":{"lookUpType":"icontains"},' +
                '"partOfFossil":{"lookUpType":"icontains"},' +
                '"dateTakenSince":{"lookUpType":"gte"},' +
                '"dateTakenTo":{"lookUpType":"lte"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['samples'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"sampleNumber":{"lookUpType":"icontains"},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"stratigraphyBed":{"lookUpType":"icontains"},' +
                '"agent":{"lookUpType":"icontains"},' +
                '"id":{"lookUpType":"iexact"},' +
                '"country":{"lookUpType":"icontains"},' +
                '"location":{"lookUpType":"icontains"},' +
                '"taxon":{"lookUpType":"icontains"},' +
                '"frequency":{"lookUpType":"iexact"},' +
                '"analysisMethod":{"lookUpType":"icontains"},' +
                '"component":{"lookUpType":"icontains"},' +
                '"content":{"lookUpType":"iexact"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['drillCores'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id",' +
                '"order":"DESCENDING"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"drillcore":{"lookUpType":"icontains"},' +
                '"country":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"storageLocation":{"lookUpType":"icontains"},' +
                '"id":{"lookUpType":"iexact"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['localities'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"searchImages":{"lookUpType":"exact","name":null},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"number":{"lookUpType":"icontains"},' +
                '"country":{"lookUpType":"icontains"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"reference":{"lookUpType":"icontains"},' +
                '"id":{"lookUpType":"iexact"},' +
                '"maPaId":{"lookUpType":"iexact"},' +
                '"latitude":{"lookUpType":"iexact"},' +
                '"longitude":{"lookUpType":"iexact"},' +
                '"verticalExtentSince":{"lookUpType":"gte"},' +
                '"verticalExtentTo":{"lookUpType":"lte"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['references'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"id":{"lookUpType":"iexact"},' +
                '"author":{"lookUpType":"icontains"},' +
                '"title":{"lookUpType":"icontains"},' +
                '"journal":{"lookUpType":"icontains"},' +
                '"book":{"lookUpType":"icontains"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['stratigraphy'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"id":{"lookUpType":"iexact"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"index":{"lookUpType":"icontains"},' +
                '"ageMinY":{"lookUpType":"iexact"},' +
                '"mainLithology":{"lookUpType":"icontains"},' +
                '"author":{"lookUpType":"icontains"},' +
                '"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['analyses'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"id":{"lookUpType":"iexact"},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"stratigraphyBed":{"lookUpType":"icontains"},' +
                '"analysisMethod":{"lookUpType":"icontains"},' +
                '"componentAnalysed":{"lookUpType":"icontains"},' +
                '"content":{"lookUpType":"iexact"},' +
                '"sample":{"lookUpType":"icontains"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['preparations'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"number":{"lookUpType":"icontains"},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"icontains"},' +
                '"collector":{"lookUpType":"icontains"},' +
                '"description":{"lookUpType":"icontains"},' +
                '"speciesRecovered":{"lookUpType":"icontains"},' +
                '"speciesFrequency":{"lookUpType":"iexact"},' +
                '"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['photoArchive'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"searchImages":{"lookUpType":"exact","name":true},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"locality":{"lookUpType":"icontains"},' +
                '"people":{"lookUpType":"icontains"},' +
                '"keywords":{"lookUpType":"icontains"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"imageNumber":{"lookUpType":"icontains"},' +
                '"authorAgent":{"lookUpType":"icontains"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
        if (['doi'].indexOf($stateParams.type) > -1) {
            $scope.searchParameters = JSON.parse(
                '{"doi":{"lookUpType":"icontains"},' +
                '"title":{"lookUpType":"icontains"},' +
                '"publishedBy":{"lookUpType":"icontains"},' +
                '"author":{"lookUpType":"icontains"},' +
                '"abstractText":{"lookUpType":"icontains"},' +
                '"dbs":["GIT","TUG","ELM","TUGO","MUMU","EGK"],' +
                '"sortField":{"sortBy":"id","order":"DESCENDING"},' +
                '"maxSize":5,' +
                '"paginateBy":25}');
        }
    }

    /**
     * If institutions are not all checked then opens dropdown.
     */
    function areInstitutionsChecked() {
        if ($scope.searchParameters.dbs != null) {
            if ($scope.searchParameters.dbs.length < 6) {
                $scope.isInstitutionsCollapsed = false;
            }
        }
    }

    /**
     * If additional criteria is used then shows it.
     */
    function isAdditionalCriteriaUsed() {
        if ((['analyses'].indexOf($stateParams.type) > -1)) {
            if ($scope.searchParameters.sample.name != null || $scope.searchParameters.adminUnit.name != null) {
                $scope.isLocationFieldsCollapsed = false;
            }
        }
        if ((['localities'].indexOf($stateParams.type) > -1)) {
            if ($scope.searchParameters.id.name != null || $scope.searchParameters.maPaId.name != null ||
                $scope.searchParameters.latitude.name != null || $scope.searchParameters.longitude.name != null ||
                $scope.searchParameters.verticalExtentSince.name != null || $scope.searchParameters.verticalExtentTo.name != null) {
                $scope.isLocationFieldsCollapsed = false;
            }
        }
        if ((['drillCores'].indexOf($stateParams.type) > -1)) {
            if ($scope.searchParameters.id.name != null || $scope.searchParameters.adminUnit.name != null) {
                $scope.isLocationFieldsCollapsed = false;
            }
        }
        if ((['samples'].indexOf($stateParams.type) > -1)) {
            if ($scope.searchParameters.id.name != null || $scope.searchParameters.country.name != null ||
                $scope.searchParameters.location.name != null || $scope.searchParameters.taxon.name != null ||
                $scope.searchParameters.frequency.name != null || $scope.searchParameters.analysisMethod.name != null ||
                $scope.searchParameters.component.name != null || $scope.searchParameters.content.name != null) {
                $scope.isLocationFieldsCollapsed = false;
            }
        }
        if ((['specimens'].indexOf($stateParams.type) > -1)) {
            if ($scope.searchParameters.id.name != null || $scope.searchParameters.depthSince.name != null ||
                $scope.searchParameters.depthTo.name != null || $scope.searchParameters.collector.name != null ||
                $scope.searchParameters.reference.name != null || $scope.searchParameters.typeStatus.name != null ||
                $scope.searchParameters.partOfFossil.name != null || $scope.searchParameters.dateTakenSince.name != null ||
                $scope.searchParameters.dateTakenTo.name != null) {
                $scope.isLocationFieldsCollapsed = false;
            }
        }
    }

};

constructor.$inject = [
    "$scope",
    "$stateParams",
    "configuration",
    "$http",
    'ApplicationService',
    '$window',
    '$translate',
    'SearchFactory',
    'ErrorService',
    'bsLoadingOverlayService'];

module.controller("SearchController", constructor);

var module = angular.module("geoApp");

var constructor = function ($scope, $location, $stateParams, configuration, $http, applicationService, $window, $translate, SearchFactory, errorService, bsLoadingOverlayService) {
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

    /**
     * Gets called if request was successful.
     * @param result, Success response from server.
     */
    function onSearchData(result) {
        // console.log(result);
        $scope.totalItems = result.data.count;

        if ($scope.totalItems === 0 || $scope.totalItems == null) $scope.noResults = true;

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

        if ($scope.isMapVisible) {
            // console.log("im here");
            $scope.getLocalities($scope.response.results);
        }

        vm.searchLoadingHandler.stop();

        // Session storage overrides localStorage data, because of order.
        // getSearchDataFromLocalStorage();
        // getSearchDataFromSessionStorage();
        // This here populates input fields

        var searchParamsSession = getSearchDataFromSessionStorage();
        var institutions = getInstitutionsFromLocalStorage();
        // console.log(location.search);
        if (typeof(searchParamsSession) !== 'undefined') {
            $scope.searchParameters = searchParamsSession;
            areInstitutionsChecked();
            isAdditionalCriteriaUsed();
        }
        if (typeof(institutions) !== 'undefined') {
            $scope.searchParameters.dbs = institutions;
            areInstitutionsChecked();
        }

        // Animates to search tabs.
        jQuery(document).ready(function ($) {
            $('html, body').animate({
                scrollTop: ($("#searches").offset().top - 70)
            }, 'fast');
        });
    }

    /**
     * Main search function.
     * Checks if image search or not, starts the loading overlay
     * and sends request with user inputted parameters.
     */
    $scope.search = function () {
        if ($scope.isMapVisible) {
            hideMap();
        }

        $scope.showImages = $scope.searchParameters.searchImages && $scope.searchParameters.searchImages.name ? true : false;
        vm.searchLoadingHandler.start();

        $scope.pageSize = $scope.searchParameters.paginateBy; // Essential for pagination
        fixPaginationIfNeeded();

        applicationService.getList($stateParams.type, $scope.searchParameters, onSearchData, onSearchError);
    };

    /**
     * Checks if user has entered valid parameters
     * if not then sets to default (1-1000).
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
        // console.log(error);
        $scope.noResults = true;
        // if(configuration.pageSetUp.debugMode) errorService.commonErrorHandler(error);
        setEmptyResponse();
        vm.searchLoadingHandler.stop();
    }

    /**
     * Sets response to empty if search has some kind of error.
     */
    function setEmptyResponse() {
        $scope.response = {count : 0, results : null};
        $scope.totalItems = 0;
    }

    /**
     * THIS GETS CALLED WHEN USER COMES TO A PAGE.
     * Adds default search parameters.
     * If data is available then gets it from sessionStorage
     * which overrides default parameters.
     * Sets sorting ASCENDING and finally calls search function.
     */
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

        /*** Get parameters from session storage and local storage START ***/
        var searchParamsSession = getSearchDataFromSessionStorage();
        var institutions = getInstitutionsFromLocalStorage();
        // var searchParamsFromUrl = getSearchDataFromUrl();
        var searchParamsFromUrl = applicationService.getSearchParamsFromUrl();
        // console.log(searchParamsFromUrl);
        if (typeof(searchParamsSession) !== 'undefined') {
            $scope.searchParameters = searchParamsSession;
        }
        if (typeof(institutions) !== 'undefined') {
            $scope.searchParameters.dbs = institutions;
        }
        if (searchParamsFromUrl != null && searchParamsFromUrl.sortField != null) {
            $scope.searchParameters = searchParamsFromUrl;
        }
        /*** Get parameters from session storage and local storage END ***/

        $scope.sortByAsc = true;
        $scope.search();
    };

    /**
     * Resets search parameters to default
     * and deletes current table from sessionStorage.
     */
    $scope.resetSearch = function () {
        resetSearchParametersToDefault();
        sessionStorage.removeItem($stateParams.type);
        localStorage.removeItem($stateParams.type);
        hideMap();
    };

    /**
     * Adds or removes institution from search parameters
     * @param institution, Name of institution to be added or removed.
     */
    $scope.addRemoveInstitution = function (institution) {
        var index = $scope.searchParameters.dbs.indexOf(institution);
        if (index === -1) {
            vm.service.toggle(institution, $scope.searchParameters.dbs);
        } else {
            $scope.searchParameters.dbs.splice(index, 1);
        }
    };

   // INITIATES DEFAULT SEARCH
    $scope.searchDefault();

    /**
     * Switches hint variable between true and false.
     */
    $scope.showHint = function () {
        $scope.isHintHidden = !$scope.isHintHidden;
    };

    /**
     * Switches map variable between true and false.
     * Also if true then calls getLocalities function.
     */
    $scope.showMap = function () {
        // console.log($scope.isMapVisible);
        $scope.isMapVisible = !$scope.isMapVisible;
        // console.log($scope.isMapVisible);
        if ($scope.isMapVisible) {
            $scope.getLocalities($scope.response.results);
        }
    };

    function hideMap() {
        $('#map-checkbox').prop('checked', false);
        $scope.isMapVisible = false;
    }

    /**
     * Takes locality fields from response and adds them
     * to localities list. Which are then used in map.
     * @param list, Response from request.
     * @returns {Array} of locality elements with data like (latitude, longitude etc.)
     */
    $scope.getLocalities = function (list) {
        // console.log(list);
        $scope.localities = [];

        // console.log($scope.localities);
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

    /**
     * Adds arguments to localities list.
     */
    function addToLocalities(latitude, longitude, locality_en, locality, locality_id) {
        // console.log("lat: " + latitude
        //     + " lon: " + longitude
        //     + " loc: " + locality_en
        //     + " loc_en: " + locality
        //     + " id: " + locality_id);
        $scope.localities.push({
            latitude: latitude,
            longitude: longitude,
            localityEng: locality_en,
            localityEt: locality,
            fid: locality_id
        });
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

    function getInstitutionsFromLocalStorage() {
        if (typeof(localStorage) !== 'undefined') {
            var tableName = $stateParams.type;
            if (tableName.indexOf(tableName) > -1 && localStorage[tableName] != null) {
                return JSON.parse(localStorage.getItem(tableName));
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
                '"classification":{"lookUpType":"hierarchy"},' +
                '"fossilName":{"lookUpType":"hierarchy"},' +
                '"mineralRock":{"lookUpType":"icontains"},' +
                '"adminUnit":{"lookUpType":"icontains"},' +
                '"locality":{"lookUpType":"icontains"},' +
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
                '"abstracts":{"lookUpType":"icontains"},' +
                '"keywords":{"lookUpType":"icontains"},' +
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
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
                '"stratigraphy":{"lookUpType":"hierarchy"},' +
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
    "$location",
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

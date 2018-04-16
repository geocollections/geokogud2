var module = angular.module("geoApp");

/**
 * @param configuration from config.json
 * @param applicationService everything from service.js
 * @param WebPagesFactory from utils.js
 * @param GlobalSearchFactory from utils.js
 * @param bsLoadingOverlayService comes from angular-loading-overlay library
 */
var constructor = function (configuration, $filter, $translate, $http, applicationService, $state, $stateParams,
                            $scope, $rootScope, WebPagesFactory, GlobalSearchFactory, bsLoadingOverlayService) {

    var vm = this;
    vm.service = applicationService;
    vm.searchLoadingHandler = bsLoadingOverlayService.createHandler({
        referenceId: "globalView"
    });

    initialisesGlobalSearchVariables();

    /**
     * At first puts loading overlay then gets search query
     * then uses searchGlobally from utils.js which first
     * parameter is search query and second callback aka response
     */
    $scope.searchGlobally = function () {
        vm.searchLoadingHandler.start();
        $scope.$parent.globalQuery = $stateParams.query;

        // console.log($scope.searchParameters);
        // console.log($stateParams.query);
        //
        // console.log($stateParams.tab);

        setSearchParameters();
        $scope.pageSize = $scope.searchParameters.paginateBy; // Essential for pagination
        fixPaginationIfNeeded();

        // console.log($scope.searchParameters);

        GlobalSearchFactory.searchGlobally(
            // $scope.searchParameters.tab,
            $stateParams.tab,
            $scope.searchParameters.page,
            $scope.searchParameters.paginateBy,
            $stateParams.query,
            onGlobalDataLoaded);
    };

    /**
     * Sets search parameters if they do not exist.
     * Also they are needed if you want to change parameters
     * directly in url.
     */
    function setSearchParameters() {
        if (!$stateParams.page) {
            $stateParams.page = 1;
        }
        if (!$stateParams.paginateBy) {
            $stateParams.paginateBy = 100;
        }

        if (!$scope.searchParameters.tab) {
            $scope.searchParameters.tab = $stateParams.tab;
        }
        if (!$scope.searchParameters.page) {
            $scope.searchParameters.page = Number($stateParams.page);
        }
        if (!$scope.searchParameters.paginateBy) {
            $scope.searchParameters.paginateBy = Number($stateParams.paginateBy);
        }
    }

    /**
     * Checks if user has entered valid parameters
     * if not then sets to default (1-1000).
     */
    function fixPaginationIfNeeded() {
        if ($scope.searchParameters.paginateBy <= 0) {
            $scope.searchParameters.paginateBy = 100;
            $scope.pageSize = 100;
        }
        if ($scope.searchParameters.paginateBy > 1000) {
            $scope.searchParameters.paginateBy = 1000;
            $scope.pageSize = 1000;
        }
    }

    /**
     * Initialises default values
     */
    function initialisesGlobalSearchVariables() {
        $scope.searchResults = {
            "specimen": {},
            "sample": {},
            "drillcore": {},
            "locality": {},
            "reference": {},
            "stratigraphy": {},
            "analysis": {},
            "preparation": {},
            "image": {},
            "doi": {},
            "taxon": {}
        };

        $scope.searchParameters = {
            sortField: {
                sortBy: "id",
                order: "DESCENDING"
            },
            maxSize: 5
        };

        $scope.response = {
            results: [],
            count: 0,
            table: ""
        };

        $scope.sortByAsc = true;
        $scope.selectedTab = $stateParams.tab;
    }

    /**
     * If no data then return empty.
     * Iterates through data elements aka tables.
     * Every searchResults "tableName" equals to responses "tableName" data.
     * And if response "tableName" equals selected
     * tabs then data is saved to $scope.response.
     * After loading the search overlay is removed.
     * @param result Response from search query
     */
    function onGlobalDataLoaded(result) {
        // console.log(result);
        if (!result.data) return;

        // Number of possible tabs
        if (result.data.length <= 11) {
            // console.log(result.data);

            result.data.forEach(function (response) {
                // console.log(response);
                if (response !== null) {
                    if (response.numFound > 0) {
                        $scope.selectedTab = response.table;

                        $scope.searchResults[response.table] = response;
                        if (response.table === $scope.selectedTab) {
                            $scope.response.results = response.response;
                            $scope.response.count = response.numFound;
                            $scope.response.table = response.table;
                        }
                    }
                }
            });

            $scope.selectTab($scope.response.table); //Chooses active table
            vm.searchLoadingHandler.stop();

        } else {
            // TODO: make nice error page why search failed.
            $state.go("/");
                $(function(){
                    $('#globalQuery').val('');
                });
        }
    }

    /**
     * Default will be specimen.
     * Gives $scope.response correspondent data which
     * will be shown to user when click is made on certain tab.
     * @param tabTitle Tab's name
     */
    $scope.selectTab = function (tabTitle) {
        if (!tabTitle) {
            tabTitle = "specimen";
        }
        $stateParams.tab = tabTitle;

        $scope.searchParameters.tab = tabTitle;
        // console.log($scope.searchParameters);

        $scope.selectedTab = tabTitle;

        // console.log($stateParams.page);

        $state.go("global",
            {
                tab: $scope.searchParameters.tab,
                page: $scope.searchParameters.page,
                paginateBy: $scope.searchParameters.paginateBy,
                query: $stateParams.query
            },
            {
                location: "replace",
                inherit: false,
                notify: false
            });

        $scope.response.results = $scope.searchResults[tabTitle].response;
        $scope.response.count = $scope.searchResults[tabTitle].numFound;
        $scope.response.table = $scope.searchResults[tabTitle].table;
        // console.log($scope.response);
    };


    /**
     * Switched global search fields ordering
     * between ASCENDING or DESCENDING
     */
    $scope.search = function () {
        $scope.response.results = $filter('orderBy')($scope.response.results,
            ($scope.searchParameters.sortField.order == 'DESCENDING' ? '-' : '') + $scope.searchParameters.sortField.sortBy);
    };

    /**
     * Gets results length for a certain tab.
     * Number of elements in a current table.
     * @param tab Tab's name
     * @returns Integer value of results length
     */
    $scope.getResultsLength = function (tab) {
        if ($scope.searchResults[tab].response != null) {
            return $scope.searchResults[tab].numFound;
        } else {
            return 0;
        }
    };

    $scope.goToPageOne = function() {
        // window.location.assign(window.location.origin + "/search/" + $scope.searchParameters.tab + "/1/" + $scope.searchParameters.paginateBy + "/" + $scope.$parent.globalQuery);
        window.location.assign(window.location.origin + "/search/" + $scope.$parent.globalQuery + "/" + $scope.searchParameters.tab + "/1/" + $scope.searchParameters.paginateBy);
    };

    /**
     * Gets number of results found with a certain query.
     * @param tab Tab's name
     * @returns Integer value of responses numFound
     */
    // $scope.getResultsNumFound = function (tab) {
    //     if ($scope.searchResults[tab].response != null) {
    //         return $scope.searchResults[tab].numFound;
    //     } else {
    //         return 0;
    //     }
    // };

    /**
     * Returns true if current open tab name
     * equals to selectedTab
     * @param tab name
     * @returns boolean value
     */
    $scope.isTabActive = function (tab) {
        return tab == $scope.selectedTab;
    };

    // Initializes global search
    $scope.searchGlobally();
};

constructor.$inject = [
    'configuration',
    '$filter',
    '$translate',
    '$http',
    'ApplicationService',
    '$state',
    '$stateParams',
    '$scope',
    '$rootScope',
    'WebPagesFactory',
    'GlobalSearchFactory',
    'bsLoadingOverlayService'
];

module.controller("GlobalSearchController", constructor);

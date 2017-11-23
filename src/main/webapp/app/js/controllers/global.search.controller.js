var module = angular.module("geoApp");

/**
 *
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
        GlobalSearchFactory.searchGlobally($stateParams.query, onGlobalDataLoaded);
    };

    /**
     * Initialises default values
     */
    function initialisesGlobalSearchVariables() {
        $scope.searchResults = {
            "specimen": {},
            "sample": {},
            "locality": {},
            "reference": {},
            "stratigraphy": {},
            "image": {},
            "taxon": {}
        };

        addClientSorting();

        $scope.response = {
            results: [],
            related_data: {}
        };
        $scope.selectedTab = $stateParams.tab;
    }

    /**
     * Sets searchParameters that sortBy is ID
     * and order is DESCENDING.
     * Also sortByAsc variable is set to true
     */
    function addClientSorting() {
        $scope.searchParameters = {
            sortField: {
                sortBy: "id",
                order: "DESCENDING"
            }
        };
        $scope.sortByAsc = true;
    }

    /**
     * If no data then return empty.
     * Iterates through data elements aka tables.
     * Every searchResults "tableName" equals to responses "tableName" data.
     * And if response "tablename" equals selected
     * tabs then data is saved to $scope.response.
     * After loading the search overlay is removed.
     * @param result Response from search query
     */
    function onGlobalDataLoaded(result) {
        if (!result.data) return;
        result.data.forEach(function (response) {

            /* Chooses table with info to be active TODO: should be changed to first table with info to be active */
            if (response.table !== null) {
                $scope.selectedTab = response.table;
            }

            $scope.searchResults[response.table] = response;
            if (response.table === $scope.selectedTab) {
                $scope.response.results = response.results;
                $scope.response.related_data = response.related_data;
            }
        });
        vm.searchLoadingHandler.stop();
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
        $state.go("global", {query: $stateParams.query, tab: tabTitle}, {location: "replace", inherit: false, notify: false});
        $stateParams.tab = tabTitle;
        $scope.selectedTab = tabTitle;
        $scope.response.results = $scope.searchResults[tabTitle].results;
        $scope.response.related_data = $scope.searchResults[tabTitle].related_data;
    };


    /**
     * Switches global search fields
     * between ASCENDING or DESCENDING
     */
    $scope.search = function () {
        // console.log($scope.searchParameters.sortField.sortBy);
        $scope.response.results = $filter('orderBy')($scope.response.results,
            ($scope.searchParameters.sortField.order == 'DESCENDING' ? '-' : '') + $scope.searchParameters.sortField.sortBy);
    };

    /**
     * Gets results length for tabs which
     * is shown after the tab name in parentheses
     * @param tab Tab's name
     * @returns Integer value of results length
     */
    $scope.getResultsLength = function (tab) {
        if ($scope.searchResults[tab].results != null) {
            return $scope.searchResults[tab].results.length;
        } else {
            return 0;
        }
    };

    /**
     * Returns true if current open tab name
     * equals to selectedTab
     * @param tab Tab's name
     * @returns boolean value
     */
    $scope.isTabActive = function (tab) {
        return tab == $scope.selectedTab;
    };

    $scope.searchGlobally();
    $scope.selectTab($stateParams.tab);
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

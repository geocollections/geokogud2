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

        console.log($scope.searchParameters);
        console.log($stateParams.query);

        GlobalSearchFactory.searchGlobally($stateParams.tab, $stateParams.page, $stateParams.paginateBy, $stateParams.query, onGlobalDataLoaded);
    };

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

        addClientSorting();
        $stateParams.tab = "specimen";
        $stateParams.page = 0;
        $stateParams.paginateBy = 100;

        $scope.response = {
            results: [],
            count: 0
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
            },
            maxSize: 5,
            page: 1,
            paginateBy: 100
        };
        $scope.sortByAsc = true;
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
        console.log(result);
        if (!result.data) return;

        // Number of possible tabs
        if (result.data.length <= 11) {
            console.log(result.data);

            result.data.forEach(function (response) {
                console.log(response);
                if (response !== null) {
                    if (response.numFound > 0) {
                        $scope.selectedTab = response.table;

                        $scope.searchResults[response.table] = response;
                        if (response.table === $scope.selectedTab) {
                            $scope.response.results = response.response;
                            $scope.response.count = response.numFound;
                        }
                    }
                }
            });
            vm.searchLoadingHandler.stop();

            // Animates to search tabs. TODO: maybe enable, maybe not
            // $('html, body').animate({
            //     scrollTop: ($("#searches").offset().top - 70)
            // }, 'fast');

        } else {
            // Search fails
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
        $scope.selectedTab = tabTitle;
        $state.go("global", {tab: $stateParams.tabTitle, page: $stateParams.page, query: $stateParams.query}, {location: "replace", inherit: false, notify: false});
        $scope.response.results = $scope.searchResults[tabTitle].response;
        $scope.response.count = $scope.searchResults[tabTitle].numFound;
        console.log($scope.response);
    };


    /**
     * Switches global search fields
     * between ASCENDING or DESCENDING
     */
    $scope.search = function () {
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
        if ($scope.searchResults[tab].response != null) {
            return $scope.searchResults[tab].numFound;
        } else {
            return 0;
        }
    };

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

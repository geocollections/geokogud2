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
            related_data: {},
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
            }
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
        if (!result.data) return;

        // Number of possible tabs
        if (result.data.length <= 7) {
            console.log(result.data);

            for (var index in result.data) {
                var response = result.data[index].response;
                console.log(response.numFound);

                if (index === "0") {
                    $scope.selectedTab = "specimen";
                } else if (index === "1") {
                    $scope.selectedTab = "sample";
                }

                $scope.searchResults[$scope.selectedTab] = response.docs;

                if ($scope.selectedTab === "specimen") {
                    $scope.response.count = response.numFound;
                    $scope.response.results = $scope.searchResults["specimen"];
                } else if ($scope.selectedTab === "sample") {
                    $scope.response.count = $scope.searchResults["sample"].numFound;
                    $scope.response.results = $scope.searchResults["sample"];
                }
                console.log($scope.response);

            }

            //
            // for (var data in sortedResult) {
            //     $scope.searchResults[sortedResult[data].table] = sortedResult[data];
            //     if (sortedResult[data].table === $scope.selectedTab) {
            //         $scope.response.results = sortedResult[data].results;
            //         $scope.response.related_data = sortedResult[data].related_data;
            //     }
            // }
            // var sortedResult = sortIntoRightQueue(result);
            //
            // for (var data in sortedResult) {
            //     console.log(data);
            //     // Null check is already been done in sortIntoRightQueue function
            //     // Last result is going to be active tab because it is already sorted into correct queue
            //     $scope.selectedTab = sortedResult[data].table;
            //
            //     $scope.searchResults[sortedResult[data].table] = sortedResult[data];
            //     if (sortedResult[data].table === $scope.selectedTab) {
            //         $scope.response.results = sortedResult[data].results;
            //         $scope.response.related_data = sortedResult[data].related_data;
            //     }
            // }

            vm.searchLoadingHandler.stop();

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
     * Sorts result into needed order
     * @param unsortedResult raw response from back-end
     * @returns sorted result in the order of tabs in front-end
     */
    function sortIntoRightQueue(unsortedResult) {
        var sortedResult = {};

        unsortedResult.data.forEach(function (response) {
            if (response.table != null) {

                // Using reversed order because when looping next time it chooses the last one to be active tab
                if (response.table == "taxon") {
                    sortedResult[0] = response;
                } else if (response.table == "image") {
                    sortedResult[1] = response;
                } else if (response.table == "stratigraphy") {
                    sortedResult[2] = response;
                } else if (response.table == "reference") {
                    sortedResult[3] = response;
                } else if (response.table == "locality") {
                    sortedResult[4] = response;
                } else if (response.table == "sample") {
                    sortedResult[5] = response;
                } else if (response.table == "specimen") {
                    sortedResult[6] = response;
                }
            }
        });

        return sortedResult;
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
            // return $scope.searchResults[tab].results.length;
            return $scope.searchResults[tab].results.count;
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

var module = angular.module("geoApp");

/**
 * @param configuration Used to get configurations from config.json
 * @param applicationService Used to get data from applicationService aka service.js
 * @param WebPagesFactory Used to call getData function which comes from utils.js under WebPagesFactory
 */
var constructor = function (configuration, $translate, $http, applicationService, $state, $scope, $rootScope, WebPagesFactory) {

    // View model
    var vm = this;
    vm.service = applicationService;
    vm.configuration = configuration;

    vm.showNews = showNews;
    vm.yearFilter = yearFilter;
    vm.getTitle = getTitle;
    vm.getText = getText;
    vm.getContent = getContent;
    vm.changeLanguage = changeLanguage;
    vm.isSearch = isSearch;
    vm.isDetailForm = isDetailForm;
    vm.isGlobalSearch = isGlobalSearch;
    vm.switchYear = switchYear;

    // Initial value for which year to show
    $scope.yearToShow = 0;

    // Event listener for rootScope. Everytime its state
    // changes successfully then asynLoadData is called
    $rootScope.$on('$stateChangeSuccess', function() {
        asyncLoadData();
    });

    /**
     * Loads all the useful wepage content unless it's
     * global search or new "pop-up" window. Webpage content
     * like news and different columns and so on
     */
    function asyncLoadData () {
        if(!isDetailForm() && !isGlobalSearch() && !isSearch()) {

            // Uses function from service.js which gets news using httpGet() from /webnews
            applicationService.getNews(onNewsData);

            // Following lines are used to get data from /webpages/{id}
            vm.geocollection = getWebPageById(2, "geocollection");
            vm.usingCollections = getWebPageById(32, "usingCollections");
            vm.database = getWebPageById(21, "database");
            vm.aboutFirstColumn = getWebPageById(56, "aboutFirstColumn");
            vm.aboutSecondColumn = getWebPageById(57, "aboutSecondColumn");
            vm.aboutThirdColumn = getWebPageById(58, "aboutThirdColumn");
            vm.git = getWebPageById(44, "git");
            vm.tug = getWebPageById(63, "tug");
            vm.elm = getWebPageById(64, "elm");
        }
        if (isSearch()) {
            vm.searchTips = getWebPageById(43, "searchTips");
            vm.drillCoreSearch = getWebPageById(53, "drillCoreSearch");
            vm.protoArchiveQuery = getWebPageById(54, "protoArchiveQuery");
            vm.doiIdentifiers = getWebPageById(55, "doiIdentifiers");
        }


    }

    /**
     * Gets results from /webpages/{id} and makes
     * variables equal to correct data.
     * @param id Identificator for desired information
     * @param page Name which given to certain result
     */
    function getWebPageById(id, page) {

        var myDataPromise = WebPagesFactory.getData(id);

        myDataPromise.then(function(result) {
            if (page == "geocollection") { vm.geocollection = result; }
            else if (page == "usingCollections") { vm.usingCollections = result; }
            else if (page == "database") { vm.database = result; }
            else if (page == "aboutFirstColumn") { vm.aboutFirstColumn = result; }
            else if (page == "aboutSecondColumn") { vm.aboutSecondColumn = result; }
            else if (page == "aboutThirdColumn") { vm.aboutThirdColumn = result; }
            else if (page == "git") { vm.git = result;}
            else if (page == "tug") { vm.tug = result;}
            else if (page == "elm") { vm.elm = result;}
            else if (page == "searchTips") { vm.searchTips = result; }
            else if (page == "drillCoreSearch") { vm.drillCoreSearch = result; }
            else if (page == "protoArchiveQuery") { vm.protoArchiveQuery = result; }
            else if (page == "doiIdentifiers") { vm.doiIdentifiers = result; }
        });
    }

    /**
     * Iterates through each webnews object and sets
     * $scopes yearToShow to first year got from response
     * @param response Webnews data from API
     */
    function onNewsData(response) {
        vm.years = [];
        vm.news = response.data;

        angular.forEach(vm.news.results, function(currentNews) {
            // This gets only the year (yyyy-mm-dd)
            var year = currentNews.date_added.split("-")[0];
            // If year is not in list then it will be pushed there
            if (vm.years.indexOf(year) == -1) { vm.years.push(year); }
        });
        $scope.yearToShow = vm.years[0];
    }

    /**
     * User clicks on certain year under news section
     * then yearToShow value updates as what was
     * clickable object's innerText
     * @param $event Click event
     */
    function showNews($event) {
        $scope.yearToShow = $event.target.innerText;
    }

    /**
     * Switches years so current yearToShow equals selectedYear.
     * @param selectedYear Given year
     */
    function switchYear(selectedYear) {
        if (selectedYear != null) { $scope.yearToShow = selectedYear; }
    }

    /**
     * Filters yearToShow to equal to array object's year
     * @param id Identificator for array objects
     * @returns returns boolean value
     */
    function yearFilter(id) {
        if (id!= null) { return id.date_added.startsWith($scope.yearToShow); }
        return null;
    }

    /**
     * Gets elements title
     * @param id Identificator for array objects
     * @returns Returns correspondent title either estonian or english
     */
    function getTitle(id) {
        if (id != null) { return $translate.use() === "et" ? id.title_et : id.title_en; }
        return null;
    }

    /**
     * Gets elements text
     * @param id Identificator for array objects
     * @returns Returns correspondent text
     */
    function getText(id) {
        if (id != null) { return $translate.use() === "et" ? id.text_et : id.text_en; }
        return null;
    }

    /**
     * Gets elements content
     * @param webpage Identificator for array objects
     * @returns return correspondent content
     */
    function getContent(webpage) {
        if (webpage != null) { return $translate.use() === "et" ? webpage.content_et : webpage.content_en; }
        return null;
    }

    /**
     * Sets $translation parameter to use
     * chosen language for application
     * @param langKey Language short code
     */
    function changeLanguage(langKey) {
        $translate.use(langKey);
    }

    //default ui-bootstrap datepicker options
    $rootScope.datePickerOptions = {
        class: 'datepicker',
        startingDay: 1
    };

    /**
     * True if states current name equals any of the search states
     * aka if user is on search page then returns true.
     * @returns boolean value
     */
    function isSearch() {
        var searchStates = [
            'specimens',
            'samples',
            'drillcores',
            'localities',
            'references',
            'stratigraphies',
            'analyses',
            'preparations',
            'photoArchives',
            'soils',
            'dois'
            ];
        return searchStates.indexOf($state.current.name) > -1;
    }

    /**
     * Checks if user is on detail form or not.
     * @returns boolean value
     */
    function isDetailForm () {
        return $state.params && $state.params.id;
    }

    /**
     * Checks if is user on global search or not
     * @returns boolean value
     */
    function isGlobalSearch () {
        return $state.params && $state.params.query;
    }
};

constructor.$inject =
    [
        'configuration',
        '$translate',
        '$http',
        'ApplicationService',
        '$state',
        '$scope',
        '$rootScope',
        'WebPagesFactory'
    ];

module.controller("MainController", constructor);

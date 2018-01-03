// Data in [] are dependencies for application module
var module = angular.module("geoApp",
    [
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
        'ui.router',
        'pascalprecht.translate',
        'ngFileUpload',
        'ngCookies',
        'ngSanitize',
        'ngAnimate',
        'ui.select2',
        'ngStorage',
        'bsLoadingOverlay',
        'ngPageTitle'
    ]);

module.config(function ($translateProvider, $locationProvider) {

    // link to docs which helps you understand html5Mode and hashPrefix
    // https://docs.angularjs.org/api/ng/provider/$locationProvider#$locationProvider-methods
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    // Overrides the default linking path prefix from '!' to ''
    $locationProvider.hashPrefix('');

    // Loads Page and API translations
    $translateProvider.useStaticFilesLoader({
        files: [{
                    prefix: 'app/i18n/translations_',
                    suffix: '.json'
                },
                {
                    prefix: 'app/i18n/translations_API_',
                    suffix: '.json'
                }]
    });

    // Necessary for app translations
    $translateProvider.preferredLanguage('et');
    $translateProvider.fallbackLanguage('en');
    $translateProvider.useLocalStorage();
    $translateProvider.useSanitizeValueStrategy('escape');
    $translateProvider.forceAsyncReload(true);
});

var fetchData = function () {
    var initInjector = angular.injector(["ng"]);
    var $http = initInjector.get("$http");

    // If config.json does not exist then console.log is thrown;
    return $http.get("config.json").then(function(response) {
        module.constant("configuration", response.data);
    }, function(errorResponse) {
        console.log("configuration missing!");
    });
};

/**
 *  Manual initialization of angular bootstrap, this is called
 * after loading and defining all modules. After that it is not
 * possible to add controllers, services, directives, etc.
 */
var bootstrapApplication = function () {
    angular.element(document).ready(function() {
        angular.bootstrap(document, ["geoApp"]);
    });
};
fetchData().then(bootstrapApplication);
var module = angular.module("geoApp");

module.config(function($stateProvider, $urlRouterProvider) {

    // For any unmatched url, redirect to '/' aka home
    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state('/',{
            data: {
                pageTitle: "{{'TITLES.HOME' | translate}}"
            },
            url: "/",
            templateUrl: "app/templates/main/geokogud.html"
    })
        .state('map',{
            data: {
                pageTitle: "{{'TITLES.MAP' | translate}}"
            },
            url: "/map",
            templateUrl: "app/templates/main/map.html"
    })
        .state('global',{
            data: {
                pageTitle: "{{'TITLES.GLOBAL' | translate}}"
            },
            url: "/global/?q=:query",
            templateUrl: "app/templates/main/global_search.html",
            controller: "GlobalSearchController as ctrl"
    })
        .state('search', {
            url: "/search",
            templateUrl: "app/templates/main/partial/search.html"
    })
        .state('specimens', {
            data: {
                pageTitle: "{{'TITLES.SPECIMEN' | translate}}"
            },
            url: "/specimen",
            templateUrl: "app/templates/search/specimen/specimen.html",
            controller: "SearchController as ctrl",
            params: {type: "specimens"}
    })
        .state('specimen', {
            template: '<data-ui-view/>'
    })
        .state('specimen.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_SPECIMEN' | translate}} {{ $stateParams.id }}"
            },
            url: "/specimen/:id",
            templateUrl: "app/templates/search/detail/specimen.html",
            controller: "DetailController as detailCtrl",
            params: {type: "specimens"}
    })
        .state('specimenImage', {
            template: '<data-ui-view/>'
    })
        .state('specimenImage.view', {
            data: {
                pageTitle: "{{'TITLES.SPECIMEN_IMAGE' | translate}} {{ $stateParams.id }}"
            },
            url: "/specimen_image/:id",
            templateUrl: "app/templates/search/detail/specimenImage.html",
            controller: "DetailController as detailCtrl",
            params: {type: "specimenImage"}
    })
        .state('samples', {
            data: {
                pageTitle: "{{'TITLES.SAMPLE' | translate}}"
            },
            url: "/sample",
            templateUrl: "app/templates/search/sample/sample.html",
            controller: "SearchController as ctrl",
            params: {type: "samples"}
    })
        .state('sample', {
            template: '<data-ui-view/>'
    })
        .state('sample.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_SAMPLE' | translate}} {{ $stateParams.id }}"
            },
            url: "/sample/:id",
            templateUrl: "app/templates/search/detail/sample.html",
            controller: "DetailController as detailCtrl",
            params: {type: "samples"}
    })
        .state('drillcores', {
            data: {
                pageTitle: "{{'TITLES.DRILLCORE' | translate}}"
            },
            url: "/drillcore",
            templateUrl: "app/templates/search/drillcore/drillcore.html",
            controller: "SearchController as ctrl",
            params: {type: "drillCores"}
    })
        .state('drillcore', {
            template: '<data-ui-view/>'
    })
        .state('drillcore.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_DRILLCORE' | translate}} {{ $stateParams.id }}"
            },
            url: "/drillcore/:id",
            templateUrl: "app/templates/search/detail/drillCoreDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "drillCores"}
    })
        .state('coreBox', {
            template: '<data-ui-view/>'
    })
        .state('coreBox.view', {
            data: {
                pageTitle: "{{'TITLES.COREBOX' | translate}} {{ $stateParams.id }}"
            },
            url: "/corebox/:id",
            templateUrl: "app/templates/search/detail/coreBox.html",
            controller: "DetailController as detailCtrl",
            params: {type: "corebox"}
    })
        .state('localities', {
            data: {
                pageTitle: "{{'TITLES.LOCALITY' | translate}}"
            },
            url: "/locality",
            templateUrl: "app/templates/search/locality/locality.html",
            controller: "SearchController as ctrl",
            params: {type: "localities"}
    })
        .state('locality', {
            template: '<data-ui-view/>'
    })
        .state('locality.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_LOCALITY' | translate}} {{ $stateParams.id }}"
            },
            url: "/locality/:id",
            templateUrl: "app/templates/search/detail/localityDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "localities"}
    })
        .state('references', {
            data: {
                pageTitle: "{{'TITLES.REFERENCE' | translate}}"
            },
            url: "/reference",
            templateUrl: "app/templates/search/reference/reference.html",
            controller: "SearchController as ctrl",
            params: {type: "references",doi: false}
    })
        .state('reference', {
            template: '<data-ui-view/>'
    })
        .state('reference.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_REFERENCE' | translate}} {{ $stateParams.id }}"
            },
            url: "/reference/:id",
            templateUrl: "app/templates/search/detail/referenceDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "references",doi: false}
    })
        .state('stratigraphies', {
            data: {
                pageTitle: "{{'TITLES.STRATIGRAPHY' | translate}}"
            },
            url: "/stratigraphy",
            templateUrl: "app/templates/search/stratigraphy/stratigraphy.html",
            controller: "SearchController as ctrl",
            params: {type: "stratigraphy"}
    })
        .state('stratigraphy', {
            template: '<data-ui-view/>'
    })
        .state('stratigraphy.view', {
            data: {
                pageTitle: "{{'TITLES.STRATIGRAPHY' | translate}} {{ $stateParams.id }}"
            },
            url: "/stratigraphy/:id",
            templateUrl: "app/templates/search/detail/stratigraphy.html",
            controller: "DetailController as detailCtrl",
            params: {type: "stratigraphy"}
    })
        .state('analyses', {
            data: {
                pageTitle: "{{'TITLES.ANALYSES' | translate}}"
            },
            url: "/analysis",
            templateUrl: "app/templates/search/analysis/analysis.html",
            controller: "SearchController as ctrl",
            params: {type: "analyses"}
    })
        .state('analysis', {
            template: '<data-ui-view/>'
    })
        .state('analysis.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_ANALYSES' | translate}} {{ $stateParams.id }}"
            },
            url: "/analysis/:id",
            templateUrl: 'app/templates/search/detail/analysis.html',
            controller: "DetailController as detailCtrl",
            params: {type: "analysis"}
    })
        .state('preparations', {
            data: {
                pageTitle: "{{'TITLES.PREPARATION' | translate}}"
            },
            url: "/preparation",
            templateUrl: "app/templates/search/preparation/preparation.html",
            controller: "SearchController as ctrl",
            params: {type: "preparations"}
    })
        .state('preparation', {
            template: '<data-ui-view/>'
    })
        .state('preparation.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_PREPARATION' | translate}} {{ $stateParams.id }}"
            },
            url: "/preparation/:id",
            templateUrl: "app/templates/search/detail/preparation.html",
            controller: "DetailController as detailCtrl",
            params: {type: "preparations"}
    })
        .state('photoArchives', {
            data: {
                pageTitle: "{{'TITLES.PHOTO_ARCHIVE' | translate}}"
            },
            url: "/image",
            templateUrl: "app/templates/search/image/image.html",
            controller: "SearchController as ctrl",
            params: {type: "photoArchive"}
    })
        .state('photoArchive', {
            template: '<data-ui-view/>'
    })
        .state('photoArchive.view', {
            data: {
                pageTitle: "{{'TITLES.DETAIL_PHOTO_ARCHIVE' | translate}} {{ $stateParams.id }}"
            },
            url: "/image/:id",
            templateUrl: "app/templates/search/detail/photoArchive.html",
            controller: "DetailController as detailCtrl",
            params: {type: "photoArchive"}
    })
            // SOIL IS CURRENTLY NOT SUPPORTED
    //     .state('soils', {
    //         url: "/soilsite",
    //         templateUrl: "app/templates/search/soil_site/soil_site.html",
    //         controller: "SearchController as ctrl",
    //         params: {type: "soil"}
    // })
    //     .state('soil', {
    //         template: '<data-ui-view/>'
    // })
    //     .state('soil.view', {
    //         url: "/soilsite/:id",
    //         templateUrl: "app/templates/search/detail/soilDetails.html",
    //         controller: "DetailController as detailCtrl",
    //         params: {type: "soil"}
    //
    // })
        .state('dois', {
            data: {
                pageTitle: "{{'TITLES.DOI' | translate}}"
            },
            url: "/doi",
            templateUrl: "app/templates/search/doi/doi.html",
            controller: "SearchController as ctrl",
            params: {type: "doi"}
    })
        .state('doi', {
            template: '<data-ui-view/>'
    })
        .state('doi.view', {
            data: {
                pageTitle: "{{'TITLES.DOI' | translate}} {{ $stateParams.id }}"
            },
            url: "/doi/:id",
            templateUrl: "app/templates/search/detail/doiDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "doi"}
    })
        .state('error', {
            url: '/error',
            templateUrl: 'app/templates/main/partial/default.error.html'
        })

    //main page
        .state('news', {
            data: {
                pageTitle: "Geocollections of Estonia: News"
            },
            url: "/news",
            views: {
                "additional": {
                    templateUrl: "app/templates/main/news.html"
                }
            }
    })
        .state('usingcollection', {
            url: "/usingcollection",
            views: {
                "additional": {
                    templateUrl: "app/templates/main/using_collection.html"
                }
            }
    })
        .state('git', {
            data: {
                pageTitle: "Geocollections of Estonia: GIT"
            },
            url: "/git",
            views: {
                "additional": {
                    templateUrl: "app/templates/main/git.html"
                }
            }
    })
        .state('geocollection', {
            url: "/geocollection",
            views: {
                "additional": {
                    templateUrl: "app/templates/main/geocollection.html"
                }
            }
    })
        .state('database', {
            url: "/database",
            views: {
                "additional": {
                    templateUrl: "app/templates/main/database.html"
                }
            }
    });
});

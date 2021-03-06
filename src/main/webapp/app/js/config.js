var module = angular.module("geoApp");

module.config(function($stateProvider, $urlRouterProvider) {

    // For any unmatched url, redirect to '/' aka home
    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state('/',{
            data: {
                pageTitle: "Geocollections of Estonia"
            },
            url: "/",
            templateUrl: "app/templates/main/geokogud.html",
    })
        .state('map',{
            data: {
                pageTitle: "Geocollections of Estonia: Map"
            },
            url: "/map",
            templateUrl: "app/templates/main/map.html",
            controller: "MapController as mapCtrl"
    })
        .state('global',{
            data: {
                pageTitle: "Geocollections of Estonia: Global search"
            },
            // url: "/search/:tab/:page/:paginateBy/:query",
            url: "/search/:query/:tab/:page/:paginateBy",
            templateUrl: "app/templates/main/global_search.html",
            controller: "GlobalSearchController as ctrl"
    })
        .state('global2',{
            data: {
                pageTitle: "Geocollections of Estonia: Global search"
            },
            // url: "/search/:tab/:page/:paginateBy/:query",
            url: "/search/:query",
            templateUrl: "app/templates/main/global_search.html",
            controller: "GlobalSearchController as ctrl"
    })
    //     .state('search', {
    //         url: "/search",
    //         templateUrl: "app/templates/main/partial/search.html"
    // })
        .state('specimens', {
            data: {
                pageTitle: "Geocollections of Estonia: Specimens"
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
                pageTitle: "Geocollections of Estonia: Specimen {{ $stateParams.id }}"
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
                pageTitle: "Geocollections of Estonia: Specimen image {{ $stateParams.id }}"
            },
            url: "/specimen_image/:id",
            templateUrl: "app/templates/search/detail/specimenImage.html",
            controller: "DetailController as detailCtrl",
            params: {type: "specimenImage"}
    })
        .state('samples', {
            data: {
                pageTitle: "Geocollections of Estonia: Samples"
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
                pageTitle: "Geocollections of Estonia: Sample {{ $stateParams.id }}"
            },
            url: "/sample/:id",
            templateUrl: "app/templates/search/detail/sample.html",
            controller: "DetailController as detailCtrl",
            params: {type: "samples"}
    })
        .state('drillcores', {
            data: {
                pageTitle: "Geocollections of Estonia: Drill cores"
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
                pageTitle: "Geocollections of Estonia: Drill core {{ $stateParams.id }}"
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
                pageTitle: "Geocollections of Estonia: Corebox {{ $stateParams.id }}"
            },
            url: "/corebox/:id",
            templateUrl: "app/templates/search/detail/coreBox.html",
            controller: "DetailController as detailCtrl",
            params: {type: "corebox"}
    })
        .state('localities', {
            data: {
                pageTitle: "Geocollections of Estonia: Localities"
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
                pageTitle: "Geocollections of Estonia: Locality {{ $stateParams.id }}"
            },
            url: "/locality/:id",
            templateUrl: "app/templates/search/detail/localityDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "localities"}
    })
        .state('references', {
            data: {
                pageTitle: "Geocollections of Estonia: References"
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
                pageTitle: "Geocollections of Estonia: Reference {{ $stateParams.id }}"
            },
            url: "/reference/:id",
            templateUrl: "app/templates/search/detail/referenceDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "references",doi: false}
    })
        .state('stratigraphies', {
            data: {
                pageTitle: "Geocollections of Estonia: Stratigraphy"
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
                pageTitle: "Geocollections of Estonia: Stratigraphy {{ $stateParams.id }}"
            },
            url: "/stratigraphy/:id",
            templateUrl: "app/templates/search/detail/stratigraphy.html",
            controller: "DetailController as detailCtrl",
            params: {type: "stratigraphy"}
    })
        .state('analyses', {
            data: {
                pageTitle: "Geocollections of Estonia: Analyses"
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
                pageTitle: "Geocollections of Estonia: Analysis {{ $stateParams.id }}"
            },
            url: "/analysis/:id",
            templateUrl: 'app/templates/search/detail/analysis.html',
            controller: "DetailController as detailCtrl",
            params: {type: "analysis"}
    })
        .state('preparations', {
            data: {
                pageTitle: "Geocollections of Estonia: Preparation"
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
                pageTitle: "Geocollections of Estonia: Preparation {{ $stateParams.id }}"
            },
            url: "/preparation/:id",
            templateUrl: "app/templates/search/detail/preparation.html",
            controller: "DetailController as detailCtrl",
            params: {type: "preparations"}
    })
        .state('photoArchives', {
            data: {
                pageTitle: "Geocollections of Estonia: Photo archive"
            },
            url: "/image",
            templateUrl: "app/templates/search/image/image.html",
            controller: "SearchController as ctrl",
            params: {type: "photoArchive"}
    })
        .state('photoArchive', {
            template: '<data-ui-view/>'
    })
    //     .state('photoArchive.view', {
    //         data: {
    //             pageTitle: "Geocollections of Estonia: Photo {{ $stateParams.id }}"
    //         },
    //         url: "/image/:id",
    //         templateUrl: "app/templates/search/detail/photoArchive.html",
    //         controller: "DetailController as detailCtrl",
    //         params: {type: "photoArchive"}
    // })
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
                pageTitle: "Geocollections of Estonia: DOI"
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
                pageTitle: "Geocollections of Estonia: DOI {{ $stateParams.id }}"
            },
            url: "/doi/{id:.+}",
            templateUrl: "app/templates/search/detail/doiDetails.html",
            controller: "DetailController as detailCtrl",
            params: {type: "doi"}
    })
        .state('error', {
            url: '/error',
            templateUrl: 'app/templates/main/partial/default.error.html'
        })

        .state('attachment', {
            template: '<data-ui-view/>'
        })

        .state('attachment.view', {
            data: {
                pageTitle: "Geocollections of Estonia: Attachment {{ $stateParams.id }}"
            },
            url: "/attachment/:id",
            templateUrl: "app/templates/search/detail/attachment.html",
            controller: "DetailController as detailCtrl",
            params: {type: "attachment"}
        })

        .state('collection', {
            template: '<data-ui-view/>'
        })

        .state('collection.view', {
            data: {
                pageTitle: "Geocollections of Estonia: Collection {{ $stateParams.id }}"
            },
            url: "/collection/:id",
            templateUrl: "app/templates/search/detail/collection.html",
            controller: "DetailController as detailCtrl",
            params: {type: "collection"}
        })

        .state('dataset', {
            template: '<data-ui-view/>'
        })

        .state('dataset.view', {
            data: {
                pageTitle: "Geocollections of Estonia: Dataset {{ $stateParams.id }}"
            },
            url: "/dataset/:id",
            templateUrl: "app/templates/search/detail/dataset.html",
            controller: "DetailController as detailCtrl",
            params: {type: "dataset"}
        })

        .state('file', {
            template: '<data-ui-view/>'
        })

        .state('file.view', {
            data: {
                pageTitle: "Geocollections of Estonia: File {{ $stateParams.id }}"
            },
            url: "/file/:id",
            templateUrl: "app/templates/search/detail/file.html",
            controller: "DetailController as detailCtrl",
            params: {type: "file"}
        })

        .state('library', {
            template: '<data-ui-view/>'
        })

        .state('library.view', {
            data: {
                pageTitle: "Geocollections of Estonia: Library {{ $stateParams.id }}"
            },
            url: "/library/:id",
            templateUrl: "app/templates/search/detail/library.html",
            controller: "DetailController as detailCtrl",
            params: {type: "library"}
        })


        /******************************
         *** MAIN PAGE SLIDER START ***
         ******************************/

        .state('news', {
            data: {
                pageTitle: "Geocollections of Estonia: News"
            },
            url: "/news",
            templateUrl: "app/templates/main/news.html"
    })
        .state('using_collections', {
            data: {
                pageTitle: "Geocollections of Estonia: Using collections"
            },
            url: "/using_collections",
            templateUrl: "app/templates/main/using_collection.html"
    })
        .state('geocollections', {
            data: {
                pageTitle: "Geocollections of Estonia: Geological collections"
            },
            url: "/geocollections",
            templateUrl: "app/templates/main/geocollection.html"
    })
        .state('database', {
            data: {
                pageTitle: "Geocollections of Estonia: Database"
            },
            url: "/database",
            templateUrl: "app/templates/main/database.html"
    })
        .state('git', {
            data: {
                pageTitle: "Geocollections of Estonia: GIT"
            },
            url: "/git",
            templateUrl: "app/templates/main/git.html"
    })
        .state('tug', {
            data: {
                pageTitle: "Geocollections of Estonia: TUG"
            },
            url: "/tug",
            templateUrl: "app/templates/main/tug.html"
    })
        .state('elm', {
            data: {
                pageTitle: "Geocollections of Estonia: ELM"
            },
            url: "/elm",
            templateUrl: "app/templates/main/elm.html"
    })
        .state('help', {
            data: {
                pageTitle: "Geocollections of Estonia: Help"
            },
            url: "/help",
            templateUrl: "app/templates/main/help.html"
    });

    /******************************
     ***  MAIN PAGE SLIDER END  ***
     ******************************/



});

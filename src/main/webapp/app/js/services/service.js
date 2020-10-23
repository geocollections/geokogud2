var module = angular.module("geoApp");

/**
 * Constructor for service.js
 * @param utils Comes from utils.js
 * @param configuration Comes from config.js
 * @param $window Angular's built in reference to browser's window object
 * @param $location From angular translate library
 * @returns service which contains
 */
var constructor = function (utils, configuration, $window, $location, $translate, $stateParams) {

    var service = {};

    service.departments = configuration.departments;
    service.pageSetUp = configuration.pageSetUp;
    service.location = $location;

    service.openDoiInNewWindow = openDoiInNewWindow;
    service.openInNewWindow = openInNewWindow;
    service.openUrlInNewWindow = openUrlInNewWindow;
    service.showGoogleMap = showGoogleMap;
    service.showEstonianLandBoardMap = showEstonianLandBoardMap;
    service.getTranslationRoot = getTranslationRoot;
    service.getCountsForAnalysisDetailView = getCountsForAnalysisDetailView;
    service.searchAllSpecimenInLocality = searchAllSpecimenInLocality;
    service.returnInstitutionSlideNumber = returnInstitutionSlideNumber;
    service.searchAllSpecimensUsingCollection = searchAllSpecimensUsingCollection;
    service.setFancyBoxCaption = setFancyBoxCaption;
    service.searchAllSamplesUsingLocality = searchAllSamplesUsingLocality;
    service.searchAllSpecimensUsingReference = searchAllSpecimensUsingReference;
    service.searchAllSpecimensUsingStratigraphy = searchAllSpecimensUsingStratigraphy;
    service.searchAllSamplesUsingStratigraphy = searchAllSamplesUsingStratigraphy;
    service.getFileLink = getFileLink;
    service.getAboutDatabase = getAboutDatabase;
    service.goToExtendedSearch = goToExtendedSearch;
    service.getCurrentLanguage = getCurrentLanguage;
    service.openDoiInDoiPortal = openDoiInDoiPortal;

    service.toggle = function (el,array) {
        utils.toggleInArray(el,array)
    };

    /**
     * Calls GET request from webnewsUrl which url comes from config.json
     * utils.httpGet comes from utils.js
     * @param callback All response status codes from 200 - 299 are considered
     *                 success and will result in the success callback being called
     * @param error Any response status code outside this range is considered
     *              and will result in the error callback being called
     */
    service.getNews = function (callback, error) {
        utils.httpGet(configuration.webnewsUrl, null, callback, error);
    };

//    service.getWebPage = function (id, callback, error) {
//        return utils.httpGet(configuration.webPagesUrl + "/" + id, null, callback, error);
//    };

    service.getList = function (searchType, data, success,error,headers) {
        var url = getSearchUrl(searchType);
        if(url != null) utils.httpPost(url, data, success, error, headers, true);
    };

    service.getSearchParamsFromUrl = function () {
        return utils.decodeUrl();
    };
    service.getMapParamsFromUrl = function () {
        return utils.decodeMapUrl();
    };
    service.getEntity = function (searchType, id, callback, error) {
        // console.log("searchType: " + searchType);
        // console.log("id: " + id);
        // console.log("callback: " + callback);
        var url = getDetailUrl(searchType);
        // console.log("url: " + url);
        if (id.includes("10.23679")) {
            console.log(url + "/" + id.substring(9))
            utils.httpGet(url + "/" + id.substring(9), null, callback, error);
        } else if (id.includes("10.15152/GEO.") || id.includes("10.15152/geo.")) { // If clause for universal DOI identifier
            utils.httpGet(url + "/" + id.substring(13), null, callback, error);
        } else {
            utils.httpGet(url + "/" + id, null, callback, error);
        }
    };

    service.loadMapData = function (callback, error) {
        utils.httpGet(configuration.mapData.allLocalities, null, callback, error);
    };
    service.loadMapDataOnFilterChange = function (filters, callback, error, headers) {
        utils.httpPost(configuration.mapData.specificLocalities, filters, callback, error, headers, false);
    };

    service.autocompleteSearch = function (table, val, searchField) {
        utils.httpGet(configuration.autocompleteUrl, {table: table, term: val, searchField: searchField}, null, null);
    };
    service.getEditPortalLoggedInState = function (callback) {
        var url = configuration.editApi + '/is_logged_in/login_state';
        utils.httpGet(url, null, callback)
    };

    // Polyfill for includes method, issue #138 (IE fix, 24.09.2018)
    if (!String.prototype.includes) {
        String.prototype.includes = function(search, start) {
            'use strict';
            if (typeof start !== 'number') {
                start = 0;
            }

            if (start + search.length > this.length) {
                return false;
            } else {
                return this.indexOf(search, start) !== -1;
            }
        };
    }

    // Polyfill for startsWith method, issue #138 (IE fix, 21.10.2018)
    if (!String.prototype.startsWith) {
        String.prototype.startsWith = function(searchString, position) {
            position = position || 0;
            return this.indexOf(searchString, position) === position;
        };
    }

    // Polyfill for endsWith method, issue #138 (IE fix, 30.01.2019)
    if (!String.prototype.endsWith) {
        String.prototype.endsWith = function(search, this_len) {
            if (this_len === undefined || this_len > this.length) {
                this_len = this.length;
            }
            return this.substring(this_len - search.length, this_len) === search;
        };
    }

    function getDetailUrl (searchType) {
        var url = null;
        switch (searchType) {
            case "specimens" : url = configuration.specimenDetailUrl; break;
            case "specimenImage" : url = configuration.specimenImageDetailUrl; break;
            case "samples" : url = configuration.sampleDetailUrl; break;
            case "drillCores" : url = configuration.drillCoreDetailUrl; break;
            case "corebox" : url = configuration.coreBoxDetailUrl; break;
            case "localities" : url = configuration.localityDetailUrl; break;
            case "references" : url = configuration.referenceDetailUrl; break;
            case "stratigraphy" : url = configuration.stratigraphyDetailUrl; break;
            case "analysis" : url = configuration.analysisDetailUrl; break;
            case "preparations" : url = configuration.preparationDetailUrl; break;
            case "photoArchive" : url = configuration.photoArchiveDetailUrl; break;
            case "soil" : url = configuration.soilDetailUrl; break;
            case "doi" : url = configuration.doiDetailUrl; break;
            case "attachment" : url = configuration.attachmentDetailUrl; break;
            case "collection" : url = configuration.collectionDetailUrl; break;
            case "dataset" : url = configuration.datasetDetailUrl; break;
            case "file" : url = configuration.attachmentDetailUrl; break;
            case "library" : url = configuration.libraryDetailUrl; break;
            default : break;
        }
        return url;
    }

    function getSearchUrl (searchType) {
        var url = null;
        switch (searchType) {
            case "specimens" : url = configuration.specimenUrl; break;
            case "samples" : url = configuration.sampleUrl; break;
            case "drillCores" : url = configuration.drillCoreUrl; break;
            case "localities" : url = configuration.localityUrl; break;
            case "references" : url = configuration.referenceUrl; break;
            case "stratigraphy" : url = configuration.stratigraphyUrl; break;
            case "analyses" : url = configuration.analysesUrl; break;
            case "preparations" : url = configuration.preparationUrl; break;
            case "photoArchive" : url = configuration.photoArchiveUrl; break;
            case "soil" : url = configuration.soilUrl; break;
            case "doi" : url = configuration.doiUrl; break;
            default : break;
        }
        return url;
    }

    function getTranslationRoot (searchType) {
        var root = "SEARCH.";
        switch (searchType) {
            case "specimens" : root += "SPECIMENS"; break;
            case "samples" : root += "SAMPLES"; break;
            case "drillCores" : root += "DRILL_CORES"; break;
            case "localities" : root += "LOCALITIES"; break;
            case "references" : root += "REFERENCES"; break;
            case "stratigraphy" : root += "STRATIGRAPHY"; break;
            case "analyses" : root += "ANALYSES"; break;
            case "preparations" : root += "PREPARATIONS"; break;
            case "photoArchive" : root += "PHOTO_ARCHIVE"; break;
            case "soil" : root += "SOIL_SITES"; break;
            case "doi" : root += "DOI"; break;
            default : break;
        }
        return root;
    }

    function openDoiInNewWindow(params) {
        var doi = params.doi;
        if (doi && doi != null) {
            doi = doi.replace(/\s/g,''); // removes all whitespace
            if (doi.startsWith('http')) {
                $window.open(doi, '', 'width=600,height=750,scrollbars, resizable');

            } else {
                doi = "1" + doi.substring(doi.indexOf("10.")+1); // gets the doi identifier
                $window.open('https://doi.org/' + doi, '', 'width=600,height=750,scrollbars, resizable');
            }
        }
    }

    function showGoogleMap(lat, lon, localityName) {
        $window.open(
            'http://maps.google.com/?q=' + lat + ',' + lon + ' (' + localityName + ')',
            '',
            'width=800,height=600,scrollbars, resizable'
        );
    }

    function showEstonianLandBoardMap(lat,lon, name) {
        $window.open(
            'https://xgis.maaamet.ee/xgis2/page/app/maainfo?punkt=' + geoToLest(lat,lon) + '&moot=300&tooltip=' + name,
            '',
            'width=800,height=600,scrollbars, resizable'
        );
    }

    /* Code from:  http://www.maaamet.ee/rr/geo-lest/files/geo-lest_function_php.txt */
    function geoToLest(north, east) {
        var LAT = north * (Math.PI / 180);
        var LON = east * (Math.PI / 180);
        var a = 6378137.0;
        var F = 298.257222100883;
        var RF = F;
        F = 1 / F;
        var B0 = (57.0 + 31.0 / 60.0 + 3.194148 / 3600.0) * (Math.PI / 180);
        var L0 = 24.0 * (Math.PI / 180);
        var FN = 6375000.0;
        var FE = 500000.0;
        var B1 = (59.0 + 20.0 / 60.0) * (Math.PI / 180);
        var B2 = 58.0 * (Math.PI / 180);
        // let xx = north - FN;
        // let yy = east - FE;
        var f1 = 1 / RF;
        var er = 2.0 * f1 - f1 * f1;
        var e = Math.sqrt(er);
        var t1 = Math.sqrt(
            ((1.0 - Math.sin(B1)) / (1.0 + Math.sin(B1))) *
            Math.pow((1.0 + e * Math.sin(B1)) / (1.0 - e * Math.sin(B1)), e)
        );
        var t2 = Math.sqrt(
            ((1.0 - Math.sin(B2)) / (1.0 + Math.sin(B2))) *
            Math.pow((1.0 + e * Math.sin(B2)) / (1.0 - e * Math.sin(B2)), e)
        );
        var t0 = Math.sqrt(
            ((1.0 - Math.sin(B0)) / (1.0 + Math.sin(B0))) *
            Math.pow((1.0 + e * Math.sin(B0)) / (1.0 - e * Math.sin(B0)), e)
        );
        var t = Math.sqrt(
            ((1.0 - Math.sin(LAT)) / (1.0 + Math.sin(LAT))) *
            Math.pow((1.0 + e * Math.sin(LAT)) / (1.0 - e * Math.sin(LAT)), e)
        );
        var m1 =
            Math.cos(B1) / Math.pow(1.0 - er * Math.sin(B1) * Math.sin(B1), 0.5);
        var m2 =
            Math.cos(B2) / Math.pow(1.0 - er * Math.sin(B2) * Math.sin(B2), 0.5);
        var n = (Math.log(m1) - Math.log(m2)) / (Math.log(t1) - Math.log(t2));
        var FF = m1 / (n * Math.pow(t1, n));
        var p0 = a * FF * Math.pow(t0, n);
        var FII = n * (LON - L0);
        var p = a * FF * Math.pow(t, n);
        n = p0 - p * Math.cos(FII) + FN;
        e = p * Math.sin(FII) + FE;

        return [n, e];
    }

    function openInNewWindow(params) {
        var width = 600;
        var target = ''
        if (params.target) {
            target = params.target
        }
        // console.log(params.object + "/" + params.id);
        switch (params.object) {
            case "specimen":
                width = 1050;
                break;
            case "locality":
                width = 1025;
                break;
            case "doi":
                width = 1000;
        }
        // New views are opened in the same window for locality and specimen view, JUST FOR TESTING PURPOSES
        if (!$window.location.href.includes('search') && ($window.location.href.includes('locality/') || $window.location.href.includes('specimen/'))) {
            $window.open('/' + params.object + '/' + params.id, '_self', 'width=' + width + ',height=750,scrollbars, resizable');
        } else {
            $window.open('/' + params.object + '/' + params.id, target, 'width=' + width + ',height=750,scrollbars, resizable');
        }
    }

    function openDoiInDoiPortal(identifier) {
        if (identifier) {
            $window.open(
                "https://doi.geocollections.info/" + identifier,
                "DoiWindow",
                'width=1050,height=750,scrollbars, resizable'
            )
        }
    }

    function openUrlInNewWindow(params) {
        // console.log(params.url);

        // Otherwise some urls won't open
        var url = params.url
        if (url.includes('http')) {
            url = url.substr(url.indexOf('http'))
        } else {
            url = 'http://' + url
        }

        if (params.target) {
            $window.open(url, params.target);
        } else {
            $window.open(url, '', 'width=600,height=750,scrollbars, resizable');
        }
    }

    // Used in analysis detail view for analysis results, counts total number of grains.
    function getCountsForAnalysisDetailView(relatedData) {
        var numOfCounts = 0;
        angular.forEach(relatedData, function(result) {
            if (result.name.includes("Counts [grains]")) {
                numOfCounts += result.value;
            }
        });
        return numOfCounts;
    }

    // Opens new window with new search url for specimens.
    function searchAllSpecimenInLocality(params) {
        if ($translate.use() === 'et') {
            $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=" + params.locality + "&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
        } else {
            $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=" + params.localityEn + "&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
        }
    }

    // Opens new window with new search url for specimens.
    function searchAllSpecimensUsingCollection(params) {
        $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=" + params.collection + "&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
    }

    // Opens new window with new search url for specimens.
    function searchAllSpecimensUsingReference(params) {
        $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=11&classification=&taxon_1=11&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=&stratigraphy_1=11&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=" + params.reference + "&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
    }

    // Opens new window with new search url for samples
    function searchAllSpecimensUsingStratigraphy(params) {
        if ($translate.use() === 'et') {
            $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=11&classification=&taxon_1=11&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=&stratigraphy_1=11&stratigraphy=" + params.stratigraphy + "&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        } else {
            $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=11&classification=&taxon_1=11&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=&stratigraphy_1=11&stratigraphy=" + params.stratigraphyEn + "&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        }
    }

    // Opens new window with new search url for samples
    function searchAllSamplesUsingLocality(params) {
        if ($translate.use() === 'et') {
            $window.open($window.location.origin + "/sample?sample_number_1=1&sample_number=&locality_1=1&locality=" + params.locality + "&stratigraphy_1=11&stratigraphy=&stratigraphy_bed_1=1&stratigraphy_bed=&agent_1=1&agent=&id_1=5&id=&country_1=1&country=&location_1=1&location=&taxon_1=1&taxon=&frequency_1=5&frequency=&analysis_1=1&analysis=&component_1=1&component=&content_1=5&content=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=sample&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        } else {
            $window.open($window.location.origin + "/sample?sample_number_1=1&sample_number=&locality_1=1&locality=" + params.localityEn + "&stratigraphy_1=11&stratigraphy=&stratigraphy_bed_1=1&stratigraphy_bed=&agent_1=1&agent=&id_1=5&id=&country_1=1&country=&location_1=1&location=&taxon_1=1&taxon=&frequency_1=5&frequency=&analysis_1=1&analysis=&component_1=1&component=&content_1=5&content=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=sample&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        }
    }

    // Opens new window with new search url for samples
    function searchAllSamplesUsingStratigraphy(params) {
        if ($translate.use() === 'et') {
            $window.open($window.location.origin + "/sample?sample_number_1=1&sample_number=&locality_1=1&locality=&stratigraphy_1=11&stratigraphy=" + params.stratigraphy + "&stratigraphy_bed_1=1&stratigraphy_bed=&agent_1=1&agent=&id_1=5&id=&country_1=1&country=&location_1=1&location=&taxon_1=1&taxon=&frequency_1=5&frequency=&analysis_1=1&analysis=&component_1=1&component=&content_1=5&content=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=sample&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        } else {
            $window.open($window.location.origin + "/sample?sample_number_1=1&sample_number=&locality_1=1&locality=&stratigraphy_1=11&stratigraphy=" + params.stratigraphyEn + "&stratigraphy_bed_1=1&stratigraphy_bed=&agent_1=1&agent=&id_1=5&id=&country_1=1&country=&location_1=1&location=&taxon_1=1&taxon=&frequency_1=5&frequency=&analysis_1=1&analysis=&component_1=1&component=&content_1=5&content=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=sample&maxSize=5&paginateBy=25&sort=id&sortdir=DESC")
        }
    }

    function returnInstitutionSlideNumber(institution) {
        if (institution === "GIT") return 4;
        if (institution === "TUG") return 5;
        if (institution === "ELM") return 6;
    }

    service.openInstitutionUrl = function (institution) {
        if (institution === "TUGO") {
            $window.open('https://www.geoloogia.ut.ee/et', '', 'width=600,height=750,scrollbars, resizable');
        }
        if (institution === "EGK") {
            $window.open('http://www.egk.ee/', '', 'width=600,height=750,scrollbars, resizable');
        }
    };

    /**
     * Builds caption data for images in fancybox gallery.
     * @param params Image data (author, date, etc)
     * @returns {string} value of caption data for images. (author, date, etc)
     */
    function setFancyBoxCaption(params) {
        var text = "";
        var author = "Author: ";
        var date = "Date: ";
        var licence = "Licence: ";
        var detailView = "Picture detail view";
        var url = $window.location.origin + '/file/';

        if ($stateParams.type === 'specimens') {
            url = $window.location.origin + '/file/'
        }

        if (params.date_taken && params.date_taken != null) {
            params.date_taken = new Date(params.date_taken).toDateString();
        }

        if ($translate.use() === 'et') {
            author = "Autor: ";
            date = "Kuupäev: ";
            licence = "Litsents: ";
            detailView = "Pildi detailvaade";
            if (params.date_taken && params.date_taken != null) {
                params.date_taken = new Date(params.date_taken).toLocaleDateString('et-EE')
            }
            if (params.licence_url && params.licence_url != null) {
                params.licence_url_en = params.licence_url;
            }
        }

        // AUTHOR BLOCK START
        if (params.author && params.author != null) {
            text +=
                "<div>" +
                    "<span>" + author + "</span>" +
                    "<strong><span>" + params.author + "</span></strong>";
            if (params.copyright_agent && params.copyright_agent != null) {
                text +=
                    "<strong>" +
                        "<span> / </span>" +
                        "<span>" + params.copyright_agent + "</span>" +
                    "</strong>" +
                "</div>";
            }

        } else if (params.author_free && params.author_free != null) {
            text +=
                "<div>" +
                    "<span>" + author + "</span>" +
                    "<strong><span>"+params.author_free+"</span></strong>";
            if (params.copyright_agent && params.copyright_agent != null) {
                text +=
                    "<strong>" +
                        "<span> / </span>" +
                        "<span>" + params.copyright_agent + "</span>" +
                    "</strong>" +
                "</div>";
            }
        } else if (params.copyright_agent && params.copyright_agent != null) {
            text +=
                "<div>" +
                    "<span>" + author + "</span>" +
                    "<strong>" +
                        "<span>" + params.copyright_agent + "</span>" +
                    "</strong>" +
                "</div>";
        }
        // AUTHOR BLOCK END

        // DATE BLOCK START
        if (params.date_taken && params.date_taken != null) {
            text +=
                "<div>" +
                    "<span>" + date + "</span>" +
                    "<strong>" +
                        "<span>" + params.date_taken + "</span>" +
                    "</strong>" +
                "</div>";
        } else if (params.date_taken_free && params.date_taken_free) {
            text +=
                "<div>" +
                    "<span>" + date + "</span>" +
                    "<strong>" +
                        "<span>" + params.date_taken_free + "</span>" +
                    "</strong>" +
                "</div>";
        }
        // DATE BLOCK END

        // LICENCE BLOCK START
        if (params.licence && params.licence != null) {
            text +=
                "<div>" +
                    "<span>" + licence + "</span>" +
                    "<strong>" +
                        "<a target='_blank' href='" + params.licence_url_en + "'>" +
                            "<span>" + params.licence + "</span>" +
                        "</a>" +
                    "</strong>" +
                "</div>";
        }
        // LICENCE BLOCK END

        // DETAIL VIEW BLOCK START
        if (params.id && params.id != null) {
            // TODO: Make this button into button design like in fossils app
            // Target must be because otherwise it redirects but won't close the fancybox preview
            text += "<div><a id='fancybox-file-btn' class='btn btn-sm btn-primary' target='_top' href='" + url + params.id + "'><span>" + detailView + "</span></a></div>";
        }
        // DETAIL VIEW BLOCK END

        return text;
    }

    /**
     * Build file's url using size and uuid
     * @param params object, size which can be empty or small, medium and large.
     * Also contains filename which is uuid.
     * @return string of file url.
     */
    function getFileLink(params) {
        if (params.size) {
            return "https://files.geocollections.info/" + params.size
                + "/" + params.filename.substring(0, 2)
                + "/" + params.filename.substring(2, 4)
                + "/" + params.filename;
        } else {
            return "https://files.geocollections.info/" + params.filename.substring(0, 2)
                + "/" + params.filename.substring(2, 4)
                + "/" + params.filename;
        }
    }

    function getAboutDatabase(params) {
        if (params.database !== null) {
            $window.open($window.location.origin + '/about_' + params.database.toLowerCase(), '', 'width=600,height=750,scrollbars, resizable')
        }
    }

    // Return extended search url by
    function goToExtendedSearch() {
        var href = $window.location.href;
        return href.substring(0, href.lastIndexOf('/'));
    }

    function getCurrentLanguage() {
        return $translate.use()
    }


    // FACEBOOK START
    service.shareImage = function (imageData) {
        var estText = "";
        var engText = "";

        // images
        if (imageData.database__acronym && imageData.specimen_image_attachment === 2) {
            if (imageData.author__agent !== null && imageData.author__agent) {
                estText += "Autor: " + imageData.author__agent + ". ";
                engText += "Author: " + imageData.author__agent + ". ";
            }
            if (imageData.date_created !== null && imageData.date_created) {
                estText += "Pildistamise aeg: " + new Date(imageData.date_created).toLocaleDateString('et-EE') + ". ";
                engText += "Date taken: " + new Date(imageData.date_created).toDateString() + ". ";
            }
            if (imageData.date_created === null && imageData.date_created_free !== null && imageData.date_created_free) {
                estText += "Pildistamise aeg: " + imageData.date_created_free + ". ";
                engText += "Date taken: " + imageData.date_created_free + ". ";
            }
        }

        // Specimen images
        if (imageData.specimen__database__acronym && imageData.specimen_image_attachment === 1) {
            if (imageData.specimenIdentification.count > 0) {
                if (imageData.specimenIdentification.results[0].taxon__taxon !== null && imageData.specimenIdentification.results[0].taxon__taxon) {
                    estText += "Nimi: " + imageData.specimenIdentification.results[0].taxon__taxon;
                    engText += "Name: " + imageData.specimenIdentification.results[0].taxon__taxon;
                }
                if (imageData.specimenIdentification.results[0].name && imageData.specimenIdentification.results[0].name !== null && (imageData.specimenIdentification.results[0].name != imageData.specimenIdentification.results[0].taxon__taxon)) {
                    estText += " | " + imageData.specimenIdentification.results[0].name + ". ";
                    engText += " | " + imageData.specimenIdentification.results[0].name + ". ";
                } else {
                    estText += ". ";
                    engText += ". ";
                }
            }
            if (imageData.author__agent !== null && imageData.author__agent) {
                estText += "Autor: " + imageData.author__agent + ". ";
                engText += "Author: " + imageData.author__agent + ". ";
            }
            if (imageData.date !== null && imageData.date) {
                estText += "Pildistamise aeg: " + new Date(imageData.date).toLocaleDateString('et-EE') + ". ";
                engText += "Date taken: " + new Date(imageData.date).toDateString() + ". ";
            }
            if (imageData.date === null && imageData.date_created_free !== null && imageData.date_created_free) {
                estText += "Pildistamise aeg: " + imageData.date_created_free + ". ";
                engText += "Date taken: " + imageData.date_created_free + ". ";
            }
        }

        // Other files
        if (imageData.specimen_image_attachment === 3) {
            if (imageData.description !== null && imageData.description) {
                estText += "Fail: " + imageData.description + ". ";
                engText += "File: " + imageData.description_en + ". ";
            }
            if (imageData.author__agent !== null && imageData.author__agent) {
                estText += "Autor: " + imageData.author__agent + ". ";
                engText += "Author: " + imageData.author__agent + ". ";
            }
            if (imageData.author__agent === null && imageData.author_free !== null && imageData.author_free) {
                estText += "Autor: " + imageData.author_free + ". ";
                engText += "Author: " + imageData.author_free + ". ";
            }

        }

        // Digitised references
        if (imageData.specimen_image_attachment === 4) {
            if (imageData.reference__reference !== null && imageData.reference__reference) {
                estText += "Kirjandusallikas: " + imageData.reference__reference + ". ";
                engText += "Reference: " + imageData.reference__reference + ". ";
            }
            if (imageData.author__agent !== null && imageData.author__agent) {
                estText += "Autor: " + imageData.author__agent + ". ";
                engText += "Author: " + imageData.author__agent + ". ";
            }
            if (imageData.author__agent === null && imageData.author_free !== null && imageData.author_free) {
                estText += "Autor: " + imageData.author_free + ". ";
                engText += "Author: " + imageData.author_free + ". ";
            }
        }

        // Only difference is that photo archive and specimen images have image in share dialog
        if (imageData.specimen_image_attachment === 1 || imageData.specimen_image_attachment === 2) {
            FB.ui({
                    method: 'share_open_graph',
                    action_type: 'og.shares',
                    action_properties: JSON.stringify({
                        object: {
                            'og:url': location.href,
                            'og:title': document.title,
                            'og:description': $translate.use() === 'et' ? estText : engText,
                            'og:image': getFileLink({filename: imageData.uuid_filename}),
                            'og:image:url': getFileLink({filename: imageData.uuid_filename}),
                            'og:image:secure_url': getFileLink({filename: imageData.uuid_filename})
                        }
                    })
                },
                function (response) {
                    // Action after response
                });
            <!-- TODO: Add more formats -->
        } else if (imageData.specimen_image_attachment === 3 && imageData.filename.endsWith('mp4')) {
            FB.ui({
                    method: 'share_open_graph',
                    action_type: 'og.shares',
                    action_properties: JSON.stringify({
                        object: {
                            'og:url': location.href,
                            'og:title': document.title,
                            'og:description': $translate.use() === 'et' ? estText : engText,
                            'og:video': getFileLink({filename: imageData.uuid_filename}),
                            'og:video:url': getFileLink({filename: imageData.uuid_filename}),
                            'og:video:secure_url': getFileLink({filename: imageData.uuid_filename})

                        }
                    })
                },
                function (response) {
                    // Action after response
                });
        } else if (imageData.specimen_image_attachment === 3 && (imageData.filename.endsWith('png') || imageData.filename.endsWith('jpeg') || imageData.filename.endsWith('svg') || imageData.filename.endsWith('jpg'))) {
            FB.ui({
                    method: 'share_open_graph',
                    action_type: 'og.shares',
                    action_properties: JSON.stringify({
                        object: {
                            'og:url': location.href,
                            'og:title': document.title,
                            'og:description': $translate.use() === 'et' ? estText : engText,
                            'og:image': getFileLink({filename: imageData.uuid_filename}),
                            'og:image:url': getFileLink({filename: imageData.uuid_filename}),
                            'og:image:secure_url': getFileLink({filename: imageData.uuid_filename})
                        }
                    })
                },
                function (response) {
                    // Action after response
                });
        } else {
            FB.ui({
                    method: 'share_open_graph',
                    action_type: 'og.shares',
                    action_properties: JSON.stringify({
                        object: {
                            'og:url': location.href,
                            'og:title': document.title,
                            'og:description': $translate.use() === 'et' ? estText : engText
                        }
                    })
                },
                function (response) {
                    // Action after response
                });
        }


    };
    // FACEBOOK END

    return service;
};

constructor.$inject = ['utils','configuration','$window', '$location', '$translate', '$stateParams'];

module.service("ApplicationService", constructor);


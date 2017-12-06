var module = angular.module("geoApp");

/**
 * Constructor for service.js
 * @param utils Comes from utils.js
 * @param configuration Comes from config.js
 * @param $window Angular's built in reference to browser's window object
 * @param $location From angular translate library
 * @returns service which contains
 */
var constructor = function (utils, configuration, $window, $location) {

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
    service.getDrillcoreImageUrl = getDrillcoreImageUrl;

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
        console.log("searchType: " + searchType);
        console.log("id: " + id);
        // console.log("callback: " + callback);
        var url = getDetailUrl(searchType);
        console.log("url: " + url);
        utils.httpGet(url + "/" + id, null, callback, error);
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

    service.composeImageUrl = function(imageData, readyUrl) {
        console.log(imageData);
        console.log(readyUrl);
        if(readyUrl) return readyUrl;
        if(imageData.image_url) return imageData.image_url;
        var imageUrl = "http://geokogud.info/" + imageData.database__acronym.toLowerCase() + "/image/";
        return imageUrl + imageData.imageset__imageset_series + "/" + imageData.imageset__imageset_number + "/" + imageData.filename;
    };

    service.composeExternalImagePath = function(imageData) {
        if(imageData.specimen__database__acronym) {
            //console.log(imageData)
            return composeSpecimenExternalPath(imageData);
        }
        if(imageData.database__acronym) {
            return composeImageExternalPath(imageData)
        }
    };
    service.getDownloadLink = function (fileName) {
        return "http://geokogud.info/files/"+fileName.substring(0,2)+"/"+fileName;
    };

    function getDrillcoreImageUrl(params) {
        if (params.database != null) {
            var imageUrl = "https://geokogud.info/" + params.database.toLowerCase() + "/drillcore_image/" + params.id;
            if (params.preview) {
                imageUrl +=  "/preview/";
            } else {
                imageUrl += "/";
            }
            return imageUrl + params.filename;
        }
    }

    function composeSpecimenExternalPath(imageData) {
        //http://geokogud.info/di.php?f=/data/git/images/specimen/663/663-6.jpg&w=400
        var applicationUrl = buildApplicationUrl();
        var imageUrl = applicationUrl
            + "specimenImg/"
            + imageData.specimen__database__acronym.toLowerCase() + "/"
            + imageData.image;
        return imageUrl;
    }

    function composeImageExternalPath(imageData) {
        //http://geokogud.info/di.php?f=/var/www/git/image/OH/OH07-1/OH07-1-4.jpg
        var applicationUrl = buildApplicationUrl();
        var imageUrl = applicationUrl
            + "img/"
            + imageData.database__acronym.toLowerCase() + "/"
            + imageData.imageset__imageset_series + "/"
            + imageData.imageset__imageset_number + "/"
            + imageData.filename;
        return imageUrl;
    }

    function buildApplicationUrl() {
        if(service.location.host().indexOf('localhost') != -1){
            return service.location.protocol() + "://" + service.location.host() + ":" + service.location.port() + "/";
        }else{
            return service.location.protocol() + "://" + service.location.host() + "/";
        }
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
        if (params.doi.includes("DOI")) {
            doi = doi.replace("DOI", "").trim();
        }
        $window.open('http://dx.doi.org/' + doi, '', 'width=600,height=750,scrollbars, resizable');
    }

    function showGoogleMap(lat, lon, localityName) {
        $window.open(
            'http://maps.google.com/?q=' + lat + ',' + lon + ' (' + localityName + ')',
            '',
            'width=800,height=600,scrollbars, resizable'
        );
    }

    function showEstonianLandBoardMap(lat,lon) {
        $window.open(
            'http://geoportaal.maaamet.ee/url/xgis-latlon.php?lat=' + lat + '&lon=' + lon + '&out=xgis&app_id=UU82',
            '',
            'width=800,height=600,scrollbars, resizable'
        );
    }

    function openInNewWindow(params) {
        console.log(params.object + "/" + params.id);
        $window.open('/' + params.object + '/' + params.id, '', 'width=600,height=750,scrollbars, resizable');
    }

    function openUrlInNewWindow(params) {
        $window.open(params.url, '', 'width=600,height=750,scrollbars, resizable');
    }

    function getCountsForAnalysisDetailView(relatedData) {
        var numOfCounts = 0;
        angular.forEach(relatedData, function(result) {
            if (result.name.includes("Counts [grains]")) {
                numOfCounts += result.value;
            }
        });
        return numOfCounts;
    }

    function searchAllSpecimenInLocality(params) {
        $window.open("http://arendus2.geokogud.info/specimen?specimen_number_1=1&specimen_number=&collection_id_1=5&collection_id=&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=5&locality=" + params.locality + "&stratigraphy_1=1&stratigraphy=&id_1=5&id=&fossil_1=1&fossil=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&tags_1=1&tags=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&sort=id&sortdir=DESC");
    }

    return service;
};

constructor.$inject = ['utils','configuration','$window', '$location'];

module.service("ApplicationService", constructor);


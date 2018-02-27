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
    service.getDrillcoreImageUrl = getDrillcoreImageUrl;
    service.composeSpecimenImageUrl = composeSpecimenImageUrl;
    service.getAttachmentFormatFromFilename = getAttachmentFormatFromFilename;
    service.returnInstitutionSlideNumber = returnInstitutionSlideNumber;
    service.searchAllSpecimensUsingCollection = searchAllSpecimensUsingCollection;
    service.setFancyBoxCaption = setFancyBoxCaption;

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
        if (id.includes("10.15152/GEO.")) { // If clause for universal DOI identifier
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

    service.composeImageUrl = function(imageData, readyUrl) {
        if(readyUrl) return readyUrl;
        if(imageData.image_url) return imageData.image_url;
        // For photo archive image
        if (imageData.database__acronym != null) {
            var imageUrl = "http://geokogud.info/" + imageData.database__acronym.toLowerCase()
                + "/image/" + imageData.imageset__imageset_series
                + "/" + imageData.imageset__imageset_number
                + "/" + imageData.filename;
        }
        // For specimen image
        if (imageData.specimen__database__acronym != null) {
            var imageUrl = "http://geokogud.info/" + imageData.specimen__database__acronym.toLowerCase()
                + "/specimen_image/"
                + "/" + imageData.image;
        }
        return imageUrl;
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

    // Used only on corebox detail view
    service.composeCoreboxImageUrl = function (imageData) {
        console.log(imageData);
        if (imageData.database__acronym != null) {
            var imageUrl = "https://geokogud.info/" + imageData.database__acronym.toLowerCase() + "/drillcore_image/" + imageData.drillcore__id + "/";
            return imageUrl + imageData.drillcoreimage__image;
        }
    };

    // // Used only on corebox detail view for different sizes
    // service.composeExternalCoreboxImagePath = function (imageData) {
    //     if (imageData.database__acronym != null) {
    //         var imageUrl = "http://geokogud.info/di.php?f=/var/www/"
    //             + imageData.database__acronym.toLowerCase() + "/"
    //             + "drillcore_image/"
    //             + imageData.drillcore__id + "/"
    //             + imageData.drillcoreimage__image + "&w=";
    //     }
    //     return imageUrl;
    // };

    // Used only on corebox detail view for different sizes
    service.composeExternalCoreboxImagePath = function(imageData) {
        //http://geokogud.info/di.php?f=/var/www/git/drillcore_image/477/477_1.jpg&w=600
        var applicationUrl = buildApplicationUrl();
        if (imageData.database__acronym != null) {
            var imageUrl = applicationUrl
                + "drillcoreImg/"
                + imageData.database__acronym.toLowerCase() + "/"
                + imageData.drillcore__id + "/"
                + imageData.drillcoreimage__image;
        }
        console.log(imageUrl);
        return imageUrl;
    };

    // Used on drillcore detail view to show corebox images which come from related_data
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

    function composeSpecimenImageUrl(params) {
        if (params.imageUrl != null) {
            var imageUrl = params.imageUrl;
        } else {
            if (params.database != null) {
                var imageUrl = "geokogud.info/" + params.database.toLowerCase() + "/specimen_image/" + params.image;
            }
        }
        return imageUrl;
    }

    function composeSpecimenExternalPath(imageData) {
        //http://geokogud.info/di.php?f=/data/git/images/specimen/663/663-6.jpg&w=400
        var applicationUrl = buildApplicationUrl();
        if (imageData.specimen__database__acronym != null) {
            var imageUrl = applicationUrl
                + "specimenImg/"
                + imageData.specimen__database__acronym.toLowerCase() + "/"
                + imageData.image;
        }
        return imageUrl;
    }

    function composeImageExternalPath(imageData) {
        //http://geokogud.info/di.php?f=/var/www/git/image/OH/OH07-1/OH07-1-4.jpg
        var applicationUrl = buildApplicationUrl();
        if (imageData.database__acronym != null) {
            var imageUrl = applicationUrl
                + "img/"
                + imageData.database__acronym.toLowerCase() + "/"
                + imageData.imageset__imageset_series + "/"
                + imageData.imageset__imageset_number + "/"
                + imageData.filename;
        }
        console.log(imageUrl);
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

    function showEstonianLandBoardMap(lat,lon) {
        $window.open(
            'http://geoportaal.maaamet.ee/url/xgis-latlon.php?lat=' + lat + '&lon=' + lon + '&out=xgis&app_id=UU82',
            '',
            'width=800,height=600,scrollbars, resizable'
        );
    }

    function openInNewWindow(params) {
        var width = 600;
        console.log(params.object + "/" + params.id);
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
        $window.open('/' + params.object + '/' + params.id, '', 'width=' + width + ',height=750,scrollbars, resizable');
    }

    function openUrlInNewWindow(params) {
        console.log(params.url);
        $window.open(params.url, '', 'width=600,height=750,scrollbars, resizable');
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
        // $window.open("http://arendus.geokogud.info/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=" + params.locality + "&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
        $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=" + params.locality + "&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
    }

    // Opens new window with new search url for specimens.
    function searchAllSpecimensUsingCollection(params) {
        $window.open($window.location.origin + "/specimen?specimen_number_1=1&specimen_number=&collection_id_1=1&collection_id=" + params.collection + "&classification_1=1&classification=&taxon_1=1&taxon=&name_geology_1=1&name_geology=&country_1=1&country=&locality_1=1&locality=&stratigraphy_1=1&stratigraphy=&id_1=5&id=&depth_since_1=12&depth_since=&depth_to_1=13&depth_to=&agent_1=1&agent=&reference_1=1&reference=&original_type_1=1&original_type=&part_1=1&part=&date_taken_since_1=12&date_taken_since=&date_taken_to_1=13&date_taken_to=&dbs%5B%5D=1&dbs%5B%5D=2&dbs%5B%5D=3&dbs%5B%5D=4&dbs%5B%5D=5&dbs%5B%5D=6&currentTable=specimen&maxSize=5&paginateBy=25&sort=id&sortdir=DESC");
    }

    // Used in specimen detail view when there are attachments like 154421451fsa548a.jpg
    // then it takes the 4 last chars aka format.
    function getAttachmentFormatFromFilename(params) {
        if (params.filename != null) {
            return params.filename.substring(params.filename.length, -4);
        }
    }

    function returnInstitutionSlideNumber(institution) {
        if (institution === "GIT") return 4;
        if (institution === "TUG") return 5;
        if (institution === "ELM") return 6;
    }

    /**
     * Builds caption data for images in fancybox gallery.
     * @param arrayOfPictureInfo Image data (author, date, etc)
     * @returns {string} value of caption data for images. (author, date, etc)
     */
    function setFancyBoxCaption(arrayOfPictureInfo) {
        // [author, date, date_free, number, author_free, id]
        if (arrayOfPictureInfo.length > 0) {
            var estText = "";
            var engText = "";

            if (arrayOfPictureInfo[0] && arrayOfPictureInfo[0] != null) {
                estText += "Pildi autor: " + "<strong>" + arrayOfPictureInfo[0] + "</strong>" + "<br>";
                engText += "Image author: " + "<strong>" + arrayOfPictureInfo[0] + "</strong>" + "<br>";
            }
            if (arrayOfPictureInfo[0] == null && arrayOfPictureInfo[4] && arrayOfPictureInfo[4] != null) {
                estText += "Pildi autor: " + "<strong>" + arrayOfPictureInfo[4] + "</strong>" + "<br>";
                engText += "Image author: " + "<strong>" + arrayOfPictureInfo[4] + "</strong>" + "<br>";
            }
            if (arrayOfPictureInfo[1] && arrayOfPictureInfo[1] != null) {
                estText += "Pildistamise aeg: " + "<strong>" + new Date(arrayOfPictureInfo[1]).toLocaleDateString('et-EE') + "</strong>" + "<br>";
                engText += "Date taken: " + "<strong>" + new Date(arrayOfPictureInfo[1]).toDateString() + "</strong>" + "<br>";
            }
            if (arrayOfPictureInfo[1] == null && arrayOfPictureInfo[2] && arrayOfPictureInfo[2] != null) {
                estText += "Pildistamise aeg: " + "<strong>" +arrayOfPictureInfo[2] + "</strong>" + "<br>";
                engText += "Date taken: " + "<strong>" + arrayOfPictureInfo[2] + "</strong>" + "<br>";
            }
            if (arrayOfPictureInfo[3] && arrayOfPictureInfo[3] != null) {
                estText = "Pildi nr. " + arrayOfPictureInfo[3] + "<br>" + estText;
                engText = "Image no. " + arrayOfPictureInfo[3] + "<br>" + engText;
            }
            if (arrayOfPictureInfo[5] && arrayOfPictureInfo[5] != null) {
                var url = 'http://arendus.geokogud.info/image/';
                if ($stateParams.type === 'specimens') {
                    url = 'http://arendus.geokogud.info/specimen_image/'
                }
                estText += "<strong>" + "<a target='_blank' href='" + url + arrayOfPictureInfo[5] + "'>Mine pildi detail vaatele</a></strong>";
                engText += "<strong>" + "<a target='_blank' href='" + url + arrayOfPictureInfo[5] + "'>Go to picture detail view</a></strong>";
            }
            return $translate.use() === 'et' ? estText : engText;
        }
    }

    return service;
};

constructor.$inject = ['utils','configuration','$window', '$location', '$translate', '$stateParams'];

module.service("ApplicationService", constructor);


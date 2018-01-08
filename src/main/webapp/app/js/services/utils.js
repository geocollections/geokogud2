var module = angular.module('geoApp');

var constructor = function ($http, $stateParams, $location, configuration) {

    var service = {};

    service.isFunction = function (testable) {
        return typeof testable == "function";
    };

    service.httpGet = function (url, data, successCb, errorCb, headers) {
        console.log("GET REQUEST url: " + url);
        console.log("GET REQUEST data: " + data);
        console.log("GET REQUEST headers: " + headers);
        var config = {
            "params": data,
            "headers": headers ? headers : {},
            "method": "GET",
            "url": url
        };
        service.httpRequest(url, config, successCb, errorCb)
    };

    service.httpPost = function (url, data, successCb, errorCb, headers, composeSearchUrl) {
        if(composeSearchUrl) {
            service.composeUrl(data);
            var searchCriteria = service.decodeUrl();
            if(searchCriteria != null) {
                data = searchCriteria;
                // Adds parameters from url to session storage
                // if (typeof(sessionStorage) !== 'undefined') {
                //     sessionStorage.setItem($stateParams.type + "Search", JSON.stringify(searchCriteria));
                // }
                console.log("------------------------------");
                console.log(searchCriteria);
                console.log("------------------------------");
                if (typeof(localStorage) !== 'undefined') {
                    localStorage.setItem($stateParams.type, JSON.stringify(searchCriteria));
                }
            }
        } else {
            service.composeUrl(data);
        }
        var config = {
            "data": data,
            "headers": headers ? headers : {},
            "method": "POST",
            "url": url
            //"params": params ? params : ""
        };
        service.httpRequest(url, config, successCb, errorCb);
    };

    service.composeUrl = function(data) {
        var url = "", currentTable = $location.$$path.split('/')[1];

        if(currentTable == "map") {
            angular.forEach(Object.values(data.filters), function (attr) {
                if (configuration.urlHelper[currentTable]) {
                    var fieldName = configuration.urlHelper[currentTable].fields[attr];
                    if (fieldName) {
                        url += fieldName + "=1&";
                    }
                }
            });
            var fieldName = configuration.urlHelper[currentTable].fields["localityName"];
            if (fieldName) {
                url += fieldName + "=" + data.localityName;
            }
        } else {
            angular.forEach(Object.keys(data), function (attr) {
                if (attr != 'sortField' && attr != 'dbs' && configuration.urlHelper[currentTable]) {
                    var fieldName = configuration.urlHelper[currentTable].fields[attr];
                    if (fieldName) url += fieldName + "_1=" + configuration.urlHelper['lookUpType'][data[attr].lookUpType] + "&" + fieldName + "=" + (data[attr].name ? data[attr].name : "") + "&";
                }
            });
            if (url != "") {
                if(configuration.urlHelper.searchWithOutDBS.indexOf(currentTable) == -1) {
                    if (data.dbs != null) {
                        angular.forEach(data.dbs, function (institution) {
                            if (institution == "GIT") {
                                url += "&dbs[]=1";
                            }
                            if (institution == "TUG") {
                                url += "&dbs[]=2";
                            }
                            if (institution == "ELM") {
                                url += "&dbs[]=3";
                            }
                            if (institution == "TUGO") {
                                url += "&dbs[]=4";
                            }
                            if (institution == "MUMU") {
                                url += "&dbs[]=5";
                            }
                            if (institution == "EGK") {
                                url += "&dbs[]=6";
                            }
                        });
                    }
                }
                url += "&currentTable=" + currentTable.trim();
                if(data.maxSize != null) {
                    url += "&maxSize=" + data.maxSize;
                }
                if(data.searchImages != null) {
                    if(data.searchImages.name) {
                        url += "&search_images=1";
                    }
                }
                if(data.page != null) {
                    url += "&page=" + data.page;
                }
                if(data.sortField.order == "DESCENDING") {
                    url += "&sort="+ data.sortField.sortBy +"&sortdir=DESC";
                } else if(data.sortField.order == "ASCENDING") {
                    url += "&sort="+ data.sortField.sortBy +"&sortdir=ASC";
                }
                angular.forEach(configuration.urlHelper.specialFields, function(specialField) {
                    // BUG FIX: Commented it because year field was not working but now url won't work in geokogud.info
                    // TODO: Maybe try to change year to publication_year in url
                    // if(specialField == "year" && currentTable == "doi") {
                    //     specialField = "publication_year";
                    // }
                    if(data[specialField + "Since"] != null && data[specialField + "To"] != null) {
                        if (data[specialField + "Since"].name != null && data[specialField + "Since"].lookUpType != null && data[specialField + "To"].name != null && data[specialField + "To"].lookUpType != null) {
                            console.log(url);
                            url += "&" + specialField + "_1=" + data[specialField + 'Since'].lookUpType + "+" + data[specialField + 'To'].lookUpType + "&" + specialField + "=" + data[specialField + "Since"].name + "+" + data[specialField + 'To'].name;
                        } else if (data[specialField + "Since"].name != null && data[specialField + "Since"].lookUpType != null) {
                            url += "&" + specialField + "_1=" + data[specialField + 'Since'].lookUpType + "&" + specialField + "=" + data[specialField + "Since"].name;
                        } else if (data[specialField + "To"].name != null && data[specialField + "To"].lookUpType != null) {
                            url += "&" + specialField + "_1=" + data[specialField + 'To'].lookUpType + "&" + specialField + "=" + data[specialField + "To"].name;
                        }
                    } else if (data[specialField + "Since"] != null) {
                        if (data[specialField + "Since"].name != null && data[specialField + "Since"].lookUpType != null) {
                            url += "&" + specialField + "_1=" + data[specialField + 'Since'].lookUpType + "&" + specialField + "=" + data[specialField + "Since"].name;
                        }
                    } else if (data[specialField + "To"] != null) {
                        if (data[specialField + "To"].name != null && data[specialField + "To"].lookUpType != null) {
                            url += "&" + specialField + "_1=" + data[specialField + 'To'].lookUpType + "&" + specialField + "=" + data[specialField + "To"].name;
                        }
                    }
                });
            }
        }
        url == "" ? $location.path($location.$$path).search() : $location.path($location.$$path).search(url);
        $location.replace();
    };

    service.decodeMapUrl = function(){
        if( Object.keys($location.$$search).length === 0) return null;
        var urlParams = $location.$$search, currentTable = $location.$$path.split('/')[1];
        var filterData = {
            filters: [],
            localityName: ""
        };
        angular.forEach(Object.keys(urlParams), function(attr){
            if(attr != 'currentTable' && configuration.urlHelper[currentTable]) {
                angular.forEach(Object.keys(configuration.urlHelper[currentTable].fields), function(currentField) {
                    if(configuration.urlHelper[currentTable].fields[currentField] == attr) {
                        if(attr != "loc") {
                            filterData.filters.push(currentField);
                        } else if (attr == "loc") {
                            filterData.localityName = urlParams[attr];
                        }
                    }
                })
            }
        });
        return filterData;
    };

    service.decodeUrl = function(){
        if( Object.keys($location.$$search).length === 0) return null;
        var urlParams = $location.$$search, currentTable = $location.$$path.split('/')[1], searchParams = {};
        console.log("urlParams" + JSON.stringify(urlParams));
        console.log("currentTable " + currentTable);
        angular.forEach(Object.keys(urlParams), function(attr){
            if(attr != 'currentTable' && attr != 'sortdir' && attr != 'dbs[]' && configuration.urlHelper[currentTable]) {
                angular.forEach(Object.keys(configuration.urlHelper[currentTable].fields), function(a) {
                    if(configuration.urlHelper[currentTable].fields[a] == attr) {
                        var lookUpType = getLookUpType(urlParams[attr+'_1']);
                        urlParams[attr] ? searchParams[a] = {"lookUpType":lookUpType, "name":urlParams[attr]}
                            : searchParams[a] = {"lookUpType":lookUpType};
                    }
                })
            }
        });
        if(urlParams["dbs[]"] != null) {
            searchParams["dbs"] = [];
            angular.forEach(urlParams["dbs[]"], function(institution) {
                if(institution == 1) {
                    searchParams.dbs.push("GIT");
                }
                if(institution == 2) {
                    searchParams.dbs.push("TUG");
                }
                if(institution == 3) {
                    searchParams.dbs.push("ELM");
                }
                if(institution == 4) {
                    searchParams.dbs.push("TUGO");
                }
                if(institution == 5) {
                    searchParams.dbs.push("MUMU");
                }
                if(institution == 6) {
                    searchParams.dbs.push("EGK");
                }
            });
        }
        if(urlParams["sortdir"] != null && urlParams["sort"] != null) {
            if(urlParams["sortdir"] == "DESC") {
                searchParams["sortField"] = {sortBy: urlParams["sort"], order: "DESCENDING"};
            } else if(urlParams["sortdir"] == "ASC") {
                searchParams["sortField"] = {sortBy: urlParams["sort"], order: "ASCENDING"};
            }
        }
        if(urlParams["maxSize"] != null) {
            searchParams["maxSize"] = Number(urlParams["maxSize"]);
        }
        if(urlParams["search_images"] != null) {
            if(Number(urlParams["search_images"]) == 1) {
                searchParams["searchImages"] = {lookUpType: "exact", name: true};
            }
        }
        if(urlParams["page"] != null) {
            searchParams["page"] = Number(urlParams["page"]);
        }

        angular.forEach(configuration.urlHelper.specialFields, function(specialField) {
            if(urlParams[specialField + "_1"] != null && urlParams[specialField] != null) {

                console.log(urlParams);
                console.log(urlParams[specialField]);
                console.log(urlParams[specialField + "_1"]);

                var specialFieldName = urlParams[specialField].split(" ");
                var specialFieldLookUpType = urlParams[specialField + "_1"].split(" ");
                if(specialField == "publication_year") {
                    specialField = "year";
                }
                // console.log(specialFieldName[0]);
                // console.log(specialFieldLookUpType[0]);
                // console.log(specialFieldName[1]);
                // console.log(specialFieldLookUpType[1]);
                // console.log(specialField);

                // Double check because if only 'lte' value is entered its index is also 0.
                if(specialFieldName[0] != null) {
                    if (specialFieldLookUpType[0] === "gte") {
                        if (specialField === "dateTaken") {
                            searchParams[specialField + "Since"] = {
                                lookUpType: specialFieldLookUpType[0],
                                name: specialFieldName[0]
                            }
                        } else {
                            searchParams[specialField + "Since"] = {
                                lookUpType: specialFieldLookUpType[0],
                                name: Number(specialFieldName[0])
                            }
                        }
                    } else if (specialFieldLookUpType[0] === "lte") {
                        if (specialField === "dateTaken") {
                            searchParams[specialField + "To"] = {
                                lookUpType: specialFieldLookUpType[0],
                                name: specialFieldName[0]
                            }
                        } else {
                            searchParams[specialField + "To"] = {
                                lookUpType: specialFieldLookUpType[0],
                                name: Number(specialFieldName[0])
                            }
                        }
                    }
                }

                if(specialFieldName[1] != null) {
                    if (specialField === "dateTaken") {
                        searchParams[specialField + "To"] = {
                            lookUpType: specialFieldLookUpType[1],
                            name: specialFieldName[1]
                        }
                    } else {
                        searchParams[specialField + "To"] = {
                            lookUpType: specialFieldLookUpType[1],
                            name: Number(specialFieldName[1])
                        }
                    }
                }
            }
        });
        return searchParams;
    };
    function getLookUpType(attr){
        var found = false, lookUpType = "";
        angular.forEach(Object.keys(configuration.urlHelper['lookUpType']), function(a) {
            if(!found && configuration.urlHelper['lookUpType'][a] == attr) {
                lookUpType = a;
                found = true;
            }
        });
        return lookUpType;
    }

    service.preRequestCallbacks = [];

    service.httpRequest = function (url, config, successCb, errorCb) {

        for (var i in service.preRequestCallbacks) {
            if (service.isFunction(service.preRequestCallbacks[i])) {
                service.preRequestCallbacks[i]();
            }
        }

        $http(config).then(successCallback, errorCallback);

        function successCallback(response, status, headers, config) {
            service.isFunction(successCb) && successCb(response, status, headers, config);
        }

        function errorCallback(err, status, headers, config) {
            service.isFunction(errorCb) && errorCb(err, status, headers, config);
        }

    };

    service.inArray = function (elem, arr) {
        return arr.indexOf(elem) >= 0;
    };

    service.toggleInArray = function (elem, arr) {
        var inArr = service.inArray(elem, arr);
        if (inArr) {
            arr.splice(arr.indexOf(elem), 1);
        }
        else {
            arr.push(elem);
        }
    };

    return service;
};

constructor.$inject = ['$http', '$stateParams' ,'$location', 'configuration'];

module.service('utils', constructor);

module.filter('split', function() {
    return function(input, splitChar, splitIndex) {
        return !input ? input : input.split(splitChar)[splitIndex];
    }
});

module.factory("SearchFactory", ['$http', 'configuration', function($http, configuration) {
    return {
        autocompleteSearch: function (table, term, sortField, searchField) {
            // console.log(table);
            // console.log(term);
            // console.log(sortField);
            // console.log(searchField);
            return $http.get(configuration.autocompleteUrl, {params:{table: table, term: term, sortField: sortField, searchField: searchField}})
                .then(function (response) {
                    return response.data.results;
                });
        }
    };
}]).factory("WebPagesFactory", ['$http', 'configuration', function($http, configuration) {

    var getData = function(id) {
        return $http({
            method:"GET",
            url: configuration.webPagesUrl + "/" + id
        }).then(function(result){
            return result.data.results[0];
        });
    };
    return { getData: getData };
}]).factory("GlobalSearchFactory", ['$http', function($http){
    return {
        searchGlobally: function(query, successfulCallback) {
            return $http.get('/search/global/' + query).then(successfulCallback);
        }
    };
}]);
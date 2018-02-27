angular.module('geoApp')
    .directive('loading', function () {
    return {
        restrict: 'E',
        replace: true,
        template: '<div class="loading"><img src="img/loader.gif" width="20" height="20" />Laaditakse...</div>',
        link: function (scope, element, attr) {
            scope.$watch('loading', function (val) {
                if (val)
                    $(element).show();
                else
                    $(element).hide();
            });
        }
    }
}).directive('spinnerLoad', [function spinnerLoad() {
    return {
        restrict: 'A',
        link: function spinnerLoadLink(scope, elem, attrs) {
            scope.$watch('ngSrc', function watchNgSrc() {
                elem.hide();
                elem.after('<i class="fa fa-spinner fa-lg fa-spin"></i>');  // add spinner
            });
            elem.on('load', function onLoad() {
                elem.show();
                elem.next('i.fa-spinner').remove(); // remove spinner
            });
        }
    };
}]).directive('nonIsolatedInclude', function() {
    return {
        restrict: 'AE',
        templateUrl: function(ele, attrs) {
            return attrs.templatePath;
        }
    };
}).directive('ngClick', function ($timeout) {

    /**
     * Overriding angular ng-click due to double click issue:
     * https://github.com/angular/angular.js/issues/9826
     */

    var delay = 500;

    return {
        restrict: 'A',
        priority: -1,
        link: function (scope, elem) {
            var disabled = false;

            function onClick(evt) {
                if (disabled) {
                    evt.preventDefault();
                    evt.stopImmediatePropagation();
                } else {
                    disabled = true;
                    $timeout(function () {
                        disabled = false;
                    }, delay, false);
                }
            }

            scope.$on('$destroy', function () {
                elem.off('click', onClick);
            });
            elem.on('click', onClick);
        }
    }

}).directive('focusMe', function ($timeout) {
    return {
        scope: {trigger: '@focusMe'},
        link: function (scope, element) {
            scope.$watch('trigger', function (value) {
                if (value === "true") {
                    $timeout(function () {
                        element[0].focus();
                    });
                }
            });
        }
    };
}).directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value, 10);
            });
        }
    };
}).directive('clickOutside', ['$document', function ($document) {
    return {
        restrict: 'A',
        scope: {
            clickOutside: '&',
            isActive: '='
        },
        link: function (scope, el, attr) {
            function eventHandler(e) {
                if (!scope.isActive) {
                    return;
                }
                if (el !== e.target && !el[0].contains(e.target)) {
                    scope.$apply(function () {
                        scope.$eval(scope.clickOutside);
                    });
                }
            }

            $document.on('click', eventHandler);

            scope.$on('$destroy', function () {
                $document.off('click', eventHandler);
            });
        }
    }
}]).directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
                event.preventDefault();
            }
        });
    };
}).directive('ngArrowLeft', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 37) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngArrowLeft);
                });
                event.preventDefault();
            }
        });
    };
}).directive('ngArrowRight', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 39) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngArrowRight);
                });
                event.preventDefault();
            }
        });
    };
}).directive('showPreview', function () {
    return {
        template: '<img data-toggle="tooltip" title="{{title}}" data-html="true" class="{{classes}}" ng-src="{{previewImageUrl}}" spinner-load />',
        restrict: 'E',
        scope: {
            imgUrl: '=',
            imgTitle: '=',
            titleLang: '=',
            classes: '@'
        },
        controller: ['$scope','$translate', '$rootScope', function ($scope, $translate, $rootScope) {
            $scope.$watch('imgTitle', function (newVal) {
                // [author, date, date_free, number, author_free]
                if (newVal.length > 0) {
                    var estText = "";
                    var engText = "";

                    if (newVal[0] && newVal[0] != null) {
                        estText += "Pildi autor: " + newVal[0] + "<br>";
                        engText += "Image author: " + newVal[0] + "<br>";
                    }
                    if (newVal[0] == null && newVal[4] && newVal[4] != null) {
                        estText += "Pildi autor: " + newVal[4] + "<br>";
                        engText += "Image author: " + newVal[4] + "<br>";
                    }
                    if (newVal[1] && newVal[1] != null) {
                        estText += "Pildistamise aeg: " + new Date(newVal[1]).toLocaleDateString('et-EE');
                        engText += "Date taken: " + new Date(newVal[1]).toDateString();
                    }
                    if (newVal[1] == null && newVal[2] && newVal[2] != null) {
                        estText += "Pildistamise aeg: " +newVal[2];
                        engText += "Date taken: " + newVal[2];
                    }
                    if (newVal[3] && newVal[3] != null) {
                        estText = "Pildi nr. " + newVal[3] + "<br>" + estText;
                        engText = "Image no. " + newVal[3] + "<br>" + engText;
                    }

                    $scope.title = $translate.use() === 'et' ? estText : engText;

                    $rootScope.$on('$translateChangeSuccess', function () {
                        $scope.title = $translate.use() === 'et' ? estText : engText;
                    });
                }
            });

            $scope.$watch('imgUrl', function(newValue) {
                if(newValue) {
                    if (newValue.includes("/preview/")) {
                        $scope.previewImageUrl = newValue;
                    } else {
                        var foundHttp = newValue.match(/http:/);
                        var lastSlashPosition = newValue.lastIndexOf('/');
                        $scope.previewImageUrl = (foundHttp ? "" : "http://") + newValue.substring(0, lastSlashPosition) + '/preview' + newValue.substring(lastSlashPosition);
                    }
                }
            }, true);
        }]
    };
}).directive('selecttemporary', function ($translate) {
    return {
        template:
        '<select class="{{selectCss}}" ng-model="field">' +
        '<option value="icontains" selected="selected">{{ \'SEARCH.SELECT.CONTAINS\' | translate }}</option>' +
        '<option value="iexact">{{ \'SEARCH.SELECT.EQUALS\' | translate }}</option>' +
        /*         '<option value="doesnotexact">{{ \'SEARCH.SELECT.DOESNOTEQUAL\' | translate }}</option>' +*/
        '<option value="gt">{{ \'SEARCH.SELECT.GREATERTHAN\' | translate }}</option>' +
        '<option value="lt">{{ \'SEARCH.SELECT.SMALLERTHAN\' | translate }}</option>' +
        '<option value="in">{{ \'SEARCH.SELECT.ISINLIST\' | translate }}</option>' +
        '<option value="range">{{ \'SEARCH.SELECT.ISBWETWEEN\' | translate }}</option>' +
        '</select>',
        restrict: 'E',
        scope: {
            field: '=?ngModel',
            customCss: '@'
        },
        link: function (scope) {
            scope.selectCss = angular.isUndefined(scope.customCss) ? 'col-md-4 form-control' : scope.customCss;
            scope.idOptions = [
                {name: 'contains', value: 'icontains'},
                {name: 'equals', value: 'iexact'},
                /*        {name: 'does not equal', value: 'doesnotexact'},*/
                {name: 'greater than', value: 'gt'},
                {name: 'smaller than', value: 'lt'},
                {name: 'is in list', value: 'in'},
                {name: 'is between', value: 'range'}
            ];

            scope.$watch('field', function (data) {
                if (data) return;
                scope.field = scope.idOptions[0].value;
            });
        }
    };
}).directive('selectexact', function ($translate) {
        return {
            template:
                '<select class="{{selectCss}}" ng-model="field">' +
                    '<option value="iexact" selected="selected">{{ \'SEARCH.SELECT.EQUALS\' | translate }}</option>' +
           /*         '<option value="doesnotexact">{{ \'SEARCH.SELECT.DOESNOTEQUAL\' | translate }}</option>' +*/
                    '<option value="gt">{{ \'SEARCH.SELECT.GREATERTHAN\' | translate }}</option>' +
                    '<option value="lt">{{ \'SEARCH.SELECT.SMALLERTHAN\' | translate }}</option>' +
                    '<option value="in">{{ \'SEARCH.SELECT.ISINLIST\' | translate }}</option>' +
                    '<option value="range">{{ \'SEARCH.SELECT.ISBWETWEEN\' | translate }}</option>' +
                '</select>',
            restrict: 'E',
            scope: {
                field: '=?ngModel',
                customCss: '@'
            },
            link: function (scope) {
                scope.selectCss = angular.isUndefined(scope.customCss) ? 'col-md-4 form-control' : scope.customCss;
                scope.idOptions = [
                    {name: 'equals', value: 'iexact'},
            /*        {name: 'does not equal', value: 'doesnotexact'},*/
                    {name: 'greater than', value: 'gt'},
                    {name: 'smaller than', value: 'lt'},
                    {name: 'is in list', value: 'in'},
                    {name: 'is between', value: 'range'}
                ];

                scope.$watch('field', function (data) {
                    if (data) return;
                    scope.field = scope.idOptions[0].value;
                });
            }
        };
    }).directive('selectdefault', function () {
    return {
        template:
            '<select class="{{selectCss}}" ng-model="field">' +
                '<option value="icontains" selected="selected">{{ \'SEARCH.SELECT.CONTAINS\' | translate }}</option>' +
                '<option value="iexact">{{ \'SEARCH.SELECT.EQUALS\' | translate }}</option>' +
                '<option value="istartswith">{{ \'SEARCH.SELECT.STARTSWITH\' | translate }}</option>' +
                '<option value="iendswith">{{ \'SEARCH.SELECT.ENDSWITH\' | translate }}</option>' +
             /*   '<option value="doesnotcontain">{{ \'SEARCH.SELECT.DOESNOTCONTAIN\' | translate }}</option>' +*/
                '<option value="in">{{ \'SEARCH.SELECT.ISINLIST\' | translate }}</option>' +
            '</select>',
        restrict: 'E',
        scope: {
            field: '=?ngModel',
            customCss: '@'
        },
        link: function (scope, $watch) {
            scope.selectCss = angular.isUndefined(scope.customCss) ? 'col-md-4 form-control' : scope.customCss;
            scope.defaultOptions = [
                {name: 'contains', value: 'icontains'},
                {name: 'equals', value: 'iexact'},
                {name: 'start with', value: 'istartswith'},
                {name: 'ends with', value: 'iendswith'},
             /*   {name: 'does not contain', value: 'doesnotcontain'},*/
                {name: 'is in list', value: 'in'}
            ];

            scope.$watch('field', function (data) {
                if (data) return;
                scope.field = scope.defaultOptions[0].value;
            });
        }
    };
}).directive('selecthierarchy', function () {
    return {
        template:
            '<select class="{{selectCss}}" ng-model="field">' +
                '<option value="icontains" selected="selected">{{ \'SEARCH.SELECT.CONTAINS\' | translate }}</option>' +
                '<option value="iexact">{{ \'SEARCH.SELECT.EQUALS\' | translate }}</option>' +
                '<option value="istartswith">{{ \'SEARCH.SELECT.STARTSWITH\' | translate }}</option>' +
                '<option value="iendswith">{{ \'SEARCH.SELECT.ENDSWITH\' | translate }}</option>' +
                /*'<option value="doesnotcontain">{{ \'SEARCH.SELECT.DOESNOTCONTAIN\' | translate }}</option>' +*/
                '<option value="in">{{ \'SEARCH.SELECT.ISINLIST\' | translate }}</option>' +
                '<option value="hierarchy">{{ \'SEARCH.SELECT.HIERARCHY\' | translate }}</option>' +
            '</select>',
        scope: {
            field: '=?ngModel',
            customCss: '@'
        },
        link: function (scope) {
            scope.selectCss = angular.isUndefined(scope.customCss) ? 'col-md-4 form-control' : scope.customCss;
            scope.defaultOptions = [
                {name: 'contains', value: 'icontains'},
                {name: 'equals', value: 'iexact'},
                {name: 'start with', value: 'istartswith'},
                {name: 'ends with', value: 'iendsswith'},
         /*       {name: 'does not contain', value: 'doesnotcontain'},*/
                {name: 'is in list', value: 'in'},
                {name: 'hierarchy', value: 'hierarchy'}
            ];

            scope.$watch('field', function (data) {
                if (data) return;
                scope.field = scope.defaultOptions[0].value;
            });
        }
    };
}).directive('makeBooleanReadable', ['$translate', '$rootScope', function($translate, $rootScope){
    return {
        template: '{{readableBoolean}}',
        restrict: 'E',
        scope: {
            booleanValue:'='
        },
        link: function(scope) {
            scope.$watch('booleanValue', function(newValue) {
                scope.readableBoolean = localizeBoolean(newValue);
            }, true);
            $rootScope.$on('$translateChangeSuccess', function() {
                scope.readableBoolean = localizeBoolean(scope.booleanValue);
            });
            function localizeBoolean(bool) {
                if($translate.use() == 'et') {
                    return bool ? 'Jah' : 'Ei';
                }
                return bool ? 'Yes' : 'No';
            }
        }
    };
}]).directive('sectionOpenedIcon', function () {
    return {
        template: '<i class="pull-right glyphicon" ng-class="{\'glyphicon-chevron-down\': !isOpened, \'glyphicon-chevron-right\': isOpened}"></i>',
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            isOpened: '='
        }
    };
}).directive('showRowIfPresent', function () {
    return {
        template: '<tr ng-show=\"value\"><td>{{name}}</td><td>{{value}}</td></tr>',
        restrict: 'E',
        scope: {
            name: '@',
            value: '='
        }
    };
}).directive('localize', ['$translate', '$rootScope', function ($translate, $rootScope) {
    return {
        template: '{{localizedValue}}',
        restrict: 'E',
        scope: {
            et: '=',
            en: '='
        },
        // TODO: If one language exist and other doesnt then changing language should always show the one which exists?
        link: function (scope) {
            scope.$watch('[et, en]', function(newValue) {
                scope.localizedValue = $translate.use() == 'et'
                    ? (newValue[0] == null ? newValue[1] : newValue[0])
                    : (newValue[1] == null ? newValue[0] : newValue[1]);
            }, true);
            $rootScope.$on('$translateChangeSuccess', function() {
                scope.localizedValue = $translate.use() == 'et'
                    ? (scope.et == null ? scope.en : scope.et)
                    : (scope.en == null ? scope.et : scope.en);
            });
        }
    };
}]).directive('localizeLabel', ['$translate', '$rootScope', function ($translate, $rootScope) {
    return {
        template: '{{localizedValue | translate}}',
        restrict: 'E',
        scope: {
            et: '=',
            en: '='
        },
        link: function (scope) {
            //console.log(scope.et)
            scope.$watch('[et, en]', function(newValue) {
                scope.localizedValue = $translate.use() == 'et'
                    ? (newValue[0] == null ? newValue[1] : newValue[0])
                    : (newValue[1] == null ? newValue[0] : newValue[1]);
            }, true);
            $rootScope.$on('$translateChangeSuccess', function() {
                scope.localizedValue = $translate.use() == 'et'
                    ? (scope.et == null ? scope.en : scope.et)
                    : (scope.en == null ? scope.et : scope.en);
            });
        }
    };
}]).directive('firstNotNull',function(){
    return {
        template: '{{value}}',
        restrict: 'E',
        scope: {
            values: '='
        },
        //also accepts inner arrays:
        // [entity.id, [entity.name, entity.number]] - if id is null, will show non-null elements from the inner array
        link: function (scope) {
            values = scope.values;
            if (values) {
                for (var i = 0; i < values.length; i++) {
                    if (values[i]) {
                        if (values[i] instanceof Array) {
                            result = "";
                            for (var j = 0; j < values[i].length; j++) {
                                if (values[i][j]) {
                                    result += values[i][j] + " ";
                                }
                            }
                            scope.value = result;
                        } else {
                            scope.value = values[i];
                        }
                        break;
                    }
                }
            }
        }
    };
}).directive('localityMap', function () {
        return {
            scope: {
                x: '=',
                y: '=',
                name: '=',
                fid: '='
            },
            restrict: 'AE',
            replace: true,
            template: '<div id="map" style="height: 300px; width:99%; max-width: 1980px; max-height: 500px; padding: 0px; border: solid 1px #999; margin: 0;"></div>',
            controller: ['$scope', function ($scope) {
                var watcher = $scope.$watch('x', function () {
                    if ($scope.x === undefined) return;
                    // at this point it is defined, map can be initialized
                    init();
                    // delete watcher if appropriate
                    watcher();
                });

                var olMap;

                function init() {
                    olMap = new ol.Map({
                        target: "map",
                        layers: [
                            /*
                             new ol.layer.Tile({
                             //source: new ol.source.Stamen({
                             //layer: 'toner',
                             //})
                             source: new ol.source.OSM()
                             }),*/

                            new ol.layer.Tile({
                                source: new ol.source.XYZ({
                                    url: 'https://api.tiles.mapbox.com/v4/mapbox.light/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoia3V1dG9iaW5lIiwiYSI6ImNpZWlxdXAzcjAwM2Nzd204enJvN2NieXYifQ.tp6-mmPsr95hfIWu3ASz2w'
                                })
                            })
                        ],
                        controls: ol.control.defaults({
                            attributionOptions: ({
                                collapsible: true
                            })
                        }).extend([
                            new ol.control.ScaleLine({units: "metric"}),
                            new ol.control.FullScreen()
                        ]),
                        // interactions: ol.interaction.defaults({mouseWheelZoom: true}), // changed to true 29.11.2017
                        interactions: ol.interaction.defaults().extend([
                            new ol.interaction.DragRotateAndZoom()
                        ]),
                        view: new ol.View({
                            projection: "EPSG:3857",
                            center: ol.proj.transform([$scope.y, $scope.x], 'EPSG:4326', 'EPSG:3857'),
                            zoom: 6,
                            maxZoom: 16,
                            minZoom: 4,
                            restrictedExtent: ol.proj.transformExtent([-10, 52, 2, 62], 'EPSG:4326', 'EPSG:3857')
                        })
                    });


                    function defaultStyle(feature, resolution) {
                        return [
                            new ol.style.Style({
                                image: new ol.style.Circle({
                                    radius: feature.radius,
                                    fill: new ol.style.Fill({color: feature.fillColor, opacity: 0.8}),
                                    stroke: new ol.style.Stroke({color: feature.outlineColor, width: 1})
                                }),
                                text: (resolution > 5000 ? null : new ol.style.Text({
                                        font: feature.fontSize + 'pt Arial, Helvetica, Helvetica Neue, Arial, sans-serif',
                                        text: feature.label,
                                        fill: new ol.style.Fill({color: feature.textColor}),
                                        stroke: new ol.style.Stroke({color: feature.textoutlineColor, width: 3}),
                                        textAlign: 'left',
                                        textBaseline: 'bottom',
                                        offsetX: 5,
                                        offsetY: -5,
                                    }))
                            })
                        ]
                    };
                    var vectorSource = new ol.source.Vector({
                        //attributions: [new ol.Attribution({
                        //	html: "Data from PA/Credit Suisse."})]
                    });


                    var centroidLL = ol.proj.transform([$scope.y, $scope.x], 'EPSG:4326', 'EPSG:3857');
                    var centroidPoint = new ol.geom.Point(centroidLL);
                    var feature = new ol.Feature({geometry: centroidPoint});
                    feature.name = $scope.name;
                    feature.fid = $scope.fid;
                    feature.numsamples = '0';
                    feature.dec_time_str = '';
                    feature.dec_time_num = 0.5;
                    feature.elect_pc = 0.5;
                    feature.yes_rating = 10;
                    feature.radius = 7;
                    feature.label = $scope.name;

                    feature.fontSize = parseInt(feature.radius * 0.7);
                    if (feature.fontSize < 8) {
                        feature.fontSize = 10;
                    }

                    //var r = 1-(feature.yes_rating/10.0);
                    //var g = 0;
                    //var b = 1-r;
                    feature.fillColor = 'rgba(238,59,13,0.8)';
                    feature.outlineColor = 'rgba(255,255,255,0.9)';
                    feature.textColor = 'rgba(238,59,13,1)';
                    feature.textoutlineColor = '#fff';
                    vectorSource.addFeature(feature);

                    var layerData = new ol.layer.Vector({
                        title: "Localities",
                        source: vectorSource,
                        style: function (feature, resolution) {
                            return defaultStyle(feature, resolution);
                        }
                    })

                    olMap.addLayer(layerData);

                    olMap.getViewport().addEventListener('mousemove', function (evt) {
                        var pixel = olMap.getEventPixel(evt);
                        displayFeatureInfo(pixel);
                    });

                    olMap.on('click', function (evt) {
                        displayFeatureInfo(evt.pixel);
                    }); //Useful for touch-based viewing, e.g. on iPhone.
                    olMap.on('click', function (evt) {
                        openLoc(evt.pixel);
                    });
                }


                var openLoc = function (pixel) {

                    var feature = olMap.forEachFeatureAtPixel(pixel, function (feature, layer) {
                        return feature;
                    });

                    if (feature) {
                        //window.location = '/locality/' + feature.fid;
                        window.open('/locality/' + feature.fid, '', 'width=600,height=750,scrollbars, resizable');
                    }
                    else {
                        document.getElementById('hoverbox').style.display = 'none';
                    }
                };
                var displayFeatureInfo = function (pixel) {

                    var feature = olMap.forEachFeatureAtPixel(pixel, function (feature, layer) {
                        return feature;
                    });
                    //console.log(feature);
                    if (feature) {
                        //
                        document.getElementById('hoverbox').style.display = 'block';
                        //console.log(feature.name);
                        document.getElementById('hoversystem').innerHTML = feature.name;
                        document.getElementById('hoverstat').innerHTML = "";
                    } else {
                        document.getElementById('hoverbox').style.display = 'none';
                    }
                };
            }]
        }
}).directive('localitiesMap',
    function () {
        return {
            scope: {
                localities: '='
            },
            restrict: 'AE',
            replace: true,
            template: '<div style="max-height: 500px" id="map"></div>',
            controller: ['$scope', function ($scope) {
                var watcher = $scope.$watch('localities', function () {
                    if ($scope.localities === undefined) return;

                    console.log("*****DIRECTIVE*****");
                    console.log($scope.localities);
                    console.log("*****DIRECTIVE*****");
                    // at this point it is defined, map can be initialized
                    init();
                    // delete watcher if appropriate
                    watcher();
                });
                //======================================================================
                $scope.olMap;

                var bedrockAge = new ol.layer.Tile({
                    title: 'Bedrock age',
                    visible: false,
                    /*extent: [-13884991, 2870341, -7455066, 6338219],*/
                    source: new ol.source.TileWMS({
                        url: 'http://gis.geokogud.info/geoserver/wms',
                        params: {'LAYERS': 'IGME5000:EuroGeology', 'TILED': true},
                        serverType: 'geoserver',
                        // Countries have transparency, so do not fade tiles:
                        transition: 0
                    })
                });

                var ermas = new ol.layer.Tile({
                    visible: false,
                    title: 'Show all locations',
                    source: new ol.source.TileWMS({
                        url: 'http://gis.geokogud.info:80/geoserver/sarv/wms',
                        params: {
                            'VERSION': '1.1.1',
                            tiled: true,
                            STYLES: '',
                            LAYERS: 'sarv:locality_summary',
                            tilesOrigin: -180 + "," + -90
                        }
                    })
                });

                var overlays = new ol.layer.Group({
                    title: 'Overlays',
                    layers: [
                        bedrockAge,
                        ermas
                    ]
                });

                var mapbox = new ol.layer.Tile({
                    title: 'MapBox grayscale',
                    type: 'base',
                    visible: true,
                    source: new ol.source.XYZ({
                        url: 'https://api.tiles.mapbox.com/v4/mapbox.light/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoia3V1dG9iaW5lIiwiYSI6ImNpZWlxdXAzcjAwM2Nzd204enJvN2NieXYifQ.tp6-mmPsr95hfIWu3ASz2w'
                    })
                });

                var basemaps = new ol.layer.Group({
                    'title': 'Base maps',
                    layers: [
                        new ol.layer.Tile({
                            title: 'Stamen dark',
                            type: 'base',
                            visible: false,
                            source: new ol.source.Stamen({
                                layer: 'toner'
                            })
                        }),
                        new ol.layer.Tile({
                            title: 'Stamen terrain',
                            type: 'base',
                            group: 'group-name',
                            visible: false,
                            source: new ol.source.Stamen({
                                layer: 'terrain'
                            })
                        }),
                        new ol.layer.Tile({
                            title: 'OpenStreetMap',
                            type: 'base',
                            visible: false,
                            source: new ol.source.OSM()
                        }),
                        mapbox
                    ]
                });




                function init() {
                    if (!$scope.olMap) {
                        $scope.olMap = new ol.Map({
                            target: "map",
                            layers: [basemaps, overlays],
                            controls: ol.control.defaults({
                                attributionOptions: ({
                                    collapsible: true
                                })
                            }).extend([
                                new ol.control.ScaleLine({units: "metric"}),
                                new ol.control.FullScreen()
                            ]),
                            interactions: ol.interaction.defaults().extend([
                                new ol.interaction.DragRotateAndZoom()
                            ]),
                            view: new ol.View({
                                projection: "EPSG:3857",
                                center: ol.proj.transform([25.0, 58.4], 'EPSG:4326', 'EPSG:3857'),
                                zoom: 6,
                                maxZoom: 16,
                                minZoom: 1,
                                restrictedExtent: ol.proj.transformExtent([-10, 52, 2, 62], 'EPSG:4326', 'EPSG:3857')
                            })
                        });
                    }

                    function defaultStyle(feature, resolution) {
                        return [
                            new ol.style.Style({
                                image: new ol.style.Circle({
                                    radius: 7,
                                    fill: new ol.style.Fill({color: 'rgba(236, 102, 37,0.7)', opacity: 0.8}),
                                    //stroke: new ol.style.Stroke({color: 'rgba(255,255,255,0)', width: 0})
                                }),
                                text: (resolution > 500 ? null : new ol.style.Text({
                                        font: '10pt Arial, Helvetica, Helvetica Neue, Arial, sans-serif',
                                        text: feature.name,
                                        fill: new ol.style.Fill({color: 'rgba(238,59,13,1)'}),
                                        stroke: new ol.style.Stroke({color: '#fff', width: 3}),
                                        textAlign: 'left',
                                        textBaseline: 'bottom',
                                        offsetX: 5,
                                        offsetY: -5,
                                    }))
                            })
                        ]
                    };

                    if (!$scope.vectorSource) {
                        $scope.vectorSource = new ol.source.Vector({
                            attributions: [new ol.Attribution({
                                html: "Data from PA/Credit Suisse."
                            })]
                        });
                    } else {
                        $scope.layerData.getSource().clear();
                    }

                    angular.forEach($scope.localities, function (locality) {
                        var centroidLL = ol.proj.transform([Number(locality.longitude), Number(locality.latitude)], 'EPSG:4326', 'EPSG:3857');
                        var centroidPoint = new ol.geom.Point(centroidLL);
                        var feature = new ol.Feature({geometry: centroidPoint});

                        if (locality.localityEng == null) {
                            feature.name = locality.place;
                        } else {
                            feature.name = locality.localityEng;
                        }

                        feature.fid = locality.fid;
                        $scope.vectorSource.addFeature(feature);
                    });

                    // $scope.layerDataGroup = new ol.layer.Group({
                    //     title: 'Current localities',
                    //     layers: [
                    //         $scope.layerData
                    //     ]
                    // });

                    $scope.layerData = new ol.layer.Vector({
                        title: "Localities",
                        source: $scope.vectorSource,
                        style: function (feature, resolution) {
                            return defaultStyle(feature, resolution);
                        }
                    });

                    $scope.olMap.addLayer($scope.layerData);
                    $scope.olMap.getViewport().addEventListener('mousemove', function (evt) {
                        var pixel = $scope.olMap.getEventPixel(evt);
                        displayFeatureInfo(pixel);
                    });

                    var extent = $scope.layerData.getSource().getExtent();
                    $scope.olMap.getView().fit(extent, $scope.olMap.getSize());

                    var zz = $scope.olMap.getView().getZoom();
                    if (zz > 9) {
                        $scope.olMap.getView().setZoom(9);
                    }

                    $scope.olMap.on('click', function (evt) {
                        displayFeatureInfo(evt.pixel);
                    }); //Useful for touch-based viewing, e.g. on iPhone.


                    // Can't click if ID is missing
                    if ($scope.vectorSource.getFeatures()[0].fid !== null) {
                        $scope.olMap.on('click', function (evt) {
                            openLoc(evt.pixel);
                        });
                    }

                    var layerSwitcher = new ol.control.LayerSwitcher({
                        // tipLabel: 'Légende' // Optional label for button
                    });
                    $scope.olMap.addControl(layerSwitcher);

                }



                /*
                 var featureOverlay = new ol.FeatureOverlay({
                 map: olMap,
                 style: function(feature, resolution) { return defaultStyle(feature, resolution); }
                 });
                 */
                var openLoc = function (pixel) {

                    var feature = $scope.olMap.forEachFeatureAtPixel(pixel, function (feature, layer) {
                        return feature;
                    });

                    if (feature) {
                        //window.location = '/locality/' + feature.fid;
                        window.open('/locality/' + feature.fid, '', 'width=600,height=750,scrollbars, resizable');
                    }
                    else {
                        document.getElementById('hoverbox').style.display = 'none';
                    }
                };

                var displayFeatureInfo = function (pixel) {

                    var feature = $scope.olMap.forEachFeatureAtPixel(pixel, function (feature, layer) {
                        return feature;
                    });

                    if (feature) {
                        //
                        document.getElementById('hoverbox').style.display = 'block';
                        //console.log(feature.name);
                        document.getElementById('hoversystem').innerHTML = feature.name;
                        document.getElementById('hoverstat').innerHTML = "";
                    } else {
                        document.getElementById('hoverbox').style.display = 'none';
                    }
                };
            }]
        }
    });
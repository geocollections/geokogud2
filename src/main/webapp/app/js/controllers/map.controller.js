var module = angular.module("geoApp");

var constructor = function ($scope, $state, configuration, ApplicationService) {

    var vm = this;
    vm.service = ApplicationService;

    var filterData = ApplicationService.getMapParamsFromUrl();

    if (filterData != null) {
        ApplicationService.loadMapDataOnFilterChange(filterData, onMapDataFilter);
        angular.forEach(filterData.filters, function(filter){
            angular.element("input[name=" + filter + "]").prop('checked', true);
        });
    } else {
        ApplicationService.loadMapData(onMapData);
    }

    function onMapData(response) {
        console.log(response);

        var locs = response.data;
        if (locs != null) {
            vm.countLocalities = response.data.numFound;

            /*********************************************
             *** CODE FROM geokogud.info map.php START ***
             *********************************************/

            var earthquakeFill = new ol.style.Fill({
                color: 'rgba(236, 102, 37,0.7)' //255, 153, 0,0.6
            });
            var earthquakeStroke = new ol.style.Stroke({
                color: 'rgba(0, 0, 0,0.5)', //'rgba(236, 102, 37,1)',
                width: 1
            });
            var textFill = new ol.style.Fill({
                color: '#fff'
            });
            var textStroke = new ol.style.Stroke({
                color: 'rgba(0, 0, 0, 0.5)',
                width: 3
            });
            var textStrokeW = new ol.style.Stroke({
                color: 'rgba(255, 255, 255, 0.5)',
                width: 3
            });
            var invisibleFill = new ol.style.Fill({
                color: 'rgba(238,59,13,0.5)'
            });

            function findSizeClass(arv) {
                var klass = 0;
                if (arv < 10) {
                    klass = 1;
                } else if ((9 < arv) && (arv < 100)) {
                    klass = 2;
                } else if ((99 < arv) && (arv < 1000)) {
                    klass = 3;
                } else if ((999 < arv) && (arv < 5000)) {
                    klass = 4;
                } else if (arv > 4999) {
                    klass = 5;
                }
                return klass
            }

            function createEarthquakeStyle(feature, resolution) {
                var name = feature.get('name');
                var rec = feature.get('rec'); //magnitude = parseFloat(name.substr(2));
                var klass = findSizeClass(rec);
                // console.log(name, rec, klass);
                var radius = 1 + 3 * klass; // * (magnitude - 5);

                return new ol.style.Style({
                    geometry: feature.getGeometry(),
                    image: new ol.style.Circle({
                        radius: radius,
                        fill: earthquakeFill
                        //stroke: earthquakeStroke
                    }),
                    text: (resolution > 200 ? null : new ol.style.Text({
                        font: feature.fontSize + 'pt Arial, Helvetica, Helvetica Neue, Arial, sans-serif',
                        text: name,
                        fill: earthquakeFill,
                        stroke: textStrokeW,
                        textAlign: 'left',
                        textBaseline: 'bottom',
                        offsetX: 5,
                        offsetY: -5
                    }))
                });
            }

            var maxFeatureCount;

            function calculateClusterInfo(resolution) {
                maxFeatureCount = 0;
                var features = vector.getSource().getFeatures();
                var feature, radius;
                for (var i = features.length - 1; i >= 0; --i) {
                    feature = features[i];
                    var originalFeatures = feature.get('features');
                    var extent = ol.extent.createEmpty();
                    for (var j = 0, jj = originalFeatures.length; j < jj; ++j) {
                        ol.extent.extend(extent, originalFeatures[j].getGeometry().getExtent());
                    }
                    maxFeatureCount = Math.max(maxFeatureCount, jj);
                    radius = 0.25 * (ol.extent.getWidth(extent) + ol.extent.getHeight(extent)) / resolution;
                    feature.set('radius', radius);
                }
            }

            var currentResolution;

            function styleFunction(feature, resolution) {
                if (resolution != currentResolution) {
                    calculateClusterInfo(resolution);
                    currentResolution = resolution;
                }
                var style;
                var size = feature.get('features').length;
                if (size > 1) {
                    style = [new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 16, //feature.get('radius'),
                            fill: new ol.style.Fill({
                                color: [255, 153, 0, Math.min(0.8, 0.4 + (size / maxFeatureCount))]
                            }),
                            stroke: new ol.style.Stroke({
                                color: 'rgba(255, 255, 255, 0.6)',
                                width: 1
                            })
                        }),
                        text: new ol.style.Text({
                            text: size.toString(),
                            fill: textFill,
                            stroke: textStroke
                        })
                    })];
                } else {
                    var originalFeature = feature.get('features')[0];
                    style = [createEarthquakeStyle(originalFeature, resolution)];
                }
                return style;
            }

            var currentResolution;

            function styleFunction1(feature, resolution) {
                if (resolution != currentResolution) {
                    calculateClusterInfo(resolution);
                    currentResolution = resolution;
                }
                var style;
                style = [createEarthquakeStyle1(feature, resolution)];
                return style;
            }

            function selectStyleFunction(feature, resolution) {
                var styles = [new ol.style.Style({
                    image: new ol.style.Circle({
                        radius: feature.get('radius'),
                        fill: invisibleFill
                    }),
                    text: new ol.style.Text({
                        text: feature.get('name'),
                        fill: textFill,
                        stroke: textStroke
                    })
                })];
                var originalFeatures = feature.get('features');
                var originalFeature;
                for (var i = originalFeatures.length - 1; i >= 0; --i) {
                    originalFeature = originalFeatures[i];
                    styles.push(createEarthquakeStyle(originalFeature));
                }
                return styles;
            }

            function createEarthquakeStyle1(feature, resolution) {
                var name = feature.get('name');
                var rec = feature.get('rec'); //magnitude = parseFloat(name.substr(2));
                var klass = findSizeClass(rec);
                //console.log(name, rec, klass);
                var radius = 3 * klass;// * (magnitude - 5);

                return new ol.style.Style({
                    geometry: feature.getGeometry(),
                    image: new ol.style.Circle({
                        radius: radius,
                        fill: earthquakeFill
                        //stroke: earthquakeStroke
                    }),
                    text: (resolution > 200 ? null : new ol.style.Text({
                        font: feature.fontSize + 'pt Arial, Helvetica, Helvetica Neue, Arial, sans-serif',
                        text: name,
                        fill: earthquakeFill,
                        stroke: textStrokeW,
                        textAlign: 'left',
                        textBaseline: 'bottom',
                        offsetX: 5,
                        offsetY: -5
                    }))
                });
            }

            var vectorSource = new ol.source.Vector({
                //attributions: [new ol.Attribution({
                //	html: "Data from PA/Credit Suisse."})]
            });

            for (var k in locs.response) {
                if (locs.response[k].longitude != null && locs.response[k].latitude != null) {
                    var centroidLL = ol.proj.transform([
                            locs.response[k].longitude,
                            locs.response[k].latitude],
                        'EPSG:4326', 'EPSG:3857');
                    var centroidPoint = new ol.geom.Point(centroidLL);
                    var feature = new ol.Feature({geometry: centroidPoint});
                    if (locs.response[k].locality != null) {
                        feature.set('name', locs.response[k].locality);
                    } else {
                        feature.set('name', locs.response[k].locality_en);
                    }
                    feature.set('fid', locs.response[k].id);
                    feature.set('rec', locs.response[k].total_related_records);
                    vectorSource.addFeature(feature);
                }
            }

            var vector = new ol.layer.Vector({
                title: 'Clustered',
                visible: (response.data.numFound > 999 ? true : false),
                distance: 40,
                source: new ol.source.Cluster({
                    source: vectorSource
                }),
                style: styleFunction
            });

            var vector1 = new ol.layer.Vector({
                title: 'Unclustered',
                visible: (response.data.numFound < 1000 ? true : false),
                source: vectorSource,
                style: styleFunction1
            });

            var mapbox = new ol.layer.Tile({
                title: 'MapBox grayscale',
                type: 'base',
                visible: true,
                source: new ol.source.XYZ({
                    url: 'https://api.tiles.mapbox.com/v4/mapbox.light/{z}/{x}/{y}.png?access_token=pk.eyJ1Ijoia3V1dG9iaW5lIiwiYSI6ImNpZWlxdXAzcjAwM2Nzd204enJvN2NieXYifQ.tp6-mmPsr95hfIWu3ASz2w'
                })
            });

            var vectors = new ol.layer.Group({
                title: 'Localities',
                //visible: false,
                //type: 'base',
                layers: [
                    vector,
                    vector1
                ]
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

            bedrockAge.on('change:visible', function () {
                if (bedrockAge.getVisible()) {
                    $('#bedrock-legend').removeClass("hidden");
                } else {
                    $('#bedrock-legend').addClass("hidden");
                }
            });

            var overlays = new ol.layer.Group({
                title: 'Overlays',
                layers: [
                    bedrockAge,
                    ermas
                ]
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

            var map = new ol.Map({
                layers: [basemaps, overlays, vectors],
                /*  interactions: ol.interaction.defaults().extend([new ol.interaction.Select({
                 condition: function(evt) {
                 return evt.originalEvent.type == 'mousemove' ||
                 evt.type == 'singleclick';
                 },
                 style: selectStyleFunction
                 })]),*/
                target: 'map',
                view: new ol.View({
                    projection: "EPSG:3857",
                    center: ol.proj.transform([25.0, 58.4], 'EPSG:4326', 'EPSG:3857'),
                    zoom: 6,
                    maxZoom: 16,
                    minZoom: 2
                }),
                controls: ol.control.defaults({
                    attributionOptions: ({
                        collapsible: true
                    })
                }).extend([
                    new ol.control.ScaleLine({units: "metric"}),
                    new ol.control.FullScreen()
                ])
            });

            var openLoc = function (pixel) {

                var feature = map.forEachFeatureAtPixel(pixel, function (feature, layer) {
                    return feature;

                });

                if (feature) {
                    var fid, pikk;
                    if (fid = feature.get('fid')) {
                        //document.getElementById('hoversystem').innerHTML = name;
                        window.open('/locality/' + fid, '', 'width=1025,height=750,scrollbars, resizable');
                    } else if (pikk = feature.get('features').length) {
                        if (pikk == 1) {
                            fid = feature.get('features')[0].get('fid');
                            //document.getElementById('hoversystem').innerHTML = name;
                            window.open('/locality/' + fid, '', 'width=1025,height=750,scrollbars, resizable');
                        }
                    }
                }
                /*
                 //var numFeatures = feature.get('features').length;
                 //var originalFeature = feature.get('features')[0];
                 var fid;
                 //var num = feature.get('features').length;
                 //console.log(feature);

                 if (fid = feature.get('fid')) {
                 window.open('/locality/' + fid, '', 'width=750,height=750,scrollbars, resizable');
                 }

                 else if (feature.get('features').length == 1 & feature.get('features')[0].get('fid')) {
                 window.open('/locality/' + feature.get('features')[0].get('fid'), '', 'width=750,height=750,scrollbars, resizable');
                 }*/

            };

            var displayFeatureInfo = function (pixel) {
                var feature = map.forEachFeatureAtPixel(pixel, function (feature, layer) {
                    return feature;
                });

                if (feature) {
                    var name, pikk;
                    document.getElementById('hoverbox').style.display = 'block';
                    if (name = feature.get('name')) {
                        document.getElementById('hoversystem').innerHTML = name + '<br />Click to see details';
                    } else if (pikk = feature.get('features').length) {
                        if (pikk == 1) {
                            name = feature.get('features')[0].get('name');
                            document.getElementById('hoversystem').innerHTML = name + '<br />Click to see details';
                        } else {
                            document.getElementById('hoversystem').innerHTML = pikk + ' clustered localities<br />Zoom in to see';
                        }
                    }
                    document.getElementById('hoverstat').innerHTML = "";
                } else {
                    document.getElementById('hoverbox').style.display = 'none';
                }
            };

            map.on('click', function (evt) {
                openLoc(evt.pixel);
            });

            map.getViewport().addEventListener('mousemove', function (evt) {
                var pixel = map.getEventPixel(evt);
                displayFeatureInfo(pixel);
            });

            var layerSwitcher = new ol.control.LayerSwitcher({
                // tipLabel: 'Légende' // Optional label for button
            });
            map.addControl(layerSwitcher);
            //----------------------------------------------

            if (locs.response.length > 0) {
                var extent = vector1.getSource().getExtent();
                map.getView().fit(extent, map.getSize());
                var zz = map.getView().getZoom();
                if (zz > 10) {
                    map.getView().setZoom(10);
                }
            }

            /*********************************************
             ***  CODE FROM geokogud.info map.php END  ***
             *********************************************/

            var tooltip = document.querySelectorAll('.coupontooltip');
            document.addEventListener('mousemove', fn, false);
            function fn(e) {
                for (var i = tooltip.length; i--;) {
                    tooltip[i].style.left = e.pageX + 'px';
                    tooltip[i].style.top = e.pageY + 'px';
                }
            }


        }
    }

    $scope.submitFilterForm = function () {
        var allFilters = ["specimens", "samples", "drillcores", "citing_references", "analyses",
            "stratotypes", "images", "taxon_occurrences"];

        var filterData = {
            filters: [],
            localityName: ""
        };

        for (var index in allFilters) {
            if (angular.element("input[name=" + allFilters[index] + "]").is(':checked')) {
                filterData.filters.push(allFilters[index]);
            }
        }
        if (angular.element("input[name=localityName]").val().length > 0) {
            filterData.localityName = angular.element("input[name=localityName]").val();
        } else {
            filterData.localityName = "";
        }

        ApplicationService.loadMapDataOnFilterChange(filterData, onMapDataFilter);
    };
    function onMapDataFilter(response) {
        $("#map").empty();
        onMapData(response);
    }

    $scope.toggleMapForm = function () {
        var button = $('#map-footer-button');
        var form = $('#map-footer-menu');
        if (button.hasClass('fa-angle-double-down')) {
            form.slideToggle('fast');
            button.removeClass('fa-angle-double-down');
            button.addClass('fa-angle-double-up');
        } else if (button.hasClass('fa-angle-double-up')) {
            form.slideToggle('fast');
            button.removeClass('fa-angle-double-up');
            button.addClass('fa-angle-double-down');
        }
    }
    
};

constructor.$inject = [
    '$scope',
    '$state',
    'configuration',
    'ApplicationService'
];

module.controller("MapController", constructor);
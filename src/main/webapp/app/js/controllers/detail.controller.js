var module = angular.module("geoApp");

var constructor = function ($scope, $state, $stateParams, $http, applicationService, configuration, bsLoadingOverlayService, errorService, Library, Locality) {
    var vm = this;

    vm.service = applicationService;
    vm.reload = reload;
    vm.fields = [];  vm.urlsMap = [];
    // vm.isIncludedField = isIncludedField;
    $scope.isLoggedIn = false;
    vm.isUserLoggedInToEditPortal = getUserLoggedInState();
    vm.recordPathname = getRecordPathname();

    vm.detailLoadingHandler = bsLoadingOverlayService.createHandler({referenceId: "detailView"});

    $scope.pageHasError = false;

    searchEntity();

    /**
     * type and id are for example: locality and 22860
     */
    function searchEntity () {
        vm.detailLoadingHandler.start();
        applicationService.getEntity($stateParams.type, $stateParams.id, onEntityData, onDetailError);
        if (['library'].indexOf($stateParams.type) > -1) {
            Library.referenceCollections({id: $stateParams.id, orderBy: '-sort,reference__author,-reference__year'}, onReferenceCollectionsLoaded)
        }
        if (['localities'].indexOf($stateParams.type) > -1) {
            //    TODO: specimen request with pagination
            //    TODO: sample request with pagination
            Locality.relatedSpecimens({id: $stateParams.id, paginateBy: '10', page: '1'}, onRelatedSpecimensLoaded)
            Locality.relatedSamples({id: $stateParams.id, paginateBy: '10', page: '1'}, onRelatedSamplesLoaded)
        }
    }

    function onEntityData(response) {
        console.log(response);
        if (response.data.results != null) {

            vm.results = response.data.results[0];
            vm.relatedData = response.data.related_data;
            vm.fields = Object.keys(vm.results);

            // For file detail view to get attach_link
            vm.allResults = response.data.results;

            vm.detailLoadingHandler.stop();
            getLocality();
            getRelatedData();
            vm.localities = (['doi'].indexOf($stateParams.type) > -1 ? getDoiLocalities() : []);
            vm.datasetLocalities = (['dataset'].indexOf($stateParams.type) > -1 ? getDatasetLocalities() : []);

            if (vm.relatedData != null) {
                vm.numOfSpecimens = (['preparations'].indexOf($stateParams.type) > -1 ? sumNumberOfSpecimens(vm.relatedData) : []);
                vm.preparationTaxons = (['preparations'].indexOf($stateParams.type) > -1 ? composeTaxonListInfo(vm.relatedData) : []);
            }

        } else {
            onDetailError(response);
        }
    }

    function onDetailError(error) {
        $scope.pageHasError = true;
        // console.log(error);
        errorService.commonErrorHandler(error);
        vm.detailLoadingHandler.stop();
    }

    // function isIncludedField (field) { TODO: Commented because not used
    //     return configuration.detailFieldsConfig[$stateParams.type].ignoreFields.indexOf(field) == -1;
    // }

    /**
     * Gets data from doi_geolocation table
     * and corrects it for map.
     * @returns {Array} of locality information
     */
    function getDoiLocalities() {
        var localities = [];
        angular.forEach(vm.doiGeolocation, function(location){
            localities.push({
                latitude: location.point_latitude,
                longitude: location.point_longitude,
                localityEng: location.locality__locality_en,
                localityEt: location.locality__locality,
                fid: location.locality_id,
                place: location.place
            })
        });
        return localities;
    }

    /**
     * Gets data from dataset and corrects it for map.
     * @returns {Array} of locality information
     */
    function getDatasetLocalities() {
        var localities = [];
        angular.forEach(vm.datasetAnalysis, function (location) {
            if (location.analysis__sample__locality__id != null && location.analysis__sample__locality__latitude != null && location.analysis__sample__locality__latitude != null) {
                localities.push({
                    latitude: location.analysis__sample__locality__latitude,
                    longitude: location.analysis__sample__locality__longitude,
                    localityEng: location.analysis__sample__locality__locality_en,
                    localityEt: location.analysis__sample__locality__locality,
                    fid: location.analysis__sample__locality__id,
                    place: location.analysis__sample__locality__country__value_en,
                });
            }
        });

        localities = removeDuplicates(localities);

        console.log(localities)
        return localities
    }

    /**
     * Get request for reference collections in library detail view
     */
    function onReferenceCollectionsLoaded(response) {
        console.log(response.data.results)
        vm.referenceCollections = (response.data.results !== undefined) ? response.data.results : []
    }

    function onRelatedSpecimensLoaded(response) {
        console.log(response)
        vm.relatedSpecimensCount = response.data.count
        vm.relatedSpecimens = response.data.results

        vm.relatedSpecimenIdentfication = response.data.related_data['specimen_identification']
        vm.relatedSpecimenIdentficationGeology = response.data.related_data['specimen_identification_geology']
    }

    function onRelatedSamplesLoaded(response) {
        console.log(response)
        vm.relatedSamplesCount = response.data.count
        vm.relatedSamples = response.data.results
    }

    /**
     * Removes duplicates from object array using vanilla js
     * Got this code from https://stackoverflow.com/questions/36032179/remove-duplicates-in-an-object-array-javascript
     */
    function removeDuplicates(array) {
        return array.reduce(function (p, c) {

            // create an identifying id from the object values
            var id = [c.latitude, c.longitude, c.localityEng, c.localityEt, c.fid, c.place].join('|');

            // if the id is not found in the temp array
            // add the object to the output array
            // and add the key to the temp array
            if (p.temp.indexOf(id) === -1) {
                p.out.push(c);
                p.temp.push(id);
            }
            return p;

            // return the unique array
        }, { temp: [], out: [] }).out;
    }

    /**
     * Gets locality fields which are declared in config.json
     * and then got from results which are added to locality array
     * needed for map.
     */
    function getLocality() {
        // Does not run if on corebox detail view
        if ($stateParams.type !== "corebox") {
            var localityFields = configuration.detailFieldsConfig[$stateParams.type].locality;
            if (localityFields) {
                vm.locality = {
                    latitude: vm.results[localityFields.lat],
                    longitude: vm.results[localityFields.lon],
                    localityId: vm.results[localityFields.id],
                    localityEn: vm.results[localityFields.value_en],
                    countryEn: vm.results[localityFields.country]
                };
            }
        }
    }

    /**
     * Adds related data to a variable.
     */
    // TODO: Should refactor and remove duplicates
    function getRelatedData() {
        if(vm.relatedData) {
            vm.doiAgent = vm.relatedData["doi_agent"];
            vm.doiRelatedIdentifier = vm.relatedData["doi_related_identifier"];
            vm.doiGeolocation = vm.relatedData["doi_geolocation"];
            vm.doiAttachment = vm.relatedData["attachment"];
            vm.samples = vm.relatedData["sample"];
            vm.soilHorizon = vm.relatedData["soil_horizon"];
            vm.stratigraphyStratotype = vm.relatedData["stratigraphy_stratotype"];
            vm.stratigraphyReference = vm.relatedData["stratigraphy_reference"];
            vm.stratigraphySynonym = vm.relatedData["stratigraphy_synonym"];
            vm.references = vm.relatedData["reference"];
            vm.drillcores = vm.relatedData["drillcore"];
            vm.images = vm.relatedData["image"];
            vm.drillcoreBoxes = vm.relatedData["drillcore_box"];
            vm.specimenIdentifications = vm.relatedData["specimen_identification"];
            vm.specimenReference = vm.relatedData["specimen_reference"];
            vm.specimenImage = vm.relatedData["specimen_image"];
            vm.localityReferences = vm.relatedData["locality_reference"];
            vm.localitySynonyms = vm.relatedData["locality_synonym"];
            vm.analysisResults = vm.relatedData["analysis_results"];
            vm.attachmentLink = vm.relatedData["attachment_link"];
            vm.specimens = vm.relatedData["specimen"];
            vm.taxonList = vm.relatedData["taxon_list"];
            vm.analysis = vm.relatedData["analysis"];
            vm.specimenIdentificationGeology = vm.relatedData["specimen_identification_geology"];
            vm.preparationTaxa = vm.relatedData["preparation_taxa"];
            vm.datasetReference = vm.relatedData["dataset_reference"];
            vm.datasetAttachment = vm.relatedData["attachment"];
            vm.datasetAnalysis = vm.relatedData["dataset_analysis"];
            vm.doi = vm.relatedData["doi"];
            vm.preparation = vm.relatedData["preparation"];
            vm.taxon = vm.relatedData["taxon"];
            vm.attachment = vm.relatedData["attachment"];
        }
    }

    $scope.doesAttachmentTableContainImages = function() {
        var containsImage = false;
        for (var image in vm.attachment) {
            if (vm.attachment[image].hasOwnProperty("specimen_image_attachment")) {
                if (vm.attachment[image].specimen_image_attachment === 2) {
                    containsImage = true
                }
            }
        }
        return containsImage;
    }

    $scope.getUrlPath = function () {
        return location.href
    }

    $scope.getCurrentDate = function () {
        return new Date()
    }


    /**
     * Gets triggered when pagination is used
     */
    $scope.searchRelatedSpecimens = function () {
        Locality.relatedSpecimens({id: $stateParams.id, paginateBy: '10', page: this.specimenPage}, onRelatedSpecimensLoaded)
    }

    /**
     * Calculates total number of specimens from preparation_taxa table which is
     * used in preparations detail view general info and needed to calculate percent.
     * @param results aka related data
     * @returns total number of specimens.
     */
    function sumNumberOfSpecimens(results) {
        var numOfSpecimen = 0;
        angular.forEach(results.preparation_taxa, function (preparation) {
            numOfSpecimen += preparation.frequency;
        });
        return numOfSpecimen;
    }

    /**
     * Builds array similar to preparation_taxa with
     * added 'percent' field. Used in preparations detail view.
     * @param results aka related data
     * @returns {Array} of taxons with added fields 'percent'
     */
    function composeTaxonListInfo(results) {
        var preparationTaxons = [];

        if (results && results.preparation_taxa && Array.isArray(results.preparation_taxa)) {

            var numOfSpecimen = sumNumberOfSpecimens(results);
            var percent = 0;

            angular.forEach(results.preparation_taxa, function(data) {
                percent = ((data.frequency * 100) / numOfSpecimen).toFixed(1);
                // console.log(percent);

                preparationTaxons.push({
                    name: data.name,
                    frequency: data.frequency,
                    percent: percent,
                    remarks: data.remarks
                });

            });
        }
        return preparationTaxons;
    }

    /**
     * Goes to given view.
     * For example corebox/5000
     * @param params, table -> detail view name; id -> detail view identifier
     */
    function reload(params) {
        var idFromUrl = location.pathname.split("/")[2];

        if (params.right) idFromUrl = Number(idFromUrl) + 1;
        if (params.left) idFromUrl = Number(idFromUrl) - 1;

        if (idFromUrl > 0 && Number.isInteger(idFromUrl)) {
            $state.go(params.table + '.view', {id:idFromUrl})
        }
    }


    /**
     * Checks if user is logged in to edit portal
     * @returns boolean value
     */
    function getUserLoggedInState() {
        applicationService.getEditPortalLoggedInState(onLoginStateFetched);
    }

    function onLoginStateFetched(response) {
        if (response && response.status === 200 && response.data && response.data.results) {
            $scope.isLoggedIn = response.data.results.success;
        } else $scope.isLoggedIn = false;
    }

    /**
     * Gets record pathname for edit portal
     * @returns String
     */
    function getRecordPathname() {
        var editUrl = configuration.editPortal;
        var detailViewPathname = window.location.pathname;
        var pathnameList = detailViewPathname.split("/");

        if (detailViewPathname.includes("file") || detailViewPathname.includes("specimen_image")) {
            detailViewPathname = "/attachment/" + pathnameList[pathnameList.length - 1];
        } else if (detailViewPathname.includes("corebox")) {
            detailViewPathname = "/drillcore_box/" + pathnameList[pathnameList.length - 1];
        }


        if (typeof detailViewPathname !== "undefined" && detailViewPathname !== null) {
            return editUrl + detailViewPathname;
        } else {
            return editUrl;
        }
    }

    $scope.ifDoiAttachmentContainsShowableFiles = function(doiAttachment) {
        // console.log(doiAttachment);
        if (doiAttachment.length > 0) {
            for (entity in doiAttachment) {
                var filename = doiAttachment[entity].filename;
                if (filename.endsWith('txt') || filename.endsWith('png') || filename.endsWith('jpg') || filename.endsWith('jpeg')) {
                    return true;
                }
            }
        }
        return false;
    }
};

constructor.$inject = [
    "$scope",
    "$state",
    "$stateParams",
    "$http",
    'ApplicationService',
    'configuration',
    'bsLoadingOverlayService',
    'ErrorService',
    'Library',
    'Locality'
];

module.controller("DetailController", constructor);
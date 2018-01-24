var module = angular.module("geoApp");
var constructor = function ($scope, $state, $stateParams, applicationService, configuration, bsLoadingOverlayService, errorService) {
    var vm = this;

    vm.service = applicationService;
    vm.reload = reload;
    vm.fields = [];  vm.urlsMap = [];
    vm.isIncludedField = isIncludedField;

    vm.detailLoadingHandler = bsLoadingOverlayService.createHandler({referenceId: "detailView"});

    searchEntity();

    /**
     * type and id are for example: locality and 22860
     */
    function searchEntity () {
        vm.detailLoadingHandler.start();
        applicationService.getEntity($stateParams.type, $stateParams.id, onEntityData, onDetailError)
    }

    function onEntityData(response) {
        console.log(response);
        if (response.data.results != null) {

            vm.results = response.data.results[0];
            vm.relatedData = response.data.related_data;
            vm.fields = Object.keys(vm.results);
            console.log(vm.fields);
            vm.imageUrl = (['specimenImage', 'photoArchive'].indexOf($stateParams.type) > -1 ? vm.service.composeImageUrl(vm.results) : null);
            vm.externalImagePath = (['specimenImage', 'photoArchive'].indexOf($stateParams.type) > -1 ? vm.service.composeExternalImagePath(vm.results) : null);

            vm.detailLoadingHandler.stop();
            getLocality();
            getRelatedData();
            vm.localities = (['doi'].indexOf($stateParams.type) > -1 ? getLocalities() : []);

            vm.coreboxImageUrl = (['corebox'].indexOf($stateParams.type) > -1 ? vm.service.composeCoreboxImageUrl(vm.results) : null);
            vm.externalCoreboxImagePath = (['corebox'].indexOf($stateParams.type) > -1 ? vm.service.composeExternalCoreboxImagePath(vm.results) : null);

            if (vm.relatedData != null) {
                vm.numOfSpecimens = (['preparations'].indexOf($stateParams.type) > -1 ? sumNumberOfSpecimens(vm.relatedData) : []);
                vm.preparationTaxons = (['preparations'].indexOf($stateParams.type) > -1 ? composeTaxonListInfo(vm.relatedData) : []);
            }

            if (['photoArchive'].indexOf($stateParams.type) > -1) {
                //TODO: after load
                document.getElementById('img-size').naturalWidth;
            }

        } else {
            // TODO: Do something if no response, fix error template or something.
            // $state.go("error");
        }
    }

    function onDetailError(error) {
        errorService.commonErrorHandler(error);
        vm.detailLoadingHandler.stop();
    }

    function isIncludedField (field) {
        return configuration.detailFieldsConfig[$stateParams.type].ignoreFields.indexOf(field) == -1;
    }

    function getLocalities() {
        //vm.doiGeolocation
        var localities = [];
        angular.forEach(vm.doiGeolocation, function(location){
            //console.log(location.point.split(' ')[0]);
            //console.log(location.point.split(' ')[1]);
            localities.push({
                latitude: location.point.split(' ')[0],
                longitude: location.point.split(' ')[1],
                localityEng: location.locality__locality_en,
                localityEt: location.locality__locality,
                fid: location.locality_id,
                place: location.place
            })
        });
        return localities;
    }

    // Needed for map
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
        }
    }

    function sumNumberOfSpecimens(results) {
        var numOfSpecimen = 0;
        angular.forEach(results.preparation_taxa, function (preparation) {
            numOfSpecimen += preparation.frequency;
        });
        return numOfSpecimen;
    }

    function composeTaxonListInfo(results) {
        var preparationTaxons = [];
        var numOfSpecimen = sumNumberOfSpecimens(results);
        var percent = 0;

        angular.forEach(results.preparation_taxa, function(data) {
            percent = ((data.frequency * 100) / numOfSpecimen).toFixed(1);
            console.log(percent);

            preparationTaxons.push({
                name: data.name,
                frequency: data.frequency,
                percent: percent,
                remarks: data.remarks
            });

        });
        return preparationTaxons;
    }

    function reload(id) {
        $state.go('coreBox.view', {id:id})
    }
};

constructor.$inject = ["$scope", "$state", "$stateParams", 'ApplicationService', 'configuration', 'bsLoadingOverlayService', 'ErrorService'];

module.controller("DetailController", constructor);
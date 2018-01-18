package ee.ttu.geocollection.endpoint.web;

import ee.ttu.geocollection.core.utils.ControllerHelper;
import ee.ttu.geocollection.domain.LookUpType;
import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.AsynchService;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.solr.locality.service.LocalitySolrService;
import ee.ttu.geocollection.interop.solr.photoArchive.service.PhotoArchiveSolrService;
import ee.ttu.geocollection.interop.solr.reference.service.ReferenceSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.api.analyses.pojo.AnalysesSearchCriteria;
import ee.ttu.geocollection.interop.api.analyses.search.AnalysesApiService;
import ee.ttu.geocollection.interop.api.doi.pojo.DoiSearchCriteria;
import ee.ttu.geocollection.interop.api.doi.service.DoiApiService;
import ee.ttu.geocollection.interop.api.drillCores.pojo.DrillCoreSearchCriteria;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreApiService;
import ee.ttu.geocollection.interop.api.localities.pojo.LocalitySearchCriteria;
import ee.ttu.geocollection.interop.api.localities.service.LocalitiesApiService;
import ee.ttu.geocollection.interop.api.photoArchive.pojo.PhotoArchiveSearchCriteria;
import ee.ttu.geocollection.interop.api.photoArchive.service.PhotoArchiveApiService;
import ee.ttu.geocollection.interop.api.preparations.PreparationsApiService;
import ee.ttu.geocollection.interop.api.preparations.pojo.PreparationsSearchCriteria;
import ee.ttu.geocollection.interop.api.reference.pojo.ReferenceSearchCriteria;
import ee.ttu.geocollection.interop.api.reference.service.ReferenceApiService;
import ee.ttu.geocollection.interop.api.samples.pojo.SampleSearchCriteria;
import ee.ttu.geocollection.interop.api.samples.service.SamplesApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.soil.pojo.SoilSearchCriteria;
import ee.ttu.geocollection.interop.api.soil.service.SoilApiService;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import ee.ttu.geocollection.interop.api.stratigraphies.pojo.StratigraphySearchCriteria;
import ee.ttu.geocollection.interop.api.stratigraphies.service.StratigraphyApiService;
import ee.ttu.geocollection.interop.solr.sample.service.SampleSolrService;
import ee.ttu.geocollection.interop.solr.specimen.service.SpecimenSolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController extends ControllerHelper {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ApiService apiService;
    @Autowired
    private AsynchService asynchService;

    @Autowired
    private SpecimenSolrService specimenSolrService;
    @Autowired
    private SampleSolrService sampleSolrService;
    @Autowired
    private LocalitySolrService localitySolrService;
    @Autowired
    private ReferenceSolrService referenceSolrService;
    @Autowired
    private PhotoArchiveSolrService photoArchiveSolrService;

    @Autowired
    private SpecimenApiService specimenApiService;
    @Autowired
    private SamplesApiService samplesApiService;
    @Autowired
    private DrillCoreApiService drillCoreApiService;
    @Autowired
    private LocalitiesApiService localitiesApiService;
    @Autowired
    private ReferenceApiService referenceApiService;
    @Autowired
    private StratigraphyApiService stratigraphyApiService;
    @Autowired
    private AnalysesApiService analysesApiService;
    @Autowired
    private PreparationsApiService preparationsApiService;
    @Autowired
    private PhotoArchiveApiService photoArchiveApiService;
    @Autowired
    private SoilApiService soilApiService;
    @Autowired
    private DoiApiService doiApiService;

    @GetMapping(value = "/global/{query}")
    public Iterable searchGlobally(@PathVariable String query) {
        ArrayList<SolrResponse> responses = new ArrayList<>();
        responses.add(photoArchiveSolrService.findPhotoArchiveByIndex(query));
        responses.add(referenceSolrService.findReferenceByIndex(query));
        responses.add(localitySolrService.findLocalityByIndex(query));
        responses.add(sampleSolrService.findSampleByIndex(query));
        responses.add(specimenSolrService.findSpecimenByIndex(query));
        return responses;
    }

    @GetMapping(value = "/autocomplete-field")
    public Map findDoiById(
            @RequestParam("table") String table,
            @RequestParam("term") String term,
            @RequestParam("sortField") String sortField,
            @RequestParam("searchField") String searchField) {
        return apiService.searchByField(table, term, sortField, searchField);
    }

//    @PostMapping(value = "/specimen")
//    public Map searchSpecimenSolr(@RequestBody SpecimenSearchCriteria specimenSearchCriteria) {
//        return specimenSolrService.findSpecimen(specimenSearchCriteria);
//    }

    @PostMapping(value = "/specimen")
    public ApiResponse searchSpecimen(@RequestBody SpecimenSearchCriteria specimenSearchCriteria) {
//        ApiResponse specimens = specimenApiService.findSpecimen(specimenSearchCriteria);

        ApiResponse specimens = null;

        if (specimenSearchCriteria.getSearchImages() != null
                && specimenSearchCriteria.getSearchImages().getName() != null
                && specimenSearchCriteria.getSearchImages().getName().equals("true")) {
            specimens = specimenApiService.findSpecimenImages(specimenSearchCriteria);
        } else {
            specimens = specimenApiService.findSpecimen(specimenSearchCriteria);
            if (specimens.getResult() != null) {
                // Call for specimen images
                asynchService.doAsynchCallsForEachResult(
                        specimens,
                        specimen ->
                                () -> specimenApiService.findSpecimenImage(
                                        new SearchField(specimen.get("id").toString(), LookUpType.exact)),
                        specimen ->
                                receivedImage -> specimen.put("specimen_image_thumbnail", receivedImage));

                // Call for specimen identifications
                asynchService.doAsynchCallsForEachResult(
                        specimens,
                        specimen ->
                                () -> specimenApiService.findSpecimenIdentification(
                                        new SearchField(specimen.get("id").toString(), LookUpType.exact)),
                        specimen ->
                                receivedIdentification -> specimen.put("specimen_identifications", receivedIdentification));

                // Call for specimen identification geologies
                asynchService.doAsynchCallsForEachResult(
                        specimens,
                        specimen ->
                                () -> specimenApiService.findSpecimenIdentificationGeologies(
                                        new SearchField(specimen.get("id").toString(), LookUpType.exact)),
                        specimen ->
                                receivedIdentificationGeologies -> specimen.put("specimen_identification_geologies", receivedIdentificationGeologies));
            }
        }

        return specimens;
    }
    private ApiResponse iterateImages(Map<String, Object> specimen, SpecimenSearchCriteria specimenSearchCriteria) {
        specimenSearchCriteria.setSpecimenNumber(new SearchField(
                specimen.get("specimen__specimen_id").toString(),
                LookUpType.exact));
        return specimenApiService.findSpecimen(specimenSearchCriteria);

    }

    @PostMapping(value = "/sample")
    public ApiResponse searchSample(@RequestBody SampleSearchCriteria sampleSearchCriteria) {
        return samplesApiService.findSample(sampleSearchCriteria);
    }

    @PostMapping(value = "/drillcore")
    public ApiResponse searchDrillCores(@RequestBody DrillCoreSearchCriteria searchCriteria) {
        return drillCoreApiService.findDrillCore(searchCriteria);
        //EDIT 12.12.2017 commented code because it is not used and server threw code 500
//        ApiResponse drillCores =  drillCoreApiService.findDrillCore(searchCriteria);
//        if (drillCores.getResult() != null) {
//            asynchService.doAsynchCallsForEachResult(
//                    drillCores,
//                    drillCore ->
//                            () -> drillCoreApiService.findDrillCoreImage(
//                                    new SearchField(drillCore.get("id").toString(), LookUpType.exact)),
//                    drillCore ->
//                            receivedImage -> drillCore.put("drill_core_image_thumbnail", receivedImage));
//        }
//        return drillCores;
    }

    @PostMapping(value = "/locality")
    public ApiResponse searchLocalities(@RequestBody LocalitySearchCriteria searchCriteria) {

//        TODO: Enable when findLocalityImages() is ready.
//        ApiResponse localities;
//        if (searchCriteria.getSearchImages() != null
//                && searchCriteria.getSearchImages().getName() != null
//                && searchCriteria.getSearchImages().getName().equals("true")) {
//            localities = localitiesApiService.findLocalityImages(searchCriteria);
//        } else {
//            localities = localitiesApiService.findLocality(searchCriteria);
//        }


        ApiResponse localities =  localitiesApiService.findLocality(searchCriteria);
        if (localities.getResult() != null
                && searchCriteria.getSearchImages() != null
                && searchCriteria.getSearchImages().getName() != null
                && searchCriteria.getSearchImages().getName().equals("true")) {
            asynchService.doAsynchCallsForEachResult(
                    localities,
                    locality ->
                            () -> localitiesApiService.findLocalityImage(
                                    new SearchField(locality.get("id").toString(), LookUpType.exact)),
                    locality ->
                            receivedImage -> locality.put("locality_image_thumbnail", receivedImage));
        }

        return localities;
    }

    @PostMapping(value = "/photo-archive")
    public ApiResponse searchPhotoArchive(@RequestBody PhotoArchiveSearchCriteria photoArchiveSearchCriteria) {
        return photoArchiveApiService.findPhoto(photoArchiveSearchCriteria);
    }

    @PostMapping(value = "/soil")
    public ApiResponse searchSoil(@RequestBody SoilSearchCriteria searchCriteria) {
        return soilApiService.findSoil(searchCriteria);
    }

    @PostMapping(value = "/reference")
    public ApiResponse searchReferences(@RequestBody ReferenceSearchCriteria searchCriteria) {
        return referenceApiService.findReference(searchCriteria);
    }

    @PostMapping(value = "/doi")
    public ApiResponse searchDoi(@RequestBody DoiSearchCriteria searchCriteria) {
        return doiApiService.findDoi(searchCriteria);
    }

    @PostMapping(value = "/analyses")
    public ApiResponse searchAnalyses(@RequestBody AnalysesSearchCriteria analysesSearchCriteria) {
        return analysesApiService.findAnalyses(analysesSearchCriteria);
    }

    @PostMapping(value = "/preparation")
    public ApiResponse searchPreparations(@RequestBody PreparationsSearchCriteria preparationsSearchCriteria) {
        return preparationsApiService.findPreparations(preparationsSearchCriteria);
    }

    @PostMapping(value = "/stratigraphy")
    public ApiResponse searchStratigraphy(@RequestBody StratigraphySearchCriteria stratigraphySearchCriteria) {
        return stratigraphyApiService.findStratigraphy(stratigraphySearchCriteria);
    }
}
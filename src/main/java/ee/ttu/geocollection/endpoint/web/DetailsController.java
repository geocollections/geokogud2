package ee.ttu.geocollection.endpoint.web;


import ee.ttu.geocollection.domain.LookUpType;
import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.AsynchService;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.analyses.search.AnalysesApiService;
import ee.ttu.geocollection.interop.api.doi.service.DoiApiService;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreApiService;
import ee.ttu.geocollection.interop.api.localities.service.LocalitiesApiService;
import ee.ttu.geocollection.interop.api.photoArchive.service.PhotoArchiveApiService;
import ee.ttu.geocollection.interop.api.preparations.PreparationsApiService;
import ee.ttu.geocollection.interop.api.reference.service.ReferenceApiService;
import ee.ttu.geocollection.interop.api.samples.service.SamplesApiService;
import ee.ttu.geocollection.interop.api.soil.service.SoilApiService;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import ee.ttu.geocollection.interop.api.stratigraphies.service.StratigraphyApiService;
import ee.ttu.geocollection.interop.solr.specimen.service.SpecimenSolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/details")
public class DetailsController {
    private static final Logger logger = LoggerFactory.getLogger(DetailsController.class);

    @Autowired
    private AsynchService asynchService;

    @Autowired
    private DrillCoreApiService drillCoreApiService;
    @Autowired
    private ReferenceApiService referenceApiService;
    @Autowired
    private DoiApiService doiApiService;
    @Autowired
    private SoilApiService soilApiService;
    @Autowired
    private SamplesApiService samplesApiService;
    @Autowired
    private PhotoArchiveApiService photoArchiveApiService;
    @Autowired
    private LocalitiesApiService localitiesApiService;
    @Autowired
    private SpecimenApiService specimenApiService;
    @Autowired
    private SpecimenSolrService specimenSolrService;
    @Autowired
    private StratigraphyApiService stratigraphyApiService;
    @Autowired
    private AnalysesApiService analysesApiService;
    @Autowired
    private PreparationsApiService preparationsApiService;

    @RequestMapping(value = "/raw-specimen/{id}")
    public Map findRawSpecimenById(@PathVariable Long id) {
        return specimenApiService.findRawById(id);
//        return specimenSolrService.findSpecimenById(id);
    }

    @RequestMapping(value = "/raw-specimen-image/{id}")
    public Map findRawSpecimenImageById(@PathVariable Long id) {
        return specimenApiService.findRawSpecimenImageById(id);
    }

    @RequestMapping(value = "/raw-sample/{id}")
    public Map findRawSampleById(@PathVariable Long id) {
        return samplesApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-drillcore/{id}")
    public Map findRawDrillCoreById(@PathVariable Long id) {
        return drillCoreApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-corebox/{id}")
    public Map findRawCoreBoxById(@PathVariable Long id) {
        return drillCoreApiService.findRawCoreBoxById(id);
    }

    @RequestMapping(value = "/raw-locality/{id}")
    public Map findRawLocalityById(@PathVariable Long id) {
        return localitiesApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-stratigraphy/{id}")
    public ApiResponse findRawStratigraphyById(@PathVariable Long id) {
        ApiResponse rawStratigraphy = stratigraphyApiService.findRawById(id);

        // Call for lithostratigraphies
        asynchService.doAsynchCallsForEachResult(
                rawStratigraphy,
                stratigraphy ->
                        () -> stratigraphyApiService.findAllLithostratigraphies(
                                new SearchField(stratigraphy.get("id").toString(), LookUpType.exact)),
                stratigraphy ->
                        receivedLithostratigraphy -> stratigraphy.put("lithostratigraphies", receivedLithostratigraphy));

        return rawStratigraphy;
    }

    @RequestMapping(value = "/raw-reference/{id}")
    public Map findRawReferenceById(@PathVariable Long id) {
        return referenceApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-analysis/{id}")
    public Map findRawAnalysisById(@PathVariable Long id) {
        return analysesApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-preparation/{id}")
    public Map findRawPreparationById(@PathVariable Long id) {
        return preparationsApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-photo-archive/{id}")
    public Map findRawPhotoArchiveById(@PathVariable Long id) {
        return photoArchiveApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-soil/{id}")
    public Map findRawSoilById(@PathVariable Long id)  {
        return soilApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-doi/{id}")
    public Map findRawDoiByIdentifier(@PathVariable Long id) {
        return doiApiService.findRawById(id);
    }

}
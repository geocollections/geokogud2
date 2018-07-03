package ee.ttu.geocollection.endpoint.web;


import ee.ttu.geocollection.domain.LookUpType;
import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.AsynchService;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.analyses.search.AnalysesApiService;
import ee.ttu.geocollection.interop.api.attachment.service.AttachmentApiService;
import ee.ttu.geocollection.interop.api.collection.service.CollectionApiService;
import ee.ttu.geocollection.interop.api.dataset.service.DatasetApiService;
import ee.ttu.geocollection.interop.api.doi.service.DoiApiService;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreApiService;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreBoxApiService;
import ee.ttu.geocollection.interop.api.file.service.FileApiService;
import ee.ttu.geocollection.interop.api.localities.service.LocalitiesApiService;
import ee.ttu.geocollection.interop.api.photoArchive.service.PhotoArchiveApiService;
import ee.ttu.geocollection.interop.api.preparations.PreparationsApiService;
import ee.ttu.geocollection.interop.api.reference.service.ReferenceApiService;
import ee.ttu.geocollection.interop.api.samples.service.SamplesApiService;
import ee.ttu.geocollection.interop.api.soil.service.SoilApiService;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenImageApiService;
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
    private DrillCoreBoxApiService drillCoreBoxApiService;
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
    private SpecimenImageApiService specimenImageApiService;
    @Autowired
    private SpecimenSolrService specimenSolrService;
    @Autowired
    private StratigraphyApiService stratigraphyApiService;
    @Autowired
    private AnalysesApiService analysesApiService;
    @Autowired
    private PreparationsApiService preparationsApiService;

    @Autowired
    private AttachmentApiService attachmentApiService;

    @Autowired
    private CollectionApiService collectionApiService;

    @Autowired
    private DatasetApiService datasetApiService;

    @Autowired
    private FileApiService fileApiService;


    @RequestMapping(value = "/raw-specimen/{id}")
    public Map findRawSpecimenById(@PathVariable Long id) {
        return specimenApiService.findRawById(id);
//        return specimenSolrService.findSpecimenById(id);
    }


    @RequestMapping(value = "/raw-specimen-image/{id}")
    public ApiResponse findRawSpecimenImageById(@PathVariable Long id) {
        ApiResponse specimenImages = specimenImageApiService.findRawSpecimenImageById(id);

        if (specimenImages.getResult() != null) {
            // Call for specimen identification (name)
            asynchService.doAsynchCallsForEachResult(
                    specimenImages,
                    specimenImage -> () -> specimenImageApiService.findSpecimenIdentification(
                            new SearchField(specimenImage.get("specimen_id").toString(), LookUpType.exact)),
                    specimenImage -> receivedSpecimenImage -> specimenImage.put("specimenIdentification", receivedSpecimenImage));
        }

        return specimenImages;
    }

    @RequestMapping(value = "/raw-sample/{id}")
    public ApiResponse findRawSampleById(@PathVariable Long id) {
        ApiResponse samples = samplesApiService.findRawById(id);

        if (samples.getResult() != null) {
            // Call for specimens
            asynchService.doAsynchCallsForEachResult(
                    samples,
                    sample -> () -> samplesApiService.findSpecimens(
                            new SearchField(sample.get("id").toString(), LookUpType.exact)),
                    sample -> receivedSpecimens -> sample.put("specimensFromSample", receivedSpecimens));
        }

        return samples;
    }

    @RequestMapping(value = "/raw-drillcore/{id}")
    public Map findRawDrillCoreById(@PathVariable Long id) {
        return drillCoreApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-corebox/{id}")
    public ApiResponse findRawCoreBoxById(@PathVariable Long id) {
        ApiResponse coreBox = drillCoreBoxApiService.findRawCoreBoxById(id);

        if (coreBox.getResult() != null) {
            // Call for samples
            asynchService.doAsynchCallsForEachResult(
                    coreBox,
                    box -> () -> drillCoreBoxApiService.findSamples(
                            new SearchField(box.get("drillcore__locality").toString(), LookUpType.exact),
                            new SearchField(box.get("depth_start").toString(), LookUpType.gte),
                            new SearchField(box.get("depth_end").toString(), LookUpType.lte)),
                    box -> receivedSamples -> box.put("samplesFromBox", receivedSamples));

            // Call for stratigraphic boundaries
            asynchService.doAsynchCallsForEachResult(
                    coreBox,
                    box -> () -> drillCoreBoxApiService.findStratigraphicBoundaries(
                            new SearchField(box.get("drillcore__locality").toString(), LookUpType.exact),
                            new SearchField(box.get("depth_start").toString(), LookUpType.gte),
                            new SearchField(box.get("depth_end").toString(), LookUpType.lte)),
                    box -> receivedBoundaries -> box.put("stratigraphicBoundaries", receivedBoundaries));
        }

        return coreBox;
    }

    @RequestMapping(value = "/raw-locality/{id}")
    public ApiResponse findRawLocalityById(@PathVariable Long id) {
        ApiResponse localities = localitiesApiService.findRawById(id);

        if (localities.getResult() != null) {
            // Call for specimens
            asynchService.doAsynchCallsForEachResult(
                    localities,
                    locality ->
                            () -> localitiesApiService.findAllSpecimens(
                                    new SearchField(locality.get("id").toString(), LookUpType.exact)),
                    locality ->
                            receivedSpecimens -> locality.put("specimens", receivedSpecimens));
        }


        return localities;
    }

    @RequestMapping(value = "/raw-stratigraphy/{id}")
    public ApiResponse findRawStratigraphyById(@PathVariable Long id) {
        ApiResponse rawStratigraphy = stratigraphyApiService.findRawById(id);

        if (rawStratigraphy.getResult() != null) {
            // Call for lithostratigraphies
            asynchService.doAsynchCallsForEachResult(
                    rawStratigraphy,
                    stratigraphy ->
                            () -> stratigraphyApiService.findAllLithostratigraphies(
                                    new SearchField(stratigraphy.get("id").toString(), LookUpType.exact)),
                    stratigraphy ->
                            receivedLithostratigraphy -> stratigraphy.put("lithostratigraphies", receivedLithostratigraphy));

            // Call for stratigraphy Overlain_by
            asynchService.doAsynchCallsForEachResult(
                    rawStratigraphy,
                    stratigraphy ->
                            () -> stratigraphyApiService.findOverlainByStratigraphy(
                                    new SearchField(stratigraphy.get("age_top").toString(), LookUpType.exact),
                                    new SearchField(stratigraphy.get("parent_id").toString(), LookUpType.exact)),
                    stratigraphy ->
                            receivedStratigraphy -> stratigraphy.put("overlain_by", receivedStratigraphy));

            // Call for stratigraphy Overlies
            asynchService.doAsynchCallsForEachResult(
                    rawStratigraphy,
                    stratigraphy ->
                            () -> stratigraphyApiService.findOverliesStratigraphy(
                                    new SearchField(stratigraphy.get("age_base").toString(), LookUpType.exact),
                                    new SearchField(stratigraphy.get("parent_id").toString(), LookUpType.exact)),
                    stratigraphy ->
                            receivedStratigraphy -> stratigraphy.put("overlies", receivedStratigraphy));
        }

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
        return photoArchiveApiService.findRawByIdOld(id);
    }

    @RequestMapping(value = "/raw-soil/{id}")
    public Map findRawSoilById(@PathVariable Long id)  {
        return soilApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-doi/{id}")
    public Map findRawDoiByIdentifier(@PathVariable Long id) {
        return doiApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-attachment/{id}")
    public ApiResponse findRawAttachmentByIdentifier(@PathVariable Long id) {
        ApiResponse attachment = attachmentApiService.findRawById(id);

        if (attachment.getResult() != null) {
            asynchService.doAsynchCallsForEachResult(
                    attachment,
                    specimenIdentification -> () -> specimenImageApiService.findSpecimenIdentification(
                            new SearchField(specimenIdentification.get("specimen_id").toString(), LookUpType.exact)),
                    specimenId -> receivedSpecimenId -> specimenId.put("specimenIdentification", receivedSpecimenId));
        }
        return attachment;
    }

    @RequestMapping(value = "/raw-collection/{id}")
    public ApiResponse findRawCollectionByIdentifier(@PathVariable Long id) {
        return collectionApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-dataset/{id}")
    public ApiResponse findRawDatasetByIdentifier(@PathVariable Long id) {
        return datasetApiService.findRawById(id);
    }

    @RequestMapping(value = "/raw-file/{id}")
    public ApiResponse findRawFileById(@PathVariable Long id) { return fileApiService.findRawById(id); }

}
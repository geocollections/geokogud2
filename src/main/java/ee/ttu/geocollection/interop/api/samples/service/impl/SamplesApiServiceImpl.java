package ee.ttu.geocollection.interop.api.samples.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSampleSearchApiBuilder;
import ee.ttu.geocollection.interop.api.samples.pojo.SampleSearchCriteria;
import ee.ttu.geocollection.interop.api.samples.service.SamplesApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class SamplesApiServiceImpl implements SamplesApiService {

    private static final String SAMPLE_TABLE = "sample";
    private static final String TAXON_LIST_TABLE = "taxon_list";
    private static final String ANALYSIS_TABLE = "analysis";
    private static final String SPECIMEN_TABLE = "specimen";
    private static final String ATTACHMENT_LINK = "attachment_link";
    private static final String ANALYSIS_RESULTS = "analysis_results";
    private static final String PREPARATION = "preparation";
    private static final String DATASET_ANALYSIS = "dataset_analysis";
    private static final String SAMPLE_REFERENCE = "sample_reference";

    private List<String> fields = Arrays.asList(
            "id",
            "number",
            "number_additional",
            "number_field",
            "locality_id",
            "locality__locality",
            "locality__locality_en",
            "locality_free",
            "locality__latitude",
            "locality__longitude",
            "locality__country__value",
            "locality__country__value_en",
            "soil_horizon",
            "soil_site__site",
            "soil_site__site_en",
            "soil_site__latitude",
            "soil_site__longitude",
            "stratigraphy_id",
            "stratigraphy__stratigraphy",
            "stratigraphy__stratigraphy_en",
            "lithostratigraphy__stratigraphy",
            "lithostratigraphy__stratigraphy_en",
            "stratigraphy_bed",
            "agent_collected__agent",
            "agent_collected_free",
            "date_collected",
            "date_collected_free",
            "depth",
            "depth_interval",
            "classification_rock__name",
            "classification_rock__name_en",
            "rock",
            "rock_en",
            "location",
            "mass",
            "remarks",
            "series_id",
            "database__acronym",
            "database__name",
            "database__name_en",
            "date_added",
            "date_changed",
            "parent_sample",
            "x1",
            "y1",
            "sample_purpose__value",
            "sample_purpose__value_en",
            "fossils",
            "palaeontology",
            "analysis",
            "owner__agent",
            "stratigraphy_free",
            "site__name",
            "site__name_en",
            "site__latitude",
            "site__longitude"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findSample(SampleSearchCriteria searchCriteria) {
        String requestParams = FluentSampleSearchApiBuilder.aRequest()
                .queryNumber(searchCriteria.getSampleNumber()).andReturn()
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryDepth(searchCriteria.getDepthSince()).andReturn()
                .queryDepth(searchCriteria.getDepthTo())
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryStratigraphyBed(searchCriteria.getStratigraphyBed())
                .queryAgent(searchCriteria.getAgent()).andReturn()
                .queryMass(searchCriteria.getMassSince()).andReturn()
                .queryMass(searchCriteria.getMassTo())
                .queryId(searchCriteria.getId()).andReturn()
                .queryCountry(searchCriteria.getCountry())
                .queryLocation(searchCriteria.getLocation())
                .queryTaxon(searchCriteria.getTaxon())
                .queryFrequency(searchCriteria.getFrequency())
                .queryAnalysisMethod(searchCriteria.getAnalysisMethod())
                .queryComponet(searchCriteria.getComponent())
                .queryContent(searchCriteria.getContent())
                .queryInstitutions(searchCriteria.getDbs()).andReturn()
                .returnAnalyzed()
                .returnLocalityId()
                .returnStratigraphyId()
                .returnDateChanged()
                .returnLithostratigraphyId()
                .returnLocalitylatitude()
                .returnLocalitylongitude()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                SAMPLE_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(TAXON_LIST_TABLE)
                .relatedData(ANALYSIS_TABLE)
                .relatedData(ATTACHMENT_LINK)
                .relatedData(ANALYSIS_RESULTS)
                .relatedData(PREPARATION)
                .relatedData(DATASET_ANALYSIS)
                .relatedData(SAMPLE_REFERENCE)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.searchRawEntities(SAMPLE_TABLE, requestParams);
    }

    @Override
    public ApiResponse findSpecimens(SearchField sampleId) {
        String requestParams = FluentSampleSearchApiBuilder.aRequest()
                .querySampleIdForUrl(sampleId)
                .returnId()
                .returnSpecimenId()
                .returnClassification()
                .returnTaxonName()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_TABLE, 100, 1, new SortField(), requestParams);
    }
}

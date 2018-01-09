package ee.ttu.geocollection.interop.api.analyses.search.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.analyses.pojo.AnalysesSearchCriteria;
import ee.ttu.geocollection.interop.api.analyses.search.AnalysesApiService;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentAnalysesApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AnalysesApiServiceImpl implements AnalysesApiService {

    public static final String ANALYSIS_RESULTS = "analysis_results";

    private List<String> fields = Arrays.asList(
            "id",
            "analysis_method__analysis_method",
            "analysis_method__method_en",
            "method_details",
            "lab",
            "instrument",
            "instrument_txt",
            "sample",
            "date",
            "date_free",
            "sample__locality__locality",
            "sample__locality__locality_en",
            "sample__locality_id",
            "sample__locality__depth",
            "sample__stratigraphy__stratigraphy",
            "sample__stratigraphy__stratigraphy_en",
            "sample__stratigraphy_id",
            "sample__lithostratigraphy__stratigraphy",
            "sample__lithostratigraphy__stratigraphy_en",
            "sample__lithostratigraphy_id",
            "mass",
            "lab_analysis_number",
            "material",
            "agent__agent",
            "agent__agent",
            "sample__number",
            "sample__parent_sample",
            "lab_txt",
            "sample__locality_free",
            "sample__depth",
            "sample__depth_interval",
            "sample__stratigraphy_bed",
            "sample__stratigraphy_free",
            "date_added",
            "date_changed",
            "database__name",
            "database__name_en"
            );
    @Override
    public ApiResponse findAnalyses(AnalysesSearchCriteria searchCriteria) {
        String requestParams = FluentAnalysesApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .querySample(searchCriteria.getSample()).andReturn()
                .queryStratigraphyBed(searchCriteria.getStratigraphyBed()).andReturn()
                .queryDepth(searchCriteria.getDepthSince()).andReturn()
                .queryDepth(searchCriteria.getDepthTo())
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryAdminUnit(searchCriteria.getAdminUnit())
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryAnalysisMethod(searchCriteria.getAnalysisMethod()).andReturn()
                .queryMethodDetails(searchCriteria.getMethodDetails())
                .queryLab(searchCriteria.getLab()).andReturn()
                .queryInstrument(searchCriteria.getInstrument())
                .queryInstrumentTxt(searchCriteria.getInstrumentTxt())
                .queryDate(searchCriteria.getDate())
                .queryDateFree(searchCriteria.getDateFree())
                .queryInstitutions(searchCriteria.getDbs())
                .returnStratigraphyId()
                .returnLithostratigraphyId()
                .returnLocalityId()
                .returnLithostratigraphy()
                .returnLithostratigraphyEn()
                .returnSampleNumber()
                .buildFullQuery();
        return apiService.searchRawEntities("analysis", searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    @Autowired
    private ApiService apiService;

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(ANALYSIS_RESULTS)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity("analysis", requestParams);
    }
}
package ee.ttu.geocollection.interop.api.preparations.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentPreparationSearchApiBuilder;
import ee.ttu.geocollection.interop.api.preparations.PreparationsApiService;
import ee.ttu.geocollection.interop.api.preparations.pojo.PreparationsSearchCriteria;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PreparationsApiServiceImpl implements PreparationsApiService{

    private static final String PREPARATION_TABLE = "preparation";
    private static final String PREPARATION_TAXA = "preparation_taxa";

    private List<String> fields = Arrays.asList(
            "id",
            "sample_id",
            "preparation_number",
            "sample__locality__locality",
            "sample__locality__locality_en",
            "sample__locality__id",
            "sample__depth",
            "sample__depth_interval",
            "sample__agent_collected__agent",
            "sample__agent_collected__forename",
            "sample__agent_collected__surename",
            "sample__agent_collected_free",
            "sample__stratigraphy__stratigraphy",
            "sample__stratigraphy__stratigraphy_en",
            "sample__stratigraphy__id",
            "sample__stratigraphy_free",
            "sample__lithostratigraphy__stratigraphy",
            "sample__lithostratigraphy__stratigraphy_en",
            "sample__lithostratigraphy_id",
            "sample_palaeontology__method__value",
            "sample_palaeontology__method__value_en",
            "sample_palaeontology__method_details",
            "sample_palaeontology__mass",
            "sample_palaeontology__date_start",
            "sample_palaeontology__date_end",
            "sample_palaeontology__date_free",
            "sample_palaeontology__agent__agent",
            "sample_palaeontology__location",
            "sample_palaeontology__remarks",
            "identification_agent__agent",
            "identification_date",
            "identification_remarks",
            "remarks",
            "classification__class_field",
            "classification__class_en",
            "location",
            "storage__location",
            "storage__location_location",
            "date_added",
            "date_changed",
            "user_added",
            "user_changed"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findPreparations(PreparationsSearchCriteria searchCriteria)  {
        String requestParams = FluentPreparationSearchApiBuilder.aRequest()
                .queryNumber(searchCriteria.getNumber())
                .queryLocality(searchCriteria.getLocality())
                .queryDepth(searchCriteria.getDepthSince())
                .queryDepth(searchCriteria.getDepthTo())
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryCollector(searchCriteria.getCollector())
                .querySpeciesRecovered(searchCriteria.getSpeciesRecovered())
                .querySpeciesFrequency(searchCriteria.getSpeciesFrequency())
                .returnSampleLocalityId()
                .returnSampleStratigraphyId()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                PREPARATION_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(PREPARATION_TAXA)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(PREPARATION_TABLE, requestParams);
    }
}
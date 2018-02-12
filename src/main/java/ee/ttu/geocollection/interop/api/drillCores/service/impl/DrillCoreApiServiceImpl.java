package ee.ttu.geocollection.interop.api.drillCores.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentDrillCoreSearchApiBuilder;
import ee.ttu.geocollection.interop.api.drillCores.pojo.DrillCoreSearchCriteria;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DrillCoreApiServiceImpl implements DrillCoreApiService {

    private static final String DRILLCORE = "drillcore";
    private static final String DRILLCORE_BOX = "drillcore_box";

    @Autowired
    private ApiService apiService;

    private List<String> fields = Arrays.asList(
        "id",
        "drillcore",
        "locality__country__value",
        "locality__country__value_en",
        "locality__latitude",
        "locality__longitude",
        "depth",
        "boxes",
        "box_numbers",
        "storage__location",
        "locality_id",
        "drillcore_en",
        "depository__value",
        "depository__value_en",
        "number_meters",
        "year",
        "remarks",
        "agent__agent",
        "database__acronym",
        "locality__maaamet_pa_id",
        "locality__locality",
        "locality__locality_en"
    );

    @Override
    public ApiResponse findDrillCore(DrillCoreSearchCriteria searchCriteria)  {
        String requestParams = FluentDrillCoreSearchApiBuilder.aRequest()
                .queryDrillCore(searchCriteria.getDrillcore())
                .queryLocalityCountry(searchCriteria.getCountry())
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryStorageLocation(searchCriteria.getStorageLocation())
                .queryBoxes(searchCriteria.getBoxesSince())
                .queryBoxes(searchCriteria.getBoxesTo())
                .queryId(searchCriteria.getId())
                .queryLocalityAdminUnit(searchCriteria.getAdminUnit())
                .queryInstitutions(searchCriteria.getDbs())
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                DRILLCORE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(DRILLCORE_BOX)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(DRILLCORE, requestParams);
    }

    @Override
    public Map findRawCoreBoxById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity(DRILLCORE_BOX, requestParams);
    }
}
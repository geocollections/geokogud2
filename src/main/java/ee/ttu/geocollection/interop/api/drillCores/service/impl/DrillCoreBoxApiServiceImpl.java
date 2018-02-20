package ee.ttu.geocollection.interop.api.drillCores.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentCoreBoxApiBuilder;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreBoxApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrillCoreBoxApiServiceImpl implements DrillCoreBoxApiService {

    private static final String DRILLCORE_BOX_TABLE = "drillcore_box";
    private static final String SAMPLE_TABLE = "sample";
    private static final String LOCALITY_STRATIGRAPHY_TABLE = "locality_stratigraphy";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawCoreBoxById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(DRILLCORE_BOX_TABLE, requestParams);
    }

    @Override
    public ApiResponse findSamples(SearchField localityId, SearchField depthStart, SearchField depthEnd) {
        String requestParams = FluentCoreBoxApiBuilder.aRequest()
                .queryLocalityId(localityId)
                .queryDepth(depthStart)
                .queryDepth(depthEnd)
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(SAMPLE_TABLE, 100, 1, new SortField("depth", SortingOrder.ASCENDING), requestParams);
    }

    @Override
    public ApiResponse findStratigraphicBoundaries(SearchField localityId, SearchField depthStart, SearchField depthEnd) {
        String requestParams = FluentCoreBoxApiBuilder.aRequest()
                .queryLocalityId(localityId)
                .queryDepthBase(depthStart)
                .queryDepthBase(depthEnd)
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(LOCALITY_STRATIGRAPHY_TABLE, 10, 1, new SortField(), requestParams);
    }
}

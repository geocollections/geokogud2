package ee.ttu.geocollection.interop.api.drillCores.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface DrillCoreBoxApiService {

    ApiResponse findRawCoreBoxById(Long id);

    ApiResponse findSamples(SearchField localityId, SearchField depthStart, SearchField depthEnd);

    ApiResponse findStratigraphicBoundaries(SearchField localityId, SearchField depthStart, SearchField depthEnd);

}

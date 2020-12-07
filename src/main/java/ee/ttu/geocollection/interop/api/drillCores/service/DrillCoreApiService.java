package ee.ttu.geocollection.interop.api.drillCores.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.drillCores.pojo.DrillCoreSearchCriteria;

public interface DrillCoreApiService {

    ApiResponse findDrillCore(DrillCoreSearchCriteria searchCriteria);

    ApiResponse findAttachmentLinks(SearchField drillcoreId);

    ApiResponse findRawById(Long id);

}
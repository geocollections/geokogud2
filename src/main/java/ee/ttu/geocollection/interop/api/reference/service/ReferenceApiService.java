package ee.ttu.geocollection.interop.api.reference.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.reference.pojo.ReferenceSearchCriteria;

import java.util.List;
import java.util.Map;

public interface ReferenceApiService {
    ApiResponse findReference(ReferenceSearchCriteria searchCriteria) ;

    Map findRawById(Long id);

    ApiResponse findImagesByIds(List<String> ids);
}
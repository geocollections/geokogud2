package ee.ttu.geocollection.interop.api.dataset.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface DatasetApiService {

    ApiResponse findRawById(Long id);

}

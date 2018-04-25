package ee.ttu.geocollection.interop.api.collection.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface CollectionApiService {

    ApiResponse findRawById(Long id);

}

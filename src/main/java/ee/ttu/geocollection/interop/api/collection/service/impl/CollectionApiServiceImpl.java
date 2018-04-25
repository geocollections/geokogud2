package ee.ttu.geocollection.interop.api.collection.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.collection.service.CollectionApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionApiServiceImpl implements CollectionApiService {

    private static final String COLLECITON_TABLE = "collection";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(COLLECITON_TABLE, requestParams);
    }


}

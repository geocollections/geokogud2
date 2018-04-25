package ee.ttu.geocollection.interop.api.dataset.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.dataset.service.DatasetApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetApiServiceImpl implements DatasetApiService {

    private static final String DATASET_TABLE = "dataset";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(DATASET_TABLE, requestParams);
    }

}

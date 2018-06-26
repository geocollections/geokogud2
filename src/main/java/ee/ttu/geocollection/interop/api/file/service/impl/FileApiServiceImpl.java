package ee.ttu.geocollection.interop.api.file.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.file.pojo.FileSearchCriteria;
import ee.ttu.geocollection.interop.api.file.service.FileApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileApiServiceImpl implements FileApiService {

    private static final String FILE_TABLE = "attachment";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findFile(FileSearchCriteria searchCriteria) {
        return null;
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(FILE_TABLE, requestParams);
    }
}

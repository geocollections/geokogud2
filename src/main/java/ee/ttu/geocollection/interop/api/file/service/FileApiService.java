package ee.ttu.geocollection.interop.api.file.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.file.pojo.FileSearchCriteria;

public interface FileApiService {

    ApiResponse findFile(FileSearchCriteria searchCriteria);

    ApiResponse findRawById(Long id);

}

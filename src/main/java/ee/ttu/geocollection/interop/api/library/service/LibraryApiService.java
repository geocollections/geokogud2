package ee.ttu.geocollection.interop.api.library.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.file.pojo.FileSearchCriteria;

public interface LibraryApiService {

    ApiResponse findFile(FileSearchCriteria searchCriteria);

    ApiResponse findRawById(Long id);
}

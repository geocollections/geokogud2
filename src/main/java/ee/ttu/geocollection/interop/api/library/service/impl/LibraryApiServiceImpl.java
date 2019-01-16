package ee.ttu.geocollection.interop.api.library.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentLibraryApiBuilder;
import ee.ttu.geocollection.interop.api.file.pojo.FileSearchCriteria;
import ee.ttu.geocollection.interop.api.library.service.LibraryApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LibraryApiServiceImpl implements LibraryApiService {

    private static final String LIBRARY_TABLE = "library";
    private static final String LIBRARY_REFERENCE_TABLE = "library_reference";

    private List<String> fields = Arrays.asList(
            "id",
            "author_txt",
            "year",
            "title",
            "title_en",
            "abstract",
            "abstract_en",
            "author",
            "keywords",
            "remarks",
            "date_added",
            "date_changed"
    );

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
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
//                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(LIBRARY_TABLE, requestParams);
    }

    @Override
    public ApiResponse findLibraryReferenceById(Long id) {
        String requestParams = FluentLibraryApiBuilder.aRequest()
                .queryLibrary(id)
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(LIBRARY_REFERENCE_TABLE, requestParams);
    }
}

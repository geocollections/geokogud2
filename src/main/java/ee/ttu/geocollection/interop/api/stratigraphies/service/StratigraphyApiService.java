package ee.ttu.geocollection.interop.api.stratigraphies.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.stratigraphies.pojo.StratigraphySearchCriteria;

import java.util.List;
import java.util.Map;

public interface StratigraphyApiService {

    ApiResponse findStratigraphy(StratigraphySearchCriteria searchCriteria) ;

    ApiResponse findRawById(Long id);

    ApiResponse findAllLithostratigraphies(SearchField ageChronoId);

    ApiResponse findOverliesStratigraphy(SearchField ageBase, SearchField parentId);

    ApiResponse findOverlainByStratigraphy(SearchField ageTop, SearchField parentId);

}

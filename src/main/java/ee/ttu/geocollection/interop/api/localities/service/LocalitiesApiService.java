package ee.ttu.geocollection.interop.api.localities.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.localities.pojo.LocalitySearchCriteria;


public interface LocalitiesApiService {

    ApiResponse findLocality(LocalitySearchCriteria searchCriteria) ;

    ApiResponse findLocalityImage(SearchField localityId);

    ApiResponse findLocalityImages(LocalitySearchCriteria searchCriteria);

    ApiResponse findRawById(Long id);

    ApiResponse findAllSpecimens(SearchField id);

}

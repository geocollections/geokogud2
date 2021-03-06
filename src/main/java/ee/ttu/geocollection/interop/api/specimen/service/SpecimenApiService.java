package ee.ttu.geocollection.interop.api.specimen.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;

import java.util.Map;

public interface SpecimenApiService {

    ApiResponse findSpecimen(SpecimenSearchCriteria searchCriteria) ;

    ApiResponse findSpecimenImages(SpecimenSearchCriteria searchCriteria);

    ApiResponse findSpecimenImage(SearchField specimenId) ;

    ApiResponse findSpecimenIdentification(SearchField specimenId) ;

    ApiResponse findSpecimenIdentificationGeologies(SearchField specimenId) ;

    Map findRawById(Long id);

}

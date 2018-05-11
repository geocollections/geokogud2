package ee.ttu.geocollection.interop.api.specimen.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface SpecimenImageApiService {

    ApiResponse findRawSpecimenImageById(Long id);

    ApiResponse findSpecimenIdentification(SearchField specimenId);

}

package ee.ttu.geocollection.interop.api.samples.service;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.samples.pojo.SampleSearchCriteria;

import java.util.Map;

public interface SamplesApiService {

    ApiResponse findSample(SampleSearchCriteria searchCriteria);

    ApiResponse findSpecimens(SearchField sampleId);

    ApiResponse findRawById(Long id);

}

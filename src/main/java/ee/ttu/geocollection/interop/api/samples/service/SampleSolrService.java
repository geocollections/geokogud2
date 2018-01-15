package ee.ttu.geocollection.interop.api.samples.service;

import ee.ttu.geocollection.interop.api.Response.SolrResponse;

public interface SampleSolrService {

    SolrResponse findSampleByIndex(String query);

}

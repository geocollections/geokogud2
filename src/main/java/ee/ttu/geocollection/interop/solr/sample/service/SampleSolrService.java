package ee.ttu.geocollection.interop.solr.sample.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface SampleSolrService {

    SolrResponse findSampleByIndex(String query);

}


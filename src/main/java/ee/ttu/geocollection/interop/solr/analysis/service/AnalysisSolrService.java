package ee.ttu.geocollection.interop.solr.analysis.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface AnalysisSolrService {

    SolrResponse findAnalysisByIndex(String query);

}

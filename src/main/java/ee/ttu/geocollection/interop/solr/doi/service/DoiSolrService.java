package ee.ttu.geocollection.interop.solr.doi.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface DoiSolrService {

    SolrResponse findDoiByIndex(String query);

}

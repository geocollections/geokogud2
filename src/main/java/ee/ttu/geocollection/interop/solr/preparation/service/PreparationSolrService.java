package ee.ttu.geocollection.interop.solr.preparation.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface PreparationSolrService {

    SolrResponse findPreparationByIndex(String query);

}

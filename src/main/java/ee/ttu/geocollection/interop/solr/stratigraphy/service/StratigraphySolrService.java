package ee.ttu.geocollection.interop.solr.stratigraphy.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface StratigraphySolrService {

    SolrResponse findStratigraphyByIndex(String query);

}

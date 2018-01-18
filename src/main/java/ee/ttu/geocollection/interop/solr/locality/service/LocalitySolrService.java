package ee.ttu.geocollection.interop.solr.locality.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface LocalitySolrService {

    SolrResponse findLocalityByIndex(String query);

}

package ee.ttu.geocollection.interop.solr.reference.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface ReferenceSolrService {

    SolrResponse findReferenceByIndex(String query);

}

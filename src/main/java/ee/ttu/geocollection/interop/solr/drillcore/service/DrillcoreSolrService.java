package ee.ttu.geocollection.interop.solr.drillcore.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface DrillcoreSolrService {

    SolrResponse findDrillcoreByIndex(String query);

}

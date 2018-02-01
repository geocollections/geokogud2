package ee.ttu.geocollection.interop.solr.doi.service;

import ee.ttu.geocollection.interop.api.doi.pojo.DoiSearchCriteria;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface DoiSolrService {

    SolrResponse findDoi(DoiSearchCriteria searchCriteria);

}

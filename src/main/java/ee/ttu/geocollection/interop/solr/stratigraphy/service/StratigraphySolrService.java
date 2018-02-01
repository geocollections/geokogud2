package ee.ttu.geocollection.interop.solr.stratigraphy.service;

import ee.ttu.geocollection.interop.api.stratigraphies.pojo.StratigraphySearchCriteria;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface StratigraphySolrService {

    SolrResponse findStratigraphy(StratigraphySearchCriteria searchCriteria);

}

package ee.ttu.geocollection.interop.solr.map.service;

import ee.ttu.geocollection.interop.solr.map.pojo.MapFilter;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface MapSolrService {

    SolrResponse findAllLocalities();

    SolrResponse findAllLocalitiesUsingFilter(MapFilter filters);

}

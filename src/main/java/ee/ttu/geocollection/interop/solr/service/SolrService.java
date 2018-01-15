package ee.ttu.geocollection.interop.solr.service;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;

import java.util.Map;

public interface SolrService {

    Map findRawEntity(String tableName, String requestParams);

    SolrResponse searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams);

}

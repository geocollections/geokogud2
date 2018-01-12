package ee.ttu.geocollection.interop.api.service;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.SolrResponse;

import java.util.Map;

public interface SolrService {

    Map findRawEntity(String tableName, String requestParams);

    Map searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams);

}

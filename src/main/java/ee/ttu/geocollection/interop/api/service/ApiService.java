package ee.ttu.geocollection.interop.api.service;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;

import java.util.Map;

public interface ApiService {
    Map findRawEntity(String tableName, String requestParams);

    ApiResponse searchRawEntities(String tableName, int page, SortField sortField, String requestParams) ;

    ApiResponse searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams) ;

    Map searchByField(String table, String term, String searchField);
}

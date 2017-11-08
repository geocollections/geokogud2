package ee.ttu.geocollection.interop.api.service;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;

import java.util.Map;

public interface ApiService {
    /**
     *
     * @param tableName
     * @param requestParams
     * @return
     */
    Map findRawEntity(String tableName, String requestParams);

    /**
     * Calls searchRawEntities() with extra paginateBy parameter set to 30
     * @param tableName to specify table name /tableName/
     * @param page to specify page ?page=$page
     * @param sortField to order results by a field: ?order_by=$field (ascending) or ?order_by=-$field (descending)
     * @param requestParams extra parameters given by certain request
     * @return returns searchRawEntities() with extra paginateBy parameter set to 30
     */
    ApiResponse searchRawEntities(String tableName, int page, SortField sortField, String requestParams) ;

    /**
     * Calls GET request and puts api response into table with same table name.
     * If request fails then exception is thrown.
     * @param tableName to specify table name /tableName/
     * @param paginateBy to specify the number of records per page: ?paginate_by=$paginate_by
     * @param page to specify page ?page=$page
     * @param sortField to order results by a field: ?order_by=$field (ascending) or ?order_by=-$field (descending)
     * @param requestParams extra parameters given by certain request
     * @return returns response from api
     */
    ApiResponse searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams) ;

    /**
     *
     * @param table
     * @param term
     * @param searchField
     * @return
     */
    Map searchByField(String table, String term, String searchField);
}

package ee.ttu.geocollection.interop.api.webNews.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface WebNewsApiService {

    /**
     * Calls GET request from API's /webnews/ table.
     * @return returns response from request
     */
    ApiResponse getNews();
}
package ee.ttu.geocollection.interop.api.webPages.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface WebPagesApiService {

	/**
	 * Calls GET request from API's /webpages/ table
	 * on certain id.
	 * @param id Identificator which equals to id value
	 *           in response's results array.
	 * @return Returns the API's response.
	 */
	ApiResponse getWebPages(int id) ;
}
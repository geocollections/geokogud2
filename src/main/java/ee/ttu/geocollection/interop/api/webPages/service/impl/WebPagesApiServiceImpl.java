package ee.ttu.geocollection.interop.api.webPages.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.webPages.service.WebPagesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebPagesApiServiceImpl implements WebPagesApiService {

    @Autowired
    private ApiService apiService;

    /**
     * Sorting order is set to ASCENDING,
     * table on which data is being searched is webpages
     * and search is done on the first page. First page
     * does not contain all the data but that does not matter
     * because search is done by correspondent id.
     * @param id Identificator which equals to id value
     *           in response's results array.
     * @return API's response
     */
    @Override
    public ApiResponse getWebPages(int id)  {
        SortField sortField = new SortField();
        sortField.setOrder(SortingOrder.ASCENDING);
        String params = "&id=" + id;
        return apiService.searchRawEntities("webpages", 1, sortField, params);
    }
}

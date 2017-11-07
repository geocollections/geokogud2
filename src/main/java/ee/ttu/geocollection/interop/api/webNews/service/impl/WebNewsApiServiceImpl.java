package ee.ttu.geocollection.interop.api.webNews.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.ApiFields;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.webNews.service.WebNewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class WebNewsApiServiceImpl implements WebNewsApiService {


    @Autowired
    private ApiService apiService;

    /**
     *
     * @return returns response from api request
     */
    @Override
    public ApiResponse getNews() {
        SortField sortField = new SortField();
        sortField.setOrder(SortingOrder.DESCENDING);
        return apiService.searchRawEntities("webnews", 1, sortField, buildParameters());
    }

    /**
     * Constructs parameter for api
     * @return String example "&date_added__lt=2017-12-31"
     */
    private String buildParameters() {
        return "&" + ApiFields.DATE_ADDED_LT + "=" + Calendar.getInstance().get(Calendar.YEAR) + "-12-31";
    }
}

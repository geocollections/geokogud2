package ee.ttu.geocollection.interop.api.service.impl;

import com.google.common.net.UrlEscapers;
import ee.ttu.geocollection.domain.AppError;
import ee.ttu.geocollection.domain.AppException;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    /**
     * Value is got from resources/application.properties file
     */
    @Value("${geo-api.url}")
    private String apiUrl;

    /**
     * to specify the number of records per page: ?paginate_by=$paginate_by
     * Pagination is currently set to 30
     */
    private static final int PAGINATE_BY = 30;

    /**
     * Creates a new instance of the RestTemplate
     */
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map findRawEntity(String tableName, String requestParams) {
        String url;
        if (tableName.equals("locality_summary")) {
            url = apiUrl + "/" + tableName + "/" + requestParams;
        } else  if (tableName.equals("drillcore_box") || tableName.equals("specimen_image")) {
            url = apiUrl + "/" + tableName + "/" + requestParams + "?format=json";

        } else {
            url = apiUrl + "/" + tableName + "/" + requestParams + "&format=json";
        }

        HttpHeaders headers = new HttpHeaders();
        String requestId = MDC.get("REQUEST_UUID");
        if (requestId != null) {
            headers.set("Trace-UUID", requestId);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        logger.trace(url);
        HttpEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    @Override
    public ApiResponse searchRawEntities(String tableName, String requestParams) {
        String url;
        System.out.println(requestParams);

//        TODO: Maybe instead add fields array to those tables
        if (tableName.equals("collection") || tableName.equals("specimen_image")) {
            url = apiUrl + "/" + tableName + "/" + requestParams + "?format=json";
        } else {
            url = apiUrl + "/" + tableName + "/" + requestParams + "&format=json";
        }

        logger.trace("Searching: " + url);

        return getResponse(tableName, url);
    }

    @Override
    public ApiResponse searchRawEntities(String tableName, int page, SortField sortField, String requestParams) {
        return searchRawEntities(tableName, PAGINATE_BY, page, sortField, requestParams);
    }

    @Override
    public ApiResponse searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams) {
        String url = apiUrl
                + "/" + tableName + "/"
                + "?paginate_by=" + paginateBy + "&page=" + page
                + "&order_by=" + getSortingDirection(sortField.getOrder()) + sortField.getSortBy()
                + "&format=json"
                + escapeParameters(requestParams);

        logger.trace("Searching: " + url);

        return getResponse(tableName, url);
    }

    @Override
    public Map searchByField(String table, String term, String sortField, String searchField) {
        String url;
        if (searchField.contains(",")) { // Multiple fields autocomplete, should use & instead of multi_search
            url = apiUrl + "/" + table + "/" + "?paginate_by=" + PAGINATE_BY
                    + "&format=json&distinct=true&fields="
                    + searchField
                    + "&multi_search=value:"+term+";fields:"+searchField+";lookuptype:" + sortField;
        } else { // Single field autocomplete
            url = apiUrl + "/" + table + "/" + "?paginate_by=" + PAGINATE_BY
                + "&format=json&distinct=true&fields=" + searchField + "&" + searchField + "__" + sortField + "=" + term;
        }

        logger.trace(url);
        HttpHeaders headers = new HttpHeaders();
        String requestId = MDC.get("REQUEST_UUID");
        if (requestId != null) {
            headers.set("Trace-UUID", requestId);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        System.err.println(url);
        HttpEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    /**
     * Adds nothing or "-" sign for api to understand if ordering is ascending or descending
     * @param order which is either ASCENDING or DESCENDING
     * @return returns "" or "-" dependent on order
     */
    private String getSortingDirection(SortingOrder order) {
        return order.equals(SortingOrder.ASCENDING) ? "" : "-";
    }

    /**
     * Returns the escaped form of a given string
     * @param parameters String to be escaped
     * @return returns the escaped form of String
     */
    private String escapeParameters(String parameters) {
        return UrlEscapers.urlPathSegmentEscaper().escape(parameters);
    }

    private ApiResponse getResponse(String tableName, String url) {
        try {
            /*
             * Retrieves a representation by doing GET on the URL.
             * The response (if any) is converted and returned.
             * First parameter: url.
             * Second parameter: the type of the return value.
             * Returns: the converted object
             */
            ApiResponse response = restTemplate.getForObject(new URI(url), ApiResponse.class);

            if (response != null) {
                response.setTable(tableName);
            }
            return response;

        } catch (HttpMessageNotReadableException e) {
            throw new AppException(AppError.BAD_REQUEST, e);
        } catch (HttpServerErrorException e) {
            throw new AppException(AppError.ERROR_API_UNAVAILABLE, e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
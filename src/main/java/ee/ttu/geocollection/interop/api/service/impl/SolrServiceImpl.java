package ee.ttu.geocollection.interop.api.service.impl;

import com.google.common.net.UrlEscapers;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.SolrResponse;
import ee.ttu.geocollection.interop.api.service.SolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class SolrServiceImpl implements SolrService {

    private static final int PAGINATE_BY = 30;
    private static final Logger logger = LoggerFactory.getLogger(SolrServiceImpl.class);

    @Value("${solr.host}")
    private String solrUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map findRawEntity(String tableName, String requestParams) {
//        String url = solrUrl + "/" + tableName + "/select?q=id:" + requestParams;
        String url = solrUrl + "/" + tableName + "/select?rows=100&q=" + requestParams;

        HttpHeaders headers = new HttpHeaders();
        String requestId = MDC.get("REQUEST_UUID");
        if (requestId != null) {
            headers.set("Trace_UUID", requestId);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        logger.trace(url);
        HttpEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }

    @Override
    public Map searchRawEntities(String tableName, int paginateBy, int page, SortField sortField, String requestParams) {
        String url = solrUrl + "/" + tableName + "/select?q=*&rows=" + paginateBy + "&sort=date_added desc";

        logger.trace("Searching: " + url);

        HttpHeaders headers = new HttpHeaders();
        String requestId = MDC.get("REQUEST_UUID");
        if (requestId != null) {
            headers.set("Trace_UUID", requestId);
        }
        HttpEntity<String> request = new HttpEntity<>(headers);
        HttpEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        return response.getBody();
    }
}

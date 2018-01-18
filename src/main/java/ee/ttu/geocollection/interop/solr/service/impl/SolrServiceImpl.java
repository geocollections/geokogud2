package ee.ttu.geocollection.interop.solr.service.impl;

import com.google.common.net.UrlEscapers;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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
    public SolrResponse searchRawEntities(String tableName, String requestParams) {
        String url = "";
        url = solrUrl + "/" + tableName + "/select" + requestParams + "&defType=edismax";

        logger.trace("Searching: " + url);

        try {

            SolrResponse response = restTemplate.getForObject(new URI(url), SolrResponse.class);

            if (response != null) {
                response.setTable(tableName);
            }

            return response;

        } catch (URISyntaxException|SolrException|HttpClientErrorException|HttpServerErrorException|HttpMessageNotReadableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the escaped form of a given string
     * @param parameters String to be escaped
     * @return returns the escaped form of String
     */
    public String escapeParameters(String parameters) {
        return UrlEscapers.urlPathSegmentEscaper().escape(parameters);
    }
}

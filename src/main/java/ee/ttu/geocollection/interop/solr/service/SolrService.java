package ee.ttu.geocollection.interop.solr.service;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.Map;

public interface SolrService {

    Map findRawEntity(String tableName, String requestParams);

    SolrResponse searchRawEntities(String tableName, SolrQuery requestParams);

}

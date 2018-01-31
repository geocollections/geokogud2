package ee.ttu.geocollection.interop.solr.global.service.impl;

import ee.ttu.geocollection.interop.solr.global.service.GlobalSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalSolrServiceImpl implements GlobalSolrService {

    @Autowired
    SolrService solrService;

    @Override
    public SolrResponse findEntitiesUsingTable(String table, String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(table, requestParams);
    }

    @Override
    public SolrResponse findEntitiesUsingTablePageAndPaginateBy(String table, int page, int paginateBy, String query) {
                SolrQuery requestParams = new SolrQuery(query)
                .setRows(paginateBy)
                .setStart(page);
        return solrService.searchRawEntities(table, requestParams);
    }
}

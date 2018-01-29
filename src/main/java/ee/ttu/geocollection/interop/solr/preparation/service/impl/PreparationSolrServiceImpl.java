package ee.ttu.geocollection.interop.solr.preparation.service.impl;

import ee.ttu.geocollection.interop.solr.preparation.service.PreparationSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreparationSolrServiceImpl implements PreparationSolrService {

    private static final String PREPARATION_TABLE = "preparation";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findPreparationByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(PREPARATION_TABLE, requestParams);
    }
}

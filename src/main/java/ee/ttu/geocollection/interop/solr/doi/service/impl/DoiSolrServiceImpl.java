package ee.ttu.geocollection.interop.solr.doi.service.impl;

import ee.ttu.geocollection.interop.solr.doi.service.DoiSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoiSolrServiceImpl implements DoiSolrService {

    private static final String DOI_TABLE = "doi";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findDoiByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(DOI_TABLE, requestParams);
    }
}

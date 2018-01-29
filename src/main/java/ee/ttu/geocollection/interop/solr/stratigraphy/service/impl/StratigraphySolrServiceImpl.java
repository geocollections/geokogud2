package ee.ttu.geocollection.interop.solr.stratigraphy.service.impl;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import ee.ttu.geocollection.interop.solr.stratigraphy.service.StratigraphySolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StratigraphySolrServiceImpl implements StratigraphySolrService {

    private static final String STRATIGRAPHY_TABLE = "stratigraphy";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findStratigraphyByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(STRATIGRAPHY_TABLE, requestParams);
    }
}

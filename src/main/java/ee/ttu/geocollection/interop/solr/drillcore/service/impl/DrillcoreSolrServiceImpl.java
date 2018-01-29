package ee.ttu.geocollection.interop.solr.drillcore.service.impl;

import ee.ttu.geocollection.interop.solr.drillcore.service.DrillcoreSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrillcoreSolrServiceImpl implements DrillcoreSolrService {

    private static final String DRILLCORE_TABLE = "drillcore";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findDrillcoreByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(DRILLCORE_TABLE, requestParams);
    }
}

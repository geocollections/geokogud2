package ee.ttu.geocollection.interop.solr.analysis.service.impl;

import ee.ttu.geocollection.interop.solr.analysis.service.AnalysisSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisSolrServiceImpl implements AnalysisSolrService {

    private static final String ANALYSIS_TABLE = "analysis";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findAnalysisByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(ANALYSIS_TABLE, requestParams);
    }
}

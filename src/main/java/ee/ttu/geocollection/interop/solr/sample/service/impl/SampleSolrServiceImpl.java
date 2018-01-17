package ee.ttu.geocollection.interop.solr.sample.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.sample.service.SampleSolrService;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleSolrServiceImpl implements SampleSolrService {

    private final static String SAMPLE_TABLE = "sample";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findSampleByIndex(String query) {

        return solrService.searchRawEntities(SAMPLE_TABLE, 100, 1, new SortField(), query);
    }

}




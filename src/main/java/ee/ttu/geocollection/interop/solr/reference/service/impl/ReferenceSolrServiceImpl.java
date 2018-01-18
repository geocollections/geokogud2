package ee.ttu.geocollection.interop.solr.reference.service.impl;

import ee.ttu.geocollection.interop.solr.reference.service.ReferenceSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferenceSolrServiceImpl implements ReferenceSolrService {

    private final static String REFERENCE_TABLE = "reference";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findReferenceByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(REFERENCE_TABLE, requestParams.toQueryString());
    }

}

package ee.ttu.geocollection.interop.solr.locality.service.impl;

import ee.ttu.geocollection.interop.solr.locality.service.LocalitySolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalitySolrServiceImpl implements LocalitySolrService {

    private final static String LOCALITY_TABLE = "locality";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findLocalityByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(LOCALITY_TABLE, requestParams.toQueryString());
    }

}

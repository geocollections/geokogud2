package ee.ttu.geocollection.interop.solr.taxon.service.impl;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import ee.ttu.geocollection.interop.solr.taxon.service.TaxonSolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxonSolrServiceImpl implements TaxonSolrService {

    private final static String TAXON_TABLE = "taxon";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findTaxonByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(TAXON_TABLE, requestParams.toQueryString());
    }

}

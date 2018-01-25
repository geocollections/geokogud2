package ee.ttu.geocollection.interop.solr.taxon.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface TaxonSolrService {

    SolrResponse findTaxonByIndex(String query);

}

package ee.ttu.geocollection.interop.solr.global.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface GlobalSolrService {

    SolrResponse findEntitiesUsingTablePageAndPaginateBy(String table, int page, int paginateBy, String query);

    SolrResponse findEntitiesUsingTable(String table, String query);

}

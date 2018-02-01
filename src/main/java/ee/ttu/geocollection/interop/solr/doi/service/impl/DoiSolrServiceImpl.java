package ee.ttu.geocollection.interop.solr.doi.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.doi.pojo.DoiSearchCriteria;
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
    public SolrResponse findDoi(DoiSearchCriteria searchCriteria) {
        SolrQuery requestParams = new SolrQuery()
                .setParam("identifier", setFiltering(searchCriteria.getDoi()))
                .setSort(searchCriteria.getSortField().getSortBy(), SolrQuery.ORDER.asc) //TODO: make sortField return solr ORDERS
                .setStart((searchCriteria.getPage() - 1) * searchCriteria.getPaginateBy())
                .setRows(searchCriteria.getPaginateBy());
        requestParams = new SolrQuery(requestParams.toQueryString());
        return solrService.searchRawEntities(DOI_TABLE, requestParams);
    }

    private String setFiltering(SearchField searchField) {
        String searchFieldWithFiltering = searchField.getName();
        if (searchField.getLookUpType().name().equals("icontains") || searchField.getLookUpType().name().equals("contains")) {
            searchFieldWithFiltering = "*" + searchFieldWithFiltering + "*";
        }
        if (searchField.getLookUpType().name().equals("istartswith") || searchField.getLookUpType().name().equals("startswith")) {
            searchFieldWithFiltering = searchFieldWithFiltering + "*";
        }
        if (searchField.getLookUpType().name().equals("iendswith") || searchField.getLookUpType().name().equals("endswith")) {
            searchFieldWithFiltering = "*" + searchFieldWithFiltering;
        }
        return searchFieldWithFiltering;
    }

}

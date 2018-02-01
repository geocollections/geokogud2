package ee.ttu.geocollection.interop.solr.stratigraphy.service.impl;

import ee.ttu.geocollection.interop.api.stratigraphies.pojo.StratigraphySearchCriteria;
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
    public SolrResponse findStratigraphy(StratigraphySearchCriteria searchCriteria) {
        SolrQuery requestParams = new SolrQuery("*")
                .setSort(searchCriteria.getSortField().getSortBy(), SolrQuery.ORDER.asc)
                .setStart((searchCriteria.getPage() - 1) * searchCriteria.getPaginateBy())
                .setRows(searchCriteria.getPaginateBy());
        return solrService.searchRawEntities(STRATIGRAPHY_TABLE, requestParams);
    }
}

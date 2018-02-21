package ee.ttu.geocollection.interop.solr.map.service.impl;

import ee.ttu.geocollection.interop.solr.map.pojo.MapFilter;
import ee.ttu.geocollection.interop.solr.map.service.MapSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapSolrServiceImpl implements MapSolrService {

    private static final String LOCALITY_TABLE = "locality";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findAllLocalities() {
        SolrQuery requestParams = new SolrQuery("*:*")
                .setRows(12000)
                .setFields("longitude","latitude","id","locality","locality_en","total_related_records");
        return solrService.searchRawEntities(LOCALITY_TABLE, requestParams);
    }

    @Override
    public SolrResponse findAllLocalitiesUsingFilter(MapFilter filters) {
        SolrQuery requestParams;
        if (!filters.getLocalityName().equals("")) {
            requestParams = new SolrQuery(filters.getLocalityName());
        } else {
            requestParams = new SolrQuery("*:*");
        }
        if (filters.getFilters().size() > 0) {
            for (String filter : filters.getFilters()) {
                System.out.println("Filter: " + filter);
                if (filter.equals("citing_references")) filter = "references";
                requestParams.addFilterQuery("related_" + filter + ":[1 TO *]");
            }

        }
        requestParams
                .setRows(12000)
                .setFields("longitude","latitude","id","locality","locality_en","total_related_records");
        return solrService.searchRawEntities(LOCALITY_TABLE, requestParams);
    }
}

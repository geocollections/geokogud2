package ee.ttu.geocollection.endpoint.web;

import ee.ttu.geocollection.interop.solr.map.pojo.MapFilter;
import ee.ttu.geocollection.interop.solr.map.service.MapSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapSolrService mapSolrService;

    @GetMapping(value = "/locality-summary")
    public SolrResponse findLocalitySummary() {
        return mapSolrService.findAllLocalities();
    }

    @PostMapping(value = "/locality-summary-filter")
    public SolrResponse findLocalitySummaryFilter(@RequestBody MapFilter filters) {
        return mapSolrService.findAllLocalitiesUsingFilter(filters);
    }
}


package ee.ttu.geocollection.endpoint.web;

import ee.ttu.geocollection.interop.api.map.pojo.MapFilter;
import ee.ttu.geocollection.interop.api.map.service.MapApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/map")
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    @Autowired
    private MapApiService mapApiService;

    @GetMapping(value = "/locality-summary")
    public Map findLocalitySummary() {
        return mapApiService.findLocalitiesSummary();
    }

    @PostMapping(value = "/locality-summary-filter")
    public Map findLocalitySummaryFilter(@RequestBody MapFilter filters) {
        return mapApiService.findLocalitiesSummaryFilter(filters);
    }
}


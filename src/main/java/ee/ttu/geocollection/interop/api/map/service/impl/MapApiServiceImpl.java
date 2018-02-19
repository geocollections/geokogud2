package ee.ttu.geocollection.interop.api.map.service.impl;

import ee.ttu.geocollection.interop.api.map.pojo.MapFilter;
import ee.ttu.geocollection.interop.api.map.service.MapApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapApiServiceImpl implements MapApiService {

    private static final String LOCALITY_SUMMARY = "locality_summary";

    @Autowired
    private ApiService apiService;

    @Override
    public Map findLocalitiesSummary() {
        return apiService.findRawEntity(LOCALITY_SUMMARY, "?format=json");
    }

    @Override
    public Map findLocalitiesSummaryFilter(MapFilter filters) {
        StringBuilder requestParams = new StringBuilder("?");
        if (filters.getFilters().size() > 0) {
            for (String filter: filters.getFilters()) {
                requestParams.append(filter).append("__gt=0&");
            }
        }
        if (!filters.getLocalityName().equals("")) {
            requestParams.append("multi_search=value:").append(filters.getLocalityName()).append(";fields:name,name_en;lookuptype:icontains&");
        }
        requestParams.append("format=json");
        return apiService.findRawEntity(LOCALITY_SUMMARY, requestParams.toString());
    }

}

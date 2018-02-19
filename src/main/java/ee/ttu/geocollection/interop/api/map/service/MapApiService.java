package ee.ttu.geocollection.interop.api.map.service;

import ee.ttu.geocollection.interop.api.map.pojo.MapFilter;

import java.util.Map;

public interface MapApiService {

    Map findLocalitiesSummary();

    Map findLocalitiesSummaryFilter(MapFilter filters);

}

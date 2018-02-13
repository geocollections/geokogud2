package ee.ttu.geocollection.interop.api.drillCores.service.impl;

import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreBoxApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DrillCoreBoxApiServiceImpl implements DrillCoreBoxApiService {

    private static final String DRILLCORE_BOX_TABLE = "drillcore_box";

    @Autowired
    private ApiService apiService;

    @Override
    public Map findRawCoreBoxById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity(DRILLCORE_BOX_TABLE, requestParams);
    }
}

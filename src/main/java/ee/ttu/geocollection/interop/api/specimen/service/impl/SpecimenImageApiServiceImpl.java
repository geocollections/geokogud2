package ee.ttu.geocollection.interop.api.specimen.service.impl;

import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenImageApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpecimenImageApiServiceImpl implements SpecimenImageApiService {

    private static final String SPECIMEN_IMAGE_TABLE = "specimen_image";

    @Autowired
    private ApiService apiService;

    @Override
    public Map findRawSpecimenImageById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity(SPECIMEN_IMAGE_TABLE, requestParams);
    }

}

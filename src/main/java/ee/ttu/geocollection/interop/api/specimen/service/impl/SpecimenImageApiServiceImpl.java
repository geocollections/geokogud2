package ee.ttu.geocollection.interop.api.specimen.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenIdentificationSearchApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenImageApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpecimenImageApiServiceImpl implements SpecimenImageApiService {

    private static final String SPECIMEN_IMAGE_TABLE = "specimen_image";
    private static final String SPECIMEN_IDENTIFICATION_TABLE = "specimen_identification";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawSpecimenImageById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(SPECIMEN_IMAGE_TABLE, requestParams);
    }

    @Override
    public ApiResponse findSpecimenIdentification(SearchField specimenId) {
        String requestParams = FluentSpecimenIdentificationSearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(specimenId)
                .whereCurrentIsTrue()
                .returnTaxonTaxon()
                .returnName()
                .returnTaxonId()
                .returnCurrent()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_IDENTIFICATION_TABLE, 1, 1, new SortField(), requestParams);
    }

}

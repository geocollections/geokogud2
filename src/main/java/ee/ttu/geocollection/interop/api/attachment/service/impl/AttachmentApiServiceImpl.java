package ee.ttu.geocollection.interop.api.attachment.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.attachment.service.AttachmentApiService;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentApiServiceImpl implements AttachmentApiService {

    private static final String ATTACHMENT_TABLE = "attachment";

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(ATTACHMENT_TABLE, requestParams);
    }

}

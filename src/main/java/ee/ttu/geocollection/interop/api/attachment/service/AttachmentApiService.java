package ee.ttu.geocollection.interop.api.attachment.service;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

public interface AttachmentApiService {

    ApiResponse findRawById(Long id);

}

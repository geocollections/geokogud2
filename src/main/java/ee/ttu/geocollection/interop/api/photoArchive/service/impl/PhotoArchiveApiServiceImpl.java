package ee.ttu.geocollection.interop.api.photoArchive.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentPhotoArchiveSearchApiBuilder;
import ee.ttu.geocollection.interop.api.photoArchive.pojo.PhotoArchiveSearchCriteria;
import ee.ttu.geocollection.interop.api.photoArchive.service.PhotoArchiveApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PhotoArchiveApiServiceImpl implements PhotoArchiveApiService {

    private static final String IMAGE_TABLE = "image";
    private static final String ATTACHMENT_TABLE= "attachment";

    private List<String> fields = Arrays.asList(
            "id",
            "filename",
            "date_taken",
            "author__agent",
            "locality_id",
            "author__forename",
            "author__surename",
            "author_free",
            "locality__locality",
            "locality__locality_en",
            "locality__country__value",
            "locality__country__value_en",
            "locality__maakond__maakond",
            "locality__vald__vald",
            "locality__asustusyksus__asustusyksus",
            "locality__maakond__maakond_en",
            "locality__vald__vald_en",
            "locality__asustusyksus__asustusyksus_en",
            "people",
            "keywords",
            "description",
            "object",
            "place",
            "image_number",
            "imageset__imageset_number",
            "imageset__imageset_series",
            "size_x",
            "size_y",
            "date_taken_free",
            "category__value",
            "category__value_en",
            "type__value",
            "type__value_en",
            "device__name",
            "copyright_agent__agent",
            "licence__licence_en",
            "licence__licence_url_en",
            "date_added",
            "date_changed",
            "user_added",
            "user_changed",
            "database__acronym",
            "device_digitised__name",
            "agent_digitised",
            "date_digitised",
            "date_digitised_free",
            "locality__longitude",
            "locality__latitude",
            "longitude",
            "latitude",
            "agent_digitised__forename",
            "agent_digitised__surename",
            "agent_digitised__date_added",
            "copyright_agent__agent",
            "uuid_filename",
            "uuid"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findPhoto(PhotoArchiveSearchCriteria searchCriteria)  {
        String requestParams = FluentPhotoArchiveSearchApiBuilder.aRequest()
                .querySpecimenImageAttachment()
                .queryLocality(searchCriteria.getLocality())
                .queryPeople(searchCriteria.getPeople())
                .queryKeywords(searchCriteria.getKeywords())
                .queryCountry(searchCriteria.getAdminUnit())
                .queryDateTaken(searchCriteria.getDateTakenSince())
                .queryDateTaken(searchCriteria.getDateTakenTo())
                .queryNumber(searchCriteria.getImageNumber())
                .queryAuthorAgent(searchCriteria.getAuthorAgent())
                .querySizeX(searchCriteria.getSizeSince())
                .querySizeY(searchCriteria.getSizeTo())
                .queryInstitutions(searchCriteria.getDbs())
//                .addReturningFields()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(ATTACHMENT_TABLE, searchCriteria.getPaginateBy(), searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }


    @Override
    public Map findRawByIdOld(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(IMAGE_TABLE, requestParams);
    }

    //    TODO: Change to ApiResponse
    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(ATTACHMENT_TABLE, requestParams);
    }
}
package ee.ttu.geocollection.interop.api.attachment.service.impl;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.attachment.service.AttachmentApiService;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AttachmentApiServiceImpl implements AttachmentApiService {

    private static final String ATTACHMENT_TABLE = "attachment";

    private List<String> fields = Arrays.asList(
            "id",
            "uuid",
            "uuid_filename",
            "filename",
            "description",
            "description_en",
            "type__value",
            "type__value_en",
            "attachment_format__value",
            "author__agent",
            "author__forename",
            "author__surename",
            "author_free",
            "date_created",
            "date_created_free",
            "device__name",
            "device_txt",
            "agent_digitised__agent",
            "agent_digitised__forename",
            "agent_digitised__surename",
            "date_digitised",
            "date_digitised_free",
            "device_digitised__name",
            "copyright_agent__agent",
            "copyright_agent",
            "licence__licence_en",
            "licence__licence_url_en",
            "remarks",
            "date_added",
            "date_changed",
            "database__acronym",
            "database__name",
            "database__name_en",
            "specimen_id",
            "locality",
            "locality_id",
            "locality__id",
            "locality__locality",
            "locality__locality_en",
            "locality__number",
            "locality__country__value",
            "locality__country__value_en",
            "locality__maakond__maakond",
            "locality__maakond__maakond_en",
            "locality__vald__vald",
            "locality__vald__vald_en",
            "locality__asustusyksus__asustusyksus",
            "locality__asustusyksus__asustusyksus_en",
            "locality__maaamet_pa_id",
            "locality__depth",
            "locality__longitude",
            "locality__latitude",
            "drillcore_box_id",
            "specimen_image_attachment",
            "specimen_image_id",
            "image_category__value",
            "image_category__value_en",
            "image_type__value",
            "image_type__value_en",
            "image_number",
            "imageset__imageset_series",
            "imageset__imageset_number",
            "image_object",
            "image_place",
            "image_people",
            "image_latitude",
            "image_longitude",
            "image_altitude",
            "image_scalebar",
            "image_width",
            "image_height",
            "image_description",
            "image_description_en",
            "drillcore_box__id",
            "drillcore_box__number",
            "drillcore_box__depth_start",
            "drillcore_box__depth_end",
            "drillcore_box__stratigraphy_top_id",
            "drillcore_box__stratigraphy_top__stratigraphy",
            "drillcore_box__stratigraphy_top__stratigraphy_en",
            "drillcore_box__stratigraphy_base_id",
            "drillcore_box__stratigraphy_base__stratigraphy",
            "drillcore_box__stratigraphy_base__stratigraphy_en",
            "stars",
            "tags",
            "is_preferred",
            "is_locked",
            "original_filename",
            "specimen",
            "specimen__specimen_nr",
            "specimen__specimen_id",
            "specimen__coll__number",
            "specimen__locality",
            "specimen__locality__locality",
            "specimen__locality__locality_en",
            "specimen__stratigraphy",
            "specimen__stratigraphy__stratigraphy",
            "specimen__stratigraphy__stratigraphy_en",
            "attach_link__analysis__id",
            "attach_link__collection__id",
            "attach_link__collection__number",
            "attach_link__dataset__id",
            "attach_link__dataset__name",
            "attach_link__dataset__name_en",
            "attach_link__doi__id",
            "attach_link__doi__identifier",
            "attach_link__drillcore__id",
            "attach_link__drillcore__drillcore",
            "attach_link__drillcore__drillcore_en",
            "attach_link__drillcore_box__drillcore__id",
            "attach_link__drillcore_box__number",
            "attach_link__drillcore_box__drillcore__drillcore",
            "attach_link__drillcore_box__drillcore__drillcore_en",
            "attach_link__locality__id",
            "attach_link__locality__locality",
            "attach_link__locality__locality_en",
            "attach_link__preparation__id",
            "attach_link__preparation__preparation_number",
            "attach_link__reference__id",
            "attach_link__reference__reference",
            "attach_link__sample__id",
            "attach_link__sample__number",
            "attach_link__specimen__id",
            "attach_link__specimen__specimen_id",
            "attach_link__specimen__coll__number",
            "reference__id",
            "reference__reference"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
//                .buildWithDefaultReturningFields();
        return apiService.searchRawEntities(ATTACHMENT_TABLE, requestParams);
    }

}

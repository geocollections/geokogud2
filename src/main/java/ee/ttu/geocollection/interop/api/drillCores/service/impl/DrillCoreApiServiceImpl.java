package ee.ttu.geocollection.interop.api.drillCores.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentDrillCoreSearchApiBuilder;
import ee.ttu.geocollection.interop.api.drillCores.pojo.DrillCoreSearchCriteria;
import ee.ttu.geocollection.interop.api.drillCores.service.DrillCoreApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DrillCoreApiServiceImpl implements DrillCoreApiService {

    private static final String DRILLCORE = "drillcore";
    private static final String DRILLCORE_BOX = "drillcore_box";
    private static final String ATTACHMENT = "attachment";
    private static final String ATTACHMENT_LINK = "attachment_link";

    @Autowired
    private ApiService apiService;

    private List<String> fields = Arrays.asList(
        "id",
        "drillcore",
        "locality__country__value",
        "locality__country__value_en",
        "locality__latitude",
        "locality__longitude",
        "depth",
        "boxes",
        "box_numbers",
        "storage__location",
        "locality_id",
        "drillcore_en",
        "depository__value",
        "depository__value_en",
        "number_meters",
        "year",
        "remarks",
        "agent__agent",
        "database__acronym",
        "locality__maaamet_pa_id",
        "locality__locality",
        "locality__locality_en",
        "direction_lr"
    );

    private List<String> attachmentLinkFields = Arrays.asList(
        "attachment__uuid_filename",
        "attachment__author__agent",
        "attachment__is_preferred",
        "attachment__author__agent",
        "attachment__date_added",
        "drillcore_box__depth_start",
        "drillcore_box__depth_end",
        "drillcore_box__number",
        "drillcore_box__stratigraphy_top",
        "drillcore_box__stratigraphy_top__stratigraphy",
        "drillcore_box__stratigraphy_top__stratigraphy_en",
        "drillcore_box__stratigraphy_base",
        "drillcore_box__stratigraphy_base__stratigraphy",
        "drillcore_box__stratigraphy_base__stratigraphy_en",
        "drillcore_box_id"
    );

    @Override
    public ApiResponse findDrillCore(DrillCoreSearchCriteria searchCriteria)  {
        String requestParams = FluentDrillCoreSearchApiBuilder.aRequest()
                .queryDrillCore(searchCriteria.getDrillcore())
                .queryLocalityCountry(searchCriteria.getCountry())
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryStorageLocation(searchCriteria.getStorageLocation())
                .queryBoxes(searchCriteria.getBoxesSince())
                .queryBoxes(searchCriteria.getBoxesTo())
                .queryId(searchCriteria.getId())
                .queryLocalityAdminUnit(searchCriteria.getAdminUnit())
                .queryInstitutions(searchCriteria.getDbs())
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                DRILLCORE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public ApiResponse findAttachmentLinks(SearchField drillcoreId) {
        String requestParams = FluentDrillCoreSearchApiBuilder.aRequest()
                .queryDrillcoreId(drillcoreId)
                .queryFields(attachmentLinkFields)
                .buildFullQuery();
        return apiService.searchRawEntities(ATTACHMENT_LINK, 1000, 1, new SortField("drillcore_box__depth_start", SortingOrder.ASCENDING), requestParams);
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
//                .relatedData(DRILLCORE_BOX)
//                .relatedData(ATTACHMENT)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.searchRawEntities(DRILLCORE, requestParams);
    }
}
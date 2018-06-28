package ee.ttu.geocollection.interop.api.localities.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentLocalityImageSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentLocalitySearchApiBuilder;
import ee.ttu.geocollection.interop.api.localities.pojo.LocalitySearchCriteria;
import ee.ttu.geocollection.interop.api.localities.service.LocalitiesApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

@Service
public class LocalitiesApiServiceImpl implements LocalitiesApiService {

    private static final String LOCALITY_TABLE = "locality";
    private static final String IMAGE_TABLE = "image";
    private static final String DRILLCORE_TABLE = "drillcore";
    private static final String SPECIMEN_TABLE = "specimen";
    private static final String LOCALITY_REFERENCE = "locality_reference";
    private static final String LOCALITY_SYNONYM = "locality_synonym";
    private static final String ATTACHMENT_LINK = "attachment_link";
    private static final String SAMPLE = "sample";
    private static final String STRATIGRAPHY_STRATOTYPE = "stratigraphy_stratotype";
    private static final String ATTACHMENT_TABLE = "attachment";

    private List<String> fields = Arrays.asList(
            "id",
            "asustusyksus__asustusyksus_en",
            "asustusyksus__asustusyksus",
            "country__value_en",
            "country__value",
            "depth",
            "eelis",
            "elevation",
            "latitude",
            "locality_en",
            "locality",
            "longitude",
            "maaamet_pa_id",
            "maakond__maakond_en",
            "maakond__maakond",
            "number",
            "parent__locality",
            "remarks_location",
            "stratigraphy_base__stratigraphy_en",
            "stratigraphy_base__stratigraphy",
            "stratigraphy_base_id",
            "stratigraphy_top__stratigraphy_en",
            "stratigraphy_top__stratigraphy",
            "stratigraphy_top_id",
            "localitystratigraphy__stratigraphy__stratigraphy_en",
            "type__value_en",
            "type__value",
            "vald__vald_en",
            "vald__vald",
            "coord_system",
            "coordx",
            "coordy",
            "coord_det_precision__value",
            "coord_det_method__value",
            "coord_det_method__value_en",
            "coord_det_agent__agent",
            "stratigraphy_top_free",
            "stratigraphy_base_free",
            "remarks",
            "user_added",
            "date_added",
            "date_changed"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findLocality(LocalitySearchCriteria searchCriteria)  {
        String requestParams = prepareCommonFields(searchCriteria)
                .queryId(searchCriteria.getId())
                .buildFullQuery();
        return apiService.searchRawEntities(
                LOCALITY_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    private FluentLocalitySearchApiBuilder prepareCommonFields(LocalitySearchCriteria searchCriteria) {
        return FluentLocalitySearchApiBuilder.aRequest()
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryNumber(searchCriteria.getNumber())
                .queryCountry(searchCriteria.getCountry()).andReturn()
                .queryAdminUnit(searchCriteria.getAdminUnit()).andReturn()
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryReference(searchCriteria.getReference())
                .queryMaPaId(searchCriteria.getMaPaId()).andReturn()
                .queryLatitude(searchCriteria.getLatitude()).andReturn()
                .queryLongitude(searchCriteria.getLongitude()).andReturn()
                .queryDepth(searchCriteria.getVerticalExtentSince()).andReturn()
                .queryDepth(searchCriteria.getVerticalExtentTo()).andReturn()
                .returnId()
                .returnLocalityBase()
                .returnLocalityBaseEn()
                .returnLocalityBaseId()
                .returnLocalityTop()
                .returnLocalityTopEn()
                .returnLocalityTopId();
    }

    @Override
    public ApiResponse findLocalityImages(LocalitySearchCriteria searchCriteria) {
        String requestParams = FluentLocalitySearchApiBuilder.aRequest()
                .queryLocalityIdNotNull()
                .queryImgLocality(searchCriteria.getLocality())
                .queryImgNumber(searchCriteria.getNumber())
                .queryImgCountry(searchCriteria.getCountry())
                .queryImgAdminUnit(searchCriteria.getAdminUnit())
                .queryImgStratigraphy(searchCriteria.getStratigraphy())
                .queryImgReference(searchCriteria.getReference())
                .queryImgId(searchCriteria.getId())
                .queryImgMaPaId(searchCriteria.getMaPaId())
                .queryImgLatitude(searchCriteria.getLatitude())
                .queryImgLongitude(searchCriteria.getLongitude())
                .queryImgDepth(searchCriteria.getVerticalExtentSince())
                .queryImgDepth(searchCriteria.getVerticalExtentTo())
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(IMAGE_TABLE, searchCriteria.getPaginateBy(), searchCriteria.getPage(), new SortField(LOCALITY__ID, SortingOrder.DESCENDING), requestParams);
    }

    @Override
    public ApiResponse findLocalityImage(SearchField localityId) {
        String requestParams = FluentLocalityImageSearchApiBuilder.aRequest()
                .queryLocalityIdForUrl(localityId).andReturn()
                .returnId()
                .returnAuthor()
                .returnDate()
                .returnDateFree()
                .returnUuidFilename()
                .buildFullQuery();
        return apiService.searchRawEntities(ATTACHMENT_TABLE, 1, new SortField("stars", SortingOrder.DESCENDING), requestParams);
    }

    @Override
    public ApiResponse findDrillcore(SearchField localityId) {
        String requestParams = FluentLocalitySearchApiBuilder.aRequest()
                .queryLocalityIdForUrl(localityId).andReturn()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(DRILLCORE_TABLE, 1, 1, new SortField("id", SortingOrder.ASCENDING), requestParams);
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(IMAGE_TABLE)
                .relatedData(DRILLCORE_TABLE)
                .relatedData(LOCALITY_REFERENCE)
                .relatedData(LOCALITY_SYNONYM)
                .relatedData(SPECIMEN_TABLE)
                .relatedData(ATTACHMENT_LINK)
                .relatedData(SAMPLE)
                .relatedData(STRATIGRAPHY_STRATOTYPE)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.searchRawEntities(LOCALITY_TABLE, requestParams);
    }

    @Override
    public ApiResponse findAllSpecimens(SearchField id) {
        String requestParams = FluentLocalitySearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(id)
                .returnId()
                .returnStratigraphyId()
                .returnStratigraphy()
                .returnStratigraphyEn()
                .returnTaxonId()
                .returnTaxon()
                .returnTaxonName()
                .returnGeologiesName()
                .returnGeologiesNameEn()
                .returnRockId()
                .returnRockName()
                .returnRockNameEn()
                .returnTaxonCurrent()
                .returnGeologiesCurrent()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_TABLE, 10, 1, new SortField(), requestParams);
    }

}

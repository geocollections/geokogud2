package ee.ttu.geocollection.interop.api.specimen.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenIdentificationGeologiesSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenIdentificationSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenImageSearchApiBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentSpecimenSearchApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SpecimenApiServiceImpl implements SpecimenApiService {

    private static final String SPECIMEN_TABLE = "specimen";
    private static final String SPECIMEN_IMAGE_TABLE = "specimen_image";
    private static final String SPECIMEN_IDENTIFICATION_TABLE = "specimen_identification";
    private static final String SPECIMEN_REFERENCE_TABLE = "specimen_reference";
    private static final String SPECIMEN_IDENTIFICATION_GEOLOGY = "specimen_identification_geology";
    private static final String ATTACHMENT_LINK = "attachment_link";

    private List<String> fields = Arrays.asList(
            "id",
            "classification__class_field",
            "classification__class_en",
            "coll_id",
            "coll__name",
            "coll__number",
            "coll__remarks",
            "lithostratigraphy_id",
            "lithostratigraphy__stratigraphy",
            "lithostratigraphy__stratigraphy_en",
            "locality__asustusyksus__asustusyksus",
            "locality__asustusyksus__asustusyksus_en",
            "locality__country__value",
            "locality__country__value_en",
            "locality__depth",
            "locality__eelis",
            "locality__elevation",
            "locality_id",
            "locality__latitude",
            "locality__locality",
            "locality__locality_en",
            "locality__longitude",
            "locality__maaamet_pa_id",
            "locality__maakond__maakond",
            "locality__maakond__maakond_en",
            "locality__number",
            "locality__parent__locality",
            "locality__remarks_location",
            "locality__stratigraphy_base_id",
            "locality__stratigraphy_base__stratigraphy",
            "locality__stratigraphy_base__stratigraphy_en",
            "locality__stratigraphy_top_id",
            "locality__stratigraphy_top__stratigraphy",
            "locality__stratigraphy_top__stratigraphy_en",
            "locality__type__value",
            "locality__type__value_en",
            "locality__vald__vald",
            "locality__vald__vald_en",
            "depth",
            "depth_interval",
            "sample__mass",
            "sample__number",
            "sample__rock",
            "sample__rock_en",
            "specimen_nr",
            "stratigraphy_id",
            "stratigraphy__stratigraphy",
            "stratigraphy__stratigraphy_en",
            "stratigraphy_free",
            "fossil__value",
            "fossil__value_en",
            "agent_collected_id",
            "agent_collected__agent",
            "agent_collected__forename",
            "agent_collected__surename",
            "specimenreference__reference__author",
            "specimenreference__reference__title",
            "original_status__value",
            "original_status__value_en",
            "part",
            "remarks",
            "remarks_collecting",
            "date_added",
            "date_changed",
            "specimen_id",
            "type__value_en",
            "type__value",
            "kind__value_en"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findSpecimen(SpecimenSearchCriteria searchCriteria)  {
        String requestParams = FluentSpecimenSearchApiBuilder.aRequest()
                .querySpecimenNumber(searchCriteria.getSpecimenNumber()).andReturn()
                .queryCollectionNumber(searchCriteria.getCollectionNumber()).andReturn()
                .queryClassification(searchCriteria.getClassification())
                .queryNameOfFossil(searchCriteria.getFossilName())
                .queryMineralRock(searchCriteria.getMineralRock())
                .queryAdminUnit(searchCriteria.getAdminUnit())
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryId(searchCriteria.getId()).andReturn()
                .queryDepth(searchCriteria.getDepthSince()).andReturn()
                .queryDepth(searchCriteria.getDepthTo())
                .queryCollector(searchCriteria.getCollector()).andReturn()
                .queryReference(searchCriteria.getReference())
                .queryOriginalStatus(searchCriteria.getTypeStatus()).andReturn()
                .queryPartOfFossil(searchCriteria.getPartOfFossil())
                .queryDateAdded(searchCriteria.getDateTakenSince())
                .queryDateAdded(searchCriteria.getDateTakenTo())
                .queryInstitutions(searchCriteria.getDbs()).andReturn()
                .returnDatabaseName()
                .returnLocalityId()
                .returnStratigraphyId()
                .returnLatitutde()
                .returnLongitude()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                SPECIMEN_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public ApiResponse findSpecimenImages(SpecimenSearchCriteria searchCriteria) {
        String requestParams = FluentSpecimenSearchApiBuilder.aRequest()
                .queryImageNotNull()
                .queryImgSpecimenNumber(searchCriteria.getSpecimenNumber())
                .queryImgCollectionNumber(searchCriteria.getCollectionNumber())
                .queryImgClassification(searchCriteria.getClassification())
                .queryImgNameOfFossil(searchCriteria.getFossilName())
                .queryImgMineralRock(searchCriteria.getMineralRock())
                .queryImgAdminUnit(searchCriteria.getAdminUnit())
                .queryImgLocality(searchCriteria.getLocality())
                .queryImgStratigraphy(searchCriteria.getStratigraphy())
                .queryImgId(searchCriteria.getId())
                .queryImgDepth(searchCriteria.getDepthSince())
                .queryImgDepth(searchCriteria.getDepthTo())
                .queryImgCollector(searchCriteria.getCollector())
                .queryImgReference(searchCriteria.getReference())
                .queryImgOriginalStatus(searchCriteria.getTypeStatus())
                .queryImgPartOfFossil(searchCriteria.getPartOfFossil())
                .queryImgDateAdded(searchCriteria.getDateTakenSince())
                .queryImgDateAdded(searchCriteria.getDateTakenTo())
                .queryInstitutions(searchCriteria.getDbs())
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                SPECIMEN_IMAGE_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                new SortField("specimen__id", SortingOrder.ASCENDING),
                requestParams);
    }

    @Override
    public ApiResponse findSpecimenImage(SearchField specimenId) {
        String requestParams = FluentSpecimenImageSearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(specimenId).andReturn()
                .returnImageUrl()
                .returnImage()
                .returnDatabaseAcronym()
                .returnId()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_IMAGE_TABLE, 1,1, new SortField(), requestParams);
    }

    @Override
    public ApiResponse findSpecimenIdentification(SearchField specimenId) {
        String requestParams = FluentSpecimenIdentificationSearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(specimenId).andReturn()
                .whereCurrentIsTrue()
                .returnTaxonTaxon()
                .returnName()
                .returnTaxonId()
                .returnCurrent()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_IDENTIFICATION_TABLE, 1, 1, new SortField(), requestParams);
    }

    @Override
    public ApiResponse findSpecimenIdentificationGeologies(SearchField specimenId) {
        String requestParams = FluentSpecimenIdentificationGeologiesSearchApiBuilder.aRequest()
                .querySpecimenIdForUrl(specimenId).andReturn()
                .whereCurrentIsTrue()
                .returnRockName()
                .returnRockNameEn()
                .returnRockId()
                .returnName()
                .returnNameEn()
                .returnCurrent()
                .buildFullQuery();
        return apiService.searchRawEntities(SPECIMEN_IDENTIFICATION_GEOLOGY, 5,1, new SortField(), requestParams);
    }

    @Override
    public Map findRawSpecimenImageById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return apiService.findRawEntity(SPECIMEN_IMAGE_TABLE, requestParams);
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(SPECIMEN_IDENTIFICATION_TABLE)
                .relatedData(SPECIMEN_IMAGE_TABLE)
                .relatedData(SPECIMEN_REFERENCE_TABLE)
                .relatedData(SPECIMEN_IDENTIFICATION_GEOLOGY)
                .relatedData(ATTACHMENT_LINK)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(SPECIMEN_TABLE, requestParams);
    }
}

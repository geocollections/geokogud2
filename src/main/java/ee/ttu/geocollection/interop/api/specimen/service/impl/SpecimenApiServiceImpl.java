package ee.ttu.geocollection.interop.api.specimen.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.indexing.IndexingProperties;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.ApiFields;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class SpecimenApiServiceImpl implements SpecimenApiService {
    public static final String SPECIMEN_TABLE = "specimen";
    public static final String SPECIMEN_IMAGE_TABLE = "specimen_image";
    public static final String SPECIMEN_IDENTIFICATION_TABLE = "specimen_identification";
    public static final String SPECIMEN_REFERENCE_TABLE = "specimen_reference";
    public static final String SPECIMEN_IDENTIFICATION_GEOLOGY = "specimen_identification_geology";
    public static final String ATTACHMENT_LINK = "attachment_link";

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
//            "locality_free",
            "depth",
            "depth_interval",
//            "sample__agent_collected__agent",
//            "sample__agent_collected_free",
//            "sample__classification_rock__name",
//            "sample__classification_rock__name_en",
//            "sample__database__acronym",
//            "sample__date_collected",
//            "sample__date_collected_free",
//            "sample__depth",
//            "sample__depth_interval",
//            "sample_id",
//            "sample__lithostratigraphy_id",
//            "sample__lithostratigraphy__stratigraphy",
//            "sample__lithostratigraphy__stratigraphy_en",
//            "sample__locality__asustusyksus__asustusyksus",
//            "sample__locality__asustusyksus__asustusyksus_en",
//            "sample__locality__country__value",
//            "sample__locality__country__value_en",
//            "sample__locality__depth",
//            "sample__locality__eelis",
//            "sample__locality__elevation",
//            "sample__locality_free",
//            "sample__locality_id",
//            "sample__locality__latitude",
//            "sample__locality__locality",
//            "sample__locality__locality_en",
//            "sample__locality__longitude",
//            "sample__locality__maaamet_pa_id",
//            "sample__locality__maakond__maakond",
//            "sample__locality__maakond__maakond_en",
//            "sample__locality__number",
//            "sample__locality__parent__locality",
//            "sample__locality__remarks_location",
//            "sample__locality__stratigraphy_base_id",
//            "sample__locality__stratigraphy_base__stratigraphy",
//            "sample__locality__stratigraphy_base__stratigraphy_en",
//            "sample__locality__stratigraphy_top_id",
//            "sample__locality__stratigraphy_top__stratigraphy",
//            "sample__locality__stratigraphy_top__stratigraphy_en",
//            "sample__locality__type__value",
//            "sample__locality__type__value_en",
//            "sample__locality__vald__vald",
//            "sample__locality__vald__vald_en",
//            "sample__location",
            "sample__mass",
            "sample__number",
//            "sample__number_additional",
//            "sample__remarks",
            "sample__rock",
            "sample__rock_en",
//            "sample__series_id",
//            "sample__soil_site_id",
//            "sample__soil_site__land_use",
//            "sample__soil_site__latitude",
//            "sample__soil_site__longitude",
//            "sample__soil_site__site",
//            "sample__soil_site__site_en",
//            "sample__soil_site__soil",
//            "sample__stratigraphy_bed",
//            "sample__stratigraphy_id",
//            "sample__stratigraphy__stratigraphy",
//            "sample__stratigraphy__stratigraphy_en",
            "specimen_nr",
            "stratigraphy_id",
            "stratigraphy__stratigraphy",
            "stratigraphy__stratigraphy_en",
            "stratigraphy_free",
//            "specimenidentification__name",
//            "specimenidentification__taxon_id",
//            "specimenidentification__taxon__taxon",
            //"specimenidentification__taxon__author_year",
            //"specimenidentification__taxon__parent__taxon",
            //"specimenidentification__taxon__fossil_group__taxon",
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
//            "status__value", // API gives 400 error on these fields
//            "status__value_en"
//            "original_status__value",
//            "original_status__value_en"
    );

    @Autowired
    private ApiService apiService;
    @Autowired
    private IndexingProperties indexingProperties;

    @Override
    public ApiResponse findSpecimen(SpecimenSearchCriteria searchCriteria)  {
        String requestParams = prepareCommonFields(searchCriteria)
                .queryId(searchCriteria.getId()).andReturn()
                .buildDefaultFieldsQuery();
//                .buildFullQuery();
        // Had to lower the pagination size because default 30 made it too slow.
        return apiService.searchRawEntities(SPECIMEN_TABLE, 25, searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
//        return apiService.searchRawEntitiesUsingSolr(SPECIMEN_TABLE,30, searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    private FluentSpecimenSearchApiBuilder prepareCommonFields(SpecimenSearchCriteria searchCriteria) {
        return FluentSpecimenSearchApiBuilder.aRequest()
                .querySpecimenNumber(searchCriteria.getSpecimenNumber()).andReturn()
                .queryCollectionNumber(searchCriteria.getCollectionNumber()).andReturn()
                .queryClassification(searchCriteria.getClassification())
//                .queryFossilMineralRock(searchCriteria.getFossilMineralRock())
                .queryNameOfFossil(searchCriteria.getFossilName())
                .queryMineralRock(searchCriteria.getMineralRock())
                .queryAdminUnit(searchCriteria.getAdminUnit())
                .queryLocality(searchCriteria.getLocality()).andReturn()
                .queryStratigraphy(searchCriteria.getStratigraphy()).andReturn()
                .queryDepth(searchCriteria.getDepthSince()).andReturn()
                .queryDepth(searchCriteria.getDepthTo())
                .queryCollector(searchCriteria.getCollector()).andReturn()
                .queryReference(searchCriteria.getReference())
                .queryOriginalStatus(searchCriteria.getTypeStatus()).andReturn()
                .queryPartOfFossil(searchCriteria.getPartOfFossil())
                .queryKeywords(searchCriteria.getKeyWords())
                .queryDateAdded(searchCriteria.getDateTakenSince())
                .queryDateAdded(searchCriteria.getDateTakenTo())
                .queryRockId(searchCriteria.getRockId())
                .queryInstitutions(searchCriteria.getDbs()).andReturn()
                .returnDatabaseName()
                .returnLocalityId()
                .returnStratigraphyId()
                .returnLatitutde()
                .returnLongitude();
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
//        return apiService.searchRawEntities(SPECIMEN_IMAGE_TABLE, 2,1, new SortField(), requestParams); EDIT 21.12.2017
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
//                .buildWithDefaultReturningFields();
//                TODO: Currently fast fix for duplicates in specimen detail view. Or just add paginate_by=1
                .returnCustomField(ApiFields.ID)
                .returnCustomField(ApiFields.IMAGE)
                .returnCustomField(ApiFields.IMAGE_URL)
                .returnCustomField(ApiFields.SPECIMEN__DATABASE__ACRONYM)
                .returnCustomField(ApiFields.SPECIMEN_ID)
//                .returnCustomField(ApiFields.SPECIMEN__SPECIMEN_ID)
                .returnCustomField("specimen__specimenidentification__taxon__taxon")
                .returnCustomField("specimen__specimenidentification__taxon__id")
                .returnCustomField("specimen__specimenidentification__name")
                .returnCustomField("specimen__specimenidentificationgeologies__name")
                .returnCustomField("specimen__specimenidentificationgeologies__name_en")
                .returnCustomField("specimen__locality__locality")
                .returnCustomField("specimen__locality__locality_en")
                .returnCustomField("specimen__locality")
                .returnCustomField("specimen__stratigraphy__stratigraphy")
                .returnCustomField("specimen__stratigraphy__stratigraphy_en")
                .returnCustomField("specimen__stratigraphy")
                .returnCustomField(ApiFields.TYPE_VALUE)
                .returnCustomField(ApiFields.TYPE_VALUE_EN)
                .returnCustomField(ApiFields.DATE)
                .returnCustomField(ApiFields.DATE_TAKEN_FREE)
                .returnCustomField("device_id__name")
                .returnCustomField(ApiFields.COPYRIGHT_AGENT)
                .returnCustomField(ApiFields.LICENCE)
                .returnCustomField("licence__licence_url")
                .returnCustomField(ApiFields.LICENCE_URL)
                .returnCustomField(ApiFields.DATA_ADDED)
                .returnCustomField(ApiFields.DATA_CHANGED)
                .returnCustomField(ApiFields.TAGS)
                .returnCustomField(ApiFields.DESCRIPTION)
                .returnCustomField("description_en")
                .returnCustomField(ApiFields.REMARKS)
                .returnCustomField("remarks_en")
                .returnCustomField("scalebar")
                .returnCustomField("camera")
                .returnCustomField("date_digitised")
                .returnCustomField("date_digitised_free")
                .returnCustomField("agent__forename")
                .returnCustomField("agent__surename")
                .returnCustomField("agent_digitised__forename")
                .returnCustomField("agent_digitised__surename")
                .returnCustomField(ApiFields.USER_ADDED)
                .returnCustomField("user_changed")
                .buildWithReturningFieldsAndRelatedData();
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

    @Override
    public ApiResponse findSpecimensForIndex(SpecimenSearchCriteria searchCriteria) {
        String requestParams = FluentSpecimenSearchApiBuilder.aRequest()
                .queryId(searchCriteria.getId()).andReturn()
                .querySpecimenNumber(searchCriteria.getSpecimenNumber()).andReturn()
                .queryNameOfFossil(searchCriteria.getFossilName()).andReturn()
                .queryClassification(searchCriteria.getClassification()).andReturn()
                .buildFullQuery();
        return apiService.searchRawEntities(
                SPECIMEN_TABLE, indexingProperties.getIndexingBatchSize(), searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    @Override
    public ApiResponse findSpecimensByIds(Collection<String> ids) {
        String requestParams = prepareCommonFields(new SpecimenSearchCriteria())
                .queryMultipleIds(ids).andReturn()
                .buildFullQuery();
        //This + 1 in paginateBy is very important! (API does not accept neither 0, nor 1 values there)
        return apiService.searchRawEntities(SPECIMEN_TABLE, ids.size() + 1, 1, new SortField(), requestParams);
    }
}

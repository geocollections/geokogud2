package ee.ttu.geocollection.interop.api.stratigraphies.service.impl;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentStratigraphySearchApiBuilder;
import ee.ttu.geocollection.interop.api.service.ApiService;
import ee.ttu.geocollection.interop.api.stratigraphies.pojo.StratigraphySearchCriteria;
import ee.ttu.geocollection.interop.api.stratigraphies.service.StratigraphyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StratigraphyApiServiceImpl implements StratigraphyApiService {

    private static final String STRATIGRAPHY_TABLE = "stratigraphy";
    private static final String STRATIGRAPHY_REFERENCE = "stratigraphy_reference";
    private static final String STRATIGRAPHY_STRATOTYPE = "stratigraphy_stratotype";
    private static final String STRATIGRAPHY_SYNONYM = "stratigraphy_synonym";

    private List<String> fields = Arrays.asList(
            "id",
            "stratigraphy",
            "stratigraphy_en",
            "type__value",
            "type__value_en",
            "rank__value",
            "rank__value_en",
            "scope__value",
            "scope__value_en",
            "status__value",
            "status__value_en",
            "index_main",
            "index_additional",
            "index_main_html",
            "index_additional_html",
            "parent__stratigraphy",
            "parent__stratigraphy_en",
            "parent_id",
            "age_base",
            "age_top",
            "age_chronostratigraphy__stratigraphy",
            "age_chronostratigraphy__stratigraphy_en",
            "age_chronostratigraphy_id",
            "age_reference__reference",
            "age_reference__id",
            "etymon",
            "etymon_en",
            "region",
            "region_en",
            "original_locality",
            "max_thickness",
            "remarks",
            "year",
            "color_code_cgmw",
            "lithology",
            "lithology_en",
            "author_free",
            "description",
            "description_en"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findStratigraphy(StratigraphySearchCriteria searchCriteria)  {
        String requestParams = FluentStratigraphySearchApiBuilder.aRequest()
                .queryId(searchCriteria.getId())
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryIndex(searchCriteria.getIndex())
                .queryAgeBase(searchCriteria.getAgeMinY())
                .queryLithology(searchCriteria.getMainLithology())
                .queryAuthor(searchCriteria.getAuthor())
                .returnAgeChronostratigraphyId()
                .returnParentId()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                STRATIGRAPHY_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(STRATIGRAPHY_REFERENCE)
                .relatedData(STRATIGRAPHY_STRATOTYPE)
                .relatedData(STRATIGRAPHY_SYNONYM)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.searchRawEntities(STRATIGRAPHY_TABLE, requestParams);
    }

    @Override
    public ApiResponse findAllLithostratigraphies(SearchField ageChronoId) {
        String requestParams = FluentStratigraphySearchApiBuilder.aRequest()
                .queryAgeChronostratigraphyIdForUrl(ageChronoId)
                .returnStratigraphy()
                .returnStratigraphyEn()
                .returnStratigraphyId()
                .buildFullQuery();
        return apiService.searchRawEntities(STRATIGRAPHY_TABLE, 100, 1, new SortField(), requestParams);
    }

    @Override
    public ApiResponse findOverlainByStratigraphy(SearchField ageTop, SearchField parentId) {
        String requestParams = FluentStratigraphySearchApiBuilder.aRequest()
                .queryAgeTopForAgeBase(ageTop)
                .queryCorrectParentId(parentId)
                .returnStratigraphy()
                .returnStratigraphyEn()
                .returnStratigraphyId()
                .buildFullQuery();
        return apiService.searchRawEntities(STRATIGRAPHY_TABLE, 1, 1, new SortField(), requestParams);
    }

    @Override
    public ApiResponse findOverliesStratigraphy(SearchField ageBase, SearchField parentId) {
        String requestParams = FluentStratigraphySearchApiBuilder.aRequest()
                .queryAgeBaseForAgeTop(ageBase)
                .queryCorrectParentId(parentId)
                .returnStratigraphy()
                .returnStratigraphyEn()
                .returnStratigraphyId()
                .buildFullQuery();
        return apiService.searchRawEntities(STRATIGRAPHY_TABLE, 1, 1, new SortField(), requestParams);
    }
}

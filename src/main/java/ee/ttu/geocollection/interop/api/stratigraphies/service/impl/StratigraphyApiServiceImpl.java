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
import java.util.Map;

@Service
public class StratigraphyApiServiceImpl implements StratigraphyApiService {

    public static final String STRATIGRAPHY_TABLE = "stratigraphy";

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
        "author_free"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findStratigraphy(StratigraphySearchCriteria searchCriteria)  {
        String requestParams = prepareCommonFields(searchCriteria)
                .queryId(searchCriteria.getId())
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(
                STRATIGRAPHY_TABLE,
                searchCriteria.getPaginateBy(),
                searchCriteria.getPage(),
                searchCriteria.getSortField(),
                requestParams);
    }

    private FluentStratigraphySearchApiBuilder prepareCommonFields(StratigraphySearchCriteria searchCriteria) {
        return FluentStratigraphySearchApiBuilder.aRequest()
                .queryStratigraphy(searchCriteria.getStratigraphy())
                .queryIndex(searchCriteria.getIndex())
                .queryAgeBase(searchCriteria.getAgeMinY())
                .queryLithology(searchCriteria.getMainLithology())
                .queryAuthor(searchCriteria.getAuthor())
                .returnAgeChronostratigraphyId()
                .returnParentId();
    }

    @Override
    public ApiResponse findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData("stratigraphy_reference")
                .relatedData("stratigraphy_stratotype")
                .relatedData("stratigraphy_synonym")
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
}

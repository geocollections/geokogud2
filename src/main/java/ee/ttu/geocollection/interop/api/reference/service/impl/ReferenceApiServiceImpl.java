package ee.ttu.geocollection.interop.api.reference.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.api.builder.search.FluentReferenceSearchApiBuilder;
import ee.ttu.geocollection.interop.api.reference.pojo.ReferenceSearchCriteria;
import ee.ttu.geocollection.interop.api.reference.service.ReferenceApiService;
import ee.ttu.geocollection.interop.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReferenceApiServiceImpl implements ReferenceApiService {

    public static final String REFERENCE_TABLE = "reference";
    public static final String LOCALITY_REFERENCE_TABLE = "locality_reference";
    public static final String ATTACHMENT_LINK_TABLE = "attachment_link";

    private List<String> fields = Arrays.asList(
        "id",
        "reference",
        "author",
        "year",
        "title",
        "title_original",
        "language__value",
        "language__value_en",
        "journal",
        "journal__journal_name",
        "journal_additional",
        "volume",
        "book",
        "book_original",
        "pages",
        "doi",
        "remarks",
        "abstract",
        "publisher",
        "publisher_place",
        "number",
        "type__value",
        "type__value_en"
    );

    @Autowired
    private ApiService apiService;

    @Override
    public ApiResponse findReference(ReferenceSearchCriteria searchCriteria)  {
        String requestParams = prepareCommonFields(searchCriteria)
                .queryId(searchCriteria.getId()).andReturn()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(REFERENCE_TABLE, searchCriteria.getPaginateBy(), searchCriteria.getPage(), searchCriteria.getSortField(), requestParams);
    }

    private FluentReferenceSearchApiBuilder prepareCommonFields(ReferenceSearchCriteria searchCriteria) {
        return FluentReferenceSearchApiBuilder.aRequest()
                .queryAuthor(searchCriteria.getAuthor()).andReturn()
                .queryTitle(searchCriteria.getTitle()).andReturn()
                .queryYear(searchCriteria.getYearSince()).andReturn()
                .queryYear(searchCriteria.getYearTo()).andReturn()
                .queryDoi(searchCriteria.getDoi()).andReturn()
                .queryBook(searchCriteria.getBook()).andReturn()
                .queryJournal(searchCriteria.getJournal()).andReturn();
    }

    @Override
    public Map findRawById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .relatedData(LOCALITY_REFERENCE_TABLE)
                .relatedData(ATTACHMENT_LINK_TABLE)
                .returnAllFields(fields)
                .buildWithReturningFieldsAndRelatedData();
        return apiService.findRawEntity(REFERENCE_TABLE, requestParams);
    }

    @Override
    public ApiResponse findImagesByIds(List<String> ids) {
        String requestParams = prepareCommonFields(new ReferenceSearchCriteria())
                .queryMultipleIds(ids).andReturn()
                .buildDefaultFieldsQuery();
        return apiService.searchRawEntities(REFERENCE_TABLE, ids.size() + 1, 1, new SortField(), requestParams);
    }

}
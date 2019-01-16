package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.LookUpType;
import ee.ttu.geocollection.domain.SearchField;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public abstract class FluentSearchApiBuilder<B extends FluentSearchApiBuilder<B>> {
    private String query = EMPTY;
    private String returnFields = EMPTY;
    private String relatedDataFields = EMPTY;
    private String lastQueryField = EMPTY;
    private String[] lastQueryFields = new String[]{};

    FluentSearchApiBuilder() {
    }

    abstract B getThis();

    public B andReturn() {
        if (!lastQueryField.equals(EMPTY)) {
            addReturningField(lastQueryField);
            lastQueryField = EMPTY;
        }

        Stream.of(lastQueryFields).forEach(this::addReturningField);
        lastQueryFields = new String[]{};

        return getThis();
    }

    public B returnRelatedData(String table, String... fields) {
        relatedDataFields += "&related_data=" + table + "&current=true" +
                "&fields=" + Stream.of(fields).collect(Collectors.joining(","));
        return getThis();
    }

    public B queryId(SearchField id) {
        buildFieldParameters(ID, id);
        return getThis();
    }

    public B queryInstitutions(List<String> institutions) {
        if (institutions != null) {
            // Pointless to add institutions to query if user wants to find all results from all institutions.
            if (institutions.size() != 6) {
                buildOrSearch(
                        institutions.stream()
                                .map(inst -> new OrSearchPair(new SearchField(inst, LookUpType.exact), DATABASE_ACRONYM))
                                .collect(toList()));
            }
        }
        this.lastQueryField = DATABASE_ACRONYM;
        return getThis();
    }

    public B queryMultipleIds(Collection<String> ids) {
        buildOrSearch(
                ids.stream()
                        .map(id -> new OrSearchPair(new SearchField(id, LookUpType.exact), ID))
                        .collect(toList()));
        this.lastQueryField = ID;
        return getThis();
    }

    public void buildOrSearch(List<OrSearchPair> orSearchPairs) {
        if (!orSearchPairs.isEmpty()) {
            query += "&or_search=" +
                    orSearchPairs.stream()
                            .map(pair -> pair.getField() + "__" + pair.getSearchField().getLookUpType().value() + ":" + pair.getSearchField().getName())
                            .collect(Collectors.joining(";"));
        }
    }

    public B queryDepth(SearchField depth) {
        buildFieldParameters(DEPTH, depth);
        return getThis();
    }

    public B returnDateChanged() {
        addReturningField(DATE_CHANGED);
        return getThis();
    }

    public B returnId() {
        addReturningField(ID);
        return getThis();
    }

    public B fieldIsNotNull(String field) {
        query += "&" + field + "__isnull=false";
        return getThis();
    }

    void addReturningField(String field) {
        returnFields += returnFields.isEmpty() ? field : "," + field;
    }

    public String buildFullQuery() {
        return query + "&fields=" + returnFields + relatedDataFields;
    }

    /*
        Accept search query but do not return certain fields but all by default
     */
    public String buildDefaultFieldsQuery() {
        return query;
    }

    void buildFieldParameters(String fieldName, SearchField searchField) {
        this.lastQueryField = fieldName;
        this.lastQueryFields = new String[]{};
        query += isNotNullAndEmpty(searchField) ? "&" + fieldName + "__" + extractFieldParameters(searchField) : EMPTY;
    }

    void buildMultiSearch(SearchField searchField, String... fieldName) {
        query += isNotNullAndEmpty(searchField) ?
                "&multi_search=value:" + searchField.getName()
                        + ";fields:" + StringUtils.join(fieldName, ",")
                        + ";lookuptype:" + searchField.getLookUpType()
                : EMPTY;
        this.lastQueryField = EMPTY;
        this.lastQueryFields = fieldName;
    }

    private String extractFieldParameters(SearchField searchField) {
        return searchField.getLookUpType().value() + "=" + searchField.getName();
    }

    private boolean isNotNullAndEmpty(SearchField field) {
        return field != null && StringUtils.isNotBlank(field.getName());
    }

    String booleanToString(Boolean flag) {
        return flag ? "1" : "0";
    }

    /**
     * Custom String field name and value which are used in request URL
     * @param fieldName Field in API which is given a value
     * @param value Value which is given to certain field name
     */
    void addFieldNameAndValue(String fieldName, String value) {
        query += "&" + fieldName + "=" + value;
    }

    /**
     * Same as addFieldNameAndValue
     * @param firstFieldName
     * @param value
     */
    void addFirstFieldNameAndValue(String firstFieldName, String value) {
        query += "?" + firstFieldName + "=" + value;
    }
}

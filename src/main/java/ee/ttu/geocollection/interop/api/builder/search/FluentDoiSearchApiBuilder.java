package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentDoiSearchApiBuilder extends FluentSearchApiBuilder<FluentDoiSearchApiBuilder> {

    public static FluentDoiSearchApiBuilder aRequest() {
        return new FluentDoiSearchApiBuilder();
    }

    @Override
    FluentDoiSearchApiBuilder getThis() {
        return this;
    }

    public FluentDoiSearchApiBuilder queryIdentifier(SearchField doi) {
        buildFieldParameters(IDENTIFIER, doi);
        return this;
    }

    public FluentDoiSearchApiBuilder queryTitle(SearchField title) {
        buildFieldParameters(TITLE, title);
        return this;
    }

    public FluentDoiSearchApiBuilder queryPublishedBy(SearchField journal) {
        buildFieldParameters(PUBLISHER, journal);
        return this;
    }

    public FluentDoiSearchApiBuilder queryYear(SearchField year) {
        buildFieldParameters(PUBLICATION_YEAR, year);
        return this;
    }

    public FluentDoiSearchApiBuilder queryAuthor(SearchField author) {
        buildFieldParameters(CREATORS, author);
        return this;
    }

    public FluentDoiSearchApiBuilder queryAbstract(SearchField abstractText) {
        buildFieldParameters(ABSTRACT, abstractText);
        return this;
    }

    public FluentDoiSearchApiBuilder returnReferenceId() {
        addReturningField(REFERENCE_ID);
        return this;
    }

    public FluentDoiSearchApiBuilder returnReference() {
        addReturningField(REFERENCE_REFERENCE);
        return this;
    }

    public FluentDoiSearchApiBuilder returnDatasetId() {
        addReturningField(DATASET_ID);
        return this;
    }

    public FluentDoiSearchApiBuilder returnReferenceDoi() {
        addReturningField(REFERENCE__DOI);
        return this;
    }

    public FluentDoiSearchApiBuilder queryDataciteNotNull() {
        addFieldNameAndValue("datacite_created__isnull", "false");
        return this;
    }

}

package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.preparations.service.impl.PreparationsApiServiceImpl;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentPreparationSearchApiBuilder extends FluentSearchApiBuilder<FluentPreparationSearchApiBuilder> {
    public static FluentPreparationSearchApiBuilder aRequest() {
        return new FluentPreparationSearchApiBuilder();
    }

    @Override
    FluentPreparationSearchApiBuilder getThis() {
        return this;
    }

    public FluentPreparationSearchApiBuilder queryNumber(SearchField text) {
        buildFieldParameters("preparation_number", text);
        return this;
    }
    public FluentPreparationSearchApiBuilder queryLocality(SearchField text) {
        buildFieldParameters("sample__locality__locality", text);
        return this;
    }
    public FluentPreparationSearchApiBuilder queryLocalityEn(SearchField text) {
        buildFieldParameters("sample__locality__locality_en", text);
        return this;
    }

    public FluentPreparationSearchApiBuilder queryDepth(SearchField text) {
        buildMultiSearch(text, SAMPLE_DEPTH, SAMPLE_DEPTH_INTERVAL);
        return this;
    }

//    public FluentPreparationSearchApiBuilder queryDepth(SearchField text) {
//        buildFieldParameters("sample__depth_interval", text);
//        return this;
//    }

    public FluentPreparationSearchApiBuilder queryStratigraphy(SearchField text) {
        buildFieldParameters("sample__stratigraphy__stratigraphy", text);
        return this;
    }

    public FluentPreparationSearchApiBuilder queryStratigraphyEn(SearchField text) {
        buildFieldParameters("sample__stratigraphy__stratigraphy_en", text);
        return this;
    }

    public FluentPreparationSearchApiBuilder queryCollector(SearchField text) {
        buildFieldParameters("sample__agent_collected__agent", text);
        return this;
    }

    public FluentPreparationSearchApiBuilder returnSampleLocalityId() {
        addReturningField(SAMPLE__LOCALITY__ID);
        return this;
    }

    public FluentPreparationSearchApiBuilder returnSampleStratigraphyId() {
        addReturningField(SAMPLE__STRATIGRAPHY__ID);
        return this;
    }
}

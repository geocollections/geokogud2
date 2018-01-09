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
        buildMultiSearch(text, SAMPLE_LOCALITY_LOCALITY, SAMPLE_LOCALITY_LOCALITY_EN);
        return this;
    }

    public FluentPreparationSearchApiBuilder queryDepth(SearchField text) {
        buildMultiSearch(text, SAMPLE_DEPTH, SAMPLE_DEPTH_INTERVAL);
        return this;
    }

    public FluentPreparationSearchApiBuilder queryStratigraphy(SearchField text) {
        buildMultiSearch(text, SAMPLE__STRATIGRAPHY__STRATIGRAPHY, SAMPLE__STRATIGRAPHY__STRATIGRAPHY_EN);
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

package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

public class FluentCommonSearchApiBuilder extends FluentSearchApiBuilder<FluentCommonSearchApiBuilder> {
    public static FluentCommonSearchApiBuilder aRequest() {
        return new FluentCommonSearchApiBuilder();
    }

    @Override
    FluentCommonSearchApiBuilder getThis() {
        return this;
    }

    public FluentCommonSearchApiBuilder queryField(String field, SearchField title) {
        buildFieldParameters(field, title);
        return this;
    }
    public FluentCommonSearchApiBuilder returnField(String returningField) {
        addReturningField(returningField);
        return this;
    }

}

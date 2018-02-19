package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentCoreBoxApiBuilder extends FluentSearchApiBuilder<FluentCoreBoxApiBuilder> {

    @Override
    FluentCoreBoxApiBuilder getThis() {
        return this;
    }

    public static FluentCoreBoxApiBuilder aRequest() {
        return new FluentCoreBoxApiBuilder();
    }

    public FluentCoreBoxApiBuilder queryLocalityId(SearchField localityId) {
        buildFieldParameters(LOCALITY_ID, localityId);
        return this;
    }

}

package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentSpecimenIdentificationSearchApiBuilder extends FluentSearchApiBuilder<FluentSpecimenIdentificationSearchApiBuilder> {

    public static FluentSpecimenIdentificationSearchApiBuilder aRequest() {
        return new FluentSpecimenIdentificationSearchApiBuilder();
    }

    @Override
    FluentSpecimenIdentificationSearchApiBuilder getThis() {
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder querySpecimenIdForUrl(SearchField id) {
        buildFieldParameters("specimen_id", id);
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder whereCurrentIsTrue() {
        addFieldNameAndValue("current", "true");
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder returnTaxonTaxon() {
        addReturningField(TAXON__TAXON);
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder returnName() {
        addReturningField(NAME);
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder returnTaxonId() {
        addReturningField(TAXON_ID);
        return this;
    }

    public FluentSpecimenIdentificationSearchApiBuilder returnCurrent() {
        addReturningField(CURRENT);
        return this;
    }
}

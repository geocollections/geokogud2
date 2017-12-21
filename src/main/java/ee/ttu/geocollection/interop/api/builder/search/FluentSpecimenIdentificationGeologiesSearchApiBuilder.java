package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentSpecimenIdentificationGeologiesSearchApiBuilder extends FluentSearchApiBuilder<FluentSpecimenIdentificationGeologiesSearchApiBuilder> {

    public static FluentSpecimenIdentificationGeologiesSearchApiBuilder aRequest() {
        return new FluentSpecimenIdentificationGeologiesSearchApiBuilder();
    }

    @Override
    FluentSpecimenIdentificationGeologiesSearchApiBuilder getThis() {
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder querySpecimenIdForUrl(SearchField id) {
        buildFieldParameters("specimen_id", id);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnRockName() {
        addReturningField(ROCK__NAME);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnRockNameEn() {
        addReturningField(ROCK__NAME_EN);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnRockId() {
        addReturningField(ROCK_ID);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnName() {
        addReturningField(NAME);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnNameEn() {
        addReturningField(NAME_EN);
        return this;
    }

    public FluentSpecimenIdentificationGeologiesSearchApiBuilder returnRockMindatId() {
        addReturningField(ROCK__MINDAT_ID);
        return this;
    }

}

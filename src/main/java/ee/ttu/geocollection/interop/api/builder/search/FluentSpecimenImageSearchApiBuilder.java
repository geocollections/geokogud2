package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.specimen.service.impl.SpecimenApiServiceImpl;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentSpecimenImageSearchApiBuilder extends FluentSearchApiBuilder<FluentSpecimenImageSearchApiBuilder> {

    public static FluentSpecimenImageSearchApiBuilder aRequest() {
        return new FluentSpecimenImageSearchApiBuilder();
    }

    @Override
    FluentSpecimenImageSearchApiBuilder getThis() {
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder querySpecimenIdForUrl(SearchField id) {
        buildFieldParameters("specimen_id", id);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnImageUrl(){
        addReturningField(IMAGE_URL);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnImage() {
        addReturningField(IMAGE);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnDatabaseAcronym() {
        addReturningField(SPECIMEN__DATABASE__ACRONYM);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnAgent() {
        addReturningField(AGENT__AGENT);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnDate() {
        addReturningField(DATE);
        return this;
    }

    public FluentSpecimenImageSearchApiBuilder returnDateFree() {
        addReturningField(DATE_TAKEN_FREE);
        return this;
    }
}

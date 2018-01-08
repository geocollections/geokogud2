package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentPhotoArchiveSearchApiBuilder extends FluentSearchApiBuilder<FluentPhotoArchiveSearchApiBuilder>{

    public static FluentPhotoArchiveSearchApiBuilder aRequest() {
        return new FluentPhotoArchiveSearchApiBuilder();
    }

    @Override
    FluentPhotoArchiveSearchApiBuilder getThis() {
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryFileName(SearchField id) {
        buildFieldParameters(FILE_NAME, id);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryDateTaken(SearchField dateTaken) {
        buildFieldParameters(DATE_TAKEN, dateTaken);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryAuthorAgent(SearchField id) {
        buildMultiSearch(id, AUTHOR_AGENT, AUTHOR_FORENAME, AUTHOR_SURENAME, AUTHOR_FREE);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryKeywords(SearchField keywords) {
        buildMultiSearch(keywords, KEYWORDS, DESCRIPTION, OBJECT, PLACE, LOCALITY_LOCALITY_EN, LOCALITY_LOCALITY);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryNumber(SearchField imageNumber) {
        buildMultiSearch(imageNumber, IMAGE_NUMBER, IMAGESET_NUMBER);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryPeople(SearchField people) {
        buildFieldParameters(PEOPLE, people);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryLocality(SearchField locality) {
        buildMultiSearch(locality, LOCALITY_LOCALITY, LOCALITY_LOCALITY_EN, PLACE);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryCountry(SearchField adminUnit) {
        buildMultiSearch(
                adminUnit,
                LOCALITY_COUNTRY,
                LOCALITY_COUNTRY_ENG,
                LOCALITY__MAAKOND__MAAKOND,
                LOCALITY__MAAKOND__MAAKOND_EN,
                LOCALITY__VALD__VALD,
                LOCALITY__VALD__VALD_ENG,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder querySize(SearchField sizeXY) {
        buildMultiSearch(sizeXY, SIZE_X, SIZE_Y);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder returnObject() {
        addReturningField(OBJECT);
        return this;
    }
}
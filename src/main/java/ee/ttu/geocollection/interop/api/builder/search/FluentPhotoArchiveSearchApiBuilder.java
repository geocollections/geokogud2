package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import java.util.Arrays;
import java.util.List;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentPhotoArchiveSearchApiBuilder extends FluentSearchApiBuilder<FluentPhotoArchiveSearchApiBuilder>{

    public static FluentPhotoArchiveSearchApiBuilder aRequest() {
        return new FluentPhotoArchiveSearchApiBuilder();
    }

    @Override
    FluentPhotoArchiveSearchApiBuilder getThis() {
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryLocality(SearchField locality) {
//        buildMultiSearch(locality, LOCALITY_LOCALITY, LOCALITY_LOCALITY_EN, PLACE);
        buildMultiSearch(locality, LOCALITY_LOCALITY, LOCALITY_LOCALITY_EN, IMAGE_PLACE);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryPeople(SearchField people) {
//        buildFieldParameters(PEOPLE, people);
        buildFieldParameters(IMAGE_PEOPLE, people);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryKeywords(SearchField keywords) {
//        buildMultiSearch(keywords, KEYWORDS, DESCRIPTION, OBJECT, PLACE, LOCALITY_LOCALITY_EN, LOCALITY_LOCALITY);
        buildMultiSearch(keywords, ATTACHMENTKEYWORD__KEYWORD__KEYWORD, TAGS, IMAGE_DESCRIPTION, IMAGE_OBJECT, IMAGE_PLACE, LOCALITY_LOCALITY_EN, LOCALITY_LOCALITY);
        addFieldNameAndValue("distinct", "true");
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

    public FluentPhotoArchiveSearchApiBuilder queryDateTaken(SearchField dateTaken) {
//        buildFieldParameters(DATE_TAKEN, dateTaken);
        buildFieldParameters(DATE_CREATED, dateTaken);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryNumber(SearchField imageNumber) {
        buildMultiSearch(imageNumber, IMAGE_NUMBER, IMAGESET_NUMBER);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder queryAuthorAgent(SearchField id) {
        buildMultiSearch(id, AUTHOR_AGENT, AUTHOR_FORENAME, AUTHOR_SURENAME, AUTHOR_FREE);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder querySizeX(SearchField size) {
//        buildFieldParameters(SIZE_X, size);
        buildFieldParameters(IMAGE_WIDTH, size);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder querySizeY(SearchField size) {
//        buildFieldParameters(SIZE_Y, size);
        buildFieldParameters(IMAGE_HEIGHT, size);
        return this;
    }

    public FluentPhotoArchiveSearchApiBuilder querySpecimenImageAttachment() {
        addFieldNameAndValue(SPECIMEN_IMAGE_ATTACHMENT, "2");
        return this;
    }

    private List<String> fields = Arrays.asList(
            "id",
            "filename",
            "date_taken",
            "author__agent",
            "locality_id",
            "author__forename",
            "author__surename",
            "author_free",
            "locality__locality",
            "locality__locality_en",
            "locality__country__value",
            "locality__country__value_en",
            "locality__maakond__maakond",
            "locality__vald__vald",
            "locality__asustusyksus__asustusyksus",
            "locality__maakond__maakond_en",
            "locality__vald__vald_en",
            "locality__asustusyksus__asustusyksus_en",
            "people",
            "keywords",
            "description",
            "object",
            "place",
            "image_number",
            "imageset__imageset_number",
            "imageset__imageset_series",
            "size_x",
            "size_y",
            "date_taken_free",
            "category__value",
            "category__value_en",
            "type__value",
            "type__value_en",
            "device__name",
            "copyright_agent__agent",
            "licence__licence_en",
            "licence__licence_url_en",
            "date_added",
            "date_changed",
            "user_added",
            "user_changed",
            "database__acronym",
            "device_digitised__name",
            "agent_digitised",
            "date_digitised",
            "date_digitised_free",
            "locality__longitude",
            "locality__latitude",
            "longitude",
            "latitude",
            "agent_digitised__forename",
            "agent_digitised__surename",
            "agent_digitised__date_added",
            "copyright_agent__agent",
            "uuid_filename",
            "uuid"
    );

    public FluentPhotoArchiveSearchApiBuilder addReturningFields() {
        String fields = String.join(",", this.fields);
        addFieldNameAndValue("fields", fields);
        return this;
    }
}
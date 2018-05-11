package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentSpecimenSearchApiBuilder extends FluentSearchApiBuilder<FluentSpecimenSearchApiBuilder> {

    public static FluentSpecimenSearchApiBuilder aRequest() {
        return new FluentSpecimenSearchApiBuilder();
    }

    @Override
    FluentSpecimenSearchApiBuilder getThis() {
        return this;
    }

    public FluentSpecimenSearchApiBuilder querySpecimenNumber(SearchField specimenNumber) {
        buildMultiSearch(specimenNumber, SPECIMEN_NR, SPECIMEN_ID);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryCollectionNumber(SearchField collectionNumber) {
        buildFieldParameters(COLL__NUMBER, collectionNumber);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryClassification(SearchField classification) {
        buildMultiSearch(classification, CLASSIFICATION__CLASS_FIELD, CLASSIFICATION__CLASS_EN, CLASSIFICATION__CLASS_LAT);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryNameOfFossil(SearchField nameOfFossil) {
        if (nameOfFossil != null) {
            if (nameOfFossil.getLookUpType().toString().equals("hierarchy")) {
                buildFieldParameters(SPECIMENIDENTIFICATION__TAXON__TAXON, nameOfFossil);
            } else {
                buildMultiSearch(nameOfFossil, SPECIMENIDENTIFICATION__NAME, SPECIMENIDENTIFICATION__TAXON__TAXON);
            }
        }
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryMineralRock(SearchField mineralRock) {
        buildMultiSearch(
                mineralRock,
                SPECIMENIDENTIFICATIONGEOLOGIES__NAME,
                SPECIMENIDENTIFICATIONGEOLOGIES__NAME_EN,
                SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME,
                SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryAdminUnit(SearchField adminUnit) {
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

    public FluentSpecimenSearchApiBuilder queryLocality(SearchField locality) {
        buildMultiSearch(locality, LOCALITY_LOCALITY, LOCALITY_LOCALITY_EN, LOCALITY_FREE);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryStratigraphy(SearchField stratigraphy) {
        if (stratigraphy.getLookUpType().toString().equals("hierarchy")) {
            buildMultiSearch(
                    stratigraphy,
                    STRATIGRAPHY_STRATIGRAPHY,
                    STRATIGRAPHY_STRATIGRAPHY_ENG,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG);
        } else {
            buildMultiSearch(
                    stratigraphy,
                    STRATIGRAPHY_STRATIGRAPHY,
                    STRATIGRAPHY_STRATIGRAPHY_ENG,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG,
                    STRATIGRAPHY_FREE);
        }
        return this;
    }

    @Override
    public FluentSpecimenSearchApiBuilder queryDepth(SearchField depth) {
        buildMultiSearch(depth, DEPTH, DEPTH_INTERVAL);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryCollector(SearchField collector) {
        buildMultiSearch(collector, AGENT_COLLECTED, AGENT_COLLECTED__FORENAME, AGENT_COLLECTED__SURENAME);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryReference(SearchField reference) {
        buildMultiSearch(
                reference,
                SPECIMENREFERENCE__REFERENCE__AUTHOR,
                SPECIMENREFERENCE__REFERENCE__TITLE,
                SPECIMENREFERENCE__REFERENCE__REFERENCE);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryOriginalStatus(SearchField typeStatus) {
        buildMultiSearch(typeStatus, ORIGINAL_STATUS__VALUE, ORIGINAL_STATUS__VALUE_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryPartOfFossil(SearchField partOfFossil) {
        buildFieldParameters(PART, partOfFossil);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryDateAdded(SearchField dateAdded) {
        buildFieldParameters(DATA_ADDED, dateAdded);
        return this;
    }

    public FluentSpecimenSearchApiBuilder returnDatabaseName() {
        addReturningField(DATABASE__NAME_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder returnLocalityId() {
        addReturningField(LOCALITY_ID);
        return this;
    }

    public FluentSpecimenSearchApiBuilder returnStratigraphyId() {
        addReturningField(STRATIGRAPHY_ID);
        return this;
    }

    public FluentSpecimenSearchApiBuilder returnLatitutde() {
        addReturningField(LOCALITY__LATITUDE);
        return this;
    }

    public FluentSpecimenSearchApiBuilder returnLongitude() {
        addReturningField(LOCALITY__LONGITUDE);
        return this;
    }

    /*
     * IMAGE SEARCH START
     */
    public FluentSpecimenSearchApiBuilder queryImageNotNull() {
        addFieldNameAndValue("image__isnull", "false");
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgSpecimenNumber(SearchField specimenNumber) {
        buildMultiSearch(specimenNumber, SPECIMEN__SPECIMEN_NR, SPECIMEN__SPECIMEN_ID);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgCollectionNumber(SearchField collectionNumber) {
        buildFieldParameters(SPECIMEN__COLL__NUMBER, collectionNumber);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgClassification(SearchField classification) {
        buildMultiSearch(classification, SPECIMEN__CLASSIFICATION__CLASS_FIELD, SPECIMEN__CLASSIFICATION__CLASS_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgNameOfFossil(SearchField nameOfFossil) {
        // BUG FIX: Removing specimen__specimenidentification__name from hierarchy search because of api field not available error.
        if (nameOfFossil.getLookUpType().toString().equals("hierarchy")) {
//            buildMultiSearch(nameOfFossil, SPECIMEN__SPECIMENIDENTIFICATION__TAXON__TAXON);
            buildFieldParameters(SPECIMEN__SPECIMENIDENTIFICATION__TAXON__TAXON, nameOfFossil);
        } else {
            buildMultiSearch(nameOfFossil, SPECIMEN__SPECIMENIDENTIFICATION__NAME, SPECIMEN__SPECIMENIDENTIFICATION__TAXON__TAXON);
        }
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgMineralRock(SearchField mineralRock) {
        buildMultiSearch(
                mineralRock,
                SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__NAME,
                SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__NAME_EN,
                SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME,
                SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgAdminUnit(SearchField adminUnit) {
        buildMultiSearch(
                adminUnit,
                SPECIMEN__LOCALITY_COUNTRY,
                SPECIMEN__LOCALITY_COUNTRY_EN,
                SPECIMEN__LOCALITY__MAAKOND__MAAKOND,
                SPECIMEN__LOCALITY__MAAKOND__MAAKOND_EN,
                SPECIMEN__LOCALITY__VALD__VALD,
                SPECIMEN__LOCALITY__VALD__VALD_EN,
                SPECIMEN__LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS,
                SPECIMEN__LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgLocality(SearchField locality) {
        buildMultiSearch(locality, SPECIMEN__LOCALITY__LOCALITY, SPECIMEN__LOCALITY__LOCALITY_EN, SPECIMEN__LOCALITY_FREE);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgStratigraphy(SearchField stratigraphy) {
        // BUG FIX: Removing stratigraphy_free from hierarchy search because of api 500 error.
        if (stratigraphy.getLookUpType().toString().equals("hierarchy")) {
            buildMultiSearch(
                    stratigraphy,
                    SPECIMEN__STRATIGRAPHY_STRATIGRAPHY,
                    SPECIMEN__STRATIGRAPHY_STRATIGRAPHY_EN,
                    SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY_EN);
        } else {
            buildMultiSearch(
                    stratigraphy,
                    SPECIMEN__STRATIGRAPHY_STRATIGRAPHY,
                    SPECIMEN__STRATIGRAPHY_STRATIGRAPHY_EN,
                    SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY_EN,
                    SPECIMEN__STRATIGRAPHY_FREE);
        }
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgId(SearchField specimenId) {
        buildFieldParameters(SPECIMEN__ID, specimenId);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgDepth(SearchField depth) {
        buildMultiSearch(depth, SPECIMEN__DEPTH, SPECIMEN__DEPTH_INTERVAL);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgCollector(SearchField collector) {
        buildMultiSearch(collector,
                SPECIMEN__AGENT_COLLECTED,
                SPECIMEN__AGENT_COLLECTED__FORENAME,
                SPECIMEN__AGENT_COLLECTED__SURENAME);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgReference(SearchField reference) {
        buildMultiSearch(
                reference,
                SPECIMEN__SPECIMENREFERENCE__REFERENCE__AUTHOR,
                SPECIMEN__SPECIMENREFERENCE__REFERENCE__TITLE,
                SPECIMEN__SPECIMENREFERENCE__REFERENCE__REFERENCE);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgOriginalStatus(SearchField typeStatus) {
        buildMultiSearch(typeStatus, SPECIMEN__ORIGINAL_STATUS__VALUE, SPECIMEN__ORIGINAL_STATUS__VALUE_EN);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgPartOfFossil(SearchField partOfFossil) {
        buildFieldParameters(SPECIMEN__PART, partOfFossil);
        return this;
    }

    public FluentSpecimenSearchApiBuilder queryImgDateAdded(SearchField dateAdded) {
        buildFieldParameters(SPECIMEN__DATE_ADDED, dateAdded);
        return this;
    }

}

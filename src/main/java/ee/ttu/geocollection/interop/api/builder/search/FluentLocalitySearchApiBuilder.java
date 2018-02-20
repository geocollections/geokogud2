package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.localities.service.impl.LocalitiesApiServiceImpl;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentLocalitySearchApiBuilder extends FluentSearchApiBuilder<FluentLocalitySearchApiBuilder> {

    public static FluentLocalitySearchApiBuilder aRequest() {
        return new FluentLocalitySearchApiBuilder();
    }

    @Override
    FluentLocalitySearchApiBuilder getThis() {
        return this;
    }

    public FluentLocalitySearchApiBuilder queryLocality(SearchField locality) {
        buildMultiSearch(locality, LOCALITY_ENG, LOCALITY);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryNumber(SearchField id) {
        buildFieldParameters(NUMBER, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryCountry(SearchField id) {
        buildMultiSearch(id, COUNTRY, COUNTRY_ENG);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryAdminUnit(SearchField id) {
        buildMultiSearch(id,
                COUNTRY, COUNTRY_ENG,
                COUNTY, COUNTY_ENG,
                PARISH, PARISH_EN,
                ASUSTUSYKSUS, ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryStratigraphy(SearchField id) {
        buildMultiSearch(id, LOCALITY_STRATIGRAPHY, LOCALITY_STRATIGRAPHY_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryReference(SearchField id) {
        buildFieldParameters(LOCALITYREFERENCE__REFERENCE__REFERENCE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryMaPaId(SearchField id) {
        buildFieldParameters(MA_ID, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryLatitude(SearchField id) {
        buildFieldParameters(LATITUDE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryLongitude(SearchField id) {
        buildFieldParameters(LONGITUDE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnLocalitySynonym() {
        addReturningField(LOCALITYSYNONYM_SYNONYM);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityTopEn() {
        addReturningField(LOCALITY_STRATIGRAPHY_TOP_EN);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityTop() {
        addReturningField(LOCALITY_STRATIGRAPHY_TOP);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityTopId() {
        addReturningField(LOCALITY_STRATIGRAPHY_TOP_ID);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityBaseEn() {
        addReturningField(LOCALITY_STRATIGRAPHY_BASE_EN);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityBase() {
        addReturningField(LOCALITY_STRATIGRAPHY_BASE);
        return this;
    }
    public FluentLocalitySearchApiBuilder returnLocalityBaseId() {
        addReturningField(LOCALITY_STRATIGRAPHY_BASE_ID);
        return this;
    }

    /*
     * IMAGE search START
     */
    public FluentLocalitySearchApiBuilder queryLocalityIdNotNull() {
        addFieldNameAndValue("locality_id__isnull", "false");
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgLocality(SearchField locality) {
        buildMultiSearch(locality, LOCALITY__LOCALITY_EN, LOCALITY__LOCALITY);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgNumber(SearchField number) {
        buildFieldParameters(LOCALITY__NUMBER, number);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgCountry(SearchField country) {
        buildMultiSearch(country, LOCALITY__COUNTRY, LOCALITY__COUNTRY_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgAdminUnit(SearchField adminUnit) {
        buildMultiSearch(adminUnit,
                LOCALITY__COUNTRY,
                LOCALITY__COUNTRY_EN,
                LOCALITY__COUNTY,
                LOCALITY__COUNTY_EN,
                LOCALITY__PARISH,
                LOCALITY__PARISH_EN,
                LOCALITY__ASUSTUSYKSUS,
                LOCALITY__ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgStratigraphy(SearchField id) {
        buildMultiSearch(id, LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY, LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgReference(SearchField id) {
        buildFieldParameters(LOCALITY__LOCALITYREFERENCE__REFERENCE__REFERENCE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgId(SearchField id) {
        buildFieldParameters(LOCALITY__ID, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgMaPaId(SearchField id) {
        buildFieldParameters(LOCALITY__MA_ID, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgLatitude(SearchField id) {
        buildFieldParameters(LOCALITY__LATITUDE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgLongitude(SearchField id) {
        buildFieldParameters(LOCALITY__LONGITUDE, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder queryImgDepth(SearchField id) {
        buildFieldParameters(LOCALITY__DEPTH, id);
        return this;
    }

    /*
     * SPECIMEN search START
     */

    public FluentLocalitySearchApiBuilder querySpecimenIdForUrl(SearchField id) {
        buildFieldParameters(LOCALITY__ID, id);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnStratigraphyId() {
        addReturningField(STRATIGRAPHY_ID);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnStratigraphy() {
        addReturningField(STRATIGRAPHY_STRATIGRAPHY);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnStratigraphyEn() {
        addReturningField(STRATIGRAPHY_STRATIGRAPHY_ENG);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnTaxonId() {
        addReturningField(SPECIMENIDENTIFICATION__TAXON_ID);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnTaxon() {
        addReturningField(SPECIMENIDENTIFICATION__TAXON__TAXON);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnTaxonName() {
        addReturningField(SPECIMENIDENTIFICATION__NAME);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnGeologiesName() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__NAME);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnGeologiesNameEn() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__NAME_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnRockId() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__ID);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnRockName() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnRockNameEn() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME_EN);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnTaxonCurrent() {
        addReturningField(SPECIMENIDENTIFICATION__CURRENT);
        return this;
    }

    public FluentLocalitySearchApiBuilder returnGeologiesCurrent() {
        addReturningField(SPECIMENIDENTIFICATIONGEOLOGIES__CURRENT);
        return this;
    }

}

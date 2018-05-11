package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentSampleSearchApiBuilder extends FluentSearchApiBuilder<FluentSampleSearchApiBuilder>{

    public static FluentSampleSearchApiBuilder aRequest() {
        return new FluentSampleSearchApiBuilder();
    }

    @Override
    FluentSampleSearchApiBuilder getThis() {
        return this;
    }

    public FluentSampleSearchApiBuilder queryNumber(SearchField number) {
        buildMultiSearch(number, NUMBER, NUMBER_ADDITIONAL, NUMBER_FIELD, ID);
        return this;
    }

    public FluentSampleSearchApiBuilder queryLocality(SearchField location) {
        buildMultiSearch(location, LOCALITY_LOCALITY, LOCALITY_LOCALITY_EN, LOCALITY_FREE);
        return this;
    }

    @Override
    public FluentSampleSearchApiBuilder queryDepth(SearchField depth) {
        buildMultiSearch(depth, DEPTH, DEPTH_INTERVAL);
        return this;
    }

    public FluentSampleSearchApiBuilder queryStratigraphy(SearchField stratigraphy) {
        if (stratigraphy.getLookUpType().toString().equals("hierarchy")) {
            buildMultiSearch(stratigraphy,
                    STRATIGRAPHY_STRATIGRAPHY,
                    STRATIGRAPHY_STRATIGRAPHY_ENG,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG);
        } else {
            buildMultiSearch(stratigraphy,
                    STRATIGRAPHY_STRATIGRAPHY,
                    STRATIGRAPHY_STRATIGRAPHY_ENG,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY,
                    LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG,
                    STRATIGRAPHY_FREE);
        }
        return this;
    }

    public FluentSampleSearchApiBuilder queryStratigraphyBed(SearchField stratigraphyBed) {
        buildMultiSearch(stratigraphyBed,
                STRATIGRAPHY_BED,
                STRATIGRAPHY_FREE,
                LITHOSTRATIGRAPHY__STRATIGRAPHY,
                LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG);
        return this;
    }

    public FluentSampleSearchApiBuilder queryAgent(SearchField agent) {
        buildMultiSearch(agent,
                AGENT_COLLECTED,
                AGENT_COLLECTED__FORENAME,
                AGENT_COLLECTED__SURENAME);
        return this;
    }

    public FluentSampleSearchApiBuilder queryMass(SearchField mass) {
        buildFieldParameters(MASS, mass);
        return this;
    }

    public FluentSampleSearchApiBuilder queryId(SearchField id) {
        buildFieldParameters(ID, id);
        return this;
    }

    public FluentSampleSearchApiBuilder queryCountry(SearchField country) {
        buildMultiSearch(country,
                LOCALITY_COUNTRY,
                LOCALITY_COUNTRY_ENG,
                LOCALITY__COUNTRY__ISO_CODE,
                LOCALITY__MAAKOND__MAAKOND,
                LOCALITY__MAAKOND__MAAKOND_EN,
                LOCALITY__VALD__VALD,
                LOCALITY__VALD__VALD_ENG,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentSampleSearchApiBuilder queryLocation(SearchField location) {
        buildFieldParameters(LOCATION, location);
        return this;
    }

    public FluentSampleSearchApiBuilder queryTaxon(SearchField agent) {
        buildFieldParameters(TAXONLIST__TAXON__TAXON, agent);
        return this;
    }

    public FluentSampleSearchApiBuilder queryFrequency(SearchField agent) {
        buildFieldParameters(TAXONLIST__FREQUENCY, agent);
        if (agent != null) {
            if (!agent.toString().equals("name=null")) {
                addFieldNameAndValue("distinct", "true"); // BUG FIX for duplicates
            }
        }
        return this;
    }

    public FluentSampleSearchApiBuilder queryAnalysisMethod(SearchField analysisMethod) {
        buildMultiSearch(analysisMethod, ANALYSIS_METHOD, ANALYSIS_METHOD_EN);
        return this;
    }

    public FluentSampleSearchApiBuilder queryComponet(SearchField agent) {
        buildFieldParameters(ANALYSIS_RESULTS_NAME, agent);
        return this;
    }

    public FluentSampleSearchApiBuilder queryContent(SearchField agent) {
        buildFieldParameters(ANALYSIS_RESULTS_VALUE, agent);
        return this;
    }

    public FluentSampleSearchApiBuilder returnAnalyzed() {
        addReturningField(ANALYZED);
        return this;
    }

    public FluentSampleSearchApiBuilder returnLocalityId() {
        addReturningField(LOCALITY_ID);
        return this;
    }

    public FluentSampleSearchApiBuilder returnStratigraphyId() {
        addReturningField(STRATIGRAPHY_ID);
        return this;
    }

    public FluentSampleSearchApiBuilder returnLithostratigraphyId() {
        addReturningField(LITHOSTRATIGRAPHY_ID);
        return this;
    }

    public FluentSampleSearchApiBuilder returnLocalitylongitude() {
        addReturningField(LOCALITY_LONGITUDE);
        return this;
    }

    public FluentSampleSearchApiBuilder returnLocalitylatitude() {
        addReturningField(LOCALITY_LATITUDE);
        return this;
    }

    /* Related Specimens START */
    public FluentSampleSearchApiBuilder querySampleIdForUrl(SearchField sampleId) {
        buildFieldParameters(SAMPLE_ID, sampleId);
        return this;
    }

    public FluentSampleSearchApiBuilder returnSpecimenId() {
        addReturningField(SPECIMEN_ID);
        return this;
    }

    public FluentSampleSearchApiBuilder returnClassification() {
        addReturningField(CLASSIFICATION__CLASS_EN);
        return this;
    }

    public FluentSampleSearchApiBuilder returnTaxonName() {
        addReturningField(SPECIMENIDENTIFICATION__NAME);
        return this;
    }
    /* Related Specimens END */



}
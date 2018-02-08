package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.analyses.search.impl.AnalysesApiServiceImpl;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;
public class FluentAnalysesApiBuilder  extends FluentSearchApiBuilder<FluentAnalysesApiBuilder> {

    @Override
    FluentAnalysesApiBuilder getThis() { return this; }

    public static FluentAnalysesApiBuilder aRequest() {
        return new FluentAnalysesApiBuilder();
    }

    public FluentAnalysesApiBuilder queryLocality(SearchField location) {
        buildMultiSearch(location, SAMPLE_LOCALITY_LOCALITY, SAMPLE_LOCALITY_LOCALITY_EN, SAMPLE_LOCALITY_FREE);
        return this;
    }

    public FluentAnalysesApiBuilder queryDepth(SearchField depth) {
        buildMultiSearch(depth, SAMPLE_DEPTH, SAMPLE_DEPTH_INTERVAL);
        return this;
    }

    public FluentAnalysesApiBuilder queryStratigraphy(SearchField stratigraphy) {
        buildMultiSearch(stratigraphy,
                "sample__stratigraphy__stratigraphy",
                "sample__stratigraphy__stratigraphy_en",
                "sample__lithostratigraphy__stratigraphy",
                "sample__lithostratigraphy__stratigraphy_en");
        return this;
    }

    public FluentAnalysesApiBuilder queryStratigraphyBed(SearchField field) {
        buildFieldParameters(SAMPLE_STRATIGRAPHY_BED, field);
        return this;
    }

    public FluentAnalysesApiBuilder queryAnalysisMethod(SearchField analysisMethod) {
        buildMultiSearch(analysisMethod, ANALYSIS_METHOD__ANALYSIS_METHOD, ANALYSIS_METHOD__METHOD_EN);
        return this;
    }

    public FluentAnalysesApiBuilder queryComponentAnalysed(SearchField component) {
        buildFieldParameters(ANALYSISRESULTS__NAME, component);
        return this;
    }

    public FluentAnalysesApiBuilder queryContent(SearchField content) {
        buildFieldParameters(ANALYSISRESULTS__VALUE, content);
        if (content != null) {
            addFieldNameAndValue("distinct", "true"); //removes duplicates if content is entered
        }
        return this;
    }

    public FluentAnalysesApiBuilder querySample(SearchField sample) {
        buildMultiSearch(sample, SAMPLE__ID, SAMPLE_NUMBER);
//        buildFieldParameters(SAMPLE__ID, sample);
        return this;
    }

    public FluentAnalysesApiBuilder queryAdminUnit(SearchField adminUnit) {
        buildMultiSearch(adminUnit,
                "sample__locality__country__value",
                "sample__locality__country__value_en",
                "sample__locality__maakond__maakond",
                "sample__locality__maakond__maakond_en",
                "sample__locality__vald__vald",
                "sample__locality__vald__vald_en",
                "sample__locality__asustusyksus__asustusyksus",
                "sample__locality__asustusyksus__asustusyksus_en");
        return this;
    }
    //        return this;
    //        buildFieldParameters(DATE_FREE, dateFree);
    //    public FluentAnalysesApiBuilder queryDateFree(SearchField dateFree) {
    //    }
    //        return this;
    //        buildFieldParameters(DATE, date);
    //    public FluentAnalysesApiBuilder queryDate(SearchField date) {
    //
    //    }
    //        return this;
    //        buildFieldParameters(INSTRUMENT_TXT, instrumentTxt);
    //    public FluentAnalysesApiBuilder queryInstrumentTxt(SearchField instrumentTxt) {
    //    }
    //        return this;
    //        buildFieldParameters(INSTRUMENT, instrument);
    //    public FluentAnalysesApiBuilder queryInstrument(SearchField instrument) {
    //    }
    //        return this;
    //        buildFieldParameters(LAB, lab);
    //    public FluentAnalysesApiBuilder queryLab(SearchField lab) {
    //    }
    //        return this;
    //        buildFieldParameters(METHOD_DETAILS, methodDetails);
//    public FluentAnalysesApiBuilder queryMethodDetails(SearchField methodDetails) {

//    }

    public FluentAnalysesApiBuilder returnStratigraphyId(){
        addReturningField(SAMPLE_STRATIGRAPHY_ID);
        return this;
    }

    public FluentAnalysesApiBuilder returnLithostratigraphy(){
        addReturningField(SAMPLE_LITHOSTRATIGRAPHY__STRATIGRAPHY);
        return this;
    }

    public FluentAnalysesApiBuilder returnLithostratigraphyEn(){
        addReturningField(SAMPLE_LITHOSTRATIGRAPHY__STRATIGRAPHY_EN);
        return this;
    }

    public FluentAnalysesApiBuilder returnLithostratigraphyId(){
        addReturningField(SAMPLE_LITHOSTRATIGRAPHY_ID);
        return this;
    }

    public FluentAnalysesApiBuilder returnLocalityId(){
        addReturningField(SAMPLE_LOCALITY_ID);
        return this;
    }

    public FluentAnalysesApiBuilder returnSampleNumber(){
        addReturningField(SAMPLE_NUMBER);
        return this;
    }

    public FluentAnalysesApiBuilder returnLabTxt(){
        addReturningField(LAB_TXT);
        return this;
    }

}

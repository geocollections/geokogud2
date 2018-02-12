package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentStratigraphySearchApiBuilder extends FluentSearchApiBuilder<FluentStratigraphySearchApiBuilder>{

    public static FluentStratigraphySearchApiBuilder aRequest() {
        return new FluentStratigraphySearchApiBuilder();
    }

    @Override
    FluentStratigraphySearchApiBuilder getThis() {
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryId(SearchField id) {
        buildFieldParameters(ID, id);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryStratigraphy(SearchField stratigraphy){
        buildMultiSearch(stratigraphy, STRATIGRAPHY, STRATIGRAPHY_EN);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryIndex(SearchField index){
        buildMultiSearch(index, INDEX_MAIN, INDEX_ADDITIONAL);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryAgeBase(SearchField ageBase) {
        buildFieldParameters(AGE_BASE, ageBase);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryLithology(SearchField lithology) {
        buildMultiSearch(lithology, LITHOLOGY, LITHOLOGY_EN);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryAuthor(SearchField author) {
        buildFieldParameters(AUTHOR_FREE, author);
        return this;
    }

    public FluentStratigraphySearchApiBuilder returnAgeChronostratigraphyId() {
        addReturningField(AGE_CHRONOSTRATIGRAPHY_ID);
        return this;
    }

    public FluentStratigraphySearchApiBuilder returnParentId() {
        addReturningField(PARENT_ID);
        return this;
    }

    public FluentStratigraphySearchApiBuilder returnStratigraphy() {
        addReturningField(STRATIGRAPHY);
        return this;
    }

    public FluentStratigraphySearchApiBuilder returnStratigraphyEn() {
        addReturningField(STRATIGRAPHY_EN);
        return this;
    }

    public FluentStratigraphySearchApiBuilder returnStratigraphyId() {
        addReturningField(ID);
        return this;
    }

    public FluentStratigraphySearchApiBuilder queryCorrectParentId(SearchField parentId) {
        buildFieldParameters(PARENT_ID, parentId);
        return this;
    }


    /* LITHOSTRATIGRAPHY START */
    public FluentStratigraphySearchApiBuilder queryAgeChronostratigraphyIdForUrl(SearchField ageChronoId) {
        buildFieldParameters(AGE_CHRONOSTRATIGRAPHY_ID, ageChronoId);
        return this;
    }
    /* LITHOSTRATIGRAPHY END */

    /* OVERLAIN_BY START */
    public FluentStratigraphySearchApiBuilder queryAgeTopForAgeBase(SearchField ageTop) {
        buildFieldParameters(AGE_BASE, ageTop);
        return this;
    }
    /* OVERLAIN_BY END */

    /* OVERLIES START */
    public FluentStratigraphySearchApiBuilder queryAgeBaseForAgeTop(SearchField ageBase) {
        buildFieldParameters(AGE_TOP, ageBase);
        return this;
    }
    /* OVERLIES END */

}

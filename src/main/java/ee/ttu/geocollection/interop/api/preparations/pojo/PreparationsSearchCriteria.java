package ee.ttu.geocollection.interop.api.preparations.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

public class PreparationsSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField number;
    private SearchField locality;
    private SearchField depthSince;
    private SearchField depthTo;
    private SearchField stratigraphy;
    private SearchField collector;
    private SearchField speciesRecovered;
    private SearchField speciesFrequency;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPaginateBy() {
        return paginateBy;
    }

    public void setPaginateBy(int paginateBy) {
        this.paginateBy = paginateBy;
    }

    public SortField getSortField() {
        return sortField;
    }

    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }


    public SearchField getNumber() {
        return number;
    }

    public void setNumber(SearchField number) {
        this.number = number;
    }

    public SearchField getLocality() {
        return locality;
    }

    public void setLocality(SearchField locality) {
        this.locality = locality;
    }

    public SearchField getDepthSince() {
        return depthSince;
    }

    public void setDepthSince(SearchField depthSince) {
        this.depthSince = depthSince;
    }

    public SearchField getDepthTo() {
        return depthTo;
    }

    public void setDepthTo(SearchField depthTo) {
        this.depthTo = depthTo;
    }

    public SearchField getStratigraphy() {
        return stratigraphy;
    }

    public void setStratigraphy(SearchField stratigraphy) {
        this.stratigraphy = stratigraphy;
    }

    public SearchField getCollector() {
        return collector;
    }

    public void setCollector(SearchField collector) {
        this.collector = collector;
    }

    public SearchField getSpeciesRecovered() {
        return speciesRecovered;
    }

    public void setSpeciesRecovered(SearchField speciesRecovered) {
        this.speciesRecovered = speciesRecovered;
    }

    public SearchField getSpeciesFrequency() {
        return speciesFrequency;
    }

    public void setSpeciesFrequency(SearchField speciesFrequency) {
        this.speciesFrequency = speciesFrequency;
    }
}

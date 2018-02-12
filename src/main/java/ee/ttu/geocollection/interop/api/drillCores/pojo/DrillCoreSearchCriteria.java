package ee.ttu.geocollection.interop.api.drillCores.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

import java.util.List;

public class DrillCoreSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField drillcore;
    private SearchField country;
    private SearchField stratigraphy;
    private SearchField storageLocation;
    private SearchField boxesSince;
    private SearchField boxesTo;
    private SearchField id;
    private SearchField adminUnit;

    private List<String> dbs;


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


    public SearchField getDrillcore() {
        return drillcore;
    }

    public void setDrillcore(SearchField drillcore) {
        this.drillcore = drillcore;
    }

    public SearchField getCountry() {
        return country;
    }

    public void setCountry(SearchField country) {
        this.country = country;
    }

    public SearchField getStratigraphy() {
        return stratigraphy;
    }

    public void setStratigraphy(SearchField stratigraphy) {
        this.stratigraphy = stratigraphy;
    }

    public SearchField getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(SearchField storageLocation) {
        this.storageLocation = storageLocation;
    }

    public SearchField getBoxesSince() {
        return boxesSince;
    }

    public void setBoxesSince(SearchField boxesSince) {
        this.boxesSince = boxesSince;
    }

    public SearchField getBoxesTo() {
        return boxesTo;
    }

    public void setBoxesTo(SearchField boxesTo) {
        this.boxesTo = boxesTo;
    }

    public SearchField getId() {
        return id;
    }

    public void setId(SearchField id) {
        this.id = id;
    }

    public SearchField getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(SearchField adminUnit) {
        this.adminUnit = adminUnit;
    }


    public List<String> getDbs() {
        return dbs;
    }

    public void setDbs(List<String> dbs) {
        this.dbs = dbs;
    }
}

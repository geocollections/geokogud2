package ee.ttu.geocollection.interop.api.stratigraphies.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

public class StratigraphySearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField id;
    private SearchField stratigraphy;
    private SearchField index;
    private SearchField ageMinY;
    private SearchField mainLithology;
    private SearchField author;


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


    public SearchField getId() {
        return id;
    }

    public void setId(SearchField id) {
        this.id = id;
    }

    public SearchField getStratigraphy() {
        return stratigraphy;
    }

    public void setStratigraphy(SearchField stratigraphy) {
        this.stratigraphy = stratigraphy;
    }

    public SearchField getIndex() {
        return index;
    }

    public void setIndex(SearchField index) {
        this.index = index;
    }

    public SearchField getAgeMinY() {
        return ageMinY;
    }

    public void setAgeMinY(SearchField ageMinY) {
        this.ageMinY = ageMinY;
    }

    public SearchField getMainLithology() {
        return mainLithology;
    }

    public void setMainLithology(SearchField mainLithology) {
        this.mainLithology = mainLithology;
    }

    public SearchField getAuthor() {
        return author;
    }

    public void setAuthor(SearchField author) {
        this.author = author;
    }
}

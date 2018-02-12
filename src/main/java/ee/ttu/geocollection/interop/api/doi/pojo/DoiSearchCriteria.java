package ee.ttu.geocollection.interop.api.doi.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

import java.util.List;

public class DoiSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField doi;
    private SearchField title;
    private SearchField publishedBy;
    private SearchField yearSince;
    private SearchField yearTo;
    private SearchField author;
    private SearchField abstractText;

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


    public SearchField getDoi() {
        return doi;
    }

    public void setDoi(SearchField doi) {
        this.doi = doi;
    }

    public SearchField getTitle() {
        return title;
    }

    public void setTitle(SearchField title) {
        this.title = title;
    }

    public SearchField getPublishedBy() { return publishedBy; }

    public void setPublishedBy(SearchField publishedBy) {
        this.publishedBy = publishedBy;
    }

    public SearchField getYearSince() {
        return yearSince;
    }

    public void setYearSince(SearchField yearSince) {
        this.yearSince = yearSince;
    }

    public SearchField getYearTo() {
        return yearTo;
    }

    public void setYearTo(SearchField yearTo) {
        this.yearTo = yearTo;
    }

    public SearchField getAuthor() {
        return author;
    }

    public void setAuthor(SearchField author) {
        this.author = author;
    }

    public SearchField getAbstractText() { return abstractText; }

    public void setAbstractText(SearchField abstractText) { this.abstractText = abstractText; }


    public List<String> getDbs() {
        return dbs;
    }

    public void setDbs(List<String> dbs) {
        this.dbs = dbs;
    }
}

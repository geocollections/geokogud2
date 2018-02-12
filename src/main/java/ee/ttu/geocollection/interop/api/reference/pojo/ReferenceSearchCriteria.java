package ee.ttu.geocollection.interop.api.reference.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

public class ReferenceSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField id;
    private SearchField author;
    private SearchField yearSince;
    private SearchField yearTo;
    private SearchField title;
    private SearchField journal;
    private SearchField book;
    private SearchField abstracts;
    private SearchField keywords;


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

    public SearchField getAuthor() {
        return author;
    }

    public void setAuthor(SearchField author) {
        this.author = author;
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

    public SearchField getTitle() {
        return title;
    }

    public void setTitle(SearchField title) {
        this.title = title;
    }

    public SearchField getJournal() {
        return journal;
    }

    public void setJournal(SearchField journal) {
        this.journal = journal;
    }

    public SearchField getBook() {
        return book;
    }

    public void setBook(SearchField book) {
        this.book = book;
    }

    public SearchField getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(SearchField abstracts) {
        this.abstracts = abstracts;
    }

    public SearchField getKeywords() {
        return keywords;
    }

    public void setKeywords(SearchField keywords) {
        this.keywords = keywords;
    }
}

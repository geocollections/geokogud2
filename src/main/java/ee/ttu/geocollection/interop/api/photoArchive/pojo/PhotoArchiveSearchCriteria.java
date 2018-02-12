package ee.ttu.geocollection.interop.api.photoArchive.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

import java.util.List;

public class PhotoArchiveSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField locality;
    private SearchField people;
    private SearchField keywords;
    private SearchField adminUnit;
    private SearchField dateTakenSince;
    private SearchField dateTakenTo;
    private SearchField imageNumber;
    private SearchField authorAgent;
    private SearchField sizeSince;
    private SearchField sizeTo;

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


    public SearchField getLocality() {
        return locality;
    }

    public void setLocality(SearchField locality) {
        this.locality = locality;
    }

    public SearchField getPeople() {
        return people;
    }

    public void setPeople(SearchField people) {
        this.people = people;
    }

    public SearchField getKeywords() {
        return keywords;
    }

    public void setKeywords(SearchField keywords) {
        this.keywords = keywords;
    }

    public SearchField getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(SearchField adminUnit) {
        this.adminUnit = adminUnit;
    }

    public SearchField getDateTakenSince() {
        return dateTakenSince;
    }

    public void setDateTakenSince(SearchField dateTakenSince) {
        this.dateTakenSince = dateTakenSince;
    }

    public SearchField getDateTakenTo() {
        return dateTakenTo;
    }

    public void setDateTakenTo(SearchField dateTakenTo) {
        this.dateTakenTo = dateTakenTo;
    }

    public SearchField getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(SearchField imageNumber) {
        this.imageNumber = imageNumber;
    }

    public SearchField getAuthorAgent() {
        return authorAgent;
    }

    public void setAuthorAgent(SearchField authorAgent) {
        this.authorAgent = authorAgent;
    }

    public SearchField getSizeSince() {
        return sizeSince;
    }

    public void setSizeSince(SearchField sizeSince) {
        this.sizeSince = sizeSince;
    }

    public SearchField getSizeTo() {
        return sizeTo;
    }

    public void setSizeTo(SearchField sizeTo) {
        this.sizeTo = sizeTo;
    }


    public List<String> getDbs() {
        return dbs;
    }

    public void setDbs(List<String> dbs) {
        this.dbs = dbs;
    }
}

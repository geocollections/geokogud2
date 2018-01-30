package ee.ttu.geocollection.interop.api.photoArchive.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.api.PageableSearchCriteria;

import java.util.List;

public class PhotoArchiveSearchCriteria implements PageableSearchCriteria{
    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField id;
    private SearchField fileName;
    private SearchField dateTakenSince;
    private SearchField dateTakenTo;
    private SearchField authorAgent;
    private SearchField locality;
    private SearchField keywords;
    private SearchField imageNumber;
    private SearchField people;
    private SearchField adminUnit;
    private SearchField sizeSince;
    private SearchField sizeTo;

    public int getPaginateBy() {
        return paginateBy;
    }

    public void setPaginateBy(int paginateBy) {
        this.paginateBy = paginateBy;
    }

    private List<String> dbs;

    public List<String> getDbs() {
        return dbs;
    }

    public void setDbs(List<String> dbs) {
        this.dbs = dbs;
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

    public SearchField getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(SearchField adminUnit) {
        this.adminUnit = adminUnit;
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

    public SearchField getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(SearchField imageNumber) {
        this.imageNumber = imageNumber;
    }

    public SearchField getKeywords() {
        return keywords;
    }

    public void setKeywords(SearchField keywords) {
        this.keywords = keywords;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
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

    public SearchField getFileName() {
        return fileName;
    }

    public void setFileName(SearchField fileName) {
        this.fileName = fileName;
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

    public SearchField getAuthorAgent() {
        return authorAgent;
    }

    public void setAuthorAgent(SearchField authorAgent) {
        this.authorAgent = authorAgent;
    }
}

package ee.ttu.geocollection.interop.api.specimen.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

import java.util.List;

public class SpecimenSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField searchImages;
    private SearchField specimenNumber;
    private SearchField collectionNumber;
    private SearchField classification;
    private SearchField fossilName;
    private SearchField mineralRock;
    private SearchField adminUnit;
    private SearchField locality;
    private SearchField stratigraphy;
    private SearchField id;
    private SearchField depthSince;
    private SearchField depthTo;
    private SearchField collector;
    private SearchField reference;
    private SearchField typeStatus;
    private SearchField partOfFossil;
    private SearchField dateTakenSince;
    private SearchField dateTakenTo;

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


    public SearchField getSearchImages() {
        return searchImages;
    }

    public void setSearchImages(SearchField searchImages) {
        this.searchImages = searchImages;
    }

    public SearchField getSpecimenNumber() {
        return specimenNumber;
    }

    public void setSpecimenNumber(SearchField specimenNumber) {
        this.specimenNumber = specimenNumber;
    }

    public SearchField getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(SearchField collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public SearchField getClassification() {
        return classification;
    }

    public void setClassification(SearchField classification) {
        this.classification = classification;
    }

    public SearchField getFossilName() {
        return fossilName;
    }

    public void setFossilName(SearchField fossilName) {
        this.fossilName = fossilName;
    }

    public SearchField getMineralRock() {
        return mineralRock;
    }

    public void setMineralRock(SearchField mineralRock) {
        this.mineralRock = mineralRock;
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

    public SearchField getStratigraphy() {
        return stratigraphy;
    }

    public void setStratigraphy(SearchField stratigraphy) {
        this.stratigraphy = stratigraphy;
    }

    public SearchField getId() {
        return id;
    }

    public void setId(SearchField id) {
        this.id = id;
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

    public SearchField getCollector() {
        return collector;
    }

    public void setCollector(SearchField collector) {
        this.collector = collector;
    }

    public SearchField getReference() {
        return reference;
    }

    public void setReference(SearchField reference) {
        this.reference = reference;
    }

    public SearchField getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(SearchField typeStatus) {
        this.typeStatus = typeStatus;
    }

    public SearchField getPartOfFossil() {
        return partOfFossil;
    }

    public void setPartOfFossil(SearchField partOfFossil) {
        this.partOfFossil = partOfFossil;
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


    public List<String> getDbs() {
        return dbs;
    }

    public void setDbs(List<String> dbs) {
        this.dbs = dbs;
    }
}

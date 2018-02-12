package ee.ttu.geocollection.interop.api.localities.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

public class LocalitySearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField searchImages;
    private SearchField locality;
    private SearchField number;
    private SearchField country;
    private SearchField adminUnit;
    private SearchField stratigraphy;
    private SearchField reference;
    private SearchField id;
    private SearchField maPaId;
    private SearchField latitude;
    private SearchField longitude;
    private SearchField verticalExtentSince;
    private SearchField verticalExtentTo;


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

    public SearchField getLocality() {
        return locality;
    }

    public void setLocality(SearchField locality) {
        this.locality = locality;
    }

    public SearchField getNumber() {
        return number;
    }

    public void setNumber(SearchField number) {
        this.number = number;
    }

    public SearchField getCountry() {
        return country;
    }

    public void setCountry(SearchField country) {
        this.country = country;
    }

    public SearchField getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(SearchField adminUnit) {
        this.adminUnit = adminUnit;
    }

    public SearchField getStratigraphy() {
        return stratigraphy;
    }

    public void setStratigraphy(SearchField stratigraphy) {
        this.stratigraphy = stratigraphy;
    }

    public SearchField getReference() {
        return reference;
    }

    public void setReference(SearchField reference) {
        this.reference = reference;
    }

    public SearchField getId() {
        return id;
    }

    public void setId(SearchField id) {
        this.id = id;
    }

    public SearchField getMaPaId() {
        return maPaId;
    }

    public void setMaPaId(SearchField maPaId) {
        this.maPaId = maPaId;
    }

    public SearchField getLatitude() {
        return latitude;
    }

    public void setLatitude(SearchField latitude) {
        this.latitude = latitude;
    }

    public SearchField getLongitude() {
        return longitude;
    }

    public void setLongitude(SearchField longitude) {
        this.longitude = longitude;
    }

    public SearchField getVerticalExtentSince() {
        return verticalExtentSince;
    }

    public void setVerticalExtentSince(SearchField verticalExtentSince) {
        this.verticalExtentSince = verticalExtentSince;
    }

    public SearchField getVerticalExtentTo() {
        return verticalExtentTo;
    }

    public void setVerticalExtentTo(SearchField verticalExtentTo) {
        this.verticalExtentTo = verticalExtentTo;
    }
}

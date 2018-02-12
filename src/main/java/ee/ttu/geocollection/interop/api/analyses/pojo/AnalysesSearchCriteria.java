package ee.ttu.geocollection.interop.api.analyses.pojo;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.domain.SortField;

import java.util.List;

public class AnalysesSearchCriteria {

    private int page = 1;
    private int paginateBy = 25;
    private SortField sortField;

    private SearchField id;
    private SearchField locality;
    private SearchField depthSince;
    private SearchField depthTo;
    private SearchField stratigraphy;
    private SearchField stratigraphyBed;
    private SearchField analysisMethod;
    private SearchField componentAnalysed;
    private SearchField content;
    private SearchField sample;
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


    public SearchField getId() { return id; }

    public void setId(SearchField id) {
        this.id = id;
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

    public SearchField getStratigraphyBed() {
        return stratigraphyBed;
    }

    public void setStratigraphyBed(SearchField stratigraphyBed) {
        this.stratigraphyBed = stratigraphyBed;
    }

    public SearchField getAnalysisMethod() { return analysisMethod; }

    public void setAnalysisMethod(SearchField analysisMethod) { this.analysisMethod = analysisMethod; }

    public SearchField getComponentAnalysed() {
        return componentAnalysed;
    }

    public void setComponentAnalysed(SearchField componentAnalysed) {
        this.componentAnalysed = componentAnalysed;
    }

    public SearchField getContent() {
        return content;
    }

    public void setContent(SearchField content) {
        this.content = content;
    }

    public SearchField getSample() { return sample; }

    public void setSample(SearchField sample) { this.sample = sample; }

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

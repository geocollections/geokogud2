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
//    private SearchField methodDetails;
//    private SearchField lab;
//    private SearchField instrument;
//    private SearchField instrumentTxt;
//    private SearchField date;
//    private SearchField dateFree;

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

    public SearchField getLocality() {
        return locality;
    }

    public void setLocality(SearchField locality) {
        this.locality = locality;
    }

    public SearchField getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(SearchField adminUnit) {
        this.adminUnit = adminUnit;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public SearchField getAnalysisMethod() { return analysisMethod; }

    public void setAnalysisMethod(SearchField analysisMethod) { this.analysisMethod = analysisMethod; }

//    public SearchField getMethodDetails() { return methodDetails; }
//
//    public void setMethodDetails(SearchField methodDetails) { this.methodDetails = methodDetails; }
//
//    public SearchField getLab() { return lab; }
//
//    public void setLab(SearchField lab) { this.lab = lab; }
//
//    public SearchField getInstrument() { return instrument; }
//
//    public void setInstrument(SearchField instrument) { this.instrument = instrument; }
//
//    public SearchField getInstrumentTxt() { return instrumentTxt; }
//
//    public void setInstrumentTxt(SearchField instrumentTxt) { this.instrumentTxt = instrumentTxt; }
//
    public SearchField getSample() { return sample; }

    public void setSample(SearchField sample) { this.sample = sample; }
//
//    public SearchField getDate() { return date; }
//
//    public void setDate(SearchField date) { this.date = date; }
//
//    public SearchField getDateFree() { return dateFree; }
//
//    public void setDateFree(SearchField dateFree) { this.dateFree = dateFree; }

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
}

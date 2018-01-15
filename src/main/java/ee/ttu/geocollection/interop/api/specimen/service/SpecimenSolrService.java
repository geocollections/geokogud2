package ee.ttu.geocollection.interop.api.specimen.service;

import ee.ttu.geocollection.interop.api.Response.SolrResponse;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;

import java.util.Map;

public interface SpecimenSolrService {

    SolrResponse findSpecimen(SpecimenSearchCriteria specimenSearchCriteria);

    Map findSpecimenById(Long id);

    SolrResponse findSpecimenByIndex(String query);

}

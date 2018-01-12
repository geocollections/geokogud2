package ee.ttu.geocollection.interop.api.specimen.service;

import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;

import java.util.Map;

public interface SpecimenSolrService {

    Map findSpecimen(SpecimenSearchCriteria specimenSearchCriteria);

    Map findSpecimenById(Long id);

    Map findSpecimenByIndex(String query);

}

package ee.ttu.geocollection.interop.solr.specimen.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.solr.specimen.service.SpecimenSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SpecimenSolrServiceImpl implements SpecimenSolrService {

    private final static String SPECIMEN_TABLE = "specimen";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findSpecimen(SpecimenSearchCriteria specimenSearchCriteria) {
        String requestParams = "";
        return solrService.searchRawEntities(
                SPECIMEN_TABLE,
                30,
                specimenSearchCriteria.getPage(),
                specimenSearchCriteria.getSortField(),
                requestParams);
    }

    @Override
    public Map findSpecimenById(Long id) {
        String requestParams = FluentGeoApiDetailsBuilder.aRequest()
                .id(id)
                .buildWithDefaultReturningFields();
        return solrService.findRawEntity(SPECIMEN_TABLE, requestParams);
    }

    @Override
    public SolrResponse findSpecimenByIndex(String query) {
        return solrService.searchRawEntities(SPECIMEN_TABLE, 100, 1, new SortField(), query);
    }
}

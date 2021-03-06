package ee.ttu.geocollection.interop.solr.specimen.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.Map;

public interface SpecimenSolrService {

    SolrResponse findSpecimen(SpecimenSearchCriteria specimenSearchCriteria);

    Map findSpecimenById(Long id);

    SolrResponse findSpecimenByIndex(String query);

}

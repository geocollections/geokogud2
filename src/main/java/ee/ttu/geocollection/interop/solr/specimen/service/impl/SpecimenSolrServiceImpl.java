package ee.ttu.geocollection.interop.solr.specimen.service.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.solr.specimen.service.SpecimenSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class SpecimenSolrServiceImpl implements SpecimenSolrService {

    private final static String SPECIMEN_TABLE = "specimen";

    private List<String> fields = Arrays.asList(
            "ID",
            "_root_",
            "_stratigraphy",
            "_text_",
            "_version_",
            "acronym",
            "collection_id",
            "collection_number",
            "collector",
            "collector_forename",
            "collector_full_name",
            "collector_id",
            "collector_surname",
            "date_added",
            "date_changed",
            "date_collected",
            "date_collected_free",
            "db_id",
            "depth",
            "depth_interval",
            "formula",
            "formula_html",
            "fossilgroup",
            "gr",
            "id",
            "image",
            "image_preview_url",
            "image_url",
            "is_private",
            "keywords",
            "latitude",
            "lithostratigraphy",
            "lithostratigraphy_en",
            "lithostratigraphy_id",
            "locality",
            "locality_en",
            "locality_free",
            "locality_id",
            "longitude",
            "original_status",
            "original_status_en",
            "remarks",
            "rock",
            "rock_en",
            "rock_id",
            "specimen_kind",
            "specimen_kind_en",
            "specimen_number",
            "specimen_number_field",
            "specimen_number_old",
            "specimen_part",
            "specimen_status",
            "specimen_status_en",
            "stratigraphy",
            "stratigraphy_en",
            "stratigraphy_id",
            "taxon",
            "taxon_id",
            "user_added",
            "user_changed"

    );

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
        if (query.contains(":")) {
            if (query.length() > 1) {
                if (fields.contains(query.split(":")[0])) {
                    return solrService.searchRawEntities(SPECIMEN_TABLE, 100, 1, new SortField(), query);
                } else return new SolrResponse();
            } else return new SolrResponse();
        } else {
            return solrService.searchRawEntities(SPECIMEN_TABLE, 100, 1, new SortField(), query);
        }
    }
}

package ee.ttu.geocollection.interop.solr.sample.service.impl;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.sample.service.SampleSolrService;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SampleSolrServiceImpl implements SampleSolrService {

    private final static String SAMPLE_TABLE = "sample";

    private List<String> fields = Arrays.asList(
            "_root_",
            "_text_",
            "_version_",
            "acronym",
            "agent_collected_free",
            "agent_collected_id",
            "analysis",
            "classification_rock_id",
            "collector",
            "collector_full_name",
            "collector_id",
            "coordinate_accuracy",
            "database_id",
            "date_added",
            "date_changed",
            "date_collected",
            "date_collected_free",
            "db_id",
            "depth",
            "depth_interval",
            "epsg",
            "fossils",
            "id",
            "id_temp",
            "is_private",
            "latitude",
            "latitude1",
            "lithostratigraphy",
            "lithostratigraphy_en",
            "lithostratigraphy_id",
            "locality",
            "locality_en",
            "locality_free",
            "locality_id",
            "location",
            "location_additional",
            "longitude",
            "longitude1",
            "mass",
            "number",
            "number_additional",
            "number_field",
            "owner_id",
            "palaeontology",
            "parent_sample_id",
            "parent_specimen_id",
            "public",
            "remarks",
            "remarks_en",
            "rock",
            "rock_en",
            "sample_purpose",
            "sample_type",
            "series_id",
            "soil_horizon",
            "soil_site_id",
            "storage_additional_id",
            "storage_id",
            "stratigraphy",
            "stratigraphy_bed",
            "stratigraphy_bed_thickness",
            "stratigraphy_en",
            "stratigraphy_id",
            "stratigraphy_txt",
            "timestamp",
            "timestamp_created",
            "timestamp_updated",
            "user_added",
            "user_changed",
            "user_created_id",
            "user_updated_id",
            "x1",
            "y1"
    );

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findSampleByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(SAMPLE_TABLE, requestParams);
    }

}




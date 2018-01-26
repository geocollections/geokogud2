package ee.ttu.geocollection.interop.solr.photoArchive.service.impl;

import ee.ttu.geocollection.interop.solr.photoArchive.service.PhotoArchiveSolrService;
import ee.ttu.geocollection.interop.solr.response.SolrResponse;
import ee.ttu.geocollection.interop.solr.service.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoArchiveSolrImpl implements PhotoArchiveSolrService {

    private final static String PHOTO_ARCHIVE_TABLE = "image";

    @Autowired
    private SolrService solrService;

    @Override
    public SolrResponse findPhotoArchiveByIndex(String query) {
        SolrQuery requestParams = new SolrQuery(query)
                .setRows(100);
        return solrService.searchRawEntities(PHOTO_ARCHIVE_TABLE, requestParams);
    }

}

package ee.ttu.geocollection.interop.solr.photoArchive.service;

import ee.ttu.geocollection.interop.solr.response.SolrResponse;

public interface PhotoArchiveSolrService {

    SolrResponse findPhotoArchiveByIndex(String query);

}

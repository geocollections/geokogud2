package ee.ttu.geocollection.interop.api.taxon;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;

import java.util.List;

public interface TaxonApiService {

    ApiResponse findTaxonsByIds(List<String> ids);
}

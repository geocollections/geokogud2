package ee.ttu.geocollection.endpoint.web;

import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.webPages.service.WebPagesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for webpages
 * GetMapping tells it's a GET request to
 * getWebPages() function which returns API's
 * repsonse into /webpages/{id}.
 */
@RestController
public class WebPagesController {
	@Autowired
	private WebPagesApiService webPagesApiService;

	@GetMapping("/webpages/{id}")
	public ApiResponse getWebPages(@PathVariable int id)  {
		return webPagesApiService.getWebPages(id);
	}
}

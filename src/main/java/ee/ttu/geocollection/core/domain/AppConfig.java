package ee.ttu.geocollection.core.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component class.
 * Values are from resources/application.properties
 */
@Component("appConfig")
public class AppConfig {

    @Value("${geo-api.url}")
    public String apiUrl;

    /**
     * Currently not used
     */
    @Value("${geo-api2.url}")
    public String api2Url;

    @Value("${fossils.url}")
    public String fossilsUrl;

    @Value("${ermas.url}")
    public String ermasUrl;

    @Value("${elm.url}")
    public String elmUrl;

    @Value("${geocase.url}")
    public String geocaseUrl;

    @Value("${tug.url}")
    public String tugUrl;

    @Value("${github.url}")
    public String githubUrl;

    @Value("${asynchRequestTimeoutMillisecs}")
    public Integer asynchRequestTimeoutMillisecs;

    @Value("${asynchGlobalSearchTimeoutMillisecs}")
    public Integer asynchGlobalSearchTimeoutMillisecs;

    @Value("${useLegacyImageResolver}")
    public boolean useLegacyImageResolver;
}

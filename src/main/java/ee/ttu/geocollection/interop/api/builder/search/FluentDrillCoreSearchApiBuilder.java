package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;
import ee.ttu.geocollection.interop.api.builder.details.FluentGeoApiDetailsBuilder;

import java.util.List;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class FluentDrillCoreSearchApiBuilder extends FluentSearchApiBuilder<FluentDrillCoreSearchApiBuilder>{

    private String fields = EMPTY;

    public static FluentDrillCoreSearchApiBuilder aRequest() {
        return new FluentDrillCoreSearchApiBuilder();
    }

    @Override
    FluentDrillCoreSearchApiBuilder getThis() {
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryDrillCore(SearchField drillCore) {
        buildMultiSearch(drillCore, DRILL_CORE, DRILL_CORE_EN);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryLocalityCountry(SearchField country) {
        buildMultiSearch(country,
                LOCALITY_COUNTRY,
                LOCALITY_COUNTRY_ENG,
                LOCALITY__COUNTRY__ISO_CODE);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryStratigraphy(SearchField stratigraphy) {
        buildMultiSearch(stratigraphy,
                LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY,
                LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY_EN);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryStorageLocation(SearchField id) {
        buildFieldParameters(STORAGE_LOCATION, id);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryBoxes (SearchField id) {
        buildFieldParameters(BOXES, id);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryLocalityAdminUnit(SearchField adminUnit) {
        buildMultiSearch(adminUnit,
                LOCALITY_COUNTRY,
                LOCALITY_COUNTRY_ENG,
                LOCALITY__MAAKOND__MAAKOND,
                LOCALITY__MAAKOND__MAAKOND_EN,
                LOCALITY__VALD__VALD,
                LOCALITY__VALD__VALD_ENG,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS,
                LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryDrillcoreId(SearchField drillcoreId) {
        buildFieldParameters(DRILLCORE_BOX__DRILLCORE, drillcoreId);
        return this;
    }

    public FluentDrillCoreSearchApiBuilder queryFields(List<String> fields) {
        fields.forEach(this::addReturningField);
        return this;
    }
}

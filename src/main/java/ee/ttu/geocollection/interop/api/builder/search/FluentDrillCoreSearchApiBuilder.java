package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;

public class FluentDrillCoreSearchApiBuilder extends FluentSearchApiBuilder<FluentDrillCoreSearchApiBuilder>{

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

}

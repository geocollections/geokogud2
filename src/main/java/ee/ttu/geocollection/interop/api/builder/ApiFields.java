package ee.ttu.geocollection.interop.api.builder;

public class ApiFields {
    public static final String ID = "id";

    // Fields used in SPECIMEN search
    public static final String SPECIMENIDENTIFICATION__TAXON_ID = "specimenidentification__taxon_id";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__ID = "specimenidentificationgeologies__rock__id";

    // SOIL search
    public static final String SITE = "site";
    public static final String AREA_NAME = "area_name";
    public static final String SOIL = "soilsite";
    public static final String LAND_USE = "land_use";
    public static final String IS_DEEP = "is_deep";
    public static final String TRANSECT = "transect";
    public static final String TRANSECT_POINT = "transect_point";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DATABASE__NAME_EN = "database__name_en";
    public static final String DATABASE_ACRONYM = "database__acronym";

    // Sample search
    public static final String NUMBER = "number";
    public static final String NUMBER_FIELD = "number_field";
    public static final String STRATIGRAPHY_STRATIGRAPHY = "stratigraphy__stratigraphy";
    public static final String STRATIGRAPHY_STRATIGRAPHY_ENG = "stratigraphy__stratigraphy_en";
    public static final String STRATIGRAPHY_REMARKS = "stratigraphy__remarks";
    public static final String LITHOSTRATIGRAPHY_ID = "lithostratigraphy_id";
    public static final String STRATIGRAPHY_FREE = "stratigraphy_free";
    public static final String STRATIGRAPHY_BED = "stratigraphy_bed";
    public static final String LOCATION = "location";
    public static final String MASS = "mass";
    public static final String SOIL_SITE_ID = "soil_site_id";
    public static final String NUMBER_ADDITIONAL = "number_additional";
    public static final String DEPTH_INTERVAL = "depth_interval";
    public static final String REMARKS = "remarks";
    public static final String ANALYZED = "analyzed";
    public static final String SAMPLE_ID = "sample_id";

    public static final String AGENT_COLLECTED = "agent_collected__agent";
    public static final String AGENT_COLLECTED__FORENAME = "agent_collected__forename";
    public static final String AGENT_COLLECTED__SURENAME = "agent_collected__surename";
    public static final String TAGS = "tags";

    public static final String TAXONLIST__TAXON__TAXON = "taxonlist__taxon__taxon";
    public static final String ANALYSIS_METHOD = "analysis__analysis_method__analysis_method";
    public static final String ANALYSIS_METHOD_EN = "analysis__analysis_method__method_en";
    public static final String TAXONLIST__FREQUENCY = "taxonlist__frequency";
    public static final String ANALYSIS_RESULTS_NAME = "analysis__analysisresults__name";
    public static final String ANALYSIS_RESULTS_VALUE = "analysis__analysisresults__value";
    public static final String TAXON = "taxon";
    public static final String PARENT_TAXON = "parent__taxon";
    public static final String FOSSIL_GROUP_TAXON = "fossil_group__taxon";
    public static final String FOSSIL_GROUP_ID = "fossil_group__id";
    public static final String AUTHOR_YEAR = "author_year";

    //DOI search
    public static final String IDENTIFIER = "identifier";
    public static final String REFERENCETITLE = "reference__title";
    public static final String PUBLISHER = "publisher";
    public static final String REFERENCEYEAR = "reference__year";
    public static final String PUBLICATION_YEAR = "publication_year";
    public static final String REFERENCEAUTHOR = "reference__author";
    public static final String CREATORS = "creators";
    public static final String REFERENCE_ID = "reference_id";
    public static final String REFERENCE_REFERENCE = "reference__reference";
    public static final String DATASET_ID = "dataset_id";
    public static final String REFERENCE__DOI = "reference__doi";

    // Reference search
    public static final String REFERENCE = "reference";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String DOI = "doi";
    public static final String BOOK = "book";
    public static final String JOURNAL = "journal__journal_name";
    public static final String ABSTRACT = "abstract";

    // Photo archive search
    public static final String FILE_NAME = "filename";
    public static final String DATE_TAKEN = "date_taken";
    public static final String DATE_TAKEN_FREE = "date_taken_free";
    public static final String AUTHOR_AGENT = "author__agent";
    public static final String KEYWORDS = "keywords";
    public static final String IMAGE_NUMBER = "image_number";
    public static final String PLACE = "place";
    public static final String PEOPLE = "people";
    public static final String LOCALITY_LOCALITY = "locality__locality";
    public static final String LOCALITY__MAAKOND__MAAKOND_EN = "locality__maakond__maakond_en";
    public static final String LOCALITY__MAAKOND__MAAKOND = "locality__maakond__maakond";
    public static final String LOCALITY__VALD__VALD = "locality__vald__vald";
    public static final String LOCALITY__VALD__VALD_ENG = "locality__vald__vald_en";
    public static final String LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS = "locality__asustusyksus__asustusyksus";
    public static final String LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN = "locality__asustusyksus__asustusyksus_en";
    public static final String LOCALITY__COUNTRY__ISO_CODE = "locality__country__iso_code";
    public static final String OBJECT = "object";
    public static final String SIZE_X = "size_x";
    public static final String SIZE_Y = "size_y";
    public static final String UUID_FILENAME = "uuid_filename";

    public static final String AUTHOR_FORENAME = "author__forename";
    public static final String AUTHOR_SURENAME = "author__surename";
    public static final String AUTHOR_FREE = "author_free";
    public static final String LOCALITY_LOCALITY_EN = "locality__locality_en";
    public static final String DESCRIPTION = "description";
    public static final String DATA_ADDED = "date_added";
    public static final String DATA_CHANGED = "date_changed";
    public static final String TYPE_VALUE = "type__value";
    public static final String TYPE_VALUE_EN = "type__value_en";
    public static final String DEVICE_NAME = "device__name";
    public static final String IMAGESET_NUMBER = "imageset__imageset_number";
    public static final String IMAGESET_SERIES = "imageset__imageset_series";
    public static final String COPYRIGHT_AGENT = "copyright_agent__agent";
    public static final String LICENCE = "licence__licence_en";
    public static final String LICENCE_URL = "licence__licence_url_en";
    public static final String LOCALITY_ID = "locality_id";
    public static final String STRATIGRAPHY_ID = "stratigraphy_id";

    // Drill core search
    public static final String DRILL_CORE = "drillcore";
    public static final String DRILL_CORE_EN = "drillcore_en";
    public static final String BOX_NUMBERS = "box_numbers";
    public static final String STORAGE_LOCATION = "storage__location";
    public static final String LOCALITY_COUNTRY = "locality__country__value";
    public static final String LOCALITY_COUNTRY_ENG = "locality__country__value_en";
    public static final String LOCALITY_LATITUDE = "locality__latitude";
    public static final String LOCALITY_LONGITUDE = "locality__longitude";
    public static final String DEPTH = "depth";
    public static final String BOXES = "boxes";
    public static final String DRILLCORE_ID = "drillcore__id";
    public static final String DRILLCORE_LOCALITY = "drillcore__locality";
    public static final String DEPTH_BASE = "depth_base";

    // Locality search
    public static final String LOCALITY_STRATIGRAPHY_EN = "localitystratigraphy__stratigraphy__stratigraphy_en";
    public static final String LOCALITY_STRATIGRAPHY = "localitystratigraphy__stratigraphy__stratigraphy";
    public static final String LOCALITY_STRATIGRAPHY_TOP_EN = "stratigraphy_top__stratigraphy_en";
    public static final String LOCALITY_STRATIGRAPHY_TOP = "stratigraphy_top__stratigraphy";
    public static final String LOCALITY_STRATIGRAPHY_TOP_ID = "stratigraphy_top_id";
    public static final String LOCALITY_STRATIGRAPHY_BASE_EN = "stratigraphy_base__stratigraphy_en";
    public static final String LOCALITY_STRATIGRAPHY_BASE = "stratigraphy_base__stratigraphy";
    public static final String LOCALITY_STRATIGRAPHY_BASE_ID = "stratigraphy_base_id";
    public static final String MA_ID = "maaamet_pa_id";
    public static final String LOCALITY = "locality";
    public static final String LOCALITY_ENG = "locality_en";
    public static final String LOCALITY_FREE = "locality_free";
    public static final String COUNTRY = "country__value";
    public static final String COUNTRY_ENG = "country__value_en";
    public static final String COUNTY = "maakond__maakond";
    public static final String COUNTY_ENG = "maakond__maakond_en";
    public static final String PARISH = "vald__vald";
    public static final String PARISH_EN = "vald__vald_en";
    public static final String ASUSTUSYKSUS = "asustusyksus__asustusyksus";
    public static final String ASUSTUSYKSUS_EN = "asustusyksus__asustusyksus_en";
    public static final String ELEVATION = "elevation";
    public static final String COORD_SYSTEM = "coord_system";
    public static final String COORD_X = "coordx";
    public static final String COORD_Y = "coordy";
    public static final String COORD_DET_PRECISION = "coord_det_precision__value";
    public static final String COORD_DET_METHOD = "coord_det_method__value";
    public static final String COORD_DET_AGENT = "coord_det_agent__agent";
    public static final String STRATIGRAPHY_TOP_FREE = "stratigraphy_top_free";
    public static final String STRATIGRAPHY_BASE_FREE = "stratigraphy_base_free";
    public static final String USER_ADDED = "user_added";
    public static final String DATE_ADDED = "date_added";
    public static final String DATE_CHANGED = "date_changed";
    public static final String PARENT_LOCALITY = "parent__locality";
    public static final String EELIS = "eelis";
    public static final String MAAAMET_PA_ID = "maaamet_pa_id";
    public static final String LOCALITYSYNONYM_SYNONYM = "localitysynonym__synonym";
    public static final String LOCALITYREFERENCE__REFERENCE__REFERENCE = "localityreference__reference__reference";

    // Stratigraphy search
    public static final String STRATIGRAPHY = "stratigraphy";
    public static final String STRATIGRAPHY_EN = "stratigraphy_en";
    public static final String INDEX_MAIN = "index_main";
    public static final String INDEX_ADDITIONAL = "index_additional";
    public static final String AGE_BASE = "age_base";
    public static final String AGE_TOP = "age_top";
    public static final String LITHOLOGY = "lithology";
    public static final String LITHOLOGY_EN = "lithology_en";
    public static final String LITHOSTRATIGRAPHY__STRATIGRAPHY = "lithostratigraphy__stratigraphy";
    public static final String LITHOSTRATIGRAPHY__STRATIGRAPHY_ENG = "lithostratigraphy__stratigraphy_en";
    public static final String SPECIMEN_ID = "specimen_id";
    public static final String SPECIMEN_NR = "specimen_nr";
    public static final String TAXON_ID = "taxon_id";
    public static final String TAXON_TAXON = "taxon__taxon";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE = "image";
    public static final String SPECIMEN__DATABASE__ACRONYM = "specimen__database__acronym";
    public static final String COLL__NUMBER = "coll__number";
    public static final String COLL_ID = "coll_id";
    public static final String CLASSIFICATION__CLASS_FIELD = "classification__class_field";
    public static final String CLASSIFICATION__CLASS_EN = "classification__class_en";
    public static final String CLASSIFICATION__CLASS_LAT = "classification__class_lat";
    public static final String PART = "part";
    public static final String ORIGINAL_STATUS__VALUE = "original_status__value";
    public static final String ORIGINAL_STATUS__VALUE_EN = "original_status__value_en";
    public static final String SPECIMENREFERENCE__REFERENCE__AUTHOR = "specimenreference__reference__author";
    public static final String SPECIMENREFERENCE__REFERENCE__TITLE = "specimenreference__reference__title";
    public static final String SPECIMENREFERENCE__REFERENCE__REFERENCE = "specimenreference__reference__reference";
    public static final String SPECIMENIDENTIFICATION__NAME = "specimenidentification__name";
    public static final String SPECIMENIDENTIFICATION__TAXON__TAXON = "specimenidentification__taxon__taxon";
    public static final String SPECIMENIDENTIFICATION__CURRENT = "specimenidentification__current";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME = "specimenidentificationgeologies__rock__name";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME_EN = "specimenidentificationgeologies__rock__name_en";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__NAME = "specimenidentificationgeologies__name";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__NAME_EN = "specimenidentificationgeologies__name_en";
    public static final String SPECIMENIDENTIFICATIONGEOLOGIES__CURRENT = "specimenidentificationgeologies__current";
    public static final String KIND__VALUE = "kind__value";
    public static final String KIND__VALUE_EN = "kind__value_en";
    public static final String PARENT_STRATIGRAPHY = "parent__stratigraphy";
    public static final String PARENT_STRATIGRAPHY_EN = "parent__stratigraphy_en";
    public static final String PARENT_ID = "parent_id";
    public static final String AGE_CHRONOSTRATIGRAPHY_STRATIGRAPHY = "age_chronostratigraphy__stratigraphy";
    public static final String AGE_CHRONOSTRATIGRAPHY_STRATIGRAPHY_EN = "age_chronostratigraphy__stratigraphy_en";
    public static final String AGE_CHRONOSTRATIGRAPHY_ID = "age_chronostratigraphy_id";
    public static final String AGENT__AGENT = "agent__agent";

    //Analyses search
    public static final String ANALYSIS_METHOD__ANALYSIS_METHOD = "analysis_method__analysis_method";
    public static final String ANALYSIS_METHOD__METHOD_EN = "analysis_method__method_en";
    public static final String METHOD_DETAILS = "method_details";
    public static final String LAB = "lab";
    public static final String INSTRUMENT = "instrument";
    public static final String INSTRUMENT_TXT = "instrument_txt";
    public static final String SAMPLE = "sample";
    public static final String SAMPLE__ID = "sample__id";
    public static final String DATE = "date";
    public static final String DATE_FREE = "date_free";
    public static final String SAMPLE_STRATIGRAPHY_BED = "sample__stratigraphy_bed";
    public static final String SAMPLE_DEPTH = "sample__depth";
    public static final String SAMPLE_DEPTH_INTERVAL = "sample__depth_interval";
    public static final String SAMPLE_LOCALITY_FREE = "sample__locality_free";
    public static final String SAMPLE_LOCALITY_LOCALITY_EN = "sample__locality__locality_en";
    public static final String SAMPLE_LOCALITY_LOCALITY = "sample__locality__locality";
    public static final String SAMPLE_LOCALITY_ID = "sample__locality_id";
    public static final String SAMPLE_STRATIGRAPHY_ID = "sample__stratigraphy_id";
    public static final String SAMPLE_LITHOSTRATIGRAPHY_ID = "sample__lithostratigraphy_id";
    public static final String SAMPLE_LITHOSTRATIGRAPHY__STRATIGRAPHY = "sample__lithostratigraphy__stratigraphy";
    public static final String SAMPLE_LITHOSTRATIGRAPHY__STRATIGRAPHY_EN = "sample__lithostratigraphy__stratigraphy_en";
    public static final String SAMPLE_NUMBER = "sample__number";
    public static final String LAB_TXT = "lab_txt";
    public static final String ANALYSISRESULTS__NAME = "analysisresults__name";
    public static final String ANALYSISRESULTS__VALUE = "analysisresults__value";
    public static final String ANALYSISRESULTS_VALUE_BIN = "value_bin";
    public static final String ANALYSISRESULTS_VALUE_TXT = "value_txt";

    /**
     * SPECIMEN_IDENTIFICATION search
     */
    public static final String TAXON__TAXON = "taxon__taxon";
    public static final String CURRENT = "current";

    /**
     * SPECIMEN_IDENTIFICATION_GEOLOGY search
     */
    public static final String ROCK__NAME = "rock__name";
    public static final String ROCK__NAME_EN = "rock__name_en";
    public static final String ROCK_ID = "rock_id";
    public static final String NAME_EN = "name_en";
    public static final String ROCK__MINDAT_ID = "rock__mindat_id";

    /**
     * PREPARATION search
     */
    public static final String SAMPLE__LOCALITY__ID = "sample__locality__id";
    public static final String SAMPLE__STRATIGRAPHY__ID = "sample__stratigraphy__id";
    public static final String SAMPLE__STRATIGRAPHY__STRATIGRAPHY = "sample__stratigraphy__stratigraphy";
    public static final String SAMPLE__STRATIGRAPHY__STRATIGRAPHY_EN = "sample__stratigraphy__stratigraphy_en";
    public static final String SAMPLE__LITHOSTRATIGRAPHY__STRATIGRAPHY = "sample__lithostratigraphy__stratigraphy";
    public static final String SAMPLE__LITHOSTRATIGRAPHY__STRATIGRAPHY_EN = "sample__lithostratigraphy__stratigraphy_en";
    public static final String PREPARATION_NUMBER = "preparation_number";
    public static final String TAXONLIST__NAME = "taxonlist__name";
    public static final String SAMPLE__AGENT_COLLECTED__AGENT = "sample__agent_collected__agent";
    public static final String SAMPLE__AGENT_COLLECTED__FORENAME = "sample__agent_collected__forename";
    public static final String SAMPLE__AGENT_COLLECTED__SURENAME = "sample__agent_collected__surename";
    public static final String TAXONLIST__TAXON__ID = "taxonlist__taxon__id";

    /**
     * Used in WebNewsApiServiceImpl class
     */
    public static final String DATE_ADDED_LT = "date_added__lt";

    /*
     * SPECIMEN IMAGE search
     */
    public static final String SPECIMEN__SPECIMEN_NR = "specimen__specimen_nr";
    public static final String SPECIMEN__SPECIMEN_ID = "specimen__specimen_id";
    public static final String SPECIMEN__COLL__NUMBER = "specimen__coll__number";
    public static final String SPECIMEN__CLASSIFICATION__CLASS_FIELD = "specimen__classification__class_field";
    public static final String SPECIMEN__CLASSIFICATION__CLASS_EN = "specimen__classification__class_en";
    public static final String SPECIMEN__SPECIMENIDENTIFICATION__NAME = "specimen__specimenidentification__name";
    public static final String SPECIMEN__SPECIMENIDENTIFICATION__TAXON__TAXON = "specimen__specimenidentification__taxon__taxon";
    public static final String SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__NAME = "specimen__specimenidentificationgeologies__name";
    public static final String SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__NAME_EN = "specimen__specimenidentificationgeologies__name_en";
    public static final String SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME = "specimen__specimenidentificationgeologies__rock__name";
    public static final String SPECIMEN__SPECIMENIDENTIFICATIONGEOLOGIES__ROCK__NAME_EN = "specimen__specimenidentificationgeologies__rock__name_en";
    public static final String SPECIMEN__LOCALITY_COUNTRY = "specimen__locality__country__value";
    public static final String SPECIMEN__LOCALITY_COUNTRY_EN = "specimen__locality__country__value_en";
    public static final String SPECIMEN__LOCALITY__MAAKOND__MAAKOND = "specimen__locality__maakond__maakond";
    public static final String SPECIMEN__LOCALITY__MAAKOND__MAAKOND_EN = "specimen__locality__maakond__maakond_en";
    public static final String SPECIMEN__LOCALITY__VALD__VALD = "specimen__locality__vald__vald";
    public static final String SPECIMEN__LOCALITY__VALD__VALD_EN = "specimen__locality__vald__vald_en";
    public static final String SPECIMEN__LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS = "specimen__locality__asustusyksus__asustusyksus";
    public static final String SPECIMEN__LOCALITY__ASUSTUSYKSUS__ASUSTUSYKSUS_EN = "specimen__locality__asustusyksus__asustusyksus_en";
    public static final String SPECIMEN__LOCALITY__LOCALITY = "specimen__locality__locality";
    public static final String SPECIMEN__LOCALITY__LOCALITY_EN = "specimen__locality__locality_en";
    public static final String SPECIMEN__LOCALITY_FREE = "specimen__locality_free";
    public static final String SPECIMEN__STRATIGRAPHY_STRATIGRAPHY = "specimen__stratigraphy__stratigraphy";
    public static final String SPECIMEN__STRATIGRAPHY_STRATIGRAPHY_EN = "specimen__stratigraphy__stratigraphy_en";
    public static final String SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY = "specimen__lithostratigraphy__stratigraphy";
    public static final String SPECIMEN__LITHOSTRATIGRAPHY__STRATIGRAPHY_EN = "specimen__lithostratigraphy__stratigraphy_en";
    public static final String SPECIMEN__STRATIGRAPHY_FREE = "specimen__stratigraphy_free";
    public static final String SPECIMEN__ID = "specimen__id";
    public static final String SPECIMEN__DEPTH = "specimen__depth";
    public static final String SPECIMEN__DEPTH_INTERVAL = "specimen__depth_interval";
    public static final String SPECIMEN__AGENT_COLLECTED = "specimen__agent_collected__agent";
    public static final String SPECIMEN__AGENT_COLLECTED__FORENAME = "specimen__agent_collected__forename";
    public static final String SPECIMEN__AGENT_COLLECTED__SURENAME = "specimen__agent_collected__surename";
    public static final String SPECIMEN__SPECIMENREFERENCE__REFERENCE__AUTHOR = "specimen__specimenreference__reference__author";
    public static final String SPECIMEN__SPECIMENREFERENCE__REFERENCE__TITLE = "specimen__specimenreference__reference__title";
    public static final String SPECIMEN__SPECIMENREFERENCE__REFERENCE__REFERENCE = "specimen__specimenreference__reference__reference";
    public static final String SPECIMEN__ORIGINAL_STATUS__VALUE = "specimen__original_status__value";
    public static final String SPECIMEN__ORIGINAL_STATUS__VALUE_EN = "specimen__original_status__value_en";
    public static final String SPECIMEN__PART = "specimen__part";
    public static final String SPECIMEN__DATE_ADDED = "specimen__date_added";

    /*
     * LOCALITY IMAGE search
     */
    public static final String LOCALITY__LOCALITY_EN = "locality__locality_en";
    public static final String LOCALITY__LOCALITY = "locality__locality";
    public static final String LOCALITY__NUMBER = "locality__number";
    public static final String LOCALITY__COUNTRY = "locality__country__value";
    public static final String LOCALITY__COUNTRY_EN = "locality__country__value_en";
    public static final String LOCALITY__COUNTY = "locality__maakond__maakond";
    public static final String LOCALITY__COUNTY_EN = "locality__maakond__maakond_en";
    public static final String LOCALITY__PARISH = "locality__vald__vald";
    public static final String LOCALITY__PARISH_EN = "locality__vald__vald_en";
    public static final String LOCALITY__ASUSTUSYKSUS = "locality__asustusyksus__asustusyksus";
    public static final String LOCALITY__ASUSTUSYKSUS_EN = "locality__asustusyksus__asustusyksus_en";
    public static final String LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY_EN = "locality__localitystratigraphy__stratigraphy__stratigraphy_en";
    public static final String LOCALITY__LOCALITYSTRATIGRAPHY__STRATIGRAPHY = "locality__localitystratigraphy__stratigraphy__stratigraphy";
    public static final String LOCALITY__LOCALITYREFERENCE__REFERENCE__REFERENCE = "locality__localityreference__reference__reference";
    public static final String LOCALITY__ID = "locality__id";
    public static final String LOCALITY__MA_ID = "locality__maaamet_pa_id";
    public static final String LOCALITY__LATITUDE = "locality__latitude";
    public static final String LOCALITY__LONGITUDE = "locality__longitude";
    public static final String LOCALITY__DEPTH = "locality__depth";

}

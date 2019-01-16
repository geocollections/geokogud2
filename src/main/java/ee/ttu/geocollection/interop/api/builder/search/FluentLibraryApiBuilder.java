package ee.ttu.geocollection.interop.api.builder.search;

import ee.ttu.geocollection.domain.SearchField;

public class FluentLibraryApiBuilder extends FluentSearchApiBuilder<FluentLibraryApiBuilder> {

    public static FluentLibraryApiBuilder aRequest() { return new FluentLibraryApiBuilder(); }

    @Override
    FluentLibraryApiBuilder getThis() {
        return this;
    }

    public FluentLibraryApiBuilder queryLibrary(Long library) {
        addFirstFieldNameAndValue("library", String.valueOf(library));
        return this;
    }

}

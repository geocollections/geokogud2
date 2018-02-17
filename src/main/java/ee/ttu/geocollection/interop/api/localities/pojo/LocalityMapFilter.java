package ee.ttu.geocollection.interop.api.localities.pojo;

import java.util.List;

public class LocalityMapFilter {

    private String localityName;
    private List<String> filters;


    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}

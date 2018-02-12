package ee.ttu.geocollection.interop.api.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    private String table;

    @JsonProperty("count")
    private int count;

    @JsonProperty("page")
    private String pageInfo;

    @JsonProperty("results")
    private List<Map<String, Object>> result;

    @JsonProperty("response")
    private Map response;

    @JsonProperty("related_data")
    private Map<String, List> relatedData;


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

    public Map getResponse() {
        return response;
    }

    public void setResponse(Map response) {
        this.response = response;
    }

    public Map<String, List> getRelatedData() {
        return relatedData;
    }

    public void setRelatedData(Map<String, List> relatedData) {
        this.relatedData = relatedData;
    }

}

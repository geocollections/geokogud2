package ee.ttu.geocollection.interop.api.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolrResponse { // Currently not used, will see if I even need it.

    private String table;

    @JsonProperty("responseHeader")
    private Map responseHeader;

    @JsonProperty("response")
    private Map response;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Map responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Map getResponse() {
        return response;
    }

    public void setResponse(Map response) {
        this.response = response;
    }
}

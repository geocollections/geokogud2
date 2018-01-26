package ee.ttu.geocollection.interop.solr.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolrResponse {

    private String table;
    private long numFound;

    @JsonProperty("response")
    private SolrDocumentList response;

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public SolrDocumentList getResponse() {
        return response;
    }

    public void setResponse(SolrDocumentList response) {
        this.response = response;
    }
}

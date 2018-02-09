package ee.ttu.geocollection.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class SearchField {

    /**
     * SearchFields name
     */
    private String name;

    /**
     * SearchFields lookUpType, for example iexact, icontains
     */
    @Enumerated(EnumType.STRING)
    private LookUpType lookUpType;

    public SearchField() {
    }

    public SearchField(String name, LookUpType lookUpType) {
        this.name = name;
        this.lookUpType = lookUpType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LookUpType getLookUpType() {
        return lookUpType;
    }

    public void setLookUpType(LookUpType lookUpType) {
        this.lookUpType = lookUpType;
    }
}

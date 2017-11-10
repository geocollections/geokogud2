package ee.ttu.geocollection.interop.api;

public interface PageableSearchCriteria {

    /**
     * Returns integer value of current page
     * @return page value
     */
    int getPage();

    /**
     * Sets page number
     * @param page Integer to set page
     */
    void setPage(int page);
}

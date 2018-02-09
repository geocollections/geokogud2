package ee.ttu.geocollection.domain;

public class SortField {

    /**
     * Default field which is sorted
     */
    private String sortBy = "id";

    /**
     * Default sorting order
     */
    private SortingOrder order = SortingOrder.ASCENDING;

    public SortField() {

    }

    /**
     * SortField initializer, you can initiate SortField
     * with custom field and either ASCENDING or DESCENDING order.
     * Otherwise it uses default fields.
     * @param sortBy Field which is being sorted.
     * @param order Sorting order ASC or DESC.
     */
    public SortField(String sortBy, SortingOrder order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortingOrder getOrder() {
        return order;
    }

    public void setOrder(SortingOrder order) {
        this.order = order;
    }
}

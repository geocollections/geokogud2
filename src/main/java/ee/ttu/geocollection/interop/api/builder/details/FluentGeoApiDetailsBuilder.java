package ee.ttu.geocollection.interop.api.builder.details;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class FluentGeoApiDetailsBuilder {

    private String query = EMPTY;
    private String returningFields = EMPTY;
    private String relatedData = EMPTY;

    /**
     * Returns just new instance of FluentGeoApiDetailsBuilder.
     * @return new FluentGeoApiDetailsBuilder object.
     */
    public static FluentGeoApiDetailsBuilder aRequest() {
        return new FluentGeoApiDetailsBuilder();
    }

    /**
     * Appends query with id.
     * @param id aka identifier for each object in table.
     * @return current object instance aka FluentGeoApiDetailsBuilder.
     */
    public FluentGeoApiDetailsBuilder id(Long id) {
        query += id;
        return this;
    }

    /**
     * Adds related data(s) to relatedData variable.
     * @param relatedData aka table to be added into request.
     * @return current object instance.
     */
    public FluentGeoApiDetailsBuilder relatedData(String relatedData) {
        this.relatedData += "&related_data=" + relatedData;
        return this;
    }

    /**
     * Iterates through list of values and adds them to returningFields variable.
     * @param fields aka List of String values.
     * @return current object instance.
     */
    public FluentGeoApiDetailsBuilder returnAllFields(List<String> fields) {
        fields.forEach(this::addReturningField);
        System.out.println("WTFTFTFTFT" + this.returningFields);
        return this;
    }

    /**
     * If returningFields is empty then adds field (date_taken) else it adds field with comma (,date_taken).
     * @param field String value to be added into returningFields variable.
     */
    private void addReturningField(String field) {
        returningFields += returningFields.isEmpty() ? field : "," + field;
    }

    /**
     * If relatedData is empty then returns just query variable
     * else it also adds relatedData to query variable.
     * For example '../15' or '../15?related_data=...'
     * @return string value of whole query to be added to request.
     */
    public String buildWithDefaultReturningFields() {
        return this.relatedData.equals(EMPTY) ? query : query + replaceFirstCharacterWithQuestionMark(this.relatedData);
    }

    /**
     * If returningFields is empty then adds relatedData to query variable
     * else it adds returningFields and relatedData to query.
     * For example '../15?related_data=..' or '../15?fields=id,...&related_data=...'
     * @return string value of query which is then added to request url.
     */
    public String buildWithReturningFieldsAndRelatedData() {
        return query + (returningFields.isEmpty() ? replaceFirstCharacterWithQuestionMark(this.relatedData) : "?fields=" + returningFields + relatedData);
    }

    /**
     * Replaces first character (&) in relatedData with (?).
     * For example '&related_data=image&related_data=...' --> '?related_data=...'
     * @param relatedData String value to be edited.
     * @return modified string.
     */
    private String replaceFirstCharacterWithQuestionMark(String relatedData) {
        return "?" + relatedData.substring(1);
    }
}

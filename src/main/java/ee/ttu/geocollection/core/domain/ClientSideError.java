package ee.ttu.geocollection.core.domain;

public class ClientSideError {

    /**
     * String value of url which caused error
     */
    private String url;

    /**
     * String value of error message
     */
    private String message;

    /**
     * String value of error stackTrace
     */
    private String stackTrace;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}

package challenge.response;

import org.springframework.http.HttpStatus;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class ResponseSummary {

    private HttpStatus summaryCode;

    private String message;

    public HttpStatus getSummaryCode() {
        return summaryCode;
    }

    public void setSummaryCode(HttpStatus summaryCode) {
        this.summaryCode = summaryCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

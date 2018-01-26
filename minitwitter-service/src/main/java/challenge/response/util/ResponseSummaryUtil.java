package challenge.response.util;

import challenge.response.ResponseSummary;
import com.google.common.base.Preconditions;
import org.springframework.http.HttpStatus;

/**
 *
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class ResponseSummaryUtil {

    public static ResponseSummary getResponseSummary(String message, HttpStatus status) {
        Preconditions.checkArgument(status != null, "Http status cannot be null");
        ResponseSummary responseSummary = new ResponseSummary();
        responseSummary.setMessage(message);
        responseSummary.setSummaryCode(status);
        return responseSummary;
    }
}

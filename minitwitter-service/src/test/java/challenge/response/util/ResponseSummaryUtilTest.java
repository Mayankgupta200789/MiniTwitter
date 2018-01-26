package challenge.response.util;

import challenge.response.ResponseSummary;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * @Author Mayank Gupta
 * @Date 8/21/17
 */
public class ResponseSummaryUtilTest {


    private static final String ANY_VALID_MESSAGE = "anyValidMessage";
    private static final HttpStatus ANY_VALID_HTTP_STATUS = HttpStatus.OK;


    @Test
    public void getResponseSummary() throws Exception {

        ResponseSummary responseSummary = ResponseSummaryUtil.getResponseSummary(ANY_VALID_MESSAGE, ANY_VALID_HTTP_STATUS);

        Assert.assertNotNull(responseSummary);
        Assert.assertEquals(ANY_VALID_MESSAGE,responseSummary.getMessage());
        Assert.assertEquals(ANY_VALID_HTTP_STATUS,responseSummary.getSummaryCode());
    }

    @Test
    public void getResponseSummaryWithNullMessage() {

        try {
            ResponseSummaryUtil.getResponseSummary(null, ANY_VALID_HTTP_STATUS);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponseSummaryWithNullHttpMessage() {

            ResponseSummaryUtil.getResponseSummary(ANY_VALID_MESSAGE, null);
    }

}
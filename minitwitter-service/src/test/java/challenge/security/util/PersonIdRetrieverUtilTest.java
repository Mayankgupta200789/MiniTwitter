package challenge.security.util;

import challenge.controller.SecurityContextBuilder;
import challenge.dao.exception.ForbiddenAccessException;
import challenge.security.dto.TwitterUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author Mayank Gupta
 * @Date 8/21/17
 */
public class PersonIdRetrieverUtilTest {

    private static final Integer ANY_VALID_USER_ID = 1;
    private static final String ANY_VALID_USER_NAME = "batman";

    @Test
    public void retrieve() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_USER_ID, ANY_VALID_USER_NAME);

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        Assert.assertNotNull(twitterUser);
        Assert.assertEquals(ANY_VALID_USER_ID,twitterUser.getPersonId());
        Assert.assertEquals(ANY_VALID_USER_NAME,twitterUser.getUsername());
    }

    @Test(expected = ForbiddenAccessException.class)
    public void retrieveWithoutAuthentication() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);

        PersonIdRetrieverUtil.retrieve();

    }

}
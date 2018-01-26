package challenge.security.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author Mayank Gupta
 * @Date 8/21/17
 */
public class TwitterUserDetailsServiceTest {

    private static final String ANY_VALID_USER_NAME = "batman";
    private static final String ANY_INVALID_USER_NAME = "greenlantern";
    private TwitterUserDetailsService twitterUserDetailsService;

    private EmbeddedDatabase db;
    private NamedParameterJdbcTemplate template;

    private static final String GET_PERSON_DETAILS = "select id,handle,password from people where handle=?";

    @Before
    public void setUp() throws Exception {

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        twitterUserDetailsService = new TwitterUserDetailsService();
        twitterUserDetailsService.setDataSource(db);
        twitterUserDetailsService.setUsersByUsernameQuery(GET_PERSON_DETAILS);
    }

    @Test
    public void loadUserByUsername() throws Exception {

        UserDetails userDetails = twitterUserDetailsService.loadUserByUsername(ANY_VALID_USER_NAME);

        Assert.assertNotNull(userDetails);
        Assert.assertEquals(ANY_VALID_USER_NAME,userDetails.getUsername());
        Assert.assertNotNull(userDetails.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUsersByUsernameWithInvalidUserName() throws Exception {

        twitterUserDetailsService.loadUserByUsername(ANY_INVALID_USER_NAME);

    }

    @Test(expected = IllegalArgumentException.class)
    public void loadUsersWithNullUserName() {
        twitterUserDetailsService.loadUserByUsername(null);
    }

}
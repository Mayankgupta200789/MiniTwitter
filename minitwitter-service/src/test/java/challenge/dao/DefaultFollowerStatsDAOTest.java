package challenge.dao;

import challenge.response.PopularFollowers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultFollowerStatsDAOTest {

    private EmbeddedDatabase db;
    private NamedParameterJdbcTemplate template;
    private FollowerStatsDAO followerStatsDAO;

    @Before
    public void setUp() throws Exception {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        template = new NamedParameterJdbcTemplate(db);
        PersonDAO personDAO = new DefaultPersonDAO(template);
        followerStatsDAO = new DefaultFollowerStatsDAO(template, personDAO);
    }

    @Test
    public void findPopularFollowers() throws Exception {

        PopularFollowers popularFollowers = followerStatsDAO.findPopularFollowers();

        Assert.assertNotNull(popularFollowers);
        Assert.assertNotNull(popularFollowers.getPopularPersonMap());
        Assert.assertTrue(popularFollowers.getPopularPersonMap().size() == 10);

    }

}
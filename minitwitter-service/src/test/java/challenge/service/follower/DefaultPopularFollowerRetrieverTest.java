package challenge.service.follower;

import challenge.dao.MockFollowerStatsDAO;
import challenge.dao.FollowerStatsDAO;
import challenge.response.PopularFollowers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultPopularFollowerRetrieverTest {

    private static final String ANY_VALID_PERSON_NAME = "anyValidPersonName";
    private static final String ANY_VALID_FOLLOWER_NAME = "anyValidFollowerName";
    private DefaultPopularFollowerRetriever popularFollowerRetriever;
    private Map<String, String> popularPersonMap;

    @Before
    public void setUp() throws Exception {

        PopularFollowers popularFollowers = new PopularFollowers();
        popularPersonMap = new HashMap<>();
        popularPersonMap.put(ANY_VALID_PERSON_NAME,ANY_VALID_FOLLOWER_NAME);
        popularFollowers.setPopularPersonMap(popularPersonMap);
        FollowerStatsDAO mockFollowerStatsDAO = new MockFollowerStatsDAO(popularFollowers);
        popularFollowerRetriever = new DefaultPopularFollowerRetriever(mockFollowerStatsDAO);
    }

    @Test
    public void retrieve() throws Exception {

        PopularFollowers popularFollowers = popularFollowerRetriever.retrieve();

        Assert.assertNotNull(popularFollowers);
        Assert.assertNotNull(popularFollowers.getPopularPersonMap());

        Assert.assertEquals(popularPersonMap,popularFollowers.getPopularPersonMap());

    }

}
package challenge.service.follower;

import challenge.dao.FollowerDAO;
import challenge.dao.MockFollowerDAO;
import challenge.response.PersonFollowerShortDistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultHopCountCalculatorTest {

    private static final int ANY_VALID_SHORTEST_DISTANCE = 4;
    private static final String ANY_VALID_FOLLOWER_USERNAME = "alfred";
    private static final int ANY_VALID_PERSON_ID = 7;
    private static final String ANY_VALID_NAME = "anyValidName";
    private DefaultHopCountCalculator hopCountCalculator;

    @Before
    public void setUp() throws Exception {
        FollowerDAO followerDAO = new MockFollowerDAO(ANY_VALID_PERSON_ID, ANY_VALID_NAME,ANY_VALID_SHORTEST_DISTANCE);
        hopCountCalculator = new DefaultHopCountCalculator(followerDAO);
    }

    @Test
    public void calculate() throws Exception {

        PersonFollowerShortDistance personFollowerShortDistance = hopCountCalculator.calculate(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);

        Assert.assertNotNull(personFollowerShortDistance);
        Assert.assertEquals(ANY_VALID_SHORTEST_DISTANCE,personFollowerShortDistance.getShortestDistance().intValue());
    }

}
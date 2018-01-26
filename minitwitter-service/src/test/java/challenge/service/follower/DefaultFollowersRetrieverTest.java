package challenge.service.follower;

import challenge.dao.MockFollowerDAO;
import challenge.model.Person;
import challenge.response.PersonFollowerInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultFollowersRetrieverTest {

    private static final int ANY_VALID_PERSON_ID = 4;
    private MockFollowerDAO mockFollowerDAO;
    private DefaultFollowersRetriever followersRetriever;

    @Before
    public void setUp() throws Exception {

        mockFollowerDAO = new MockFollowerDAO(ANY_VALID_PERSON_ID);
        followersRetriever = new DefaultFollowersRetriever(mockFollowerDAO);


    }

    @Test
    public void retrieve() throws Exception {

        List<Integer> followerIds = followersRetriever.retrieve(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(followerIds);

        Assert.assertEquals(1,followerIds.size());

        Integer followerId = followerIds.get(0);
        Assert.assertEquals(4,followerId.intValue());
    }

    @Test
    public void retrieveWithNullPersonId() throws Exception {

        List<Integer> followerIds = followersRetriever.retrieve(null);

        Assert.assertNotNull(followerIds);

        Assert.assertEquals(0,followerIds.size());

    }

    @Test
    public void retrievePersonInfo() {

        List<Person> followers = new LinkedList<>();
        List<Person> followedByPersons = new LinkedList<>();

        mockFollowerDAO = new MockFollowerDAO(followers,followedByPersons,false);

        followersRetriever = new DefaultFollowersRetriever(mockFollowerDAO);

        PersonFollowerInfo personFollowerInfo = followersRetriever.retrievePersonInfo(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(personFollowerInfo);
        Assert.assertNotNull(personFollowerInfo.getFollowers());
        Assert.assertNotNull(personFollowerInfo.getFollowedByPersons());
    }

}
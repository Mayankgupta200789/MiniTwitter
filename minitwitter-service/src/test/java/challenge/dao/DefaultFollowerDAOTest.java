package challenge.dao;

import challenge.dao.exception.PersonAlreadyFollowedException;
import challenge.dao.exception.PersonIdNotExistException;
import challenge.dao.exception.PersonNotFollowedException;
import challenge.response.PersonFollowerInfo;
import challenge.response.PersonFollowerShortDistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultFollowerDAOTest {
    private static final int ANY_VALID_PERSON_ID = 1;
    private static final int ANY_INVALID_PERSON_ID = -1;
    private static final String ANY_VALID_FOLLOWER_USERNAME = "zod";
    private static final String ANY_INVALID_FOLLOWER_USERNAME = "superwoman";
    private static final String ANY_VALID_USERNAME_THAT_IS_NOT_BEING_FOLLOWED = "thor";
    private static final String ANY_VALID_ZERO_DISTANCE_USERNAME = "catwoman";
    private static final String ANY_VALID_ALREADY_FOLLOWED_USER_NAME = "profx";
    private static final int ANY_VALID_PERSON_WITHOUT_FOLLOWERS = 11;

    private static final String FOLLOWERS = "Followers";
    private static final String PERSON_WHO_FOLLOWS_ME = "Person who follows me !";


    private EmbeddedDatabase db;
    private NamedParameterJdbcTemplate template;
    private FollowerDAO followerDAO;

    @Before
    public void setUp() throws Exception {

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        template = new NamedParameterJdbcTemplate(db);
        PersonDAO personDAO = new DefaultPersonDAO(template);
        followerDAO = new DefaultFollowerDAO(template, personDAO);
    }

    @Test
    public void findFollowerId() throws Exception {

        List<Integer> followerIds = followerDAO.findFollowerId(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(followerIds);
        Assert.assertTrue(followerIds.size() > 1);

    }

    @Test
    public void findFollowerIdWithInvalidPersonId() throws Exception {

        List<Integer> followerIds = followerDAO.findFollowerId(ANY_INVALID_PERSON_ID);

        Assert.assertNotNull(followerIds);
        Assert.assertEquals(0, followerIds.size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullJdbcTemplateShouldThrowIllegalArgumentException() throws Exception {

        new DefaultFollowerDAO(null, null);


    }

    @Test
    public void testFollowWithValidPersonID() {

        try {
            followerDAO.follow(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = PersonIdNotExistException.class)
    public void testFollowWithInvalidPersonID() {

        followerDAO.follow(ANY_VALID_PERSON_ID, ANY_INVALID_FOLLOWER_USERNAME);
    }

    @Test(expected = PersonAlreadyFollowedException.class)
    public void testFollowWithInValidPersonID() {

        followerDAO.follow(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);
        followerDAO.follow(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);

    }

    @Test(expected = PersonIdNotExistException.class)
    public void testFollowWithInValidFollowerId() {

        followerDAO.follow(ANY_VALID_PERSON_ID, null);

    }


    @Test(expected = PersonIdNotExistException.class)
    public void testUnFollowWithInvalidPersonID() {

        followerDAO.unFollow(ANY_VALID_PERSON_ID, ANY_INVALID_FOLLOWER_USERNAME);
    }

    @Test(expected = PersonNotFollowedException.class)
    public void testUnfollowWithAlreadyUnfollowedPersonId() {
        followerDAO.unFollow(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);
    }

    @Test
    public void testUnfollowWithAValidFollowedUserName() {
        try {
            followerDAO.unFollow(ANY_VALID_PERSON_ID, ANY_VALID_ALREADY_FOLLOWED_USER_NAME);
        } catch (Exception e) {
            Assert.fail();
        }
    }


    @Test
    public void testFindShortestDistance() {

        PersonFollowerShortDistance personFollowerShortDistance = followerDAO.findShortestDistance(ANY_VALID_PERSON_ID, ANY_VALID_FOLLOWER_USERNAME);
        Integer shortestDistance = personFollowerShortDistance.getShortestDistance();

        Assert.assertNotNull(shortestDistance);
        Assert.assertEquals(4, shortestDistance.intValue());
        Assert.assertNotNull(personFollowerShortDistance.getPersonName());
        Assert.assertEquals("Bruce Wayne",personFollowerShortDistance.getPersonName());
        Assert.assertNotNull(personFollowerShortDistance.getFollowerName());
        Assert.assertEquals("Dru-Zod",personFollowerShortDistance.getFollowerName());
    }


    @Test(expected = PersonIdNotExistException.class)
    public void testFindShortestDistanceWithInvalidFollower() {

        followerDAO.findShortestDistance(ANY_VALID_PERSON_ID, ANY_INVALID_FOLLOWER_USERNAME);

    }


    @Test
    public void testFindZeroShortestDistance() {

        PersonFollowerShortDistance personFollowerShortDistance = followerDAO.findShortestDistance(3, ANY_VALID_ZERO_DISTANCE_USERNAME);



        Assert.assertNotNull(personFollowerShortDistance);
        Assert.assertEquals(0, personFollowerShortDistance.getShortestDistance().intValue());

    }

    @Test
    public void testFindNegativeShortestDistance() {

        PersonFollowerShortDistance personFollowerShortDistance = followerDAO.findShortestDistance(3, ANY_VALID_USERNAME_THAT_IS_NOT_BEING_FOLLOWED);

        Assert.assertNotNull(personFollowerShortDistance);
        Assert.assertEquals(-1, personFollowerShortDistance.getShortestDistance().intValue());

    }

    @Test
    public void testFindPersonInfo(){

        PersonFollowerInfo personInfo = followerDAO.findPersonInfo(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(personInfo);
        Assert.assertNotNull(personInfo.getFollowedByPersons());
        Assert.assertTrue(personInfo.getFollowedByPersons().get(PERSON_WHO_FOLLOWS_ME).size() > 1);
        Assert.assertNotNull(personInfo.getFollowers());
        Assert.assertTrue(personInfo.getFollowers().get(FOLLOWERS).size() > 1);
    }

    @Test
    public void testFindPersonInfoWithoutAnyFollowers(){

        PersonFollowerInfo personInfo = followerDAO.findPersonInfo(ANY_VALID_PERSON_WITHOUT_FOLLOWERS);
        Assert.assertNotNull(personInfo);

        Assert.assertNotNull(personInfo.getFollowedByPersons());
        Assert.assertNotNull(personInfo.getFollowedByPersons().get(PERSON_WHO_FOLLOWS_ME));

        Assert.assertNotNull(personInfo.getFollowers());
        Assert.assertNotNull(personInfo.getFollowers().get(FOLLOWERS));



    }

}
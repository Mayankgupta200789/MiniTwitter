package challenge.dao;

import challenge.model.Person;
import challenge.response.PersonFollowerInfo;
import challenge.response.PersonFollowerShortDistance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockFollowerDAO implements FollowerDAO {

    private Integer personId;
    private String personName;
    private Integer shortestDistance;

    private List<Integer> personIds;

    private List<String> followerIds;

    private List<Person> followers;

    private List<Person> followedByPersons;

    private static final String FOLLOWERS = "Followers";
    private static final String PERSON_WHO_FOLLOWS_ME = "Person who follows me !";


    public MockFollowerDAO(Integer personId) {
        this.personId = personId;
    }

    public MockFollowerDAO(List<Integer> personIds, List<String> followerIds) {
        this.personIds = personIds;
        this.followerIds = followerIds;
    }

    public MockFollowerDAO(Integer personId,String personName,Integer shortestDistance) {

        this.personId = personId;
        this.personName = personName;
        this.shortestDistance = shortestDistance;
    }

    public MockFollowerDAO(List<Person> followers, List<Person> followedByPersons,boolean isFlag) {
        this.followers = followers;
        this.followedByPersons = followedByPersons;
    }

    @Override
    public List<Integer> findFollowerId(Integer personId) {

        if( personId == null ) {
            return new LinkedList<>();
        }

        List<Integer> followers = new LinkedList<>();

        followers.add(personId);
        return followers;
    }

    @Override
    public void follow(Integer personId, String followerUserName) {

        personIds.add(personId);
        followerIds.add(followerUserName);


    }

    @Override
    public void unFollow(Integer personId, String followerUserName) {

        personIds.remove(personId);
        followerIds.remove(followerUserName);
    }

    @Override
    public PersonFollowerShortDistance findShortestDistance(Integer personId, String followerUserName) {

        PersonFollowerShortDistance personFollowerShortDistance = new PersonFollowerShortDistance();
        personFollowerShortDistance.setShortestDistance(shortestDistance);
        return personFollowerShortDistance;
    }

    @Override
    public PersonFollowerInfo findPersonInfo(Integer personId) {

        PersonFollowerInfo personFollowerInfo = new PersonFollowerInfo();

        Map<String,List<Person>> followersMap = new HashMap<>();
        followersMap.put(FOLLOWERS,followers);

        Map<String,List<Person>> followedByMap = new HashMap<>();
        followedByMap.put(PERSON_WHO_FOLLOWS_ME,followedByPersons);


        personFollowerInfo.setFollowedByPersons(followedByMap);
        personFollowerInfo.setFollowers(followersMap);
        return personFollowerInfo;
    }

}

package challenge.service.follower;

import challenge.response.PersonFollowerInfo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockFollowersRetriever implements FollowersRetriever {


    private Set<Integer> validPersonId = new HashSet<>();


    public MockFollowersRetriever(Integer personId) {
        validPersonId.add(personId);
    }

    @Override
    public List<Integer> retrieve(Integer personId) {

        if(!validPersonId.contains(personId)) {
            return null;
        }

        List<Integer> followers = new LinkedList<>();
        followers.add(personId);
        return followers;
    }

    @Override
    public PersonFollowerInfo retrievePersonInfo(Integer personId) {
        return null;
    }
}

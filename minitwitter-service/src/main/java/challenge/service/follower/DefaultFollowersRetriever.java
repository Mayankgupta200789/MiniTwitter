package challenge.service.follower;

import challenge.dao.FollowerDAO;
import challenge.response.PersonFollowerInfo;

import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultFollowersRetriever implements FollowersRetriever {

    private FollowerDAO followerDAO;

    public DefaultFollowersRetriever(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

    @Override
    public List<Integer> retrieve(Integer personId) {


        return followerDAO.findFollowerId(personId);
    }

    @Override
    public PersonFollowerInfo retrievePersonInfo(Integer personId) {


        return followerDAO.findPersonInfo(personId);
    }
}

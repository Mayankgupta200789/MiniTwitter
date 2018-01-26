package challenge.service.follower;

import challenge.dao.FollowerDAO;
import challenge.response.PersonFollowerShortDistance;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultHopCountCalculator implements HopCountCalculator {

    private FollowerDAO followerDAO;

    public DefaultHopCountCalculator(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

    @Override
    public PersonFollowerShortDistance calculate(Integer personId, String followerUserName) {

        return followerDAO.findShortestDistance(personId, followerUserName);
    }
}

package challenge.service.follower;

import challenge.dao.FollowerStatsDAO;
import challenge.response.PopularFollowers;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultPopularFollowerRetriever implements PopularFollowerRetriever {

    private FollowerStatsDAO followerStatsDAO;

    public DefaultPopularFollowerRetriever(FollowerStatsDAO followerStatsDAO) {
        this.followerStatsDAO = followerStatsDAO;
    }

    @Override
    public PopularFollowers retrieve() {

        return followerStatsDAO.findPopularFollowers();
    }
}

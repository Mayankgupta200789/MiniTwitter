package challenge.dao;

import challenge.response.PopularFollowers;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class MockFollowerStatsDAO implements FollowerStatsDAO {

    private PopularFollowers popularFollowers;

    public MockFollowerStatsDAO(PopularFollowers popularFollowers) {
        this.popularFollowers = popularFollowers;
    }

    @Override
    public PopularFollowers findPopularFollowers() {
        return popularFollowers;
    }
}

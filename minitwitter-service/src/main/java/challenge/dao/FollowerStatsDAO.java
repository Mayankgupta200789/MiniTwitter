package challenge.dao;

import challenge.response.PopularFollowers;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public interface FollowerStatsDAO {


    /**
     * This method is responsible to retrieve list of popular
     * followers for all the list of possible persons
     *
     * @return
     */
    PopularFollowers findPopularFollowers();


}

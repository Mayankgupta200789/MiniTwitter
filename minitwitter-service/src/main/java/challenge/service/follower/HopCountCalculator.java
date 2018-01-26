package challenge.service.follower;

import challenge.response.PersonFollowerShortDistance;

/**
 *
 * This interface is responsible to calculate
 * the shortest distance between two persons
 * via followers
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public interface HopCountCalculator {

    /**
     * This method is responsible to calculate the shortest
     * distance between a person and its follower username
     *
     * @param personId
     * @param followerUserName
     * @return
     */
    PersonFollowerShortDistance calculate(Integer personId, String followerUserName);
}

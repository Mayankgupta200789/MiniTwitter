package challenge.dao;

import challenge.response.PersonFollowerInfo;
import challenge.response.PersonFollowerShortDistance;

import java.util.List;

/**
 *
 * This interface is reponsible to fetch
 * all the follower details for a given
 * person
 *
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface FollowerDAO {

    /**
     * This method is responsible to retrieve
     * all the follower ids for a give person
     *
     * @param personId
     * @return list of followers ids
     */
    List<Integer> findFollowerId(Integer personId);

    /**
     * This method is responsible to make a person
     * follow another person with a given personId
     * and followerUsername
     *
     * @param personId
     * @param followerUserName
     */
    void follow(Integer personId, String followerUserName);

    /**
     * This method is responsible to make a person
     * unfollow a person with a given personId and
     * followerUsername
     *
     * @param personId
     * @param followerUserName
     */
    void unFollow(Integer personId, String followerUserName);

    /**
     * This method is responsible to provide shortest distance between
     * a person and follower
     *
     * @param personId
     * @param followerUserName
     * @return
     */
    PersonFollowerShortDistance findShortestDistance(Integer personId, String followerUserName);

    /**
     * This method is responsible to return list of people who
     * the person follows and the persons who follow him.
     * @param personId
     * @return
     */
    PersonFollowerInfo findPersonInfo(Integer personId);

}

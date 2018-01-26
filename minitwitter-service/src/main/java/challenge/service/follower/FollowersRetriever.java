package challenge.service.follower;

import challenge.response.PersonFollowerInfo;

import java.util.List;

/**
 *
 * This interface is responsible to retrieve the
 * follower information for a person
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface FollowersRetriever {

    /**
     * This method retrieves the list of followerids
     * based on the person Id
     *
     * @param personId
     * @return
     */
    List<Integer> retrieve(Integer personId);

    /**
     * This method retrieve the list of followers
     * and the persons who follow this person.
     *
     * @param personId
     * @return Information about the followers and person whol follows him
     */
    PersonFollowerInfo retrievePersonInfo(Integer personId);
}

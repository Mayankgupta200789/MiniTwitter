package challenge.service.follower;

import challenge.response.PopularFollowers;

/**
 * This interface is responsible to retrieve
 * most popular followers for a person
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public interface PopularFollowerRetriever {

    /**
     * This method retrieve the list of persons
     * and its popular followers
     *
     * @return
     */
    PopularFollowers retrieve();
}

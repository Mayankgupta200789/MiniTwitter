package challenge.controller;

import challenge.response.PersonFollowerInfo;
import challenge.response.PersonFollowerShortDistance;
import challenge.response.PopularFollowers;
import challenge.security.dto.TwitterUser;
import challenge.security.util.PersonIdRetrieverUtil;
import challenge.service.follower.FollowersRetriever;
import challenge.service.follower.HopCountCalculator;
import challenge.service.follower.PopularFollowerRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * This controller is responbile to handle
 * following 2 request
 * 1) It is able to provide popular follower details
 * 2) It is able to shortest distance between two persons
 *
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
@RestController
@RequestMapping(value = "/twitter-service/v1")
public class PersonInfoController {

    private PopularFollowerRetriever popularFollowerRetriever;

    private HopCountCalculator hopCountCalculator;

    private FollowersRetriever followersRetriever;

    @Autowired
    public PersonInfoController(@Qualifier(value = "popularFollowerRetriever") PopularFollowerRetriever popularFollowerRetriever,
                                @Qualifier(value = "hopCountCalculator") HopCountCalculator hopCountCalculator,
                                @Qualifier(value = "followersRetriever")FollowersRetriever followersRetriever) {
        this.popularFollowerRetriever = popularFollowerRetriever;
        this.hopCountCalculator = hopCountCalculator;
        this.followersRetriever = followersRetriever;
    }

    /**
     * This method is responsible to return popular followers for list of users
     * @return PopularFollowers list for all the persons
     */
    @RequestMapping(method = RequestMethod.GET, value = "/persons/followers/favorites")
    public ResponseEntity<PopularFollowers> getPopularFollowers(){

        return new ResponseEntity<>(popularFollowerRetriever.retrieve(), HttpStatus.OK);
    }

    /**
     * This method is responsible to calculate the shortest distance
     * between a person and followerUsername
     * @param followerUserName
     * @return PersonFollowerShortDistance that contains the person name
     * follower name and their shortest distance
     */
    @RequestMapping(method = RequestMethod.GET, value = "/persons/followers/{followerUserName}/distance")
    public ResponseEntity<PersonFollowerShortDistance> getHopCount(@PathVariable String followerUserName){

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        return new ResponseEntity<>(hopCountCalculator.calculate(twitterUser.getPersonId(), followerUserName), HttpStatus.OK);
    }

    /**
     * This method is responsible to retrieve the followers
     * of a person and person who followed him.
     *
     * @return PersonFollowerInfo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/persons")
    public ResponseEntity<PersonFollowerInfo> getPersonInfo(){

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        Integer personId = twitterUser.getPersonId();

        return new ResponseEntity<>(followersRetriever.retrievePersonInfo(personId),HttpStatus.OK);
    }

}

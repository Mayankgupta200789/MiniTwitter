package challenge.dao;

import challenge.dao.exception.PersonAlreadyFollowedException;
import challenge.dao.exception.PersonIdNotExistException;
import challenge.dao.exception.PersonNotFollowedException;
import challenge.model.Person;
import challenge.response.PersonFollowerInfo;
import challenge.response.PersonFollowerShortDistance;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultFollowerDAO implements FollowerDAO {

    private static final String GET_FOLLOWER_IDS_FROM_PERSON_ID =
            "SELECT follower_person_id FROM followers WHERE person_id=:id";

    private static final String GET_PERSON_IDS_FROM_FOLLOWER_PERSON_ID =
            "SELECT person_id FROM followers WHERE follower_person_id=:follower_person_id";


    private static final String CHECK_FOLLOWER_ID_AGAINST_PERSON_ID =
            "SELECT follower_person_id FROM followers " +
            "WHERE person_id=:id and follower_person_id=:follower_person_id";

    private static final String INSERT_FOLLOWER_TO_PERSON =
            "INSERT INTO followers(person_id, follower_person_id)\n" +
            "VALUES\n" +
            "    (:person_id, :follower_person_id)";

    private static final String DELETE_FOLLOWER_FOR_A_PERSON =
            "DELETE FROM followers " +
            "WHERE person_id=:id and follower_person_id=:follower_person_id";


    private static final String ID = "id";
    private static final String FOLLOWER_PERSON_ID = "follower_person_id";
    private static final String PERSON_ID = "person_id";
    private static final String FOLLOWERS = "Followers";
    private static final String PERSON_WHO_FOLLOWS_ME = "Person who follows me !";

    private NamedParameterJdbcOperations jdbcTemplate;

    private PersonDAO personDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFollowerDAO.class);


    public DefaultFollowerDAO(NamedParameterJdbcOperations jdbcTemplate, PersonDAO personDAO) {
        Preconditions.checkArgument(personDAO != null, "Person DAO cannot be null");
        Preconditions.checkArgument(jdbcTemplate != null, "Jdbc Template cannot be null");
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    @Override
    public List<Integer> findFollowerId(Integer personId) {

        Map<String, Object> map = createSearchParamMap(personId,null);

        List<Integer> followerIds = jdbcTemplate.queryForList(GET_FOLLOWER_IDS_FROM_PERSON_ID, map, Integer.class);

        return followerIds;
    }



    @Override
    @CacheEvict(value = {"personsUserNameCache","personsCache"})
    public void follow(Integer personId, String followerUsername) {

        Person follower = personDAO.findByUserName(followerUsername);

        validateFollower(follower, followerUsername);

        Person person = personDAO.findById(personId);
        Integer followerId = follower.getPersonId();

        Map<String, Object> map = createSearchParamMap(personId,followerId);

        List<Integer> followerIds = jdbcTemplate.queryForList(CHECK_FOLLOWER_ID_AGAINST_PERSON_ID, map, Integer.class);

        if(CollectionUtils.isEmpty(followerIds)) {
            jdbcTemplate.update(INSERT_FOLLOWER_TO_PERSON,map);
            LOGGER.info("Follower " + followerUsername +" follows the person " +person.getHandle());
        } else {
            LOGGER.warn("Follower " +followerUsername +" for the person username " +person.getHandle() +" is already followed");
            throw new PersonAlreadyFollowedException("Follower " +followerUsername +" for the person " +person.getHandle() +" is already followed");
        }
    }


    @Override
    @CacheEvict(value = {"personsUserNameCache","personsCache"})
    public void unFollow(Integer personId, String followerUserName) {

        Person person = personDAO.findById(personId);
        Person follower = personDAO.findByUserName(followerUserName);

        validateFollower(follower,followerUserName);

        Integer followerId = follower.getPersonId();

        Map<String, Object> map = createSearchParamMap(personId,followerId);

        List<Integer> currentFollower = findFollowerId(personId);

        if(!currentFollower.contains(followerId)) {
            LOGGER.warn("Follower " +followerUserName + "is  unfollowed already");
            throw new PersonNotFollowedException("Follower " +followerUserName + "is  unfollowed already by " +person.getHandle());
        }

        jdbcTemplate.update(DELETE_FOLLOWER_FOR_A_PERSON,map);
        LOGGER.info("Follower " + followerUserName + " is now followed by the person" + person.getHandle());

    }

    @Override
    @CacheEvict(value = {"personsCache"})
    public PersonFollowerShortDistance findShortestDistance(Integer personId, String followerUserName) {

        Person person = personDAO.findById(personId);
        Person follower = personDAO.findByUserName(followerUserName);

        validateFollower(follower,followerUserName);

        Integer followerId = follower.getPersonId();

        PersonFollowerShortDistance personFollowerShortDistance = new PersonFollowerShortDistance();

        personFollowerShortDistance.setPersonName(person.getName());
        personFollowerShortDistance.setFollowerName(follower.getName());

        if(personId.equals(followerId)) {
            personFollowerShortDistance.setShortestDistance(0);
            return personFollowerShortDistance;
        }

        List<Integer> followerIds = findFollowerId(personId);

        Set<Integer> visited = new HashSet<>();

        Queue<Integer> queue = new LinkedList<>();

        int level = 1;

        queue.addAll(followerIds);
        queue.add(null);

        while(!queue.isEmpty()) {

            Integer inputFolowerId = queue.remove();

            if( inputFolowerId == null) {
                level++;
            } else if ( inputFolowerId.equals(followerId)) {
                personFollowerShortDistance.setShortestDistance(level);
                return personFollowerShortDistance;
            } else if( !visited.contains(inputFolowerId)){
                visited.add(inputFolowerId);
                queue.addAll(findFollowerId(inputFolowerId));
                queue.add(null);
            }
        }

        personFollowerShortDistance.setShortestDistance(-1);

        return personFollowerShortDistance;
    }

    @Override
    public PersonFollowerInfo findPersonInfo(Integer personId) {

        PersonFollowerInfo personFollowerInfo = new PersonFollowerInfo();
        personFollowerInfo.setPersonName(personDAO.findById(personId).getName());

        List<Integer> followers = findFollowerId(personId);

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put(FOLLOWER_PERSON_ID,personId);
        List<Integer> followedByPersonIds = jdbcTemplate.queryForList(GET_PERSON_IDS_FROM_FOLLOWER_PERSON_ID, hashMap, Integer.class);

        Map<String,List<Person>> followersMap = new HashMap<>();
        Map<String,List<Person>> followedByHashMap = new HashMap<>();

        List<Person> listOfFollowerPersons = new LinkedList<>();
        List<Person> listOfFollowedByPersons = new LinkedList<>();

        addListOfFollowers(followers, listOfFollowerPersons);
        followersMap.put(FOLLOWERS,listOfFollowerPersons);

        addListOfFollowers(followedByPersonIds, listOfFollowedByPersons);
        followedByHashMap.put(PERSON_WHO_FOLLOWS_ME,listOfFollowedByPersons);

        personFollowerInfo.setFollowers(followersMap);
        personFollowerInfo.setFollowedByPersons(followedByHashMap);

        return personFollowerInfo;
    }

    private void addListOfFollowers(List<Integer> followers, List<Person> listOfFollowerPersons) {
        if(!CollectionUtils.isEmpty(followers)) {
            for (Integer followerId : followers) {
                Person person = personDAO.findById(followerId);
                listOfFollowerPersons.add(person);
            }
        }
    }

    private void validateFollower(Person follower,String followerUsername) {

        if(follower == null ) {

            LOGGER.error("Follower UserName " + followerUsername + " does not exist");
            throw new PersonIdNotExistException("Follower UserName " + followerUsername + " does not exist");
        }
    }

    private Map<String, Object> createSearchParamMap(Object personId,
                                                     Object followerId) {
        Map<String,Object> map= new HashMap<>();
        map.put(ID,personId);
        if( followerId != null ) {
            map.put(FOLLOWER_PERSON_ID, followerId);
        }
        map.put(PERSON_ID,personId);

        return map;
    }
}

package challenge.dao;

import challenge.dao.mapper.PopularFollowerIdRowMapper;
import challenge.model.Person;
import challenge.response.PopularFollowers;
import com.google.common.base.Preconditions;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class DefaultFollowerStatsDAO implements FollowerStatsDAO {

    private static final String GET_PERSON_COUNT_WHO_FOLLOW_MOST_PERSONS =
            "SELECT f.person_id,f.follower_person_id from followers f" +
                    " WHERE f.follower_person_id =  " +
                    "( SELECT fo.follower_person_id FROM followers fo" +
                    "  WHERE fo.person_id = f.person_id" +
                    "  GROUP BY fo.follower_person_id" +
                    "  ORDER BY COUNT(*) DESC LIMIT 1) ";

    private NamedParameterJdbcOperations jdbcTemplate;
    private PersonDAO personDAO;

    public DefaultFollowerStatsDAO(NamedParameterJdbcOperations jdbcTemplate,
                                   PersonDAO personDAO) {
        Preconditions.checkArgument(jdbcTemplate != null, "Jdbc template cannot be null");
        Preconditions.checkArgument(personDAO != null, "Person DAO cannot be null");
        this.personDAO = personDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PopularFollowers findPopularFollowers() {


        List<Map<Integer, Integer>> popularFollowerIds = jdbcTemplate.query(GET_PERSON_COUNT_WHO_FOLLOW_MOST_PERSONS,
                new PopularFollowerIdRowMapper());

        Map<String, String> popularPersonMap = new HashMap<>();

        PopularFollowers popularFollowers = new PopularFollowers();

        for (Map<Integer, Integer> popularFollowerIdMap : popularFollowerIds) {

            for (Map.Entry<Integer, Integer> entry : popularFollowerIdMap.entrySet()) {

                Person person = personDAO.findById(entry.getKey());
                Person follower = personDAO.findById(entry.getValue());

                popularPersonMap.put(person.getName(), follower.getName());
            }

        }
        popularFollowers.setPopularPersonMap(popularPersonMap);

        return popularFollowers;
    }
}

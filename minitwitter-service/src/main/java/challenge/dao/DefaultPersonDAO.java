package challenge.dao;

import challenge.dao.mapper.PersonRowMapper;
import challenge.model.Person;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class DefaultPersonDAO implements PersonDAO {

    private static final String ID = "id";
    private static final String HANDLE = "handle";
    private NamedParameterJdbcOperations jdbcTemplate;

    private static final String GET_PERSON_FROM_ID = "SELECT id,handle,name FROM PEOPLE WHERE ID=:id";
    private static final String GET_PERSON_FROM_USERNAME = "SELECT id,handle,name FROM PEOPLE WHERE handle=:handle";

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPersonDAO.class);

    public DefaultPersonDAO(NamedParameterJdbcOperations jdbcTemplate) {
        Preconditions.checkArgument(jdbcTemplate != null, "Jdbc template cannot be null");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Cacheable(value = "personsCache")
    public Person findById(Integer personId) {

        Map<String, Object> map = prepareParams(personId,ID);

        Person person = null;

        try {
            person = (Person) jdbcTemplate.queryForObject(GET_PERSON_FROM_ID,
                    map, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Person " + personId + " cannot be found");
            return person;
        }

        return person;
    }

    @Override
    @Cacheable(value = "personsUserNameCache")
    public Person findByUserName(String personUserName) {

        Map<String, Object> map = prepareParams(personUserName,HANDLE);

        Person person = null;

        try {
            person = (Person) jdbcTemplate.queryForObject(GET_PERSON_FROM_USERNAME,
                    map, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Person " + personUserName + " cannot be found due to " +e.getLocalizedMessage());
            return person;
        } catch (Exception e) {
            LOGGER.error("Could not retrive the id for user name " + personUserName +" due to " + e.getLocalizedMessage());
            return person;
        }

        return person;
    }

    private Map<String, Object> prepareParams(Object param,String searchIndex) {
        Map<String,Object> map= new HashMap<>();
        map.put(searchIndex,param);
        return map;
    }
}

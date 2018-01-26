package challenge.dao;

import challenge.dao.mapper.MessageRowMapper;
import challenge.model.Message;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageDAO implements MessageDAO {


    private static final String KEYWORD = "keyword";
    private static final String AMPERSAND = "%";
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_MESSAGES_FROM_PERSON_ID =
            "SELECT DISTINCT content FROM messages WHERE person_id=:id";

    private static final String GET_MESSAGES_FROM_PERSON_ID_WITH_KEYWORD =
            "SELECT DISTINCT content FROM messages WHERE person_id=:id AND content LIKE :keyword";

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageDAO.class);


    private static final String ID = "id";

    public DefaultMessageDAO(NamedParameterJdbcTemplate jdbcTemplate) {
        Preconditions.checkArgument(jdbcTemplate != null, " Jdbc template cannot be null");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Cacheable(value = "messageCache")
    public List<Message> fetch(Integer personId, String keyword) {

        Map<String, Object> map = new HashMap<>();
        map.put(ID, personId);
        map.put(KEYWORD,AMPERSAND + keyword + AMPERSAND);

        List<Message> messages = null;

        final String sql = !StringUtils.isEmpty(keyword) ? GET_MESSAGES_FROM_PERSON_ID_WITH_KEYWORD : GET_MESSAGES_FROM_PERSON_ID;

        try {
            messages =
                    jdbcTemplate.queryForObject(sql, map, new MessageRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Message for the person " +personId +" could not be found");
            return messages;
        }

        return messages;
    }
}

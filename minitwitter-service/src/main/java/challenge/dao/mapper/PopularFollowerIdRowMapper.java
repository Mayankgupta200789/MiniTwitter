package challenge.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * This method is responsible to map
 * the follower details
 *
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class PopularFollowerIdRowMapper implements RowMapper {


    /**
     * This method is responsible to
     * @param resultSet
     * @param i
     * @return Map for a person and follower name
     * @throws SQLException
     */
    @Override
    public Map<Integer,Integer> mapRow(ResultSet resultSet, int i) throws SQLException {

        Map<Integer,Integer> hashMap = new HashMap<>();

        hashMap.put(resultSet.getInt(1),resultSet.getInt(2));

        return hashMap;
    }
}

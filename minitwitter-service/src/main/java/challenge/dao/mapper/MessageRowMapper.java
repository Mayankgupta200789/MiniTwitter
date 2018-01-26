package challenge.dao.mapper;

import challenge.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * This class is responsible to map
 * the result set from the message dao
 * class to a dto
 *
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MessageRowMapper implements RowMapper<List<Message>> {


    /**
     * This method maps the output resultset to
     * a message and then return those list of all the
     * messages
     *
     * @param resultSet
     * @param i
     * @return Person
     * @throws SQLException
     */
    @Override
    public List<Message> mapRow(ResultSet resultSet, int i) throws SQLException {

        List<Message> messages = new LinkedList<>();

        do {
            Message message = new Message();
            message.setContent(resultSet.getString(1));
            messages.add(message);
        }while(resultSet.next());

        return messages;
    }
}

package challenge.dao;

import challenge.model.Message;

import java.util.List;

/**
 *
 * This DAO class is responsible
 * to fetch message details for a person
 * and with a given keyword
 *
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface MessageDAO {

    /**
     * This method is responsible to fetch list of all the messages
     * based on the personId and keyword
     *
     * @param personId
     * @param keyword
     * @return
     */
    List<Message> fetch(Integer personId, String keyword);
}

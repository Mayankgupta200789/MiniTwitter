package challenge.dao;

import challenge.model.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockMessageDAO implements MessageDAO {

    private String content;

    public MockMessageDAO(String content) {
        this.content = content;
    }

    @Override
    public List<Message> fetch(Integer personId, String keyword) {

        List<Message> messages = new LinkedList<>();
        Message message = new Message();
        message.setContent(content);
        messages.add(message);
        return messages;
    }
}

package challenge.service.message;

import challenge.model.Message;
import challenge.service.message.context.MessageRetrieverContext;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockMessageRetriever implements MessageRetriever {


    private Set<Integer> validPersonIds = new HashSet<>();

    private String content;

    public MockMessageRetriever(Integer personId,String content) {
        validPersonIds.add(personId);
        this.content = content;
    }

    @Override
    public List<Message> retrieve(MessageRetrieverContext messageRetrieverContext) {

        Integer personId = messageRetrieverContext.getPersonId();

        if(!validPersonIds.contains(personId)) {
            return null;
        }

        Message message = new Message();
        message.setContent(content);
        List<Message> messages = new LinkedList<>();
        messages.add(message);

        return messages;
    }
}

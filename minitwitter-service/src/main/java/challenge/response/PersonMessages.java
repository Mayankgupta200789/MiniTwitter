package challenge.response;

import challenge.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonMessages {

    private String name;

    private List<Message> messages;

    private List<PersonMessages> followerMessages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<PersonMessages> getFollowerMessages() {
        return followerMessages;
    }

    public void setFollowerMessages(List<PersonMessages> followerMessages) {
        this.followerMessages = followerMessages;
    }
}

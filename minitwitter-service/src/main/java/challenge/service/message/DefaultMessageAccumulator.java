package challenge.service.message;

import challenge.model.Message;
import challenge.model.Person;
import challenge.response.PersonMessages;
import challenge.service.follower.FollowersRetriever;
import challenge.service.message.context.MessageAccumulatorContext;
import challenge.service.message.context.MessageRetrieverContext;
import challenge.service.person.PersonRetriever;
import com.google.common.base.Preconditions;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageAccumulator implements MessageAccumulator {


    private MessageRetriever messageRetriever;

    private FollowersRetriever followersRetriever;

    private PersonRetriever personRetriever;

    public DefaultMessageAccumulator(MessageRetriever messageRetriever,
                                     FollowersRetriever followersRetriever,
                                     PersonRetriever personRetriever) {
        Preconditions.checkArgument(messageRetriever != null, "Message Retriever service cannot be null");
        Preconditions.checkArgument(followersRetriever != null, " Follower Retriever service cannot be null");
        Preconditions.checkArgument(personRetriever != null, "Person Retriever service cannot be null");
        this.messageRetriever = messageRetriever;
        this.followersRetriever = followersRetriever;
        this.personRetriever = personRetriever;
    }

    @Override
    public PersonMessages accumulate(MessageAccumulatorContext messageAccumulatorContext) {

        Integer personId = messageAccumulatorContext.getPersonId();
        String keyword = messageAccumulatorContext.getKeyword();

        Person person = personRetriever.retrieve(personId);

        List<Integer> followerIds = followersRetriever.retrieve(personId);

        MessageRetrieverContext messageRetrieverContext = createMessageRetrieverContext(personId, keyword);

        List<Message> personalMessages = messageRetriever.retrieve(messageRetrieverContext);

        PersonMessages personMessages = new PersonMessages();

        if( person == null ) {
            return personMessages;
        }

        personMessages.setName(person.getName());
        if(!CollectionUtils.isEmpty(personalMessages)) {
            personMessages.setMessages(personalMessages);
        }

        if( followerIds != null ) {
            prepareFollowerMessages(followerIds, personMessages,keyword);
        }


        return personMessages;
    }

    private MessageRetrieverContext createMessageRetrieverContext(Integer personId, String keyword) {
        MessageRetrieverContext messageRetrieverContext = new MessageRetrieverContext(personId,keyword);
        messageRetrieverContext.setKeyword(keyword);
        messageRetrieverContext.setPersonId(personId);
        return messageRetrieverContext;
    }

    private void prepareFollowerMessages(List<Integer> followerIds,
                                         PersonMessages personMessages,
                                         String keyword) {

        for( Integer followerId : followerIds ) {

            Person follower = personRetriever.retrieve(followerId);
            MessageRetrieverContext messageRetrieverContext = createMessageRetrieverContext(followerId, keyword);
            List<Message> messages = messageRetriever.retrieve(messageRetrieverContext);

            if( messages == null ) continue;

            PersonMessages followerMessage = new PersonMessages();
            followerMessage.setName(follower.getName());
            followerMessage.setMessages(messages);
            List<PersonMessages> followerMessages = personMessages.getFollowerMessages();

            if( followerMessages == null ) {
                followerMessages = new LinkedList<>();
            }
            followerMessages.add(followerMessage);
            personMessages.setFollowerMessages(followerMessages);
        }
    }
}

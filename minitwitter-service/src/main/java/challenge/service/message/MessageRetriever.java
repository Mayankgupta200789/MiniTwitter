package challenge.service.message;

import challenge.model.Message;
import challenge.service.message.context.MessageRetrieverContext;

import java.util.List;

/**
 *
 * This interface is responsible to fetch
 * the messages based on the search context
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface MessageRetriever {

    /**
     * This method is responsible to retrieve the message from message
     * retriever context that context person id and search keyword
     *
     * @param messageRetrieverContext
     * @return
     */
    List<Message> retrieve(MessageRetrieverContext messageRetrieverContext);
}

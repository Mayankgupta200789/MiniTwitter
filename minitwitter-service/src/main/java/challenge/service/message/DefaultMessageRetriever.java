package challenge.service.message;

import challenge.dao.MessageDAO;
import challenge.model.Message;
import challenge.service.message.context.MessageRetrieverContext;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageRetriever implements MessageRetriever {

    private MessageDAO messageDAO;

    public DefaultMessageRetriever(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public List<Message> retrieve(MessageRetrieverContext messageRetrieverContext) {


        Integer personId = messageRetrieverContext.getPersonId();
        String keyword = messageRetrieverContext.getKeyword();



        if( personId == null ) {
            return new LinkedList<>();
        }

        return messageDAO.fetch(personId,keyword);
    }
}

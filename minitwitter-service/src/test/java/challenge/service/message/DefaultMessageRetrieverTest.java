package challenge.service.message;

import challenge.dao.MessageDAO;
import challenge.dao.MockMessageDAO;
import challenge.model.Message;
import challenge.service.message.context.MessageRetrieverContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageRetrieverTest {

    private static final int ANY_VALID_PERSON_ID = 4;
    private static final String ANY_VALID_CONTENT = "anyValidContent";
    private static final String ANY_VALID_KEYWORD = "like";
    private DefaultMessageRetriever messageRetriever;
    private MessageRetrieverContext messageRetrieverContext;

    @Before
    public void setUp() throws Exception {

        MessageDAO mockMessageDAO = new MockMessageDAO(ANY_VALID_CONTENT);
        messageRetriever = new DefaultMessageRetriever(mockMessageDAO);

        messageRetrieverContext = new MessageRetrieverContext(ANY_VALID_PERSON_ID, ANY_VALID_KEYWORD);
    }

    @Test
    public void retrieve() throws Exception {

        List<Message> messages = messageRetriever.retrieve(messageRetrieverContext);

        Assert.assertNotNull(messages);

        Assert.assertEquals(1,messages.size());

        Message message = messages.get(0);


        Assert.assertNotNull(message);
        Assert.assertNotNull(ANY_VALID_CONTENT, message.getContent());

    }

    @Test
    public void retrieveWithNullPersonId() throws Exception {

        messageRetrieverContext.setPersonId(null);

        List<Message> messages = messageRetriever.retrieve(messageRetrieverContext);

        Assert.assertNotNull(messages);

        Assert.assertEquals(0,messages.size());

    }

}
package challenge.service;

import challenge.model.Message;
import challenge.model.Person;
import challenge.response.PersonMessages;
import challenge.service.follower.FollowersRetriever;
import challenge.service.follower.MockFollowersRetriever;
import challenge.service.message.DefaultMessageAccumulator;
import challenge.service.message.MessageAccumulator;
import challenge.service.message.MessageRetriever;
import challenge.service.message.MockMessageRetriever;
import challenge.service.message.context.MessageAccumulatorContext;
import challenge.service.person.MockPersonRetriever;
import challenge.service.person.PersonRetriever;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageAccumulatorTest {


    private static final int ANY_VALID_PERSON_ID = 4;
    private static final String MOCK_PERSON_NAME = "mock_Person_Name";
    private static final String ANY_VALID_CONTENT = "anyValidContent";
    private static final int ANY_INVALID_PERSON_ID = -1;
    private static final String MOCK_CONTENT = "Mock Content";
    private static final String ANY_VALID_KEYWORD = "like";
    private MessageAccumulator validMessageAccumulator;
    private MessageAccumulator invalidMessageAccumulator;
    private MessageAccumulatorContext messageAccumulatorContextWithValidPersonID;
    private MessageAccumulatorContext messageAccumulatorContextWithInvalidPersonId;

    @Before
    public void setUp() throws Exception {

        Message mockMessage = new Message();
        mockMessage.setContent(ANY_VALID_CONTENT);
        Person mockPerson = new Person();
        mockPerson.setPersonId(ANY_VALID_PERSON_ID);
        mockPerson.setName(MOCK_PERSON_NAME);
        List<Integer> followerIds = new LinkedList<>();
        followerIds.add(ANY_VALID_PERSON_ID);
        List<Message> messages = new LinkedList<>();
        messages.add(mockMessage);
        MessageRetriever mockValidMessageRetriever = new MockMessageRetriever(ANY_VALID_PERSON_ID, MOCK_CONTENT);
        FollowersRetriever mockValidFollowersRetriever = new MockFollowersRetriever(ANY_VALID_PERSON_ID);
        PersonRetriever mockValidPersonRetriever = new MockPersonRetriever(ANY_VALID_PERSON_ID,MOCK_PERSON_NAME);

        MessageRetriever mockInvalidMessageRetriever = new MockMessageRetriever(ANY_INVALID_PERSON_ID, MOCK_CONTENT);
        FollowersRetriever mockInvalidFollowersRetriever = new MockFollowersRetriever(ANY_INVALID_PERSON_ID);
        PersonRetriever mockInvalidPersonRetriever = new MockPersonRetriever(ANY_INVALID_PERSON_ID,null);

        messageAccumulatorContextWithValidPersonID = createMessageAccumulatorContext(ANY_VALID_PERSON_ID, ANY_VALID_KEYWORD);
        messageAccumulatorContextWithInvalidPersonId = createMessageAccumulatorContext(ANY_INVALID_PERSON_ID, ANY_VALID_KEYWORD);


        validMessageAccumulator = new DefaultMessageAccumulator(mockValidMessageRetriever, mockValidFollowersRetriever, mockValidPersonRetriever);
        invalidMessageAccumulator = new DefaultMessageAccumulator(mockInvalidMessageRetriever, mockInvalidFollowersRetriever, mockInvalidPersonRetriever);

    }

    @Test
    public void accumulate() throws Exception {




        PersonMessages personMessages = validMessageAccumulator.accumulate(messageAccumulatorContextWithValidPersonID);

        Assert.assertNotNull(personMessages);

        Assert.assertEquals(MOCK_PERSON_NAME , personMessages.getName());
        Assert.assertNotNull(personMessages.getMessages());
    }

    @Test
    public void accumulateWithInvalidId() throws Exception {

        PersonMessages personMessages = null;
        try {
            personMessages = invalidMessageAccumulator.accumulate(messageAccumulatorContextWithInvalidPersonId);
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertNotNull(personMessages);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMessageAccumulatorWithNullRetrievers() {

        new DefaultMessageAccumulator(null,null,null);
    }

    private MessageAccumulatorContext createMessageAccumulatorContext(int anyValidPersonId, String anyValidKeyword) {
        MessageAccumulatorContext messageAccumulatorContext = new MessageAccumulatorContext();
        messageAccumulatorContext.setPersonId(anyValidPersonId);
        messageAccumulatorContext.setKeyword(anyValidKeyword);
        return messageAccumulatorContext;
    }

}
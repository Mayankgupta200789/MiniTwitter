package challenge.service.message;

import challenge.response.PersonMessages;
import challenge.service.message.context.MessageAccumulatorContext;

/**
 * @Author Mayank Gupta
 * @Date 8/18/17
 */
public class MockMessageAccumulator implements MessageAccumulator {

    private PersonMessages personMessages = new PersonMessages();

    public MockMessageAccumulator(PersonMessages personMessages) {
        this.personMessages = personMessages;
    }

    @Override
    public PersonMessages accumulate(MessageAccumulatorContext messageAccumulatorContext) {
        return personMessages;
    }
}

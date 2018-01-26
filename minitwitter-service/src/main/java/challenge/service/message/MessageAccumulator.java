package challenge.service.message;

import challenge.response.PersonMessages;
import challenge.service.message.context.MessageAccumulatorContext;

/**
 *
 * This interface is responsible to
 * assemble all the messages from different
 * followers and filter it using the search filter
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public interface MessageAccumulator {


    /**
     * This method assembles the messages based on the message
     * accumulator context
     *
     * @param messageAccumulatorContext
     * @return
     */
    PersonMessages accumulate(MessageAccumulatorContext messageAccumulatorContext);

}

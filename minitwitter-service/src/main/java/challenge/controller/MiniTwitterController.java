package challenge.controller;

import challenge.response.PersonMessages;
import challenge.response.ResponseSummary;
import challenge.security.dto.TwitterUser;
import challenge.security.util.PersonIdRetrieverUtil;
import challenge.service.command.Command;
import challenge.service.command.context.CommandContext;
import challenge.service.message.MessageAccumulator;
import challenge.service.message.context.MessageAccumulatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static challenge.response.util.ResponseSummaryUtil.getResponseSummary;

/**
 *
 * This controller is responsible to handle requests as
 * mentioned below
 * 1) It will retrieve all the messages from its followers
 * and himself. In addition it will also provide an additional
 * benefit of passing a search parameter to filter the messages
 * 2) It will let persons follow any person who exist in our
 * database
 * 3  It will let persons unfollow any person who exist in our
 * system
 *
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
@RestController
@RequestMapping(value = "/twitter-service/v1")
public class MiniTwitterController {


    private MessageAccumulator messageAccumulator;

    private Command followCommand;

    private Command unFollowCommand;

    @Autowired
    public MiniTwitterController(@Qualifier(value = "messageAccumulator") MessageAccumulator messageAccumulator,
                                 @Qualifier(value = "followCommand") Command followCommand,
                                 @Qualifier(value = "unFollowCommand") Command unFollowCommand) {
        this.messageAccumulator = messageAccumulator;
        this.followCommand = followCommand;
        this.unFollowCommand = unFollowCommand;
    }

    /**
     * This method is responsible to return list of messages
     * for a given person and search param
     * @param keyword
     * @return PersonMessages : list of all the personal messsages and followers messages
     */
    @RequestMapping(method = RequestMethod.GET, value = "/persons/messages")
    public ResponseEntity<PersonMessages> getMessages(@RequestParam(value = "search",required = false) String keyword){

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        Integer personId = twitterUser.getPersonId();

        MessageAccumulatorContext messageAccumulatorContext = new MessageAccumulatorContext();
        if(!StringUtils.isEmpty(keyword)) {
            messageAccumulatorContext.setKeyword(keyword);
        }
        messageAccumulatorContext.setPersonId(personId);

        return new ResponseEntity<>(messageAccumulator.accumulate(messageAccumulatorContext), HttpStatus.OK);
    }

    /**
     * This method is responsible to make a person
     * follow another person
     * @param followerUsername
     * @return status code
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/persons/followers/{followerUsername}")
    public ResponseEntity<ResponseSummary> followPerson(@PathVariable String followerUsername){

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        Integer personId = twitterUser.getPersonId();
        String username = twitterUser.getUsername();

        CommandContext commandContext = getCommandContext(personId,followerUsername);

        followCommand.execute(commandContext);
        ResponseSummary responseSummary = getResponseSummary("Follower " + followerUsername + " is followed for person " + username, HttpStatus.CREATED);
        return new ResponseEntity<>(responseSummary,HttpStatus.CREATED);
    }

    /**
     * This method is responsible to make a person
     * unfollow another person
     *
     * @param followerUsername
     * @return ResponseSummary
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/persons/followers/{followerUsername}")
    public ResponseEntity<ResponseSummary> unFollowPerson(@PathVariable String followerUsername){

        TwitterUser twitterUser = PersonIdRetrieverUtil.retrieve();

        Integer personId = twitterUser.getPersonId();
        String username = twitterUser.getUsername();

        CommandContext commandContext = getCommandContext(personId, followerUsername);

        unFollowCommand.execute(commandContext);
        ResponseSummary responseSummary = getResponseSummary("Follower " + followerUsername + " is unfollowed for person " + username, HttpStatus.OK);
        return new ResponseEntity<>(responseSummary,HttpStatus.OK);
    }

    private CommandContext getCommandContext(Integer personId,String followerUsername) {
        CommandContext commandContext = new CommandContext();
        commandContext.setPersonId(personId);
        commandContext.setFollowerUsername(followerUsername);
        return commandContext;
    }


}

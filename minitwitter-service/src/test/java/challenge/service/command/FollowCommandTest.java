package challenge.service.command;

import challenge.dao.FollowerDAO;
import challenge.dao.MockFollowerDAO;
import challenge.service.command.context.CommandContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class FollowCommandTest {

    private static final int ANY_VALID_PERSON_ID = 2;
    private static final String ANY_VALID_FOLLOWER_USERNAME = "daredevil";
    private FollowerDAO followerDAO;
    private Command followCommand;
    private CommandContext commandContext;
    private List<Integer> personIds;
    private List<String> followerUserNames;

    @Before
    public void setUp() throws Exception {

        personIds = new LinkedList<>();
        followerUserNames = new LinkedList<>();
        followerDAO = new MockFollowerDAO(personIds, followerUserNames);
        followCommand = new FollowCommand(followerDAO);

        commandContext = new CommandContext();
        commandContext.setFollowerUsername(ANY_VALID_FOLLOWER_USERNAME);
        commandContext.setPersonId(ANY_VALID_PERSON_ID);
    }

    @Test
    public void execute() throws Exception {

        followCommand.execute(commandContext);

        Assert.assertEquals(1,personIds.size());
        Assert.assertEquals(1, followerUserNames.size());
    }

    @Test
    public void executeWithNullCommandContext() throws Exception {

        followCommand.execute(null);

        Assert.assertEquals(0,personIds.size());
        Assert.assertEquals(0, followerUserNames.size());
    }

    @Test
    public void executeWithNullPersonId() throws Exception {
        commandContext.setPersonId(null);

        followCommand.execute(commandContext);

        Assert.assertEquals(0,personIds.size());
        Assert.assertEquals(0, followerUserNames.size());
    }

    @Test
    public void executeWithNullFollowerId() throws Exception {
        commandContext.setFollowerUsername(null);

        followCommand.execute(commandContext);

        Assert.assertEquals(0,personIds.size());
        Assert.assertEquals(0, followerUserNames.size());
    }


}
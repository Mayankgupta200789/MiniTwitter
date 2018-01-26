package challenge.service.command;

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
public class UnFollowCommandTest {

    private static final int ANY_VALID_PERSON_ID = 1;
    private static final String ANY_VALID_FOLLOWER_USERNAME = "daredevil";
    private MockFollowerDAO followerDAO;
    private UnFollowCommand unFollowCommand;
    private CommandContext commandContext;
    private List<Integer> personIds;
    private List<String> followerIds;

    @Before
    public void setUp() throws Exception {

        personIds = new LinkedList<>();
        personIds.add(ANY_VALID_PERSON_ID);
        followerIds = new LinkedList<>();
        followerIds.add(ANY_VALID_FOLLOWER_USERNAME);
        followerDAO = new MockFollowerDAO(personIds, followerIds);
        unFollowCommand = new UnFollowCommand(followerDAO);

        commandContext = new CommandContext();
        commandContext.setPersonId(ANY_VALID_PERSON_ID);
        commandContext.setFollowerUsername(ANY_VALID_FOLLOWER_USERNAME);

    }

    @Test
    public void execute() throws Exception {

        unFollowCommand.execute(commandContext);

        Assert.assertEquals(0,personIds.size());
        Assert.assertEquals(0,followerIds.size());
    }

    @Test
    public void executeWithNullCommandContext() throws Exception {

        unFollowCommand.execute(null);

        Assert.assertEquals(1,personIds.size());
        Assert.assertEquals(1,followerIds.size());
    }

    @Test
    public void executeWithNullFollowerId() throws Exception {

        commandContext.setFollowerUsername(null);

        unFollowCommand.execute(commandContext);

        Assert.assertEquals(1,personIds.size());
        Assert.assertEquals(1,followerIds.size());
    }

    @Test
    public void executeWithNullPersonId() throws Exception {

        commandContext.setPersonId(null);

        unFollowCommand.execute(commandContext);

        Assert.assertEquals(1,personIds.size());
        Assert.assertEquals(1,followerIds.size());
    }

}
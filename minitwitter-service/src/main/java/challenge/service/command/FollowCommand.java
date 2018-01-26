package challenge.service.command;

import challenge.dao.FollowerDAO;
import challenge.service.command.context.CommandContext;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class FollowCommand implements Command {

    private FollowerDAO followerDAO;

    public FollowCommand(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

    @Override
    public void execute(CommandContext commandContext) {

        if( commandContext == null || commandContext.getPersonId() == null
                || commandContext.getFollowerUsername() == null ) {
            return;
        }

        followerDAO.follow(commandContext.getPersonId(),commandContext.getFollowerUsername());

    }
}

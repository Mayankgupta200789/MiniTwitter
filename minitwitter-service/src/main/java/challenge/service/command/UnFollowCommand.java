package challenge.service.command;

import challenge.dao.FollowerDAO;
import challenge.service.command.context.CommandContext;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class UnFollowCommand implements Command {

    private FollowerDAO followerDAO;

    public UnFollowCommand(FollowerDAO followerDAO) {
        this.followerDAO = followerDAO;
    }

    @Override
    public void execute(CommandContext commandContext) {

        if( commandContext == null ||commandContext.getPersonId() == null
                || commandContext.getFollowerUsername() == null ) {
            return;
        }

        followerDAO.unFollow(commandContext.getPersonId(),commandContext.getFollowerUsername());
    }
}

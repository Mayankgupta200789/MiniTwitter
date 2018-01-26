package challenge.service.command;

import challenge.service.command.context.CommandContext;

/**
 *
 * This interface is responsible to handle
 * the incoming commands that will let
 * persons follow or unfollow a person
 *
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface Command {

    /**
     * This method will execute the command
     * based on the context of the command
     *
     * @param commandContext
     */
    void execute(CommandContext commandContext);
}

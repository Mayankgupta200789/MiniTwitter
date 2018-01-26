package challenge.controller.configuration;

import challenge.dao.FollowerDAO;
import challenge.dao.configuration.DAOConfiguration;
import challenge.service.command.Command;
import challenge.service.command.FollowCommand;
import challenge.service.command.UnFollowCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
@Configuration
@Import(value = DAOConfiguration.class)
public class CommandServiceTestConfiguration {

    @Bean(name = "followCommand")
    public Command followCommand(FollowerDAO followerDAO) {
        return new FollowCommand(followerDAO);
    }

    @Bean(name = "unFollowCommand")
    public Command unFollowCommand(FollowerDAO followerDAO) {
        return new UnFollowCommand(followerDAO);
    }
}

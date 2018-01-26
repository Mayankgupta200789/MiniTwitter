package challenge.service.configuration;

import challenge.dao.FollowerDAO;
import challenge.dao.MessageDAO;
import challenge.dao.PersonDAO;
import challenge.dao.configuration.DAOConfiguration;
import challenge.service.follower.DefaultFollowersRetriever;
import challenge.service.follower.FollowersRetriever;
import challenge.service.message.DefaultMessageAccumulator;
import challenge.service.message.DefaultMessageRetriever;
import challenge.service.message.MessageAccumulator;
import challenge.service.message.MessageRetriever;
import challenge.service.person.DefaultPersonRetriever;
import challenge.service.person.PersonRetriever;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
@Configuration
@Import(value = DAOConfiguration.class)
public class MessageRetrievalServiceConfiguration {

    @Bean(name = "messageRetriever")
    public MessageRetriever messageRetriever(MessageDAO messageDAO) {
        return new DefaultMessageRetriever(messageDAO);
    }

    @Bean(name = "messageAccumulator")
    public MessageAccumulator messageAccumulator(MessageRetriever messageRetriever,
                                                 FollowersRetriever followersRetriever,
                                                 PersonRetriever personRetriever) {
        return new DefaultMessageAccumulator(messageRetriever,followersRetriever,personRetriever);
    }

    @Bean(name = "followersRetriever")
    public FollowersRetriever followersRetriever(@Qualifier(value = "followerDAO") FollowerDAO followerDAO) {
        return new DefaultFollowersRetriever(followerDAO);
    }

    @Bean(name = "personRetriever")
    public PersonRetriever personRetriever(@Qualifier(value = "personDAO") PersonDAO personDAO) {

        return new DefaultPersonRetriever(personDAO);
    }

}

package challenge.service.configuration;

import challenge.dao.FollowerDAO;
import challenge.dao.FollowerStatsDAO;
import challenge.dao.configuration.DAOConfiguration;
import challenge.service.follower.DefaultHopCountCalculator;
import challenge.service.follower.DefaultPopularFollowerRetriever;
import challenge.service.follower.HopCountCalculator;
import challenge.service.follower.PopularFollowerRetriever;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
@Configuration
@Import(value = DAOConfiguration.class)
public class PersonInfoConfiguration {

    @Bean(name = "popularFollowerRetriever")
    public PopularFollowerRetriever popularFollowerRetriever(@Qualifier(value = "popularFollowerDAO") FollowerStatsDAO followerStatsDAO) {
        return new DefaultPopularFollowerRetriever(followerStatsDAO);
    }

    @Bean(name = "hopCountCalculator")
    public HopCountCalculator hopCountCalculator(@Qualifier(value = "followerDAO") FollowerDAO followerDAO) {
        return new DefaultHopCountCalculator(followerDAO);
    }
}

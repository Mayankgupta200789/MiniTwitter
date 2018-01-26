package challenge.dao.configuration;

import challenge.dao.*;
import challenge.dao.datasource.H2DataBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * This class instantiates all the DAO classes
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
@Import(value = H2DataBaseConfig.class)
public class DAOConfiguration {

    @Bean(value = "personDAO")
    public PersonDAO personDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new DefaultPersonDAO(namedParameterJdbcTemplate);
    }

    @Bean(value = "followerDAO")
    public FollowerDAO followerDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate,PersonDAO personDAO) {
        return new DefaultFollowerDAO(namedParameterJdbcTemplate,personDAO);
    }

    @Bean(value = "messageDAO")
    public MessageDAO messageDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new DefaultMessageDAO(namedParameterJdbcTemplate);
    }

    @Bean(name = "popularFollowerDAO")
    public FollowerStatsDAO popularFollowerDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PersonDAO personDAO) {
        return new DefaultFollowerStatsDAO(namedParameterJdbcTemplate,personDAO);
    }

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {

        return new NamedParameterJdbcTemplate(dataSource);
    }

}

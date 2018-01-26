package challenge.security.adapter;

import challenge.dao.datasource.H2DataBaseConfig;
import challenge.security.service.TwitterUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Import(value = H2DataBaseConfig.class)
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private static final String GET_PERSON_DETAILS = "select id,handle,password from people where handle=?";
    @Autowired
    @Qualifier(value = "h2DataSource")
    private DataSource h2DataSource;

    /**
     * This method is responsible to wire data source
     * and retrieve person information and authenticate it
     *
     * @param auth
     * @param authenticationProvider
     * @throws Exception
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth,
                                     @Qualifier(value = "authenticationProvider")
                                     DaoAuthenticationProvider authenticationProvider) throws Exception {

        auth.authenticationProvider(authenticationProvider)
                .jdbcAuthentication()
                .dataSource(h2DataSource)
                .usersByUsernameQuery(GET_PERSON_DETAILS);
    }

    /**
     * This method is responsible to configure http security
     * to authenticate person information against the database
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .logout()
                .deleteCookies("JSESSIONID")
            .and()
                .csrf();


        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * This method is responsible to wire
     * userDetail service that will match the credentials and
     * verify them. Furthermore, it will also does the encryption part
     *
     * @param twitterUserDetailsService
     * @param passwordEncoder
     * @return DAOAuthenticationProvider
     */
    @Bean(name = "authenticationProvider")
    public DaoAuthenticationProvider authenticationProvider(
            @Qualifier(value = "twitterUserDetailsService" ) TwitterUserDetailsService twitterUserDetailsService,
            @Qualifier(value = "passwordEncoder") PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(twitterUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * This method is responsible to create userdetails service
     * that contain the implementatino details for verifying the credentials
     *
     * @return TwitterUserDetailsService
     */
    @Bean(name = "twitterUserDetailsService")
    public TwitterUserDetailsService twitterUserDetailsService(){
        TwitterUserDetailsService twitterUserDetailsService = new TwitterUserDetailsService();
        twitterUserDetailsService.setDataSource(h2DataSource);
        twitterUserDetailsService.setUsersByUsernameQuery(GET_PERSON_DETAILS);
        return twitterUserDetailsService;
    }

    /**
     * This method is responsible to create password encoder class
     * that will encode the credentials information
     *
     * @return PasswordEncoder
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
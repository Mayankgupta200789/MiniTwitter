package challenge.security.service;

import challenge.security.dto.TwitterUser;
import com.google.common.base.Preconditions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * This service is responsible to retrieve
 * the person details for a given username
 *
 * @Author Mayank Gupta
 * @Date 8/20/17
 */
public class TwitterUserDetailsService extends JdbcDaoImpl{

    private String usersByUsernameQuery;


    /**
     * This method loads the username based on the credentials
     * If the user is not found then it throws UserNameNotFoundException
     *
     * @param userName
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Preconditions.checkArgument(userName != null, " UserName cannot be null");

        List<UserDetails> users = this.loadUsersByUsername(userName);
        if(users.size() == 0) {
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{userName}, "Username {0} not found"));
        } else {
            TwitterUser user = (TwitterUser)users.get(0);
            return this.createUserDetails(user.getPersonId(),userName, user, new LinkedList<>());

        }
    }

    /**
     * This method interacts with database and return the list based
     * on the username
     *
     * @param username
     * @return
     */
    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return this.getJdbcTemplate().query(this.usersByUsernameQuery, new String[]{username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                return new TwitterUser(id,username, password, true, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }

    protected TwitterUser createUserDetails(Integer personId,String username, TwitterUser userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        return new TwitterUser(userFromUserQuery.getPersonId(),returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities);
    }

    @Override
    public void setUsersByUsernameQuery(String usersByUsernameQuery) {
        this.usersByUsernameQuery = usersByUsernameQuery;
    }
}

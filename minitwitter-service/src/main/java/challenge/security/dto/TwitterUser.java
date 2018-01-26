package challenge.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author Mayank Gupta
 * @Date 8/20/17
 */
public class TwitterUser extends User {

    private Integer personId;

    public TwitterUser(Integer personId,String username,
                       String password,
                       boolean enabled,
                       boolean accountNonExpired,
                       boolean credentialsNonExpired,
                       boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {
        super(username,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        this.personId = personId;
    }

    public TwitterUser(Integer personId,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}

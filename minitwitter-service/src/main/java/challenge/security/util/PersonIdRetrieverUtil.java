package challenge.security.util;

import challenge.dao.exception.ForbiddenAccessException;
import challenge.security.dto.TwitterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 *
 * This method is responsible to retrieve
 * the person id who has logged in session
 *
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class PersonIdRetrieverUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonIdRetrieverUtil.class);

    public static TwitterUser retrieve() {

        if( getContext() == null
                || getContext().getAuthentication() == null
                || getContext().getAuthentication().getPrincipal() == null ) {
            LOGGER.error("Please enter the valid credentials");
            throw new ForbiddenAccessException("Access Denied");
        }

        return (TwitterUser) getContext().getAuthentication().getPrincipal();
    }
}

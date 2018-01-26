package challenge.controller;

import challenge.security.dto.TwitterUser;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.LinkedList;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
public class SecurityContextBuilder {

    private static final String ANY_PASSWORD = "anyPassword";

    public static void build(Integer personId, String name) {
        TwitterUser user = new TwitterUser(personId,name, ANY_PASSWORD, new LinkedList<>());

        UsernamePasswordAuthenticationToken principal =
                new UsernamePasswordAuthenticationToken(user, "testCredentials");


        SecurityContextHolder.getContext().setAuthentication(principal);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MiniTwitterControllerTest.MockSecurityContext(principal));
    }
}

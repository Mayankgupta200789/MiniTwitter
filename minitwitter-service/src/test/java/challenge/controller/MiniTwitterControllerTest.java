package challenge.controller;

import challenge.controller.advice.MiniTwitterControllerAdvice;
import challenge.controller.configuration.CommandServiceTestConfiguration;
import challenge.controller.configuration.MessageRetrievalServiceTestConfiguration;
import challenge.service.command.Command;
import challenge.service.message.MessageAccumulator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MessageRetrievalServiceTestConfiguration.class,
        CommandServiceTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MiniTwitterControllerTest {

    private static final Integer ANY_VALID_USER_ID = 1;
    private static final String ANY_VALID_ALREADY_FOLLOWER_ID = "catwoman";
    private static final String ANY_VALID_NOT_FOLLOWER_ID = "zod";
    private static final String ANY_VALID_USER_NAME = "batman";
    private MockMvc mockMvc;

    private MiniTwitterController miniTwitterController;
    private MiniTwitterControllerAdvice miniTwitterControllerAdvice;

    @Autowired
    @Qualifier(value = "messageAccumulator")
    private MessageAccumulator messageAccumulator;

    @Autowired
    @Qualifier(value = "followCommand")
    private Command followCommand;

    @Autowired
    @Qualifier(value = "unFollowCommand")
    private Command unFollowCommand;


    @Before
    public void setUp() throws Exception {

        miniTwitterControllerAdvice = new MiniTwitterControllerAdvice();
        miniTwitterController = new MiniTwitterController(messageAccumulator, followCommand, unFollowCommand);
        mockMvc = standaloneSetup(miniTwitterController).
                setControllerAdvice(miniTwitterControllerAdvice).build();
    }

    @Test
    public void getMessagesWithoutTheAuthentication() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/twitter-service/v1/persons/messages?search=underneath"))

                .andExpect(status().isForbidden());
    }

    @Test
    public void getMessagesWithAuthenticationWithValidUserName() throws Exception {


        SecurityContextBuilder.build(ANY_VALID_USER_ID, ANY_VALID_USER_NAME);


        mockMvc.perform(get("/twitter-service/v1/persons/messages?search=underneath"))

                .andExpect(status().isOk());
    }


    @Test
    public void followPersonWithAlreadyFollowedPerson() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_USER_ID,ANY_VALID_USER_NAME );

        mockMvc.perform(put("/twitter-service/v1/persons/followers/"+ ANY_VALID_ALREADY_FOLLOWER_ID))

                .andExpect(status().isNotModified());
    }

    @Test
    public void followPersonWithFollowedPerson() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_USER_ID,ANY_VALID_USER_NAME );

        mockMvc.perform(put("/twitter-service/v1/persons/followers/" + ANY_VALID_NOT_FOLLOWER_ID))

                .andExpect(status().isCreated());
    }

    @Test
    public void unFollowPerson() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_USER_ID, ANY_VALID_USER_NAME);

        mockMvc.perform(delete("/twitter-service/v1/persons/followers/" + ANY_VALID_ALREADY_FOLLOWER_ID))

                .andExpect(status().isOk());
    }

    static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }


}
package challenge.controller;

import challenge.controller.advice.MiniTwitterControllerAdvice;
import challenge.service.configuration.MessageRetrievalServiceConfiguration;
import challenge.service.configuration.PersonInfoConfiguration;
import challenge.service.follower.FollowersRetriever;
import challenge.service.follower.HopCountCalculator;
import challenge.service.follower.PopularFollowerRetriever;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @Author Mayank Gupta
 * @Date 8/19/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PersonInfoConfiguration.class, MessageRetrievalServiceConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonInfoControllerTest {

    private static final String ANY_VALID_FOLLOWER_ID = "zod";
    private static final int ANY_VALID_PERSON_ID = 1;
    private static final String ANY_VALID_USER_NAME = "batman";
    @Autowired
    @Qualifier(value = "popularFollowerRetriever")
    private PopularFollowerRetriever popularFollowerRetriever;


    @Autowired
    @Qualifier(value = "hopCountCalculator")
    private HopCountCalculator hopCountCalculator;

    @Autowired
    @Qualifier(value = "followersRetriever")
    private FollowersRetriever followersRetriever;


    private PersonInfoController personInfoController;

    private MiniTwitterControllerAdvice miniTwitterControllerAdvice;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        miniTwitterControllerAdvice = new MiniTwitterControllerAdvice();
        personInfoController = new PersonInfoController(popularFollowerRetriever,hopCountCalculator,followersRetriever);
        mockMvc = standaloneSetup(personInfoController)
                .setControllerAdvice(miniTwitterControllerAdvice)
                .build();
    }

    @Test
    public void getPopularFollowers() throws Exception {

        mockMvc.perform(get("/twitter-service/v1/persons/followers/favorites"))

                .andExpect(status().isOk());

    }

    @Test
    public void getPopularFollowersWithAuthorization() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_PERSON_ID, ANY_VALID_USER_NAME);

        mockMvc.perform(get("/twitter-service/v1/persons/followers/" + ANY_VALID_FOLLOWER_ID + "/distance"))

                .andExpect(status().isOk());

    }

    @Test
    public void getPopularFollowersWithoutAuthorization() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/twitter-service/v1/persons/followers/" + ANY_VALID_FOLLOWER_ID + "/distance"))

                .andExpect(status().isForbidden());

    }

    @Test
    public void getPersonInfoWithAuthorization() throws Exception {

        SecurityContextBuilder.build(ANY_VALID_PERSON_ID, ANY_VALID_USER_NAME);

        mockMvc.perform(get("/twitter-service/v1/persons"))

                .andExpect(status().isOk());

    }

}
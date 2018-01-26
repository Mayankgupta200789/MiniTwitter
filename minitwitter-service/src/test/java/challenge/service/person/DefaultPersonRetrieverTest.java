package challenge.service.person;

import challenge.dao.MockPersonDAO;
import challenge.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultPersonRetrieverTest {

    private static final String ANY_VALID_NAME = "anyValidName";
    private static final int ANY_VALID_PERSON_ID = 4;
    private PersonRetriever personRetriever;

    @Before
    public void setUp() throws Exception {

        MockPersonDAO mockPersonDAO = new MockPersonDAO(ANY_VALID_PERSON_ID, ANY_VALID_NAME);

        personRetriever = new DefaultPersonRetriever(mockPersonDAO);
    }

    @Test
    public void retrieve() throws Exception {

        Person person = personRetriever.retrieve(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(person);
        Assert.assertEquals(ANY_VALID_PERSON_ID,person.getPersonId().intValue());
        Assert.assertEquals(ANY_VALID_NAME,person.getName());
    }

    @Test
    public void retrieveWithNullPersonId() throws Exception {

        Person person = personRetriever.retrieve(null);

        Assert.assertNull(person);
    }

}
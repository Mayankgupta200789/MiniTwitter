package challenge.dao;


import challenge.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class DefaultPersonDAOTest  {

    private static final int ANY_VALID_PERSON_ID = 1;
    private static final int ANY_INVALID_PERSON_ID = -1;
    private EmbeddedDatabase db;

    private PersonDAO personDAO;

    @Before
    public void setUp() throws Exception {

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
        personDAO = new DefaultPersonDAO(template);
    }


    @Test
    public void findById() throws Exception {


        Person person = personDAO.findById(ANY_VALID_PERSON_ID);

        Assert.assertNotNull(person);
        Assert.assertEquals("batman", person.getHandle());
        Assert.assertEquals("Bruce Wayne", person.getName());
    }

    @Test
    public void findByInvalidPersonId() throws Exception {

        Person person = personDAO.findById(ANY_INVALID_PERSON_ID);

       Assert.assertNull(person);
    }

}
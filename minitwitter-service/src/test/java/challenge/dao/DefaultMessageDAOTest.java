package challenge.dao;

import challenge.model.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultMessageDAOTest {

    private static final int ANY_VALID_PERSON_ID = 1;
    private EmbeddedDatabase db;
    private MessageDAO messageDAO;


    @Before
    public void setUp() throws Exception {

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);

        messageDAO = new DefaultMessageDAO(template);

    }

    @Test
    public void findMessages() throws Exception {


        List<Message> messages = messageDAO.fetch(ANY_VALID_PERSON_ID, null);

        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() > 1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void findMessageWithNullJdbcTemplateShouldThrowIllegalArgumentException() {
        new DefaultMessageDAO(null);
    }

    @Test
    public void findMessageWithAKeyword() {

        List<Message> messages = messageDAO.fetch(ANY_VALID_PERSON_ID, "underneath");

        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() > 0);



    }

}
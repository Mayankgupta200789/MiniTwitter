package challenge.security.encode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Mayank Gupta
 * @Date 8/20/17
 */
public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        passwordEncoder = new PasswordEncoder();
    }

    @Test
    public void encode() throws Exception {

        String batman = passwordEncoder.encode("b@tm@n");

        Assert.assertNotNull(batman);
    }

}
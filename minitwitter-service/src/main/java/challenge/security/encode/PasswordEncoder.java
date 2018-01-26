package challenge.security.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author Mayank Gupta
 * @Date 8/20/17
 */
public class PasswordEncoder {

    public String encode(String input) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(input);
    }
}

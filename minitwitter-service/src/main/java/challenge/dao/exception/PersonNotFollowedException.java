package challenge.dao.exception;

/**
 * @Author Mayank Gupta
 * @Date 8/18/17
 */
public class PersonNotFollowedException extends RuntimeException {

    public PersonNotFollowedException(String message) {
        super(message);
    }
}

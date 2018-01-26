package challenge.dao.exception;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class PersonAlreadyFollowedException extends RuntimeException{

    public PersonAlreadyFollowedException(String message) {
        super(message);
    }

    public PersonAlreadyFollowedException(String message, Throwable cause) {
        super(message, cause);
    }
}

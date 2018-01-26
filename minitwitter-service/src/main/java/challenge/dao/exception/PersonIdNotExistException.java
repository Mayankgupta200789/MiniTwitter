package challenge.dao.exception;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class PersonIdNotExistException extends RuntimeException {


    public PersonIdNotExistException(String message) {
        super(message);
    }

    public PersonIdNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

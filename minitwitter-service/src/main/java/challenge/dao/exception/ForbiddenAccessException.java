package challenge.dao.exception;

/**
 * @Author Mayank Gupta
 * @Date 8/18/17
 */
public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenAccessException(String message) {
        super(message);
    }
}

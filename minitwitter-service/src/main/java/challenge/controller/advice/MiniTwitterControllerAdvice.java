package challenge.controller.advice;

import challenge.dao.exception.ForbiddenAccessException;
import challenge.dao.exception.PersonAlreadyFollowedException;
import challenge.dao.exception.PersonIdNotExistException;
import challenge.dao.exception.PersonNotFollowedException;
import challenge.response.ResponseSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static challenge.response.util.ResponseSummaryUtil.getResponseSummary;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
@ControllerAdvice(basePackages = "challenge.controller")
public class MiniTwitterControllerAdvice {

    @ExceptionHandler(PersonAlreadyFollowedException.class)
    public ResponseEntity<ResponseSummary> personAlreadyFollowedException(PersonAlreadyFollowedException exception) {
        ResponseSummary responseSummary = getResponseSummary(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(responseSummary,HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(PersonIdNotExistException.class)
    public ResponseEntity<ResponseSummary> personIdNotFoundException(PersonIdNotExistException exception) {
        ResponseSummary responseSummary = getResponseSummary(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseSummary,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ResponseSummary> forbiddenAccessException(ForbiddenAccessException exception) {
        ResponseSummary responseSummary = getResponseSummary(exception.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(responseSummary,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PersonNotFollowedException.class)
    public ResponseEntity<ResponseSummary> personNotFollowedException(PersonNotFollowedException exception) {
        ResponseSummary responseSummary = getResponseSummary(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(responseSummary,HttpStatus.NOT_MODIFIED);
    }


}

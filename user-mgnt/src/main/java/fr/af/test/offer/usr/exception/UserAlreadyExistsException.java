package fr.af.test.offer.usr.exception;

/**
 * Exceptions on services call
 */
public class UserAlreadyExistsException extends ServiceException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}

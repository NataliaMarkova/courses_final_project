package ua.natalia_markova.project4.exceptions;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}

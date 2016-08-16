package ua.natalia_markova.project4.exceptions;

/**
 * Created by natalia_markova on 03.08.2016.
 */
public class WrongUserDataException extends Exception {
    public WrongUserDataException() {
        super();
    }

    public WrongUserDataException(String message) {
        super(message);
    }
}

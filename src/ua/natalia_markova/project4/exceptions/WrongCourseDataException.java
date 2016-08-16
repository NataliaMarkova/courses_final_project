package ua.natalia_markova.project4.exceptions;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class WrongCourseDataException extends Exception {
    public WrongCourseDataException() {
        super();
    }

    public WrongCourseDataException(String message) {
        super(message);
    }
}

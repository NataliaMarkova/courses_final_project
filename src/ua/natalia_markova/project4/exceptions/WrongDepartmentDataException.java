package ua.natalia_markova.project4.exceptions;

/**
 * Created by natalia_markova on 19.07.2016.
 */
public class WrongDepartmentDataException extends Exception {
    public WrongDepartmentDataException() {
        super();
    }

    public WrongDepartmentDataException(String message) {
        super(message);
    }
}

package ua.natalia_markova.project4.exceptions;

/**
 * Created by natalia_markova on 19.07.2016.
 */
public class WrongArchiveItemDataException extends Exception {
    public WrongArchiveItemDataException() {
        super();
    }

    public WrongArchiveItemDataException(String message) {
        super(message);
    }
}

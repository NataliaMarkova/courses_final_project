package ua.natalia_markova.project4.wrappers;

/**
 * Created by natalia_markova on 25.07.2016.
 */
public interface SessionWrapper {
    void setAttribute(String name, Object value);
    Object getAttribute(String name);
    void invalidate();
}

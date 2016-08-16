package ua.natalia_markova.project4.wrappers;

/**
 * Created by natalia_markova on 25.07.2016.
 */
public interface RequestWrapper {
    String getParameter(String name);
    void setAttribute(String name, Object value);
    SessionWrapper getSession();
    SessionWrapper getSession(boolean createIfAbsent);

}

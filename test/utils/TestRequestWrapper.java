package utils;

import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by natalia_markova on 26.07.2016.
 */
public class TestRequestWrapper implements RequestWrapper {

    private TestSessionWrapper session = new TestSessionWrapper();
    private Map<String, Object> attributes;
    private Map<String, String> parameters;

    public TestRequestWrapper() {
        attributes = new HashMap<>();
        parameters = new HashMap<>();
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public TestSessionWrapper getSession() {
        return session;
    }

    @Override
    public TestSessionWrapper getSession(boolean createIfAbsent) {
        return session;
    }

    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }
}

package utils;

import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by natalia_markova on 26.07.2016.
 */
public class TestSessionWrapper implements SessionWrapper {

    private boolean valid;
    private Map<String, Object> attributes;

    public TestSessionWrapper() {
        valid = true;
        attributes = new HashMap<>();
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }
}

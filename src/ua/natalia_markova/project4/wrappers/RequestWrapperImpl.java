package ua.natalia_markova.project4.wrappers;

import com.sun.istack.internal.NotNull;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * Created by natalia_markova on 25.07.2016.
 */
public class RequestWrapperImpl implements RequestWrapper {

    private HttpServletRequest request;

    public RequestWrapperImpl(@NotNull HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String item = request.getParameter(name);
        if (item != null) {
            byte[] bytes = item.getBytes(StandardCharsets.ISO_8859_1);
            item = new String(bytes, StandardCharsets.UTF_8);
        }
        return item;
    }

    @Override
    public void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    @Override
    public SessionWrapper getSession() {
        return new SessionWrapperImpl(request.getSession());
    }

    @Override
    public SessionWrapper getSession(boolean createIfAbsent) {
        return new SessionWrapperImpl(request.getSession(createIfAbsent));
    }
}

package ua.natalia_markova.project4.wrappers;

import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpSession;

/**
 * Created by natalia_narkova on 25.07.2016.
 */
public class SessionWrapperImpl implements SessionWrapper {

    HttpSession session;

    public SessionWrapperImpl(@NotNull HttpSession session) {
        this.session = session;
    }

    @Override
    public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return session.getAttribute(name);
    }

    @Override
    public void invalidate() {
        session.invalidate();
    }
}

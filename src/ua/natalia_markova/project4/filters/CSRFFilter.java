package ua.natalia_markova.project4.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by natalia_markova on 25.07.2016.
 */
public class CSRFFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CSRFFilter.class);
    /** <code>urlPatterns</code> set of URI paths to proceed only in case CSRF-token is OK */
    private Set<String> urlPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
        urlPatterns = new HashSet<>();
        urlPatterns.add(bundle.getString("update_department"));
        urlPatterns.add(bundle.getString("add_course"));
        urlPatterns.add(bundle.getString("enroll_on_course"));
        urlPatterns.add(bundle.getString("do_put_mark"));
        urlPatterns.add(bundle.getString("do_edit_user"));
        urlPatterns.add(bundle.getString("do_change_password"));
    }

    /**
     * Checks if CSRF-token in session matches the one in request in case request path is in <code>urlPatterns</code>
     * if CSRF-tokens don't match sets the error attribute value in request to <code>true</code>
     * @param request ServletRequest object
     * @param response ServletResponse object
     * @param filterChain FilterChain object
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getPathInfo();
        logger.debug(path);
        boolean filerError = (boolean) req.getAttribute("filter_error");
        if (!filerError && urlPatterns.contains(path)) {
            HttpSession session = req.getSession();
            String CSRFToken = request.getParameter("CSRFToken");
            String CSRFTokenSession = (String) session.getAttribute("CSRFToken");
            logger.debug("Request CSRFToken : " + CSRFToken);
            logger.debug("Session CSRFToken : " + CSRFTokenSession);
            if (CSRFTokenSession == null || CSRFToken == null) {
                req.setAttribute("filter_error", true);
            }
            boolean result = CSRFToken.equals(CSRFTokenSession);
            if (!result) {
                session.invalidate();
                req.setAttribute("filter_error", true);
            }
        }
        filterChain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }
}

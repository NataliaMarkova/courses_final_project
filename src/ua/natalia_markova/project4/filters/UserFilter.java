package ua.natalia_markova.project4.filters;

import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.User;

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
public class UserFilter implements Filter {

    private static final Logger logger = Logger.getLogger(UserFilter.class);
    /** <code>urlPatterns</code> set of URI paths that can't be proceeded without logged in user */
    private Set<String> urlPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
        urlPatterns = new HashSet<>();
        // common commands
        urlPatterns.add(bundle.getString("courses"));
        urlPatterns.add(bundle.getString("logout"));
        urlPatterns.add(bundle.getString("edit_user"));
        urlPatterns.add(bundle.getString("do_edit_user"));
        urlPatterns.add(bundle.getString("change_password"));
        urlPatterns.add(bundle.getString("do_change_password"));
        // administrator's commands
        urlPatterns.add(bundle.getString("users"));
        urlPatterns.add(bundle.getString("departments"));
        urlPatterns.add(bundle.getString("new_department"));
        urlPatterns.add(bundle.getString("edit_department"));
        urlPatterns.add(bundle.getString("update_department"));
        urlPatterns.add(bundle.getString("new_course"));
        urlPatterns.add(bundle.getString("add_course"));
        // professor's commands
        urlPatterns.add(bundle.getString("put_mark"));
        urlPatterns.add(bundle.getString("do_put_mark"));
        // student's commands
        urlPatterns.add(bundle.getString("archive"));
        urlPatterns.add(bundle.getString("available_courses"));
        urlPatterns.add(bundle.getString("enroll_on_course"));
        // professor's and administrator's commands
        urlPatterns.add(bundle.getString("course_information"));
    }

    /**
     * Checks if there is a user in session in case request path is in <code>urlPatterns</code>
     * if there is no user in session sets the error attribute value in request to <code>true</code>
     * @param request ServletRequest object
     * @param response ServletResponse object
     * @param filterChain FilterChain object
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getPathInfo();
        logger.debug(path);
        if (urlPatterns.contains(path)) {
            logger.debug("doFilter: " + path);
            HttpSession session = req.getSession();
            if (session == null) {
                logger.debug("Filter is NOT passed, session == null");
                req.setAttribute("filter_error", true);
            } else {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    logger.debug("Filter is NOT passed, user == null");
                    req.setAttribute("filter_error", true);
                }
            }
        }
        filterChain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }
}

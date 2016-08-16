package ua.natalia_markova.project4.filters;

import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;

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
public class StudentFilter implements Filter {

    private static final Logger logger = Logger.getLogger(StudentFilter.class);
    /** <code>urlPatterns</code> set of URI paths that can be proceeded only by a student */
    private Set<String> urlPatterns;
    /** <code>urlPatternsForbidden</code> set of URI paths that can't be proceeded by a student */
    private Set<String> urlPatternsForbidden;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
        urlPatterns = new HashSet<>();
        urlPatterns.add(bundle.getString("archive"));
        urlPatterns.add(bundle.getString("available_courses"));
        urlPatterns.add(bundle.getString("enroll_on_course"));

        urlPatternsForbidden = new HashSet<>();
        urlPatternsForbidden.add(bundle.getString("course_information"));
    }

    /**
     * Checks if user in session is a student in case request path is in <code>urlPatterns</code>
     * if user is not a student sets the error attribute value in request to <code>true</code>
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
        if (!filerError && urlPatterns.contains(path) || urlPatternsForbidden.contains(path)) {
            logger.debug("doFilter: " + path);
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if ((urlPatterns.contains(path) && user.getUserType() != UserType.STUDENT) ||
                    (urlPatternsForbidden.contains(path) && user.getUserType() == UserType.STUDENT)) {
                logger.debug("Filter is NOT passed");
                req.setAttribute("filter_error", true);
            }
        }
        filterChain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }
}

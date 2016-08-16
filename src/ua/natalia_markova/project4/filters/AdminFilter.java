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
public class AdminFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AdminFilter.class);
    /** <code>urlPatterns</code> set of URI paths that can be proceeded only by an administrator */
    private Set<String> urlPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
        urlPatterns = new HashSet<>();
        urlPatterns.add(bundle.getString("users"));
        urlPatterns.add(bundle.getString("departments"));
        urlPatterns.add(bundle.getString("new_department"));
        urlPatterns.add(bundle.getString("edit_department"));
        urlPatterns.add(bundle.getString("update_department"));
        urlPatterns.add(bundle.getString("new_course"));
        urlPatterns.add(bundle.getString("add_course"));
    }

    /**
     * Checks if user in session is an administrator in case request path is in <code>urlPatterns</code>
     * if user is not an administrator sets the error attribute value in request to <code>true</code>
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
            logger.debug("doFilter: " + path);
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user.getUserType() != UserType.ADMIN) {
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

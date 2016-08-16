package ua.natalia_markova.project4.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by natalia_markova on 25.07.2016.
 */
public class LocalizationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LocalizationFilter.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private Set<String> urlPatterns;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        urlPatterns = new HashSet<>();
        urlPatterns.add(bundle.getString("localization"));
    }

    /**
     * Changes locale in session to the one from the request and forwards to the URI
     * which is specified in the request
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
            HttpSession session = req.getSession(true);
            String lang = req.getParameter("lang");
            logger.debug("lang = " + lang);
            if (lang.equals("rus")) {
                session.setAttribute("locale", new Locale("ru", "UA"));
            }else {
                session.setAttribute("locale", new Locale("en", "US"));
            }
            String commandName = req.getParameter("command_name");
            logger.debug("commandName = " + commandName);
            if (commandName.equals(bundle.getString("course_information"))) {
                commandName = bundle.getString("courses");
            } else if (commandName.equals(bundle.getString("authenticate"))) {
                commandName = bundle.getString("index");
            }
            request.getRequestDispatcher("/Courses/" + commandName).forward(request, response);
        } else {
            filterChain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {

    }
}


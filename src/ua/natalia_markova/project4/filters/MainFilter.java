package ua.natalia_markova.project4.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class MainFilter implements Filter {

    private static final Logger logger = Logger.getLogger(MainFilter.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private ResourceBundle servletProperties = ResourceBundle.getBundle("resource.servlet_config");

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        logger.debug("doFilter: " + path);
        if (path.startsWith("/css/")) {
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            boolean correctURI = false;
            Set<String> keys = bundle.keySet();
            for (String key: keys) {
                if (bundle.getString(key).equals(path)) {
                    correctURI = true;
                    break;
                }
            }
            if (correctURI) {
                request.setAttribute("filter_error", false);
                request.getRequestDispatcher(servletProperties.getString("SERVLET_PREFIX") + path).forward(request, response);
            } else {
                logger.debug("Wrong URI path: " + path);
                request.setAttribute("resource_uri", path);
                request.getRequestDispatcher(servletProperties.getString("JSP_PREFIX") + "error404" + servletProperties.getString("JSP_POSTFIX")).forward(request, response);
            }

        }
    }
}

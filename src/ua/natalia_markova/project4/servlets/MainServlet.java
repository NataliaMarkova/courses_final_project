package ua.natalia_markova.project4.servlets;

import org.apache.log4j.Logger;
import ua.natalia_markova.project4.controllers.ControllerManager;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.RequestWrapperImpl;
import ua.natalia_markova.project4.wrappers.SessionWrapper;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.DaoType;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.factories.DaoFactory;
import ua.natalia_markova.project4.factories.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class MainServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MainServlet.class);
    private ResourceBundle servletProperties = ResourceBundle.getBundle("resource.servlet_config");
    private ControllerManager controllerManager;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing Main Controller");
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("POST request: " + request.getPathInfo());
        handleRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("GET request: " + request.getPathInfo());
        handleRequest(request, response);
    }

    /**
     * Handles request that came from the client
     * Gets response string and passes it to RequestDispatcher
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (controllerManager == null) {
            initializeControllerManager();
        }
        String responseString = getResponseString(request);
        logger.debug("Response string: " + responseString);

        if (responseString.equals("/")) {
            responseString = getDefaultRedirectPage(new RequestWrapperImpl(request));
        }
        try {
            if (responseString.startsWith("/")) {
                request.getRequestDispatcher(servletProperties.getString("SERVLET_PREFIX") + responseString).forward(request, response);
            } else {
                request.getRequestDispatcher(servletProperties.getString("JSP_PREFIX") + responseString + servletProperties.getString("JSP_POSTFIX")).forward(request, response);
            }
        } catch (ServletException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Gets response string depending on request URI
     * @param request HttpServletRequest object
     * @return jsp-page name or request URI
     */
    private String getResponseString(HttpServletRequest request)  {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            request.setAttribute("message", "Internal error");
            return "exception";
        }
        boolean filterError = (boolean) request.getAttribute("filter_error");
        logger.debug("Filter error == " + filterError);
        if (filterError) {
            return "/";
        }
        String requestString = request.getPathInfo();
        request.setAttribute("command_name", requestString); // for localization filter to redirect
        logger.debug("Request command_name: " + requestString);

        String responseString;
        try {
            responseString = controllerManager.manageRequest(new RequestWrapperImpl(request), requestString);
        } catch (WrongRequestURIException e) {
            logger.error(e.getMessage());
            request.setAttribute("resource_uri", requestString);
            return "error404";
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            request.setAttribute("message", "Internal error");
            return "exception";

        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            request.setAttribute("message", "Internal error");
            return "exception";
        }
        return responseString;
    }

    /**
     * Initializes DaoFactory, ServiceFactory and after all ControllerManager that is used to handle all income requests
     * @return void
     */
    private void initializeControllerManager() {
        DaoFactory daoFactory = DaoFactory.getFactory(DaoType.JDBC);
        ServiceFactory serviceFactory = ServiceFactory.getFactory(daoFactory, ServiceType.SIMPLE);
        controllerManager = ControllerManager.getControllerManager(serviceFactory);
    }


    /**
     * Defines default jsp-page name for current user
     * If there is no user, returns start page (index.jsp)
     * @param request RequestWrapper object
     * @return jsp-page name
     */
    public static String getDefaultRedirectPage(RequestWrapper request) {
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "index";
        } else {
            request.setAttribute("main_page", getUserRedirectPage(user));
            request.setAttribute("data_page", "user_personal_data.jsp");
            return "user_page";
        }
    }

    /**
     *  Defines and returns the name of the user's main page depending on user type
     *  @param user user to get main page by
     *  @return name of the jsp-page
     */
    public static String getUserRedirectPage(User user) {
        String pageName;
        if (user.getUserType() == UserType.ADMIN) {
            pageName = "admin";
        } else if (user.getUserType() == UserType.STUDENT) {
            pageName = "student";
        } else {
            pageName = "professor";
        }
        return pageName + "_main_page.jsp";
    }

}

package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.AuthenticationException;
import ua.natalia_markova.project4.exceptions.RegistrationException;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.exceptions.WrongUserDataException;
import ua.natalia_markova.project4.service.UserService;
import ua.natalia_markova.project4.servlets.MainServlet;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 29.06.2016.
 */
public class UserController implements RequestHandler {

    private static final Logger logger = Logger.getLogger(UserController.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");

    private UserService userService;
    private SecureRandom random;

    public UserController(@NotNull UserService userService) {
        this.userService = userService;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to get instance of SecureRandom");
        }
    }

    /**
     * @throws WrongRequestURIException
     * {@inheritDoc}
     */
    @Override
    public String handleRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException {
        if (requestURI.equals(bundle.getString("index"))) {
            return getStartPage(request);
        } else if (requestURI.equals(bundle.getString("authenticate"))) {
            return authenticate(request);
        } else if (requestURI.equals(bundle.getString("logout"))) {
            return logOut(request);
        } else if (requestURI.equals(bundle.getString("edit_user"))) {
            return editUserPersonalData(request);
        } else if (requestURI.equals(bundle.getString("change_password"))) {
            return changePassword(request);
        } else if (requestURI.equals(bundle.getString("registration"))) {
            return getRegistrationPage(request);
        } else if (requestURI.equals(bundle.getString("register"))) {
            return registerUser(request);
        } else if (requestURI.equals(bundle.getString("do_edit_user"))) {
            return updateUserPersonalData(request);
        } else if (requestURI.equals(bundle.getString("do_change_password"))) {
            return changeUserPassword(request);
        } else {
            logger.error("Wrong request URI: " + requestURI);
            throw new WrongRequestURIException();
        }
    }

    /**
     * If user is logged in returns the name of the user's main page depending on user type,
     * otherwise - the name of the start page with log in and registration
     * @param request RequestWrapper object
     * @return name of the jsp-page
     */
    private String getStartPage(RequestWrapper request) {
        logger.debug("getStartPage()");
        SessionWrapper session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale == null) {
            session.setAttribute("locale", new Locale("en", "US"));
        }
        return MainServlet.getDefaultRedirectPage(request);
    }

    /**
     *  Gets authentication information (login and password) from the request and looks in the database if there is user with such data;
     *  if yes, returns the name of the user's main page depending on user type, otherwise - returns the name of the start log in and registration page
     *  @param request RequestWrapper object
     *  @return name of the jsp-page
     */
    private String authenticate(RequestWrapper request) {
        logger.debug("authenticate()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return bundle.getString("index");
        }
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            user = userService.login(login, password);
        } catch (AuthenticationException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("login", login);
            return "index";
        }
        if (user == null) {
            request.setAttribute("error", "No user with such data found. Wrong login or password!");
            request.setAttribute("login", login);
            return "index";
        } else {
            session.setAttribute("user", user);
            addCSRFToken(session);
            request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));
            request.setAttribute("data_page", "user_personal_data.jsp");
            return "user_page";
        }
    }

    /**
     *  Invalidates user's session and returns the name of the start page with log in and registration
     *  @param request RequestWrapper object
     *  @return name of the start log in and registration page
     */
    private String logOut(RequestWrapper request) {
        logger.debug("logOut()");
        SessionWrapper session = request.getSession(true);
        session.invalidate();
        return "index";
    }

    /**
     *  Generates CSRF-token and puts it in session
     *  @param session - session to put CSRF-token in
     *  @return void
     */
    private void addCSRFToken(SessionWrapper session) {
        String CSRFToken = new BigInteger(130, random).toString(32);
        logger.debug("CSRF-token: " + CSRFToken);
        session.setAttribute("CSRFToken", CSRFToken);
    }

    /**
     *  Returns the name of the Registration page depending on user;
     *  if user is not logged in, returns registration page for a new student,
     *  otherwise - returns common registration page in case user is an administrator, otherwise - URI to the start page
     *  @param request RequestWrapper object
     *  @return name of the jsp page or URI to the start page
     */
    private String getRegistrationPage(RequestWrapper request) {
        logger.debug("getRegistrationPage()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        if (user == null || user.getUserType() == UserType.ADMIN) {
            return "registration";
        } else {
            return bundle.getString("index");
        }
    }

    /**
     *  Gets user's information from the request and creates a new record in the database.
     *  If fails to create a record or user's information is wrong puts all the data
     *  that came from the request excluding password and password confirmation
     *  back to the request and returns the name of the Registration page,
     *  otherwise - returns the name of the start page with log in and registration if user wasn't logged in
     *  or the name of Users' list page if user is an administrator
     *  @param request RequestWrapper object
     *  @return name of the Registration page
     */
    private String registerUser(RequestWrapper request) {
        logger.debug("registerUser()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        UserType userType;
        if (user == null) {
            // registering new student
            userType = UserType.STUDENT;
        } else if (user.getUserType() == UserType.ADMIN){
            userType = UserType.valueOf(request.getParameter("user_type").toUpperCase());
        } else {
            return bundle.getString("index");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_confirmation");
        String name = request.getParameter("name");
        String familyName = request.getParameter("family_name");
        String patronymic = request.getParameter("patronymic");

        try {
            boolean result = userService.registerUser(login, password, passwordConfirmation, name, familyName, patronymic, userType);
            if (result) {
                if (user == null) {
                    request.setAttribute("message", "Registration is successful");
                    return bundle.getString("index");
                } else {
                    return bundle.getString("users");
                }
            } else {
                request.setAttribute("error", "An internal error occurred while trying to register a new user");
            }
        } catch (RegistrationException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("login", login);
        request.setAttribute("name", name);
        request.setAttribute("familyName", familyName);
        request.setAttribute("patronymic", patronymic);

        return "registration";
    }

    /**
     *  Returns the name of the page where user can edit his personal data
     *  @param request RequestWrapper object
     *  @return the name of the page to edit personal data
     */
    private String editUserPersonalData(RequestWrapper request) {
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("name", user.getName());
        request.setAttribute("familyName", user.getFamilyName());
        request.setAttribute("patronymic", user.getPatronymic());
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));
        request.setAttribute("data_page", "user_edit_personal_data.jsp");
        return "user_page";
    }

    /**
     *  Returns the name of the page where user can change his password
     *  @param request RequestWrapper object
     *  @return the name of the page to change password
     */
    private String changePassword(RequestWrapper request) {
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));
        request.setAttribute("data_page", "user_edit_password.jsp");
        return "user_page";
    }

    /**
     *  Updates user personal data
     *  @param request RequestWrapper object
     *  @return the name of the user page with personal data in case operation's successful,
     *          otherwise - the name of the page where user can correct his data
     */
    private String updateUserPersonalData(RequestWrapper request) {
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));

        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String familyName = request.getParameter("family_name");
        String patronymic = request.getParameter("patronymic");

        try {
            if (!userService.changeUserData(user, password, name, familyName, patronymic)) {
                request.setAttribute("error", "Internal error: failed to updated personal data");
            } else {
                request.setAttribute("message", "Personal data is saved");
            }
            request.setAttribute("data_page", "user_personal_data.jsp");
        } catch (WrongUserDataException e) {
            request.setAttribute("name", name);
            request.setAttribute("familyName", familyName);
            request.setAttribute("patronymic", patronymic);
            request.setAttribute("error", e.getMessage());
            request.setAttribute("data_page", "user_edit_personal_data.jsp");
        }
        return "user_page";
    }

    /**
     *  Changes user's password
     *  @param request RequestWrapper object
     *  @return the name of the user page with personal data in case operation's successful,
     *          otherwise - the name of the page where user can try to change his password one more time
     */
    private String changeUserPassword(RequestWrapper request) {
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));

        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");
        String passwordConfirmation = request.getParameter("password_confirmation");

        try {
            if (!userService.changePassword(user, oldPassword, newPassword, passwordConfirmation)) {
                request.setAttribute("error", "Internal error: failed to updated personal data");
            } else {
                request.setAttribute("message", "Password is successfully changed");
            }
            request.setAttribute("data_page", "user_personal_data.jsp");
        } catch (WrongUserDataException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("data_page", "user_edit_password.jsp");
        }
        return "user_page";
    }

}

package controllers;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.natalia_markova.project4.controllers.RequestHandler;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.factories.ControllerFactory;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.AdminService;
import ua.natalia_markova.project4.service.CourseService;
import utils.TestRequestWrapper;
import utils.TestSessionWrapper;
import utils.TestUtil;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 26.07.2016.
 */
public class CoursesControllerTest {

    private static ControllerFactory controllerFactory;
    private static ResourceBundle bundle;
    private static ServiceFactory serviceFactory;

    @BeforeClass
    public static void init() throws NamingException {
        bundle = ResourceBundle.getBundle("resource.requestURI");
        serviceFactory = TestUtil.getServiceFactory(ServiceType.SIMPLE);
        controllerFactory = ControllerFactory.getControllerFactory(serviceFactory);
    }

    @Test
    public void adminControllerViewCoursesTestOK() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        AdminService service = serviceFactory.getAdminService();
        String requestURI = bundle.getString("courses");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);
        List<User> users = service.getUsers();
        for (User user: users) {
            session.setAttribute("user", user);
            for (CourseType type: CourseType.values()) {
                request.setParameter("course_type", type.toString());
                String response = handler.handleRequest(request, requestURI);
                Assert.assertTrue(response.equals("courses"));
            }
        }
    }

    @Test
    public void adminControllerViewCourseInformationTestOK() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        CourseService courseService = serviceFactory.getCourseService();
        AdminService adminService = serviceFactory.getAdminService();
        String requestURI = bundle.getString("course_information");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);
        List<User> users = adminService.getUsers();
        for (User user: users) {
            if (user.getUserType() == UserType.STUDENT) {
                continue;
            }
            session.setAttribute("user", user);
            List<Course> courses = courseService.getCoursesByUser(user, CourseType.ALL);
            for (Course course: courses) {
                request.setParameter("course_id", course.getId().toString());
                String response = handler.handleRequest(request, requestURI);
                Assert.assertTrue(response.equals("students"));
            }
        }
    }
}

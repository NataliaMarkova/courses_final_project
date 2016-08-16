package controllers;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ua.natalia_markova.project4.controllers.RequestHandler;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.factories.ControllerFactory;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.AdminService;
import ua.natalia_markova.project4.service.CourseService;
import ua.natalia_markova.project4.service.StudentService;
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
public class StudentControllerTest {

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
    public void studentControllerViewArchiveTestOK() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        String requestURI = bundle.getString("archive");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);

        AdminService adminService = serviceFactory.getAdminService();
        List<User> users = adminService.getUsers();
        for (User user: users) {
            if (user.getUserType() != UserType.STUDENT) {
                continue;
            }
            session.setAttribute("user", user);
            String response = handler.handleRequest(request, requestURI);
            Assert.assertTrue(response.equals("student_archive"));
        }
    }

    @Test
    public void studentControllerViewAvailableCoursesTestOK() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        String requestURI = bundle.getString("available_courses");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);

        AdminService adminService = serviceFactory.getAdminService();
        List<User> users = adminService.getUsers();
        for (User user: users) {
            if (user.getUserType() != UserType.STUDENT) {
                continue;
            }
            session.setAttribute("user", user);
            String response = handler.handleRequest(request, requestURI);
            Assert.assertTrue(response.equals("available_courses"));
        }
    }

    @Ignore
    @Test
    public void studentControllerEnrollOnCourseTestOK() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        String requestURI = bundle.getString("enroll_on_course");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);
        AdminService adminService = serviceFactory.getAdminService();
        StudentService studentService = serviceFactory.getStudentService();
        List<User> users = adminService.getUsers();
        for (User user: users) {
            if (user.getUserType() != UserType.STUDENT) {
                continue;
            }
            session.setAttribute("user", user);
            List<Course> courses = studentService.getAvailableCourses((Student) user);
            if (courses.size() == 0) {
                continue;
            }
            Course course = courses.get(0);
            request.setParameter("course_id", course.getId().toString());
            String response = handler.handleRequest(request, requestURI);
            String error = (String) request.getAttribute("error");
            Assert.assertTrue(response.equals("/available_courses"));
            Assert.assertNull(error);
        }
    }

    @Test
    public void studentControllerEnrollOnCourseTestError() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        String requestURI = bundle.getString("enroll_on_course");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);
        AdminService adminService = serviceFactory.getAdminService();
        CourseService courseService = serviceFactory.getCourseService();
        List<User> users = adminService.getUsers();
        for (User user: users) {
            if (user.getUserType() != UserType.STUDENT) {
                continue;
            }
            session.setAttribute("user", user);
            List<Course> courses = courseService.getCoursesByUser(user, CourseType.ALL);
            if (courses.size() == 0) {
                continue;
            }
            Course course = courses.get(0);
            request.setParameter("course_id", course.getId().toString());
            String response = handler.handleRequest(request, requestURI);
            String error = (String) request.getAttribute("error");
            Assert.assertTrue(response.equals("/available_courses"));
            Assert.assertNotNull(error);
        }
    }
}

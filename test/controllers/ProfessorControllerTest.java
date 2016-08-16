package controllers;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.natalia_markova.project4.controllers.RequestHandler;
import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.enums.CourseType;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.factories.ControllerFactory;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.AdminService;
import ua.natalia_markova.project4.service.CourseService;
import ua.natalia_markova.project4.service.ProfessorService;
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
public class ProfessorControllerTest {

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
    public void putMarkTest() throws ServletException, WrongRequestURIException, IOException {
        TestRequestWrapper request = new TestRequestWrapper();
        TestSessionWrapper session = request.getSession();
        String requestURI = bundle.getString("put_mark");
        String requestURI_do = bundle.getString("do_put_mark");
        RequestHandler handler = controllerFactory.getRequestHandler(requestURI);

        AdminService adminService = serviceFactory.getAdminService();
        CourseService courseService = serviceFactory.getCourseService();

        List<Professor> professors = adminService.getProfessors();
        for (Professor professor: professors) {
            session.setAttribute("user", professor);
            List<Course> courses = courseService.getCoursesByUser(professor, CourseType.ARCHIVE);
            for (Course course: courses) {
                List<ArchiveItem> items = courseService.getArchiveItemsByCourse(course);
                for (ArchiveItem item: items) {
                    // put mark OK
                    request.setParameter("student_id", item.getStudent().getId().toString());
                    request.setParameter("course_id", item.getCourse().getId().toString());
                    String response = handler.handleRequest(request, requestURI);
                    Assert.assertTrue(response.equals("put_mark"));

                    // do put mark OK
                    request.setParameter("mark", item.getMark().toString());
                    response = handler.handleRequest(request, requestURI_do);
                    Assert.assertTrue(response.equals("/course_information?course_id=" + item.getCourse().getId()));

                    // do put mark error
                    request.setParameter("mark", "dfdf");
                    response = handler.handleRequest(request, requestURI_do);
                    String error = (String) request.getAttribute("error");
                    Assert.assertTrue(response.equals("/put_mark"));
                    Assert.assertNotNull(error);
                }
            }
        }
    }

}

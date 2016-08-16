package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.service.StudentService;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 18.07.2016.
 */
public class StudentController implements RequestHandler {

    private static final Logger logger = Logger.getLogger(StudentController.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");

    private StudentService studentService;

    public StudentController(@NotNull StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @throws WrongRequestURIException
     * {@inheritDoc}
     */
    @Override
    public String handleRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException {
        if (requestURI.equals(bundle.getString("archive"))) {
            return viewArchive(request);
        } else if (requestURI.equals(bundle.getString("available_courses"))) {
            return viewAvailableCourses(request);
        } else if (requestURI.equals(bundle.getString("enroll_on_course"))) {
            return enrollOnCourse(request);
        } else {
            logger.error("Wrong request URI: " + requestURI);
            throw new WrongRequestURIException();
        }
    }

    /**
     * Gets list of archive items from the database and puts it in the request
     * @param request
     * @return name of Archive's jsp-page
     */
    private String viewArchive(RequestWrapper request) {
        logger.debug("viewArchive()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("items", studentService.getArchiveItems((Student) user));
        return "student_archive";
    }

    /**
     * Gets list of all available courses from the database and puts it in the request
     * @param request
     * @return name of the Available courses' jsp-page
     */
    private String viewAvailableCourses(RequestWrapper request) {
        logger.debug("viewAvailableCourses()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        request.setAttribute("course_type", "available");
        request.setAttribute("courses", studentService.getAvailableCourses((Student) user));
        return "available_courses";
    }

    /**
     * Gets Course' information (id) from the request and creates a record of the ArchiveItem object in the database
     * Returns the name of the start page if user doesn't have rights for the action, otherwise - returns URI to available courses
     * @param request
     * @return name of of the start page or URI to available courses
     */
    private String enrollOnCourse(RequestWrapper request) {
        logger.debug("enrollOnCourse()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        Long courseId = Long.valueOf(request.getParameter("course_id"));

        Course course = studentService.enrollOnCourse(courseId, (Student) user);
        if (course != null) {
            request.setAttribute("message", "You were successfully enrolled on course " + course.getName() + ", start date " + new SimpleDateFormat("dd.MM.yyyy").format(course.getStartDate()));
        } else {
            request.setAttribute("error", "Failed to enroll on course!");
        }
        return bundle.getString("available_courses");
    }

}

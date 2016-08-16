package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.service.CourseService;
import ua.natalia_markova.project4.servlets.MainServlet;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class CoursesController implements RequestHandler {

    private static final Logger logger = Logger.getLogger(CoursesController.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private CourseService courseService;

    public CoursesController(@NotNull CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * @throws WrongRequestURIException
     * {@inheritDoc}
     */
    @Override
    public String handleRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException {
        if (requestURI.equals(bundle.getString("courses"))) {
            return viewCourses(request);
        } else if (requestURI.equals(bundle.getString("course_information"))) {
            return viewCourseInformation(request);
        } else {
            logger.error("Wrong request URI: " + requestURI);
            throw new WrongRequestURIException();
        }
    }

    /**
     * Gets the list of Courses from database depending on user type and puts it in the request;
     * returns the name of the appropriate jsp-page
     * @param request RequestWrapper object
     * @return name of the jsp-page
     */
    public String viewCourses(RequestWrapper request) {
        logger.debug("viewCourses()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        CourseType courseType = CourseType.ALL;
        String parameter = request.getParameter("course_type");
        logger.debug("viewCourses(): " + parameter);
        if (parameter != null) {
            courseType = CourseType.valueOf(parameter.toUpperCase());
        }
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));
        request.setAttribute("course_type", courseType.toString());
        request.setAttribute("courses", courseService.getCoursesByUser(user, courseType));
        return "courses";
    }

    /**
     * Gets Course' information (id) from the request, gets the Course object and the list of Students who are enrolled on the course
     * from the database and puts them in the request;
     * returns the name of the Course' information page
     * @param request RequestWrapper object
     * @return name of jsp-page
     */
    public String viewCourseInformation(RequestWrapper request) {
        logger.debug("viewCourseInformation()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        Long courseId = Long.valueOf(request.getParameter("course_id"));
        Course course = null;

        // looks for a course with given id in the list of user's courses
        // in case Professor tries to get information not on one of his courses;
        // finds Course object with given id to pass it to the request
        List<Course> courses = courseService.getCoursesByUser(user, CourseType.ALL);
        boolean ok = false;
        for (Course c_course: courses) {
            if (c_course.getId() == courseId) {
                course = c_course;
                ok = true;
                break;
            }
        }
        if (!ok) {
            return "/";
        }
        request.setAttribute("main_page", MainServlet.getUserRedirectPage(user));
        request.setAttribute("course", course);
        request.setAttribute("items", courseService.getArchiveItemsByCourse(course));
        if (user.getUserType() == UserType.PROFESSOR && course.getStartDate().compareTo(new Date()) < 0) {
            request.setAttribute("put_mark", true);
        } else {
            request.setAttribute("put_mark", false);
        }
        return "students";
    }
}

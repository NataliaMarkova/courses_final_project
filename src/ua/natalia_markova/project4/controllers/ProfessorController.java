package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.exceptions.WrongArchiveItemDataException;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.service.ProfessorService;
import ua.natalia_markova.project4.wrappers.RequestWrapper;
import ua.natalia_markova.project4.wrappers.SessionWrapper;

import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 19.07.2016.
 */
public class ProfessorController implements Controller {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");
    private static final Logger logger = Logger.getLogger(ProfessorController.class);

    private ProfessorService professorService;

    public ProfessorController(@NotNull ProfessorService professorService) {
        this.professorService = professorService;
    }

    /**
     *  Gets information from the request (student_id and course_id), finds appropriate Archive item object in database and puts in it request.
     *  Returns name of the Archive item's edit page or URI to the start page if user doesn't have rights to put marks
     *  @param request
     *  @return name of the Archive item's edit page or URI to the start page
     */
    @RequestHandler(value = "/put_mark")
    public String putMark(RequestWrapper request) {
        logger.debug("putMark()");
        SessionWrapper session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        Long studentId = Long.valueOf(request.getParameter("student_id"));
        Long courseId = Long.valueOf(request.getParameter("course_id"));

        Student student = professorService.getStudent(studentId);
        Course course = professorService.getCourse(courseId);
        ArchiveItem item = professorService.getArchiveItem(studentId, courseId);
        if (student == null || course == null || item == null) {
            return "exception";
        }
        if (!course.getProfessor().equals(user)) {
            return "exception";
        }

        request.setAttribute("student", student);
        request.setAttribute("course", course);
        request.setAttribute("mark", item.getMark());
        return "put_mark";
    }

    /**
     *  Gets Archive item's information from the request (student_id, course_id and mark) and updates appropriate record in the database.
     *  If user doesn't have rights to put marks, returns URI to the start page;
     *  if fails to update a record, returns the name of the Archive item's edit page,
     *  otherwise - name of the Course' information page.
     *  @param request
     *  @return name of the Course' information page or name of the Archive item's edit page or URI to the start page
     */
    @RequestHandler(value = "/do_put_mark")
    public String doPutMark(RequestWrapper request) {
        logger.debug("doPutMark()");
        Long studentId = Long.valueOf(request.getParameter("student_id"));
        Long courseId = Long.valueOf(request.getParameter("course_id"));
        String markString = request.getParameter("mark");
        int mark;
        try {
            mark = Integer.valueOf(markString);
            if (professorService.putMarkForCourse(courseId, studentId, mark)) {
                // everything is OK, go back to the course information page
                return bundle.getString("course_information") + "?course_id=" + courseId;
            } else {
                // internal error, go back to the put mark page
                request.setAttribute("error", "Internal error: connect to administrator");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Wrong mark value");
            logger.debug("doPutMark(): wrong mark value: " + markString);
        } catch (IllegalArgumentException e) {
            // no Archive item with such data found -> exception
            request.setAttribute("message", e.getMessage());
            logger.error("doPutMark(): " + e.getMessage());
            return "exception";
        } catch (WrongArchiveItemDataException e) {
            request.setAttribute("error", e.getMessage());
            logger.debug("doPutMark(): " + e.getMessage());
        }
        return bundle.getString("put_mark");
    }
}

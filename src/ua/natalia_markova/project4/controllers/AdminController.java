package ua.natalia_markova.project4.controllers;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Department;
import ua.natalia_markova.project4.exceptions.WrongCourseDataException;
import ua.natalia_markova.project4.exceptions.WrongDepartmentDataException;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.service.AdminService;
import ua.natalia_markova.project4.wrappers.RequestWrapper;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 17.07.2016.
 */
public class AdminController implements RequestHandler {

    private static final Logger logger = Logger.getLogger(AdminController.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.requestURI");

    private AdminService adminService;

    public AdminController(@NotNull AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * @throws WrongRequestURIException {@inheritDoc}
     */
    @Override
    public String handleRequest(RequestWrapper request, String requestURI) throws WrongRequestURIException {
        if (requestURI.equals(bundle.getString("users"))) {
            return viewUsers(request);
        } else if (requestURI.equals(bundle.getString("departments"))) {
            return viewDepartments(request);
        } else if (requestURI.equals(bundle.getString("new_department"))) {
            return editDepartment(request, true);
        } else if (requestURI.equals(bundle.getString("edit_department"))) {
            return editDepartment(request, false);
        } else if (requestURI.equals(bundle.getString("update_department"))) {
            return updateDepartment(request);
        } else if (requestURI.equals(bundle.getString("new_course"))) {
            return newCourse(request);
        } else if (requestURI.equals(bundle.getString("add_course"))) {
            return addCourse(request);
        }else {
            logger.error("Wrong request URI: " + requestURI);
            throw new WrongRequestURIException();
        }
    }

    /**
     * Gets the list of all users from the database and puts it in the request
     * @param request
     * @return name of the Users' list jsp-page
     */
    private String viewUsers(RequestWrapper request) {
        logger.debug("viewUsers(): ");
        request.setAttribute("users", adminService.getUsers());
        return "users";
    }

    /**
     * Gets the list of all departments from the database and puts it in the request
     * @param request
     * @return name of the Departments' list jsp-page
     */
    private String viewDepartments(RequestWrapper request) {
        logger.debug("viewDepartments(): ");
        request.setAttribute("departments", adminService.getDepartments());
        return "departments";
    }

    /**
     *  Puts the Department object that is going to be edited in the request
     *  @param request
     *  @param isNew true value means editing new department, false - editing existing department
     *  @return name of the Department's edit page
     */
    private String editDepartment(RequestWrapper request, boolean isNew) {
        logger.debug("editDepartment(): ");
        Department department;
        if (isNew) {
            department = new Department("");
        } else {
            Long departmentId;
            try {
                departmentId = Long.valueOf(request.getParameter("department_id"));
            } catch (NumberFormatException e) {
                return "exception";
            }
            department = adminService.getDepartment(departmentId);
            if (department == null) {
                return "exception";
            }
        }
        request.setAttribute("department", department);
        return "department";
    }

    /**
     *  Gets department information from the request and creates or updates a record in the database
     *  if fails, returns the name of the Department's edit page, otherwise - URI to the Departments' list page
     *  @param request
     *  @return name of the Department's edit page or URI to the Departments' list page
     */
    private String updateDepartment(RequestWrapper request) {
        logger.debug("updateDepartment(): ");
        String departmentName = request.getParameter("department_name");
        Department department = new Department(departmentName);
        String departmentIdString = request.getParameter("department_id");
        if (!departmentIdString.isEmpty()) {
            Long departmentId = Long.valueOf(departmentIdString);
            department.setId(departmentId);
        }
        try {
            if (adminService.updateDepartment(department)) {
                logger.debug("updateDepartment(): operation's successful");
            } else {
                logger.debug("updateDepartment(): FALSE");
                request.setAttribute("error", "Internal error");
            }
        } catch (WrongDepartmentDataException e) {
            logger.debug("updateDepartment(): exception - " + e.getMessage());
            request.setAttribute("department", department);
            request.setAttribute("error", e.getMessage());
            return "department";
        }
        return bundle.getString("departments");
    }

    /**
     *  Gets the lists of Departments and Professors from the database and puts them in the request
     *  @param request
     *  @return name of the Course's edit page
     */
    private String newCourse(RequestWrapper request) {
        logger.debug("newCourse(): ");
        request.setAttribute("course", new Course("", new Date(), new Date()));
        request.setAttribute("professors", adminService.getProfessors());
        request.setAttribute("departments", adminService.getDepartments());
        return "course";
    }

    /**
     *  Gets Course' information from the request and creates a record of the Course object in the database
     *  if fails to create a record, returns Course's edit page, otherwise - URI to the Courses' list page
     *  @param request
     *  @return name of the Course's edit page or URI to the Courses' list page
     */
    private String addCourse(RequestWrapper request) {
        logger.debug("addCourse()");
        String courseName = request.getParameter("course_name");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        Long professorId = Long.valueOf(request.getParameter("professor_id"));
        Long departmentId = Long.valueOf(request.getParameter("department_id"));
        try {
            if (adminService.addNewCourse(courseName, start_date, end_date, professorId, departmentId)) {
                logger.debug("addCourse(): operation's successful");
                return bundle.getString("courses");
            } else {
                logger.debug("addCourse(): fail");
                request.setAttribute("error", "Internal error");
            }
        } catch (WrongCourseDataException e) {
            logger.debug("addCourse(): exception - " + e.getMessage());
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("course", new Course(courseName, new Date(), new Date()));
        request.setAttribute("professors", adminService.getProfessors());
        request.setAttribute("departments", adminService.getDepartments());
        return "course";
    }

}

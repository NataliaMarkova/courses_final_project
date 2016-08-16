package ua.natalia_markova.project4.service;

import ua.natalia_markova.project4.domain.Department;
import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.exceptions.WrongCourseDataException;
import ua.natalia_markova.project4.exceptions.WrongDepartmentDataException;

import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public interface AdminService {
    /**
     *  Returns list of all users who have user type == Professor
     *  @return list of Professor objects
     */
    List<Professor> getProfessors();

    /**
     *  Returns list of all departments
     *  @return list of Department objects
     */
    List<Department> getDepartments();

    /**
     *  Returns a Department object by given id.
     *  If no record with such id found, returns null
     *  @param departmentId id of the department to be found
     *  @return found Department object
     */
    Department getDepartment(Long departmentId);

    /**
     *  Returns list of all users
     *  @return list of User objects
     */
    List<User> getUsers();

    /**
     *  Updates given department or creates a new one in case department's id is null
     *  @throws WrongDepartmentDataException in case department name is empty
     *  @param department department to be updated/created
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean updateDepartment(Department department) throws WrongDepartmentDataException;

    /**
     *  Creates a new record of Course object with given parameters.
     *  @throws WrongCourseDataException in case some course data is wrong
     *  @param courseName name of the course
     *  @param startDate start date of the course
     *  @param endDate end date of the course
     *  @param professorId ID of Professor object
     *  @param departmentId ID of Department object
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean addNewCourse(String courseName, String startDate, String endDate, Long professorId, Long departmentId) throws WrongCourseDataException;
}

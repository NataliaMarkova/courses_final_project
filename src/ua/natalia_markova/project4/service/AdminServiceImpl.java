package ua.natalia_markova.project4.service;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.dao.DepartmentDao;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Department;
import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.exceptions.WrongCourseDataException;
import ua.natalia_markova.project4.exceptions.WrongDepartmentDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class AdminServiceImpl implements AdminService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private UserDao userDao;
    private CourseDao courseDao;
    private DepartmentDao departmentDao;

    public AdminServiceImpl(@NotNull UserDao userDao, @NotNull CourseDao courseDao, @NotNull DepartmentDao departmentDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
        this.departmentDao = departmentDao;
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public List<Professor> getProfessors() {
        return userDao.getProfessors();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public List<Department> getDepartments() {
        return departmentDao.findAll();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public Department getDepartment(Long departmentId) {
        return departmentDao.read(departmentId);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    /**
     *  @throws WrongDepartmentDataException {@inheritDoc}
     */
    @Override
    public boolean updateDepartment(Department department) throws WrongDepartmentDataException {
        if (department.getName().isEmpty()) {
            throw new WrongDepartmentDataException("Name can't be empty");
        }
        if (department.getId() == null) {
            return departmentDao.create(department) != null;
        } else {
            return departmentDao.update(department);
        }
    }

    /**
     *  @throws WrongCourseDataException {@inheritDoc}
     */
    @Override
    public boolean addNewCourse(String courseName, String startDate, String endDate, Long professorId, Long departmentId) throws WrongCourseDataException {
        if (courseName.isEmpty()) {
            throw new WrongCourseDataException("Name is a required field!");
        }
        if (startDate.isEmpty()) {
            throw new WrongCourseDataException("Start date is a required field!");
        }
        if (endDate.isEmpty()) {
            throw new WrongCourseDataException("End date is a required field!");
        }
        Date start_date = null;
        try {
            start_date = DATE_FORMAT.parse(startDate);
        } catch (ParseException e) {
            throw new WrongCourseDataException("Wrong Start date format. Expected 'dd.MM.yyyy'.");
        }
        Date end_date = null;
        try {
            end_date = DATE_FORMAT.parse(endDate);
        } catch (ParseException e) {
            throw new WrongCourseDataException("Wrong End date format. Expected 'dd.MM.yyyy'.");
        }
        if (start_date.compareTo(new Date()) <= 0) {
            throw new WrongCourseDataException("Start date must be grater than today!");
        }
        if (start_date.compareTo(end_date) >= 0) {
            throw new WrongCourseDataException("Start date can't be grater than end date!");
        }
        if (professorId == null) {
            throw new WrongCourseDataException("Professor is a required field!");
        }
        if (departmentId == null) {
            throw new WrongCourseDataException("Department is a required field!");
        }
        Professor professor = (Professor) userDao.read(professorId);
        Department department = departmentDao.read(departmentId);
        if (professor == null) {
            throw new WrongCourseDataException("Wrong professor_id!");
        }
        if (department == null) {
            throw new WrongCourseDataException("Wrong department_id!");
        }
        Course course = new Course(courseName, start_date, end_date, professor, department);
        return courseDao.create(course) != null;
    }
}


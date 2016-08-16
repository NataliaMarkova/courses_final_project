package dao;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.dao.DepartmentDao;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.domain.*;
import ua.natalia_markova.project4.enums.CourseType;
import utils.TestJdbcDaoFactory;
import utils.TestUtil;

import javax.naming.NamingException;
import java.util.Date;
import java.util.List;

/**
 * Created by natalia_markova on 24.07.2016.
 */
public class CourseDaoImplTest {

    private TestJdbcDaoFactory daoFactory;
    private CourseDao courseDao;
    private final String name = "Test";
    private final Date startDate = new Date();
    private final Date endDate = new Date();

    private final String professorEmail = "unit1@test.com";
    private final String professorPassword = "11";
    private final String professorName = "Test";
    private final String professorFamilyName = "Unit";

    private final String departmentName = "Department";

    @Before
    public void init() throws NamingException {
        daoFactory = TestUtil.getDaoFactory();
        courseDao = daoFactory.getCourseDao();
    }

    @After
    public void destroy() {
        daoFactory.closeDataSource();
    }

    @Test
    public void crudTestOK() {

        UserDao userDao = daoFactory.getUserDao();
        DepartmentDao departmentDao = daoFactory.getDepartmentDao();

        Professor professor = new Professor(professorEmail, professorPassword, professorName, professorFamilyName);
        userDao.create(professor);

        Department department = new Department(departmentName);
        departmentDao.create(department);

        // create
        Course course = new Course(name, startDate, endDate, professor, department);
        Long id = courseDao.create(course);
        Assert.assertTrue(id != null);

        // read
        course = courseDao.read(id);

        Assert.assertTrue(course.getName().equals(name));
        Assert.assertTrue(TestUtil.datesAreEqualIgnoreTime(course.getStartDate(), startDate));
        Assert.assertTrue(TestUtil.datesAreEqualIgnoreTime(course.getEndDate(), endDate));
        Assert.assertTrue(course.getProfessor().equals(professor));
        Assert.assertTrue(course.getDepartment().equals(department));

        // update
        String newName = "New";
        course.setName(newName);
        courseDao.update(course);
        course = courseDao.read(id);
        Assert.assertTrue(course.getName().equals(newName));

        // delete
        Assert.assertTrue(courseDao.delete(course));

        userDao.delete(professor);
        departmentDao.delete(department);
    }

    @Test
    public void selectionsAreOK() {
        List<Course> list;
        for(CourseType courseType: CourseType.values()) {
            list = courseDao.findAll(courseType);
        }
        Assert.assertTrue(true);
    }

}

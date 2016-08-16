package dao;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.natalia_markova.project4.dao.ArchiveItemDao;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.dao.DepartmentDao;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.domain.*;
import utils.TestJdbcDaoFactory;
import utils.TestUtil;

import javax.naming.NamingException;
import java.util.Date;
import java.util.List;

/**
 * Created by natalia_markova on 24.07.2016.
 */
public class ArchiveItemDaoImplTest {

    private TestJdbcDaoFactory daoFactory;
    private ArchiveItemDao archiveItemDao;

    private int mark = 12;

    private final String courseName = "Test";
    private final Date startDate = new Date();
    private final Date endDate = new Date();

    private final String professorEmail = "unit@test.com";
    private final String professorPassword = "11";
    private final String professorName = "Test";
    private final String professorFamilyName = "Unit";

    private final String departmentName = "Department";

    private final String studentEmail = "unit.student@test.com";
    private final String studentPassword = "11";
    private final String studentName = "Test";
    private final String studentFamilyName = "Unit";

    @Before
    public void init() throws NamingException {
        daoFactory = TestUtil.getDaoFactory();
        archiveItemDao = daoFactory.getArchiveItemDao();
    }

    @After
    public void destroy() {
        daoFactory.closeDataSource();
    }

    @Test
    public void crudTestOK() {

        UserDao userDao = daoFactory.getUserDao();
        Professor professor = new Professor(professorEmail, professorPassword, professorName, professorFamilyName);
        userDao.create(professor);

        Student student = new Student(studentEmail, studentPassword, studentName, studentFamilyName);
        userDao.create(student);

        DepartmentDao departmentDao = daoFactory.getDepartmentDao();
        Department department = new Department(departmentName);
        departmentDao.create(department);

        CourseDao courseDao = daoFactory.getCourseDao();
        Course course = new Course(courseName, startDate, endDate, professor, department);
        Long courseId = courseDao.create(course);
        course = courseDao.read(courseId);

        // create
        ArchiveItem item = new ArchiveItem(course, student);
        Assert.assertTrue(archiveItemDao.create(item));

        // read
        item = archiveItemDao.read(student.getId(), course.getId());

        Assert.assertTrue(item.getCourse().equals(course));
        Assert.assertTrue(item.getStudent().equals(student));
        Assert.assertTrue(item.getMark() == 0);

        // update
        item.setMark(mark);
        archiveItemDao.update(item);
        item = archiveItemDao.read(student.getId(), course.getId());
        Assert.assertTrue(item.getMark() == mark);

        // delete
        Assert.assertTrue(archiveItemDao.delete(item));

        courseDao.delete(course);
        userDao.delete(professor);
        departmentDao.delete(department);
        userDao.delete(student);
    }

    @Test
    public  void findAllTestOK() {
        List<ArchiveItem> list = archiveItemDao.findAll();
        Assert.assertTrue(true);
    }

}

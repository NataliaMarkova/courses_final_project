package dao;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.natalia_markova.project4.dao.DepartmentDao;
import ua.natalia_markova.project4.domain.Department;
import utils.TestJdbcDaoFactory;
import utils.TestUtil;

import javax.naming.NamingException;

/**
 * Created by natalia_markova on 24.07.2016.
 */
public class DepartmentDaoImplTest {

    private TestJdbcDaoFactory daoFactory;
    private DepartmentDao departmentDao;
    private final String name = "Test";


    @Before
    public void init() throws NamingException {
        daoFactory = TestUtil.getDaoFactory();
        departmentDao = daoFactory.getDepartmentDao();
    }

    @After
    public void destroy() {
        daoFactory.closeDataSource();
    }

    @Test
    public void crudTestOK() {
        // create
        Department department = new Department(name);
        Long id = departmentDao.create(department);
        Assert.assertTrue(id != null);

        // read
        department = departmentDao.read(id);
        Assert.assertTrue(department.getName().equals(name));

        // update
        String newName = "New";
        department.setName(newName);
        departmentDao.update(department);
        department = departmentDao.read(id);
        Assert.assertTrue(department.getName().equals(newName));

        // delete
        Assert.assertTrue(departmentDao.delete(department));
    }
}

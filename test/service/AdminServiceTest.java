package service;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.natalia_markova.project4.domain.Department;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.exceptions.WrongDepartmentDataException;

import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.AdminService;
import utils.TestUtil;

import javax.naming.NamingException;

/**
 * Created by natalia_markova on 20.07.2016.
 */
public class AdminServiceTest {

    private AdminService adminService;

    @Before
    public void init() throws NamingException {
        ServiceFactory serviceFactory = TestUtil.getServiceFactory(ServiceType.SIMPLE);
        adminService = serviceFactory.getAdminService();
    }

    @Test(expected = WrongDepartmentDataException.class)
    public void updateDepartmentTestNameIsEmpty() throws WrongDepartmentDataException {
        Department department = new Department("");
        adminService.updateDepartment(department);
    }


    @Ignore
    @Test
    public void updateDepartmentTestCreate() throws WrongDepartmentDataException {
        Department department = new Department("Test department");
        Assert.assertTrue(adminService.updateDepartment(department));
    }

    @Test
    public void updateDepartmentTestUpdate() throws WrongDepartmentDataException {
        Department department = adminService.getDepartment(1L);
        Assert.assertTrue(adminService.updateDepartment(department));
    }
}

package controllers;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import ua.natalia_markova.project4.controllers.Controller;
import ua.natalia_markova.project4.domain.Department;
import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.exceptions.WrongRequestURIException;
import ua.natalia_markova.project4.controllers.ControllerManager;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.AdminService;
import utils.TestRequestWrapper;
import utils.TestUtil;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 26.07.2016.
 */

@RunWith(Theories.class)
public class AdminControllerTest {

    private static ControllerManager controllerManager;
    private static ResourceBundle bundle;
    private static ServiceFactory serviceFactory;

    @BeforeClass
    public static void init() throws NamingException {
        bundle = ResourceBundle.getBundle("resource.requestURI");
        serviceFactory = TestUtil.getServiceFactory(ServiceType.SIMPLE);
        controllerManager = ControllerManager.getControllerManager(serviceFactory);
    }

    @Test
    public void adminControllerViewUsersTestOK() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        String requestURI = bundle.getString("users");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("users"));
    }

    @Test
    public void adminControllerViewDepartmentsTestOK() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        String requestURI = bundle.getString("departments");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("departments"));
    }

    @Test
    public void adminControllerNewDepartmentTestOK()throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        String requestURI = bundle.getString("new_department");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("department"));
    }

    @Test
    public void adminControllerEditDepartmentTestOK() throws  WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("department_id", "1");
        String requestURI = bundle.getString("edit_department");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("department"));
    }

    @DataPoints
    public static String[] incomeData() {
        String[] data = {"-1", "dfdg", ""};
        return data;
    }

    @Theory
    public void adminControllerEditDepartmentTestException(final String id) throws  WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("department_id", id);
        String requestURI = bundle.getString("edit_department");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("exception"));
    }

    @Test
    public void adminControllerUpdateDepartmentTestOK() throws  WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        AdminService service = serviceFactory.getAdminService();
        Department department = service.getDepartment(1L);
        request.setParameter("department_id", department.getId().toString());
        request.setParameter("department_name", department.getName());
        String requestURI = bundle.getString("update_department");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("/departments"));
    }

    @Test
    public void adminControllerUpdateDepartmentTestInternalError() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("department_id", "-1");
        request.setParameter("department_name", "fkgjfkghkf");
        String requestURI = bundle.getString("update_department");
        String response = controllerManager.manageRequest(request, requestURI);
        String error = (String) request.getAttribute("error");
        Assert.assertTrue(response.equals("/departments"));
        Assert.assertNotNull(error);
    }

    @Test
    public void adminControllerUpdateDepartmentTestWrongDepartmentDataException() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException{
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("department_id", "");
        request.setParameter("department_name", "");
        String requestURI = bundle.getString("update_department");
        String response = controllerManager.manageRequest(request, requestURI);
        String error = (String) request.getAttribute("error");
        Assert.assertTrue(response.equals("department"));
        Assert.assertNotNull(error);
    }

    @Test
    public void adminControllerNewCourseTestOK() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        String requestURI = bundle.getString("new_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("course"));
    }

    @Ignore
    @Test
    public void adminControllerAddCourseTestOK() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        AdminService service = serviceFactory.getAdminService();
        List<Department> departments = service.getDepartments();
        List<Professor> professors = service.getProfessors();
        if (departments.size() == 0 || professors.size() == 0) {
            Assert.assertTrue(true);
            return;
        }
        request.setParameter("course_name", "ddkjfhkdhg");
        request.setParameter("start_date", "2017-02-28");
        request.setParameter("end_date", "2017-03-31");
        request.setParameter("professor_id", professors.get(0).getId().toString());
        request.setParameter("department_id", departments.get(0).getId().toString());
        String requestURI = bundle.getString("add_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("/courses"));
    }

    @Test
    public void adminControllerAddCourseTestWrondIds() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("course_name", "ddkjfhkdhg");
        request.setParameter("start_date", "2017-02-28");
        request.setParameter("end_date", "2017-03-31");
        request.setParameter("professor_id", "-1");
        request.setParameter("department_id", "-1");
        String requestURI = bundle.getString("add_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("course"));
        String error = (String) request.getAttribute("error");
        Assert.assertNotNull(error);
    }

    @Test
    public void adminControllerAddCourseTestCourseNameIsEmpty() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("course_name", "");
        request.setParameter("start_date", "2017-02-28");
        request.setParameter("end_date", "2017-03-31");
        request.setParameter("professor_id", "12");
        request.setParameter("department_id", "1");
        String requestURI = bundle.getString("add_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("course"));
        String error = (String) request.getAttribute("error");
        Assert.assertNotNull(error);
    }

    @Test
    public void adminControllerAddCourseTestStartDateIsEmpty() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("course_name", "fdgfdgfdg");
        request.setParameter("start_date", "");
        request.setParameter("end_date", "2017-03-31");
        request.setParameter("professor_id", "12");
        request.setParameter("department_id", "1");
        String requestURI = bundle.getString("add_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("course"));
        String error = (String) request.getAttribute("error");
        Assert.assertNotNull(error);
    }

    @Test
    public void adminControllerAddCourseTestEndDateIsEmpty() throws WrongRequestURIException, InvocationTargetException, IllegalAccessException {
        TestRequestWrapper request = new TestRequestWrapper();
        request.setParameter("course_name", "fdgfdgfdg");
        request.setParameter("start_date", "2017-03-31");
        request.setParameter("end_date", "");
        request.setParameter("professor_id", "12");
        request.setParameter("department_id", "1");
        String requestURI = bundle.getString("add_course");
        String response = controllerManager.manageRequest(request, requestURI);
        Assert.assertTrue(response.equals("course"));
        String error = (String) request.getAttribute("error");
        Assert.assertNotNull(error);
    }

}

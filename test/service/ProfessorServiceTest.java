package service;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.exceptions.WrongArchiveItemDataException;
import ua.natalia_markova.project4.factories.ServiceFactory;
import ua.natalia_markova.project4.service.ProfessorService;
import utils.TestUtil;

import javax.naming.NamingException;

/**
 * Created by natalia_markova on 22.07.2016.
 */

@RunWith(Theories.class)
public class ProfessorServiceTest {

    private ProfessorService professorService;

    @Before
    public void init() throws NamingException {
        ServiceFactory serviceFactory = TestUtil.getServiceFactory(ServiceType.SIMPLE);
        professorService = serviceFactory.getProfessorService();
    }

    @DataPoints
    public static int[][] incomeData() {
        int[][] data = {{1, -1, 0}, {-1, 10, 0}, {1, 10, 0}};
        return data;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Theory
    public void putMarkForCourseTestWrongCourseId(final int[] inputs) throws WrongArchiveItemDataException {
        thrown.expect(WrongArchiveItemDataException.class );
        professorService.putMarkForCourse((long) inputs[0], (long) inputs[1], inputs[2]);

    }

    @Test
    public void putMarkForCourseTestTrue() throws WrongArchiveItemDataException {
        Assert.assertTrue(professorService.putMarkForCourse(1L, 10L, 12));
    }

}

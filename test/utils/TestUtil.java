package utils;

import ua.natalia_markova.project4.enums.ServiceType;
import ua.natalia_markova.project4.factories.DaoFactory;
import ua.natalia_markova.project4.factories.ServiceFactory;

import javax.naming.NamingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by natalia_markova on 22.07.2016.
 */
public class TestUtil {

    private TestUtil() {
    }

    public static TestJdbcDaoFactory getDaoFactory() {
        return TestJdbcDaoFactory.getInstance();
    }

    public static ServiceFactory getServiceFactory(ServiceType serviceType ) {
        DaoFactory daoFactory = getDaoFactory();
        ServiceFactory serviceFactory = ServiceFactory.getFactory(daoFactory, serviceType);
        return serviceFactory;
    }

    public static boolean datesAreEqualIgnoreTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}

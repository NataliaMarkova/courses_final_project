package ua.natalia_markova.project4.factories;

import ua.natalia_markova.project4.dao.ArchiveItemDao;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.dao.DepartmentDao;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.enums.DaoType;

/**
 * Created by natalia_markova on 12.07.2016.
 */
public abstract class DaoFactory {
    public abstract UserDao getUserDao();
    public abstract ArchiveItemDao getArchiveItemDao();
    public abstract CourseDao getCourseDao();
    public abstract DepartmentDao getDepartmentDao();

    public static DaoFactory getFactory(DaoType type) {
        if (type == DaoType.JDBC) {
            return JdbcDaoFactory.getInstance();
        } else {
            return null;
        }
    }
}

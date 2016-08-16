package ua.natalia_markova.project4.factories;

import org.apache.log4j.Logger;
import ua.natalia_markova.project4.dao.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by natalia_markova on 12.07.2016.
 */
public class JdbcDaoFactory extends DaoFactory {

    private static final Logger logger = Logger.getLogger(JdbcDaoFactory.class);
    private static JdbcDaoFactory instance;
    private DataSource datasource;

    private JdbcDaoFactory() {
        try {
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            datasource = (DataSource) context.lookup("jdbc/MySQLDataSource");
        } catch (NamingException e) {
            logger.error("Failed to initialize context: " + e.getMessage());
        }
    }

    public static JdbcDaoFactory getInstance() {
        synchronized (JdbcDaoFactory.class) {
            if (instance == null) {
                instance = new JdbcDaoFactory();
            }
        }
        return instance;
    }

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl(datasource);
    }

    @Override
    public ArchiveItemDao getArchiveItemDao() {
        return new ArchiveItemDaoImpl(datasource);
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseDaoImpl(datasource);
    }

    @Override
    public DepartmentDao getDepartmentDao() {
        return new DepartmentDaoImpl(datasource);
    }
}

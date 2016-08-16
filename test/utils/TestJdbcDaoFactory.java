package utils;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import ua.natalia_markova.project4.dao.*;
import ua.natalia_markova.project4.factories.DaoFactory;

import javax.naming.NamingException;

/**
 * Created by natalia_markova on 12.07.2016.
 */
public class TestJdbcDaoFactory extends DaoFactory {

    private static TestJdbcDaoFactory instance;
    private DataSource datasource;

    private TestJdbcDaoFactory() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/epam_courses?useUnicode=yes&characterEncoding=utf8");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);

    }

    public static TestJdbcDaoFactory getInstance() {
        synchronized (TestJdbcDaoFactory.class) {
            if (instance == null) {
                instance = new TestJdbcDaoFactory();
            }
        }
        return instance;
    }

    public void closeDataSource() {
        datasource.close();
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

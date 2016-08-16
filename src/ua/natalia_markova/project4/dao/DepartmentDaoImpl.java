package ua.natalia_markova.project4.dao;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.Department;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 02.07.2016.
 */
public class DepartmentDaoImpl implements DepartmentDao {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.department_sql_queries");
    private static final Logger logger = Logger.getLogger(DepartmentDaoImpl.class);
    private DataSource datasource;

    public DepartmentDaoImpl(@NotNull DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Long create(Department department) {
        String query_text = bundle.getString("SQL_INSERT_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, department.getName());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()) {
                long id = result.getLong(1);
                department.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to insert into Departments! " + e.getMessage());
            return null;
        }
    }

    @Override
    public Department read(Long id) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + bundle.getString("SQL_READ_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<Department> departments = getDepartmentsFromResultSet(result);
            if (departments.size() > 0) {
                Department department = departments.get(0);
                return department;

            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Departments! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Department department) {
        String query_text = bundle.getString("SQL_UPDATE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text);) {
            statement.setString(1, department.getName());
            statement.setLong(2, department.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Failed to update Departments! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Department department) {
        String query_text = bundle.getString("SQL_DELETE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, department.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Failed to delete from Departments! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Department> findAll() {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT");
        logger.info(query_text);
        List<Department> departments = new ArrayList<>();
        try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            departments = getDepartmentsFromResultSet(result);
        } catch (SQLException e) {
            logger.error("Failed to read from Departments! " + e.getMessage());
        }
        return departments;
    }

    private List<Department> getDepartmentsFromResultSet(ResultSet result) throws SQLException {
        List<Department> departments = new ArrayList<>();
        while (result.next()) {
            String name = result.getString("name");
            long id = result.getLong("id");
            departments.add(new Department(id, name));
        }
        return departments;
    }
}

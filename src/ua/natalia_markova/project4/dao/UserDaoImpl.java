package ua.natalia_markova.project4.dao;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 23.06.2016.
 */
public class UserDaoImpl implements UserDao {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.user_sql_queries");
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private DataSource datasource;

    public UserDaoImpl(@NotNull DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Long create(User user) {
        String query_text = bundle.getString("SQL_INSERT_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getFamilyName());
            statement.setString(5, user.getPatronymic());
            statement.setString(6, user.getUserType().toString());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()) {
                long id = result.getLong(1);
                user.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to insert into Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public User read(Long id) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +  bundle.getString("SQL_READ_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<User> users = getUsersFromResultSet(result);
            if (users.size() > 0) {
                User user = users.get(0);
                return user;

            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        String query_text = bundle.getString("SQL_UPDATE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getFamilyName());
            statement.setString(5, user.getPatronymic());
            statement.setString(6, user.getUserType().toString());
            statement.setLong(7, user.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Failed to update Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        String query_text = bundle.getString("SQL_DELETE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1,user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Failed to delete from Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT");
        logger.info(query_text);
        List<User> users = new ArrayList<>();
        try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            users = getUsersFromResultSet(result);
        } catch (SQLException e) {
            logger.error("Failed to read from Users! " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUser(String email, String password) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +  bundle.getString("SQL_GET_USER_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            List<User> users = getUsersFromResultSet(result);
            if (users.size() > 0) {
                return users.get(0);

            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Users! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean hasUser(String email) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +  bundle.getString("SQL_HAS_USER_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;

            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Users! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Professor> getProfessors() {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +  bundle.getString("SQL_GET_PROFESSORS_QUERY_TEXT");
        logger.info(query_text);
        List<Professor> list = new ArrayList<>();
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, UserType.PROFESSOR.toString());
            ResultSet result = statement.executeQuery();
            List<User> users  = getUsersFromResultSet(result);
            for (User user: users) {
                list.add((Professor) user);
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Users! " + e.getMessage());
        } catch (ClassCastException e) {
            logger.error("Failed to cast from User To Professor! " + e.getMessage());
        }
        return list;
    }

    private List<User> getUsersFromResultSet(ResultSet result) throws SQLException {
        List<User> users = new ArrayList<>();
        while (result.next()) {
            String email = result.getString("email");
            String password = result.getString("password");
            String name = result.getString("name");
            String familyName = result.getString("familyname");
            String patronymic = result.getString("patronymic");
            UserType userType = UserType.valueOf(result.getString("user_type").toUpperCase());
            long id = result.getLong("id");

            User user = User.getUser(email, password, name, familyName, patronymic, userType);
            user.setId(id);
            users.add(user);
        }
        return users;
    }

}

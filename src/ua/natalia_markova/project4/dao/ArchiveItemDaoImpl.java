package ua.natalia_markova.project4.dao;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.*;
import ua.natalia_markova.project4.enums.UserType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 25.06.2016.
 */
public class ArchiveItemDaoImpl implements ArchiveItemDao {
    private static final Logger logger = Logger.getLogger(ArchiveItemDaoImpl.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.archive_item_sql_queries");
    private DataSource datasource;

    public ArchiveItemDaoImpl(@NotNull DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public boolean create(ArchiveItem item) {
        String query_text = bundle.getString("SQL_INSERT_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, item.getStudent().getId());
            statement.setLong(2, item.getCourse().getId());
            Integer mark = item.getMark();
            if (mark == null) {
                statement.setNull(3, Types.TINYINT);
            } else {
                statement.setInt(3, item.getMark());
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Failed to insert into Courses_students! " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArchiveItem read(long studentId, long courseId) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + bundle.getString("SQL_READ_QUERY_TEXT") ;
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, studentId);
            statement.setLong(2, courseId);
            ResultSet result = statement.executeQuery();
            List<ArchiveItem> items = getArchiveItemsFromResultSet(result);
            if (items.size() > 0) {
                return items.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Courses_Students! " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(ArchiveItem item) {
        String query_text = bundle.getString("SQL_UPDATE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            Integer mark = item.getMark();
            if (mark == null) {
                statement.setNull(1, Types.TINYINT);
            } else {
                statement.setInt(1, item.getMark());
            }
            statement.setLong(2, item.getStudent().getId());
            statement.setLong(3, item.getCourse().getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Failed to updated Courses_Students! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(ArchiveItem item) {
        String query_text = bundle.getString("SQL_DELETE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, item.getStudent().getId());
            statement.setLong(2, item.getCourse().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Failed to delete from Courses_Students! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ArchiveItem> findAll() {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT");
        logger.info(query_text);
        List<ArchiveItem> items = new ArrayList<>();
        try (Connection connection = datasource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query_text);
            items = getArchiveItemsFromResultSet(result);
        } catch (SQLException e) {
            logger.error("Failed to read from Courses_Students! " + e.getMessage());
        }
        return items;
    }

    @Override
    public List<ArchiveItem> getArchiveItemsByCourse(Course course) {
        List<ArchiveItem> list = new ArrayList<>();
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + bundle.getString("SQL_COURSE_CONDITION_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, course.getId());
            ResultSet result = statement.executeQuery();
            list = getArchiveItemsFromResultSet(result);
        } catch (SQLException e) {
            logger.error("Failed to read from Courses_Students! " + e.getMessage());
            return null;
        }
        return list;
    }

    @Override
    public List<ArchiveItem> getArchiveItemsByStudent(Student student) {
        List<ArchiveItem> list;
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + bundle.getString("SQL_STUDENT_CONDITION_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, student.getId());
            statement.setDate(2, new Date(new java.util.Date().getTime()));
            ResultSet result = statement.executeQuery();
            list = getArchiveItemsFromResultSet(result);
        } catch (SQLException e) {
            logger.error("Failed to read from Courses_Students! " + e.getMessage());
            return null;
        }
        return list;
    }

    private List<ArchiveItem> getArchiveItemsFromResultSet(ResultSet result) throws SQLException {
        List<ArchiveItem> items = new ArrayList<>();
        while (result.next()) {
            String email = result.getString("student.email");
            String password = result.getString("student.password");
            String userName = result.getString("student.name");
            String familyName = result.getString("student.familyname");
            String patronymic = result.getString("student.patronymic");
            UserType userType = UserType.valueOf(result.getString("student.user_type").toUpperCase());
            Long userId = result.getLong("student.id");

            Student student = (Student) User.getUser(email, password, userName, familyName, patronymic, userType);
            student.setId(userId);

            email = result.getString("professor.email");
            password = result.getString("professor.password");
            userName = result.getString("professor.name");
            familyName = result.getString("professor.familyname");
            patronymic = result.getString("professor.patronymic");
            userType = UserType.valueOf(result.getString("professor.user_type").toUpperCase());
            userId = result.getLong("professor.id");
            Long courseId = result.getLong("courses.id");

            String courseName = result.getString("courses.name");
            Date startDate = result.getDate("courses.start_date");
            Date endDate = result.getDate("courses.end_date");

            Professor professor = (Professor) User.getUser(email, password, userName, familyName, patronymic, userType);
            professor.setId(userId);

            Long departmentId = result.getLong("course_department.id");
            String departmentName = result.getString("course_department.name");
            Department department = new Department(departmentId, departmentName);

            Course course = new Course(courseName, startDate, endDate, professor, department);
            course.setId(courseId);

            Integer mark = result.getInt("mark");

            ArchiveItem item = new ArchiveItem(course, student, mark);
            items.add(item);
        }
        return items;
    }
}

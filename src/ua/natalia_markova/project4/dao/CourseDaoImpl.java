package ua.natalia_markova.project4.dao;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.natalia_markova.project4.domain.*;
import ua.natalia_markova.project4.enums.CourseType;
import ua.natalia_markova.project4.enums.UserType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by natalia_markova on 25.06.2016.
 */
public class CourseDaoImpl implements CourseDao {

    private static final Logger logger = Logger.getLogger(CourseDaoImpl.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("resource.course_sql_queries");
    private DataSource datasource;

    public CourseDaoImpl(@NotNull DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Long create(Course course) {
        String query_text = bundle.getString("SQL_INSERT_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text, Statement.RETURN_GENERATED_KEYS);) {
            statement.setString(1, course.getName());
            statement.setDate(2, new Date(course.getStartDate().getTime()));
            statement.setDate(3, new Date(course.getEndDate().getTime()));
            statement.setLong(4, course.getProfessor().getId());
            statement.setLong(5, course.getDepartment().getId());

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next()) {
                long id = result.getLong(1);
                course.setId(id);
                return id;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to insert into Courses! " + e.getMessage());
            return null;
        }
    }

    @Override
    public Course read(Long id) {
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +  bundle.getString("SQL_READ_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            List<Course> courses = getCoursesFromResultSet(result);
            if (courses.size() > 0) {
                Course course = courses.get(0);
                return course;

            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Failed to read from Courses! " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Course course) {
        String query_text = bundle.getString("SQL_UPDATE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setString(1, course.getName());
            statement.setDate(2, new Date(course.getStartDate().getTime()));
            statement.setDate(3, new Date(course.getEndDate().getTime()));
            statement.setLong(4, course.getProfessor().getId());
            statement.setLong(5, course.getDepartment().getId());
            statement.setLong(6, course.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Failed to updated Courses! " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Course course) {
        String query_text = bundle.getString("SQL_DELETE_QUERY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, course.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Failed to delete from Courses! " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Course> findAll(CourseType type) {
        List<Course> courses = new ArrayList<>();
        String dateConditionText = "";
        if (type == CourseType.CURRENT) {
            dateConditionText = bundle.getString("WHERE") + bundle.getString("SQL_CURRENT_CONDITION_TEXT");
        } else if (type == CourseType.FUTURE) {
            dateConditionText = bundle.getString("WHERE") + bundle.getString("SQL_FUTURE_CONDITION_TEXT");
        } else if (type == CourseType.ARCHIVE) {
            dateConditionText = bundle.getString("WHERE") + bundle.getString("SQL_ARCHIVE_CONDITION_TEXT");
        }
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + dateConditionText + bundle.getString("SQL_ORDER_BY_TEXT");

        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            if (type != CourseType.ALL) {
                statement.setDate(1, new Date(new java.util.Date().getTime()));
            }
            ResultSet result = statement.executeQuery();
            courses = getCoursesFromResultSet(result);
            return courses;

        } catch (SQLException e) {
            logger.error("Failed to read from Courses! " + e.getMessage());
        }
        return courses;
    }

    @Override
    public List<Course> getCoursesByUser(User user, CourseType type) {
        if (user.getUserType() == UserType.STUDENT) {
            return getCoursesByStudent((Student) user, type);
        } else if (user.getUserType() == UserType.PROFESSOR) {
            return getCoursesByProfessor((Professor) user, type);
        } else { // admin
            return findAll(type);
        }
    }

    @Override
    public List<Course> getAvailableCourses(Student student) {
        List<Course> courses = new ArrayList<>();
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") + bundle.getString("SQL_AVAILABLE_COURSES_TEXT") ;
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, student.getId());
            statement.setDate(2, new Date(new java.util.Date().getTime()));
            ResultSet result = statement.executeQuery();
            courses = getCoursesFromResultSet(result);
            return courses;

        } catch (SQLException e) {
            logger.error("Failed to get courses by Student! " + e.getMessage());
        }
        return courses;
    }

    private  List<Course> getCoursesByStudent(Student student, CourseType type) {
        List<Course> courses = new ArrayList<>();
        String dateConditionText = "";
        if (type == CourseType.CURRENT) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_CURRENT_CONDITION_TEXT");
        } else if (type == CourseType.FUTURE) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_FUTURE_CONDITION_TEXT");
        } else if (type == CourseType.ARCHIVE) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_ARCHIVE_CONDITION_TEXT");
        }
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT2") +
                bundle.getString("SQL_STUDENT_ID_CONDITION_TEST") +
                dateConditionText + bundle.getString("SQL_ORDER_BY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, student.getId());
            if (type != CourseType.ALL) {
                statement.setDate(2, new Date(new java.util.Date().getTime()));
            }
            ResultSet result = statement.executeQuery();
            courses = getCoursesFromResultSet(result);
            return courses;

        } catch (SQLException e) {
            logger.error("Failed to get courses by Student! " + e.getMessage());
        }
        return courses;
    }

    private  List<Course> getCoursesByProfessor(Professor professor, CourseType type) {
        List<Course> courses = new ArrayList<>();
        String dateConditionText = "";
        if (type == CourseType.CURRENT) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_CURRENT_CONDITION_TEXT");
        } else if (type == CourseType.FUTURE) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_FUTURE_CONDITION_TEXT");
        } else if (type == CourseType.ARCHIVE) {
            dateConditionText = bundle.getString("AND") + bundle.getString("SQL_ARCHIVE_CONDITION_TEXT");
        }
        String query_text = bundle.getString("SQL_BASE_QUERY_SELECTION_TEXT") +
                bundle.getString("SQL_PROFESSOR_ID_CONDITION_TEST") +
                dateConditionText +
                bundle.getString("SQL_ORDER_BY_TEXT");
        logger.info(query_text);
        try (Connection connection = datasource.getConnection(); PreparedStatement statement = connection.prepareStatement(query_text)) {
            statement.setLong(1, professor.getId());
            if (type != CourseType.ALL) {
                statement.setDate(2, new Date(new java.util.Date().getTime()));
            }
            ResultSet result = statement.executeQuery();
            courses = getCoursesFromResultSet(result);
            return courses;
        } catch (SQLException e) {
            logger.error("Failed to get courses by Student! " + e.getMessage());
        }
        return courses;
    }

    private List<Course> getCoursesFromResultSet(ResultSet result) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (result.next()) {
            String email = result.getString("professor.email");
            String password = result.getString("professor.password");
            String userName = result.getString("professor.name");
            String familyName = result.getString("professor.familyname");
            String patronymic = result.getString("professor.patronymic");
            UserType userType = UserType.valueOf(result.getString("professor.user_type").toUpperCase());
            long professor_id = result.getLong("professor.id");
            long id = result.getLong("id");

            String name = result.getString("name");
            Date startDate = result.getDate("start_date");
            Date endDate = result.getDate("end_date");

            Professor professor = (Professor) User.getUser(email, password, userName, familyName, patronymic, userType);
            professor.setId(professor_id);

            Long departmentId = result.getLong("course_department.id");
            String departmentName = result.getString("course_department.name");
            Department department = new Department(departmentId, departmentName);

            Course course = new Course(name, startDate, endDate, professor, department);
            course.setId(id);
            courses.add(course);
        }
        return courses;
    }

}

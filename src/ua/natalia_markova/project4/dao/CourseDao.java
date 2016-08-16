package ua.natalia_markova.project4.dao;

import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;

import java.util.List;

/**
 * Created by natalia_markova on 24.06.2016.
 */
public interface CourseDao {
    Long create(Course course);
    Course read(Long id);
    boolean update(Course course);
    boolean delete(Course course);
    List<Course> findAll(CourseType type);
    List<Course> getCoursesByUser(User user, CourseType type);
    List<Course> getAvailableCourses(Student student);
}

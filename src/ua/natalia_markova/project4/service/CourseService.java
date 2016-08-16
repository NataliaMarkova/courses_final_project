package ua.natalia_markova.project4.service;

import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;

import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public interface CourseService {
    /**
     *  Returns list of Courses by given user and course type;
     *  if user type == UserType.ADMIN, returns all the courses;
     *  if user type == UserType.PROFESSOR, returns the courses which the user teaches;
     *  if user type == UserType.STUDENT, returns the courses on which the user is enrolled;
     *  @param user user to get courses by
     *  @param courseType course type (can be: CourseType.ALL, CourseType.CURRENT, CourseType.FUTURE or CourseType.ARCHIVE)
     *  @return list of found courses
     */
    List<Course> getCoursesByUser(User user, CourseType courseType);

    /**
     *  Returns list of ArchiveItems by given course;
     *  @param course Course object archive items to be found by
     *  @return list of found archive items
     */
    List<ArchiveItem> getArchiveItemsByCourse(Course course);
}

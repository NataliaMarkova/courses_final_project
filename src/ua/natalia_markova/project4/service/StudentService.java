package ua.natalia_markova.project4.service;

import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;

import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public interface StudentService {
    /**
     *  Creates a new record of ArchiveItem object by given course's id and student
     *  @param courseId id of the course
     *  @param student student to be enrolled on the course with id == courseId
     *  @return Course object on which the student was enrolled in case operation's successful, otherwise - null
     */
    Course enrollOnCourse(Long courseId, Student student);

    /**
     *  Returns the list of ArchiveItems by given student
     *  @param student student by who ArchiveItems to be found
     *  @return found list of ArchiveItems
     */
    List<ArchiveItem> getArchiveItems(Student student);

    /**
     *  Returns the list of future courses on which the student hasn't yet been enrolled
     *  @param student student by who the courses to be found
     *  @return list of found Courses
     */
    List<Course> getAvailableCourses(Student student);
}

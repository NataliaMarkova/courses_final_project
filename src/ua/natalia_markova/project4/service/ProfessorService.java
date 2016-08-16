package ua.natalia_markova.project4.service;

import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.exceptions.WrongArchiveItemDataException;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public interface ProfessorService {
    /**
     *  Returns a Student object by given id.
     *  If no record with such id found or user type is not UserType.STUDENT, returns null
     *  @param studentId id of the student to be found
     *  @return found Student object
     */
    Student getStudent(Long studentId);

    /**
     *  Returns a Course object by given id.
     *  If no record with such id found, returns null
     *  @param courseId id of the course to be found
     *  @return found Course object
     */
    Course getCourse(Long courseId);

    /**
     *  Returns an ArchiveItem object by given student_id and course_id
     *  If no record with such ids found, returns null
     *  @param courseId - id of the course
     *  @param studentId - id of the student
     *  @return found ArchiveItem object
     */
    ArchiveItem getArchiveItem(Long studentId, Long courseId);

    /**
     *  Finds an ArchiveItem object by given student_id and course_id and update it's mark value
     *  @throws WrongArchiveItemDataException in case the is ni archive item with such data or mark value
     *          is not in the range from 1 to 12
     *  @param courseId id of the course
     *  @param studentId id of the student
     *  @param mark new mark value
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean putMarkForCourse(Long courseId, Long studentId, int mark) throws WrongArchiveItemDataException;
}

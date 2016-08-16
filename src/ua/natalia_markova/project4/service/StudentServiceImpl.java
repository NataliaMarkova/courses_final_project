package ua.natalia_markova.project4.service;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.dao.ArchiveItemDao;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;

import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class StudentServiceImpl implements StudentService {

    private ArchiveItemDao archiveItemDao;
    private CourseDao courseDao;

    public StudentServiceImpl(@NotNull CourseDao courseDao,@NotNull ArchiveItemDao archiveItemDao) {
        this.courseDao = courseDao;
        this.archiveItemDao = archiveItemDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course enrollOnCourse(Long courseId, Student student) {
        Course course = courseDao.read(courseId);
        if (archiveItemDao.create(new ArchiveItem(course, student))) {
            return course;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ArchiveItem> getArchiveItems(Student student) {
        return archiveItemDao.getArchiveItemsByStudent(student);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getAvailableCourses(Student student) {
        return courseDao.getAvailableCourses(student);
    }


}

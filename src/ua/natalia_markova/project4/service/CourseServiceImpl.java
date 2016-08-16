package ua.natalia_markova.project4.service;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.dao.ArchiveItemDao;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.CourseType;

import java.util.List;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;
    private ArchiveItemDao archiveItemDao;

    public CourseServiceImpl(@NotNull CourseDao courseDao, @NotNull ArchiveItemDao archiveItemDao) {
        this.courseDao = courseDao;
        this.archiveItemDao = archiveItemDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getCoursesByUser(User user, CourseType courseType) {
        return courseDao.getCoursesByUser(user, courseType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ArchiveItem> getArchiveItemsByCourse(Course course) {
        return archiveItemDao.getArchiveItemsByCourse(course);
    }
}

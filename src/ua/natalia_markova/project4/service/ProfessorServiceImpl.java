package ua.natalia_markova.project4.service;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.dao.ArchiveItemDao;
import ua.natalia_markova.project4.dao.CourseDao;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.WrongArchiveItemDataException;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class ProfessorServiceImpl implements ProfessorService {

    private ArchiveItemDao archiveItemDao;
    private UserDao userDao;
    private CourseDao courseDao;

    public ProfessorServiceImpl(@NotNull ArchiveItemDao archiveItemDao, @NotNull UserDao userDao, @NotNull CourseDao courseDao) {
        this.archiveItemDao = archiveItemDao;
        this.userDao = userDao;
        this.courseDao = courseDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Student getStudent(Long studentId) {
        User user = userDao.read(studentId);
        if (user != null && user.getUserType() != UserType.STUDENT) {
            user = null;
        }
        return (Student) user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course getCourse(Long courseId) {
        return courseDao.read(courseId);
    }

    @Override
    public ArchiveItem getArchiveItem(Long studentId, Long courseId) {
        return archiveItemDao.read(studentId, courseId);
    }

    /**
     * @throws WrongArchiveItemDataException {@inheritDoc}
     */
    @Override
    public boolean putMarkForCourse(Long courseId, Long studentId, int mark) throws WrongArchiveItemDataException {
        if (mark < 1 || mark > 12) {
            throw new WrongArchiveItemDataException("Mark should have value from 1 to 12");
        }
        ArchiveItem item = getArchiveItem(studentId, courseId);
        if (item == null) {
            throw new WrongArchiveItemDataException("No archive item with such data found");
        }
        return archiveItemDao.update(new ArchiveItem(getCourse(courseId), getStudent(studentId), mark));
    }
}

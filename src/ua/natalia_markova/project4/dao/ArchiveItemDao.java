package ua.natalia_markova.project4.dao;

import ua.natalia_markova.project4.domain.ArchiveItem;
import ua.natalia_markova.project4.domain.Course;
import ua.natalia_markova.project4.domain.Student;

import java.util.List;

/**
 * Created by natalia_markova on 25.06.2016.
 */
public interface ArchiveItemDao {
    boolean create(ArchiveItem item);
    ArchiveItem read(long studentId, long courseId);
    boolean update(ArchiveItem item);
    boolean delete(ArchiveItem item);
    List<ArchiveItem> findAll();
    List<ArchiveItem> getArchiveItemsByCourse(Course course);
    List<ArchiveItem> getArchiveItemsByStudent(Student student);
}

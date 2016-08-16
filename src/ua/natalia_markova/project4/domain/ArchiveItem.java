package ua.natalia_markova.project4.domain;

import com.sun.istack.internal.NotNull;

/**
 * Created by natalia_markova on 20.06.2016.
 */
public class ArchiveItem {

    private Course course;
    private Student student;
    private Integer mark;

    public ArchiveItem(@NotNull Course course, @NotNull Student student) {
        this.course = course;
        this.student = student;
    }

    public ArchiveItem(@NotNull Course course, @NotNull Student student, int mark) {
        this(course, student);
        this.mark = mark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "ArchiveItem{" +
                "course=" + course +
                ", student=" + student +
                ", mark=" + mark +
                '}';
    }
}

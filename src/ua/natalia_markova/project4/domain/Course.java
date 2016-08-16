package ua.natalia_markova.project4.domain;

import com.sun.istack.internal.NotNull;
import java.util.Date;

/**
 * Created by natalia_markova on 20.06.2016.
 */

public class Course {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Professor professor;
    private Department department;

    public Course(@NotNull String name, @NotNull  Date startDate, @NotNull Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course(@NotNull String name, @NotNull Date startDate, @NotNull Date endDate, @NotNull Professor professor, @NotNull Department department) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.professor = professor;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!id.equals(course.id)) return false;
        if (!name.equals(course.name)) return false;
        if (!startDate.equals(course.startDate)) return false;
        if (!endDate.equals(course.endDate)) return false;
        if (!professor.equals(course.professor)) return false;
        return department.equals(course.department);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + professor.hashCode();
        result = 31 * result + department.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", professor=" + professor +
                ", department=" + department +
                '}';
    }
}

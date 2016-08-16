package ua.natalia_markova.project4.dao;

import ua.natalia_markova.project4.domain.Department;

import java.util.List;

/**
 * Created by natalia_markova on 02.07.2016.
 */
public interface DepartmentDao {
    Long create(Department department);
    Department read(Long id);
    boolean update(Department department);
    boolean delete(Department department);
    List<Department> findAll();
}

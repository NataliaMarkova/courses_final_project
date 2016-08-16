package ua.natalia_markova.project4.dao;

import ua.natalia_markova.project4.domain.Professor;
import ua.natalia_markova.project4.domain.User;

import java.util.List;

/**
 * Created by natalia_markova on 20.06.2016.
 */
public interface UserDao {
    Long create(User user);
    User read(Long id);
    boolean update(User user);
    boolean delete(User user);
    List<User> findAll();
    User getUser(String email, String password);
    boolean hasUser(String email);
    List<Professor> getProfessors();
}

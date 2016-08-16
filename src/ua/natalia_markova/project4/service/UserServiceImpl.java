package ua.natalia_markova.project4.service;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.dao.UserDao;
import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.AuthenticationException;
import ua.natalia_markova.project4.exceptions.RegistrationException;
import ua.natalia_markova.project4.exceptions.WrongUserDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(@NotNull UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @throws AuthenticationException {@inheritDoc}
     */
    @Override
    public User login(String login, String password) throws AuthenticationException {
        if (login == null || login.isEmpty()) {
            throw new AuthenticationException("Login is a required field!");
        }
        if (password == null || password.isEmpty()) {
            throw new AuthenticationException("Password is a required field!");
        }
        return userDao.getUser(login, password);
    }

    /**
     * @throws RegistrationException {@inheritDoc}
     */
    @Override
    public boolean registerUser(String login, String password, String passwordConfirmation, String name, String familyName, String patronymic, UserType userType) throws RegistrationException {
        checkRegistrationData(login, name, familyName, password, passwordConfirmation);
        User user = User.getUser(login, password, name, familyName, patronymic, userType);
        return userDao.create(user) != null;
    }

    /**
     * @throws WrongUserDataException {@inheritDoc}
     */
    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword, String passwordConfirmation)  throws WrongUserDataException {
        if (!user.getPassword().equals(oldPassword)) {
            throw new WrongUserDataException("Wrong password");
        }
        checkPassword(newPassword, passwordConfirmation);
        user.setPassword(newPassword);
        return userDao.update(user);
    }

    /**
     * @throws WrongUserDataException {@inheritDoc}
     */
    @Override
    public boolean changeUserData(User user, String password, String name, String familyName, String patronymic)  throws WrongUserDataException {
        if (!user.getPassword().equals(password)) {
            throw new WrongUserDataException("Wrong password");
        }
        checkDataIsNotEmpty(name, "Name");
        checkDataIsNotEmpty(familyName, "Family name");
        user.setName(name);
        user.setFamilyName(familyName);
        user.setPatronymic(patronymic);
        return userDao.update(user);
    }

    /**
     *  Checks if user's registration data is OK
     *  @throws WrongUserDataException in case some data is invalid
     *  @param login user login
     *  @param password user password
     *  @param passwordConfirmation password confirmation (must match password)
     *  @param name user name
     *  @param familyName user family name
     */
    private void checkRegistrationData(String login, String name, String familyName, String password, String passwordConfirmation) throws RegistrationException {
        try {
            checkDataIsNotEmpty(login, "Login");
            if (!login.toLowerCase().matches("[a-z]+((\\.|_)[a-z]+)*@[a-z]+(\\.[a-z]+)*\\.[a-z]{2,3}")) {
                throw new RegistrationException("Login should be in email format. For example: your.name@host.com");
            }
            checkPassword(password, passwordConfirmation);
            checkDataIsNotEmpty(name, "Name");
            checkDataIsNotEmpty(familyName, "Family name");
        } catch (WrongUserDataException e) {
            throw new RegistrationException(e.getMessage());
        }
        if (userDao.hasUser(login)) {
            throw new RegistrationException("User with login '" + login + "' already exists");
        }
    }

    /**
     *  Checks if password is OK and password confirmation matches password
     *  @throws WrongUserDataException
     *  @param password user password
     *  @param passwordConfirmation password confirmation (must match password)
     */
    private void checkPassword(String password, String passwordConfirmation) throws WrongUserDataException {
        if (password.isEmpty()) {
            throw new WrongUserDataException("Password is a required field!");
        }
        if (password.length() < 5) {
            throw new WrongUserDataException("Password must be 5 symbols minimum");
        }
        Pattern pattern = Pattern.compile("[a-zA-Z]|[\\u0400-\\u044F]");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new WrongUserDataException("Password must contain at least one letter");
        }
        pattern = Pattern.compile("[0-9]");
        matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new WrongUserDataException("Password must contain at least one digit");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new WrongUserDataException("Password and password confirmation do not match");
        }
    }

    /**
     *  Checks if given String object is not empty
     *  @throws WrongUserDataException
     *  @param data data to check
     *  @param dataName data name representation
     */
    private void checkDataIsNotEmpty(String data, String dataName) throws WrongUserDataException {
        if (data == null || data.isEmpty()) {
            throw new WrongUserDataException(dataName + " can't be empty!");
        }
    }
}

package ua.natalia_markova.project4.service;

import ua.natalia_markova.project4.domain.User;
import ua.natalia_markova.project4.enums.UserType;
import ua.natalia_markova.project4.exceptions.AuthenticationException;
import ua.natalia_markova.project4.exceptions.RegistrationException;
import ua.natalia_markova.project4.exceptions.WrongUserDataException;

/**
 * Created by natalia_markova on 26.06.2016.
 */
public interface UserService {
    /**
     *  Looks for a record by given parameters; if founds, returns appropriate User object, otherwise - returns null.
     *  @throws AuthenticationException in case login and/or password are empty
     *  @param login user login
     *  @param password user passwords
     *  @return found User object
     */
    User login(String login, String password) throws AuthenticationException;

    /**
     *  Create a record of a new user with given parameters;
     *  @throws RegistrationException in case some parameters are invalid
     *  @param login user login
     *  @param password user password
     *  @param passwordConfirmation password confirmation (must match password)
     *  @param name user name
     *  @param familyName user family name
     *  @param patronymic user patronymic
     *  @param userType user type; available values: UserType.ADMIN, UserType.PROFESSOR or UserType.STUDENT
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean registerUser(String login, String password, String passwordConfirmation, String name, String familyName, String patronymic, UserType userType) throws RegistrationException;

    /**
     *  Changes user's password to a new one
     *  @throws WrongUserDataException in case some parameters are invalid
     *  @param user current user
     *  @param oldPassword current user's password
     *  @param newPassword new user's password
     *  @param passwordConfirmation new password confirmation (must match new password)
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean changePassword(User user, String oldPassword, String newPassword, String passwordConfirmation) throws WrongUserDataException;

    /**
     *  Changes user's personal data e.g. name, family name and patronymic
     *  @throws WrongUserDataException in case some parameters are invalid
     *  @param user current user
     *  @param password user's current password
     *  @param name user's new name
     *  @param familyName user's new family name
     *  @param patronymic user's new patronymic
     *  @return <code><b>true</b></code> if operation's successful, otherwise - <code><b>false</b></code>
     */
    boolean changeUserData(User user, String password, String name, String familyName, String patronymic)  throws WrongUserDataException;
}

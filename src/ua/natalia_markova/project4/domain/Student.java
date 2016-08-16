package ua.natalia_markova.project4.domain;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.enums.UserType;

/**
 * Created by natalia_markova on 20.06.2016.
 */

public class Student extends User {

    public Student(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String familyName, @NotNull String patronymic) {
        super(email, password, name, familyName, patronymic);
    }

    public Student(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String familyName) {
        super(email, password, name, familyName);
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", name='" + getName() + '\'' +
                ", familyName='" + getFamilyName() + '\'' +
                ", patronymic='" + getPatronymic() +
                '}';
    }
}

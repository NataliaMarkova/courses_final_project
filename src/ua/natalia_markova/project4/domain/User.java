package ua.natalia_markova.project4.domain;

import com.sun.istack.internal.NotNull;
import ua.natalia_markova.project4.enums.UserType;

import java.io.Serializable;

/**
 * Created by natalia_markova on 19.06.2016.
 */

public abstract class User implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String familyName;
    private String patronymic;

    public User(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String familyName, @NotNull String patronymic) {
        this(email, password, name, familyName);
        this.patronymic = patronymic;
    }

    public User(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String familyName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.familyName = familyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFullName() {
        return familyName + " " + name + (patronymic == null || patronymic.isEmpty() ? "" : " " + patronymic);
    }

    public abstract UserType getUserType();

    public static User getUser(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String familyName, String patronymic, UserType userType) {
        if (userType == UserType.STUDENT) {
            return new Student(email, password, name, familyName, patronymic);
        } else if (userType == UserType.PROFESSOR) {
            return new Professor(email, password, name, familyName, patronymic);
        }else {
            return new Admin(email, password, name, familyName, patronymic);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", patronymic='" + patronymic +
                '}';
    }
}

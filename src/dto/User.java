package dto;

import java.time.LocalDate;

/**
 * Klasa reprezentująca użytkownika.
 */

public class User {
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Sex sex;
    private String email;
    private Account account;

    public User() {
    }

    /**
     * Konstruktor dla nowego użytkownika (bez ID).
     */

    public User(String userName, String password, String firstName,
                String lastName, LocalDate birthDate, Sex sex, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
    }

    /**
     * Konstruktor dla istniejącego użytkownika (z ID).
     */

    public User(Long id, String userName, String password, String firstName,
                String lastName, LocalDate birthDate, Sex sex, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

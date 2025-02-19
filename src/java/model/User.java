package model;

import model.enums.UserRole;
import java.sql.Date;

public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String userName;
    private Date dob;
    private String phone;
    private String email;
    private String password;
    private UserRole role;

    public User() {
    }

    public User(String firstName, String lastName, String userName, Date dob, String phone, String email,
            String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String userID, String firstName, String lastName, String userName, Date dob, String phone, String email,
            String password, UserRole role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
                + userName + ", dob=" + dob + ", phone=" + phone + ", email=" + email + ", password=" + password
                + ", role=" + role + '}';
    }

}
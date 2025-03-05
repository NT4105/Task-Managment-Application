package dto;

import model.enums.UserRole;
import java.sql.Timestamp;
import java.sql.Date;
import model.User;

public class UserDTO {
    private String userID;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private Date dob;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String password;

    // Constructor
    public UserDTO(String userID, String userName, String password, String firstName, String lastName,
            String email, String phone, UserRole role, Date dob,
            Timestamp createdAt, Timestamp updatedAt) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.dob = dob;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Add method to convert to User entity
    public User toUser() {
        User user = new User();
        user.setUserID(this.userID);
        user.setUserName(this.userName);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPhone(this.phone);
        user.setRole(this.role);
        user.setDob(this.dob);
        user.setCreatedAt(Timestamp.valueOf(this.createdAt.toLocalDateTime()));
        user.setUpdatedAt(Timestamp.valueOf(this.updatedAt.toLocalDateTime()));
        return user;
    }
}
package model;

import model.enums.UserRole;
import java.sql.Date;

public class User {
    private String userID;
    private String userName;
    private String password;
    private UserRole role;
    private Date createdAt;
    private Date updatedAt;

    public User() {
    }

    public User(String userID, String userName, String password, UserRole role,
            Date createdAt, Date updatedAt) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", password=" + password + ", role=" + role
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
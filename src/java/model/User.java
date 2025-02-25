package model;

import model.enums.UserRole;
import java.sql.Date;

public class User {
//    private String userID;
//    private String userName;
//    private String password;
//    private UserRole role;
//    private Date createdAt;
//    private Date updatedAt;
      private String id, firstName, lastName, userName, phone, email, password, role;
      private Date dob;

    public User() {
    }

//    public User(String userID, String userName, String password, UserRole role,
//            Date createdAt, Date updatedAt) {
//        this.userID = userID;
//        this.userName = userName;
//        this.password = password;
//        this.role = role;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
    public User(String id, String firstName, String lastName, String userName, Date dob, String phone, String email, String password, String role){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

//    public String getuserID() {
//        return userID;
//    }
//
//    public void setuserID(String userID) {
//        this.userID = userID;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserRole getRole() {
//        return role;
//    }
//
//    public void setRole(UserRole role) {
//        this.role = role;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" + "userID=" + userID + ", userName=" + userName + ", password=" + password + ", role=" + role
//                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String toString(){
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s", getId(), getFirstName(), getLastName()
                , getUserName(), getDob(), getPhone(), getEmail(), getPassword(), getRole());
    }
}
 package model;

import Utilities.Validation;


public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String role;

    public User() {
    }

    public User(String userID, String firstName, String lastName, String userName, String email, String password, String role) {
        setUserID(userID);
        setFirstName(firstName);
        setLastName(lastName);
        setUserName(userName);
        setEmail(email);
        this.password = password;
        this.role = role;
    }

    // Getters and setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        if(Validation.checkRegex(userID, "^[A-Za-z0-9]+$")){
            
        }else{
            this.userID = "Unexpected";
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(Validation.checkRegex(firstName, "^[A-Za-z]+([-' ][A-Za-z]+)*$")){
            this.firstName = firstName;
        }else{
            this.firstName = "Unexpected";
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(Validation.checkRegex(lastName, "^[A-Za-z]+([-' ][A-Za-z]+)*$")){
            this.lastName = lastName;
        }else{
            this.lastName = "Unexpected";
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(Validation.checkRegex(lastName, "^[A-Za-z0-9!@#$%^&*()_+=[\\]{}|;:'\",.<>/?\\-]+$")){
            this.userName = userName;
        }else{
            this.userName = "Unexpected";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(Validation.checkRegex(lastName, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            this.email = email;
        }else{
            this.email = "Unexpected";
        }
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

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s", getUserID(), getFirstName(), getLastName(), getUserName(), getEmail(), getPassword(), getRole());
    }
}
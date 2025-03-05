package model;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;

public class Profile {
    private String profileID;
    private String userID; // Foreign key to User
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dob;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Profile() {
    }

    public Profile(String profileID, String userID, String firstName,
            String lastName, String email, String phone,
            Date dob, Timestamp createdAt, Timestamp updatedAt) {
        this.profileID = profileID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Method để kiểm tra xem có thay đổi so với profile khác không
    public boolean hasChanges(Profile other) {
        return !Objects.equals(this.firstName, other.firstName) ||
                !Objects.equals(this.lastName, other.lastName) ||
                !Objects.equals(this.email, other.email) ||
                !Objects.equals(this.phone, other.phone) ||
                !Objects.equals(this.dob, other.dob);
    }

    // Getters and setters
    public String getProfileId() {
        return profileID;
    }

    public void setProfileId(String profileID) {
        this.profileID = profileID;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userID) {
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

    public Date getDateOfBirth() {
        return dob;
    }

    public void setDateOfBirth(Date dob) {
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

    @Override
    public String toString() {
        return "Profile [profileID=" + profileID + ", userID=" + userID + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + email + ", phone=" + phone + ", dob=" + dob + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
package model;

import java.sql.Date;
import java.sql.Timestamp;

public class ProjectMember {
    private String projectID;
    private String userID;
    private String firstName;
    private String lastName;
    private Date joinedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public ProjectMember() {
    }

    public ProjectMember(String projectID, String userID, String firstName, String lastName, Date joinedAt,
            Timestamp createdAt, Timestamp updatedAt) {
        this.projectID = projectID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.joinedAt = joinedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public String getProjectId() {
        return projectID;
    }

    public void setProjectId(String projectID) {
        this.projectID = projectID;
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

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
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
        return "ProjectMember [projectID=" + projectID + ", userID=" + userID + ", joinedAt=" + joinedAt
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
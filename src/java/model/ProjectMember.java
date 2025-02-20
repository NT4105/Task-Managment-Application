package model;

import java.sql.Date;

public class ProjectMember {
    private String projectID;
    private String userID;
    private Date joinedAt;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public ProjectMember() {
    }

    public ProjectMember(String projectID, String userID, Date joinedAt,
            Date createdAt, Date updatedAt) {
        this.projectID = projectID;
        this.userID = userID;
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

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
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
        return "ProjectMember [projectID=" + projectID + ", userID=" + userID + ", joinedAt=" + joinedAt
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
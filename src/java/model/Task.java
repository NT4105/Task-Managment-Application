package model;

import model.enums.TaskStatus;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Task {
    private String taskID;
    private String projectID;
    private String taskName;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private List<String> assignedUsers; // changed from single assignedTo
    private String submissionLink;
    private String submissionFilePath;
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Task() {
        this.assignedUsers = new ArrayList<>();
    }

    public Task(String taskID, String projectID, String taskName, String description, TaskStatus status, Date dueDate,
            List<String> assignedUsers, String submissionLink, String submissionFilePath, Date createdAt,
            Date updatedAt) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedUsers = assignedUsers;
        this.submissionLink = submissionLink;
        this.submissionFilePath = submissionFilePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter & Setter
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<String> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<String> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public String getSubmissionFilePath() {
        return submissionFilePath;
    }

    public void setSubmissionFilePath(String submissionFilePath) {
        this.submissionFilePath = submissionFilePath;
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
        return "Task{" + "taskID=" + taskID + ", projectID=" + projectID + ", taskName=" + taskName + ", description="
                + description + ", status="
                + status + ", dueDate=" + dueDate + ", assignedUsers=" + assignedUsers + ", submissionLink="
                + submissionLink
                + ", submissionFilePath=" + submissionFilePath + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + '}';
    }

    // Add these methods to check if the task is due soon or overdue
    public boolean isDueSoon() {
        if (dueDate == null)
            return false;

        long diffInMillies = dueDate.getTime() - System.currentTimeMillis();
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

        return diffInDays >= 0 && diffInDays <= 3;
    }

    public boolean isOverdue() {
        if (dueDate == null)
            return false;
        return dueDate.before(new Date(System.currentTimeMillis()));
    }
}

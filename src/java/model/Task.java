package model;

import model.enums.TaskStatus;
import java.sql.Date;

public class Task {
    private String taskID;
    private String projectID;
    private String taskName;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private String assignedTo;
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Task() {
    }

    public Task(String taskID, String projectID, String taskName, String description,
            TaskStatus status, Date dueDate, String assignedTo, Date createdAt, Date updatedAt) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter & Setter
    public String getTaskId() {
        return taskID;
    }

    public void setTaskId(String taskID) {
        this.taskID = taskID;
    }

    public String getProjectId() {
        return projectID;
    }

    public void setProjectId(String projectID) {
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
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
                + status + ", dueDate=" + dueDate + ", assignedTo=" + assignedTo + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + '}';
    }

}

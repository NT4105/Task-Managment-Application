package dto;

import model.enums.TaskStatus;
import java.sql.Date;
import java.sql.Timestamp;

public class TaskDTO {
    private String taskID;
    private String projectID;
    private String taskName;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private String assignedUserID;
    private String projectName;
    private int progress;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor
    public TaskDTO(String taskID, String projectID, String taskName,
            String description, TaskStatus status, Date dueDate,
            String assignedUserID, String projectName, int progress,
            Timestamp createdAt, Timestamp updatedAt) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedUserID = assignedUserID;
        this.projectName = projectName;
        this.progress = progress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
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

    public String getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(String assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
}
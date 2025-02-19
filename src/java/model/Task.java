package model;

import model.enums.TaskStatus;
import java.sql.Date;

public class Task {
    private String taskId;
    private String taskName;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private String projectId;
    private String assignedTo;

    // Constructor
    public Task() {
    }

    public Task(String taskId, String taskName, String description, TaskStatus status,
            Date dueDate, String projectId, String assignedTo) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
    }

    // Getter & Setter
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Task{" + "taskId=" + taskId + ", taskName=" + taskName + ", description=" + description + ", status="
                + status + ", dueDate=" + dueDate + ", projectId=" + projectId + ", assignedTo=" + assignedTo + '}';
    }

}

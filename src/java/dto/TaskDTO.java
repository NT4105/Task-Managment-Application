package dto;

import model.enums.TaskStatus;
import java.sql.Date;

public class TaskDTO {
    private String taskName;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private String assignedUserName; // Thay vì assignedTo ID

    // Constructor
    public TaskDTO(String taskName, String description, TaskStatus status,
            Date dueDate, String assignedUserName) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedUserName = assignedUserName;
    }

    // Getter & Setter
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

    public String getAssignedUserName() {
        return assignedUserName;
    }

    public void setAssignedUserName(String assignedUserName) {
        this.assignedUserName = assignedUserName;
    }

    // Getters and setters

}
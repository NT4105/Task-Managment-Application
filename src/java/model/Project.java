package model;

import java.sql.Date;
import java.util.List;

public class Project {
    private String projectID;
    private String projectName;
    private String description;
    private List<Task> tasks;
    private Date startDate;
    private Date endDate;
    private String managerId;
    private int totalTasks;
    private int completedTasks;
    private Date createdAt;
    private Date updatedAt;

    // Constructor

    public Project() {
    }

    public Project(String projectID, String projectName, String description, List<Task> tasks, Date startDate,
            Date endDate, String managerId, int totalTasks, int completedTasks, Date createdAt, Date updatedAt) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerId = managerId;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getprojectID() {
        return projectID;
    }

    public void setprojectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
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
        return "Project{" + "projectID=" + projectID + ", projectName=" + projectName + ", description=" + description
                + ", tasks=" + tasks + ", startDate=" + startDate + ", endDate=" + endDate + ", managerId=" + managerId
                + ", totalTasks=" + totalTasks + ", completedTasks=" + completedTasks + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + '}';
    }

}
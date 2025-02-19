package model;

import java.sql.Date;
import java.util.List;

public class Project {
    private String projectId;
    private String projectName;
    private String description;
    private List<Task> tasks;
    private Date startDate;
    private Date endDate;
    private String managerId;

    // Constructor

    public Project() {
    }

    public Project(String projectId, String projectName, String description, List<Task> tasks, Date startDate,
            Date endDate, String managerId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerId = managerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projectName=" + projectName + ", description=" + description
                + ", tasks=" + tasks + ", startDate=" + startDate + ", endDate=" + endDate + ", managerId=" + managerId
                + '}';
    }

}
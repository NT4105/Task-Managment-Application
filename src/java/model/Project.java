// src/java/model/Project.java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author ACER
 */
public class Project {
    private String projectId;
    private String projectName;
    private String description;
    private List<Task> tasks;
    private int startDate;
    private int endDate;
    private String manageId;
    
    // Constructor
    
    public Project() {
    }

    public Project(String projectId, String projectName, String description, List<Task> tasks, int startDate, int endDate, String manageId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.tasks = tasks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manageId = manageId;
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

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getManageId() {
        return manageId;
    }

    public void setManageId(String manageId) {
        this.manageId = manageId;
    }

    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projectName=" + projectName + ", description=" + description + ", tasks=" + tasks + ", startDate=" + startDate + ", endDate=" + endDate + ", manageId=" + manageId + '}';
    }
    
    
}
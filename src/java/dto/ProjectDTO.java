package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import model.enums.TaskStatus;

public class ProjectDTO {
    private String projectID;
    private String projectName;
    private String description;
    private Date startDate; // Start date & end date are just get mm/dd/yyyy format
    private Date endDate;
    private String managerID;
    private List<String> teamMemberIDs;
    private List<TaskDTO> tasks;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Thêm method để tính toán động
    public int getTotalTasks() {
        return tasks != null ? tasks.size() : 0;
    }

    public int getCompletedTasks() {
        if (tasks == null)
            return 0;
        return (int) tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .count();
    }

    public double getProgress() {
        int total = getTotalTasks();
        return total > 0 ? (getCompletedTasks() * 100.0 / total) : 0;
    }

    // Constructor
    public ProjectDTO(String projectID, String projectName, String description,
            Date startDate, Date endDate, String managerID,
            List<String> teamMemberIDs, List<TaskDTO> tasks,
            Timestamp createdAt, Timestamp updatedAt) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerID = managerID;
        this.teamMemberIDs = teamMemberIDs;
        this.tasks = tasks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
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

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public List<String> getTeamMemberIDs() {
        return teamMemberIDs;
    }

    public void setTeamMemberIDs(List<String> teamMemberIDs) {
        this.teamMemberIDs = teamMemberIDs;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
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
package dto;

import java.sql.Date;
import java.util.List;

public class ProjectDTO {
    private String projectName;
    private String description;
    private Date startDate;
    private Date endDate;
    private String managerName; // Thay vì managerId
    private int totalTasks; // Số lượng task
    private int completedTasks; // Số task đã hoàn thành
    private int pendingTasks; // Số task chưa hoàn thành
    private List<TaskDTO> recentTasks; // Chỉ lấy các task gần đây

    // Constructor
    public ProjectDTO(String projectName, String description,
            Date startDate, Date endDate, String managerName,
            int totalTasks, int completedTasks, int pendingTasks,
            List<TaskDTO> recentTasks) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerName = managerName;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.recentTasks = recentTasks;
    }

    // Getters and Setters
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
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

    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public List<TaskDTO> getRecentTasks() {
        return recentTasks;
    }

    public void setRecentTasks(List<TaskDTO> recentTasks) {
        this.recentTasks = recentTasks;
    }
}
package model;

import java.sql.Date;
import java.sql.Timestamp;
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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ProjectMember> projectMembers;
    private String encodedId;
    // Constructor

    public Project() {
    }

    public Project(String projectID, String projectName, String description, List<Task> tasks, Date startDate,
            Date endDate, String managerId, int totalTasks, int completedTasks, Timestamp createdAt,
            Timestamp updatedAt,
            String encodedId) {
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
        this.encodedId = encodedId;
    }

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

    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public List<ProjectMember> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    // Getter for completion percentage
    public double getCompletionPercentage() {
        if (totalTasks == 0)
            return 0;
        return (completedTasks * 100.0) / totalTasks;
    }

    public boolean isCompleted() {
        // Project được coi là hoàn thành khi:
        // 1. Có ít nhất 1 task
        // 2. Tất cả các task đều đã hoàn thành
        return totalTasks > 0 && totalTasks == completedTasks;
    }

    // Kiểm tra xem project có member nào không
    public boolean hasTeamMember(String userId) {
        if (projectMembers == null)
            return false;
        return projectMembers.stream().anyMatch(member -> member.getUserId().equals(userId));
    }

    @Override
    public String toString() {
        return "Project{" + "projectID=" + projectID + ", projectName=" + projectName + ", description=" + description
                + ", tasks=" + tasks + ", startDate=" + startDate + ", endDate=" + endDate + ", managerId=" + managerId
                + ", totalTasks=" + totalTasks + ", completedTasks=" + completedTasks + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", encodedId=" + encodedId + '}';
    }

}
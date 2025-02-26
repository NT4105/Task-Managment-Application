package model;

import java.sql.Timestamp;
import model.enums.TaskStatus;

public class TaskAssignment {
    private String taskId;
    private String userId;
    private String username;
    private String fullName;
    private TaskStatus status;
    private String submissionLink;
    private String submissionFilePath;
    private Timestamp assignedAt;
    private Timestamp completedAt;

    // Constructors, getters, and setters
    public TaskAssignment() {
    }

    public TaskAssignment(String taskId, String userId, String username, String fullName, TaskStatus status,
            String submissionLink, String submissionFilePath, Timestamp assignedAt, Timestamp completedAt) {
        this.taskId = taskId;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.status = status;
        this.submissionLink = submissionLink;
        this.submissionFilePath = submissionFilePath;
        this.assignedAt = assignedAt;
        this.completedAt = completedAt;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public String getSubmissionFilePath() {
        return submissionFilePath;
    }

    public Timestamp getAssignedAt() {
        return assignedAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public void setSubmissionFilePath(String submissionFilePath) {
        this.submissionFilePath = submissionFilePath;
    }

    public void setAssignedAt(Timestamp assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "TaskAssignment [taskId=" + taskId + ", userId=" + userId + ", username=" + username + ", fullName="
                + fullName + ", status=" + status + ", submissionLink=" + submissionLink + ", submissionFilePath="
                + submissionFilePath + ", assignedAt=" + assignedAt + ", completedAt=" + completedAt + "]";
    }
}
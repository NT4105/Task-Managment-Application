package dao;

import model.Task;
import utils.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.enums.TaskStatus;

public class TaskDAO {
    private static TaskDAO instance;

    public static TaskDAO getInstance() {
        if (instance == null) {
            instance = new TaskDAO();
        }
        return instance;
    }

    public int insert(Task task) {
        int result = 0;
        String sql = "INSERT INTO Tasks (TaskId, ProjectId, TaskName, Description, Status, DueDate, AssignedTo, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTaskId());
            ps.setString(2, task.getProjectId());
            ps.setString(3, task.getTaskName());
            ps.setString(4, task.getDescription());
            ps.setString(5, task.getStatus().toString());
            ps.setDate(6, task.getDueDate());
            ps.setString(7, task.getAssignedTo());
            ps.setDate(8, task.getCreatedAt());
            ps.setDate(9, task.getUpdatedAt());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

     // Add other necessary methods like update, delete, select, etc.
    public int update(Task task) {
        int result = 0;
        String sql = "UPDATE Tasks SET TaskName = ?, Description = ?, AssignedTo = ?, DueDate = ?, UpdatedAt = ? WHERE TaskId = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getAssignedTo());
            ps.setDate(4, task.getDueDate());
            ps.setDate(5, task.getUpdatedAt());
            ps.setString(6, task.getTaskId());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public int delete(String taskId) {
        int result = 0;
        String sql = "DELETE FROM Tasks WHERE TaskId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, taskId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Task getTaskById(String taskId) {
        Task task = null;
        String sql = "SELECT * FROM Tasks WHERE TaskId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, taskId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                task = new Task();
                task.setTaskId(rs.getString("TaskId"));
                task.setProjectId(rs.getString("ProjectId"));
                task.setTaskName(rs.getString("TaskName"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                task.setDueDate(rs.getDate("DueDate"));
                task.setAssignedTo(rs.getString("AssignedTo"));
                task.setCreatedAt(rs.getDate("CreatedAt"));
                task.setUpdatedAt(rs.getDate("UpdatedAt"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }

    public List<Task> getTasksByProject(String projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE ProjectId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getString("TaskId"));
                task.setProjectId(rs.getString("ProjectId"));
                task.setTaskName(rs.getString("TaskName"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                task.setDueDate(rs.getDate("DueDate"));
                task.setAssignedTo(rs.getString("AssignedTo"));
                task.setCreatedAt(rs.getDate("CreatedAt"));
                task.setUpdatedAt(rs.getDate("UpdatedAt"));

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTasksByUser(String userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE AssignedTo = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getString("TaskId"));
                task.setProjectId(rs.getString("ProjectId"));
                task.setTaskName(rs.getString("TaskName"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                task.setDueDate(rs.getDate("DueDate"));
                task.setAssignedTo(rs.getString("AssignedTo"));
                task.setCreatedAt(rs.getDate("CreatedAt"));
                task.setUpdatedAt(rs.getDate("UpdatedAt"));

                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}

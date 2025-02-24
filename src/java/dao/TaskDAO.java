package dao;

import model.Task;
import model.enums.TaskStatus;
import utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    // Add other necessary methods like update, delete, select, etc.
}
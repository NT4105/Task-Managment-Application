package dao;

import model.Task;
import model.TaskAssignment;
import model.enums.TaskStatus;
import utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.SecurityUtil;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO {
    private static TaskDAO instance;
    private static final Logger LOGGER = Logger.getLogger(TaskDAO.class.getName());

    public static TaskDAO getInstance() {
        if (instance == null) {
            instance = new TaskDAO();
        }
        return instance;
    }

    public int insert(Task task) {
        int result = 0;
        String sql = "INSERT INTO Tasks (TaskID, ProjectID, TaskName, Description, Status, DueDate, CreatedAt, UpdatedAt) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTaskID());
            ps.setString(2, task.getProjectID());
            ps.setString(3, task.getTaskName());
            ps.setString(4, task.getDescription());
            ps.setString(5, TaskStatus.PENDING.toString());
            ps.setDate(6, task.getDueDate());
            ps.setTimestamp(7, new Timestamp(task.getCreatedAt().getTime()));
            ps.setTimestamp(8, new Timestamp(task.getUpdatedAt().getTime()));

            result = ps.executeUpdate();

            // Insert task assignments
            if (result > 0 && task.getAssignedUsers() != null) {
                String assignSql = "INSERT INTO TaskAssignments (TaskID, UserID) VALUES (?, ?)";
                try (PreparedStatement assignPs = con.prepareStatement(assignSql)) {
                    for (String userId : task.getAssignedUsers()) {
                        assignPs.setString(1, task.getTaskID());
                        assignPs.setString(2, userId);
                        assignPs.addBatch();
                    }
                    assignPs.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int update(Task task) {
        int result = 0;
        String sql = "UPDATE Tasks SET TaskName = ?, Description = ?, DueDate = ?, UpdatedAt = ? WHERE TaskID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, task.getDueDate());
            ps.setTimestamp(4, new Timestamp(task.getUpdatedAt().getTime()));
            ps.setString(5, task.getTaskID());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Add method to get assignees for a task
    public List<String> getTaskAssignees(String taskId) {
        List<String> assignees = new ArrayList<>();
        String sql = "SELECT UserID FROM TaskAssignments WHERE TaskID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assignees.add(rs.getString("UserID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignees;
    }

    public boolean acceptTask(String taskId, String userId) {
        String sql = "INSERT INTO TaskAssignments (TaskID, UserID, Status, AssignedAt) " +
                "VALUES (?, ?, 'IN_PROGRESS', GETDATE())";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, taskId);
            ps.setString(2, userId);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            // Update task status to IN_PROGRESS
            if (ps.executeUpdate() > 0) {
                String updateTaskSql = "UPDATE Tasks SET Status = 'IN_PROGRESS' WHERE TaskID = ?";
                try (PreparedStatement updatePs = con.prepareStatement(updateTaskSql)) {
                    updatePs.setString(1, taskId);
                    updatePs.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean submitTask(String taskId, String userId, String submissionLink, String filePath) {
        String sql = "UPDATE TaskAssignments SET Status = 'COMPLETED', " +
                "SubmissionLink = ?, SubmissionFilePath = ? " +
                "WHERE TaskID = ? AND UserID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, submissionLink);
            ps.setString(2, filePath);
            ps.setString(3, taskId);
            ps.setString(4, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TaskAssignment> getTaskAssignments(String taskId) {
        List<TaskAssignment> assignments = new ArrayList<>();
        String sql = "SELECT ta.*, u.Username, CONCAT(p.FirstName, ' ', p.LastName) as FullName " +
                "FROM TaskAssignments ta " +
                "JOIN Users u ON ta.UserID = u.UserID " +
                "JOIN Profiles p ON u.UserID = p.UserID " +
                "WHERE ta.TaskID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TaskAssignment assignment = new TaskAssignment();
                    assignment.setTaskId(rs.getString("TaskID"));
                    assignment.setUserId(rs.getString("UserID"));
                    assignment.setUsername(rs.getString("Username"));
                    assignment.setFullName(rs.getString("FullName"));
                    assignment.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                    assignment.setSubmissionLink(rs.getString("SubmissionLink"));
                    assignment.setSubmissionFilePath(rs.getString("SubmissionFilePath"));
                    assignment.setAssignedAt(rs.getTimestamp("AssignedAt"));
                    assignment.setCompletedAt(rs.getTimestamp("CompletedAt"));
                    assignments.add(assignment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    public List<Task> getCompletedTasksByUser(String userId) {
        List<Task> completedTasks = new ArrayList<>();
        String sql = "SELECT t.*, ta.SubmissionLink, ta.SubmissionFilePath, ta.CompletedAt " +
                "FROM Tasks t " +
                "JOIN TaskAssignments ta ON t.TaskID = ta.TaskID " +
                "WHERE ta.UserID = ? AND ta.Status = 'COMPLETED' " +
                "ORDER BY ta.CompletedAt DESC";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task task = mapResultSetToTask(rs);
                    task.setSubmissionLink(rs.getString("SubmissionLink"));
                    task.setSubmissionFilePath(rs.getString("SubmissionFilePath"));
                    completedTasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return completedTasks;
    }

    // Get tasks with search and sort functionality
    public List<Task> getTasksWithFilters(String projectId, String searchName, String sortBy) {
        List<Task> tasks = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT t.*, u.Username, CONCAT(p.FirstName, ' ', p.LastName) as FullName " +
                        "FROM Tasks t " +
                        "LEFT JOIN Users u ON t.AssignedTo = u.UserID " +
                        "LEFT JOIN Profiles p ON u.UserID = p.UserID " +
                        "WHERE t.ProjectID = ? ");

        // Add search condition if name is provided
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append("AND t.TaskName LIKE ? ");
        }

        // Add sorting
        if ("dueDate".equals(sortBy)) {
            sql.append("ORDER BY t.DueDate ASC");
        } else if ("dueSoon".equals(sortBy)) {
            sql.append("ORDER BY CASE " +
                    "WHEN t.DueDate < GETDATE() THEN 1 " + // Overdue tasks first
                    "WHEN t.DueDate <= DATEADD(day, 3, GETDATE()) THEN 2 " + // Due within 3 days
                    "ELSE 3 END, " +
                    "t.DueDate ASC");
        }

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql.toString())) {

            ps.setString(1, projectId);
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(2, "%" + searchName + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task task = mapResultSetToTask(rs);

                    // Add assigned user info
                    List<String> assignedUsers = new ArrayList<>();
                    assignedUsers.add(rs.getString("AssignedTo"));
                    task.setAssignedUsers(assignedUsers);

                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        // Lấy dữ liệu từ các cột trong ResultSet và gán vào object Task
        task.setTaskID(rs.getString("TaskID")); // Lấy TaskID từ cột "TaskID"
        task.setProjectID(rs.getString("ProjectID")); // Lấy ProjectID từ cột "ProjectID"
        task.setTaskName(rs.getString("TaskName")); // Lấy TaskName từ cột "TaskName"
        task.setDescription(rs.getString("Description")); // Lấy Description từ cột "Description"
        task.setStatus(TaskStatus.valueOf(rs.getString("Status"))); // Chuyển đổi Status thành enum
        task.setDueDate(rs.getDate("DueDate")); // Lấy DueDate từ cột "DueDate"
        task.setCreatedAt(rs.getTimestamp("CreatedAt")); // Lấy CreatedAt từ cột "CreatedAt"
        task.setUpdatedAt(rs.getTimestamp("UpdatedAt")); // Lấy UpdatedAt từ cột "UpdatedAt"
        return task;
    }

    public boolean deleteTask(String taskId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // First delete the task ID mapping
            String deleteMappingSQL = "DELETE FROM TaskIdMapping WHERE TaskID = ?";
            ps = con.prepareStatement(deleteMappingSQL);
            ps.setString(1, taskId);
            ps.executeUpdate();

            // Then delete the task
            String deleteTaskSQL = "DELETE FROM Tasks WHERE TaskID = ?";
            ps = con.prepareStatement(deleteTaskSQL);
            ps.setString(1, taskId);
            int result = ps.executeUpdate();

            con.commit();
            return result > 0;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Error rolling back transaction", ex);
                }
            }
            LOGGER.log(Level.SEVERE, "Error deleting task", e);
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error closing connection", e);
                }
            }
        }
    }

    // Dùng để lấy task từ taskID, kiểm tra xem task có tồn tại không
    public Task selectById(String taskId) {
        String sql = "SELECT t.*, p.ProjectName " +
                "FROM Tasks t " +
                "JOIN Projects p ON t.ProjectID = p.ProjectID " +
                "WHERE t.TaskID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Task task = new Task();
                    task.setTaskID(rs.getString("TaskID"));
                    task.setProjectID(rs.getString("ProjectID"));
                    task.setTaskName(rs.getString("TaskName"));
                    task.setDescription(rs.getString("Description"));
                    task.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                    task.setDueDate(rs.getDate("DueDate"));
                    task.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    task.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    task.setProjectName(rs.getString("ProjectName"));

                    // Set encoded task ID
                    String encodedId = getEncodedTaskId(task.getTaskID());
                    if (encodedId == null) {
                        encodedId = SecurityUtil.encodeTaskId(task.getTaskID());
                        saveIdTaskMapping(task.getTaskID(), encodedId);
                    }
                    task.setEncodedTaskId(encodedId);

                    return task;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting task by ID", e);
        }
        return null;
    }

    public String getEncodedTaskId(String taskId) {
        String sql = "SELECT EncodedTaskID FROM TaskIdMapping WHERE TaskID = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, taskId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("EncodedTaskID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOriginalTaskId(String encodedTaskId) {
        String sql = "SELECT TaskID FROM TaskIdMapping WHERE EncodedTaskID = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, encodedTaskId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TaskID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveIdTaskMapping(String taskId, String encodedTaskId) {
        String sql = "INSERT INTO TaskIdMapping (TaskID, EncodedTaskID) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, taskId);
            ps.setString(2, encodedTaskId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Task> getTasksByProject(String projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Tasks WHERE ProjectID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setTaskID(rs.getString("TaskID"));
                task.setProjectID(rs.getString("ProjectID"));
                task.setTaskName(rs.getString("TaskName"));
                task.setDescription(rs.getString("Description"));
                task.setStatus(TaskStatus.valueOf(rs.getString("Status")));
                task.setDueDate(rs.getDate("DueDate"));
                task.setCreatedAt(rs.getTimestamp("CreatedAt"));
                task.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                // Set encoded ID
                task.setEncodedTaskId(SecurityUtil.encodeTaskId(task.getTaskID()));

                tasks.add(task);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading tasks", e);
        }
        return tasks;
    }
}
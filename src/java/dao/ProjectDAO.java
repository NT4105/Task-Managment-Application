package dao;

import model.Project;
import model.ProjectMember;
import utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;
import utils.SecurityUtil;
import java.util.Base64;

public class ProjectDAO {
    private static ProjectDAO instance;
    private static final String SALT = "your-secret-salt";
    private static final Logger LOGGER = Logger.getLogger(ProjectDAO.class.getName());

    public static ProjectDAO getInstance() {
        if (instance == null) {
            instance = new ProjectDAO();
        }
        return instance;
    }

    public int insert(Project project) {
        String sql = "INSERT INTO Projects (ProjectID, ProjectName, Description, StartDate, EndDate, ManagerID, CreatedAt, UpdatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, project.getProjectID());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getDescription());
            ps.setDate(4, project.getStartDate());
            ps.setDate(5, project.getEndDate());
            ps.setString(6, project.getManagerId());
            ps.setTimestamp(7, project.getCreatedAt());
            ps.setTimestamp(8, project.getUpdatedAt());
            int result = ps.executeUpdate();
            if (result > 0) {
                String encodedId = SecurityUtil.encodeProjectId(project.getProjectID());
                saveIdProjectMapping(project.getProjectID(), encodedId);
                project.setEncodedProjectId(encodedId);
            }
            return result;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating project", e);
            return 0;
        }
    }

    public List<Project> getProjectsWithTaskInfo(String managerId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.*, " +
                "(SELECT COUNT(*) FROM Tasks t WHERE t.ProjectID = p.ProjectID) as TotalTasks, " +
                "(SELECT COUNT(*) FROM Tasks t WHERE t.ProjectID = p.ProjectID AND t.Status = 'COMPLETED') as CompletedTasks "
                +
                "FROM Projects p WHERE p.ManagerID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setProjectID(rs.getString("ProjectID"));
                project.setProjectName(rs.getString("ProjectName"));
                project.setDescription(rs.getString("Description"));
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                project.setManagerId(rs.getString("ManagerID"));
                project.setTotalTasks(rs.getInt("TotalTasks"));
                project.setCompletedTasks(rs.getInt("CompletedTasks"));

                // Thêm encoded ID
                String encodedId = getEncodedProjectId(project.getProjectID());
                if (encodedId == null) {
                    encodedId = SecurityUtil.encodeProjectId(project.getProjectID());
                    saveIdProjectMapping(project.getProjectID(), encodedId);
                }
                project.setEncodedProjectId(encodedId);

                projects.add(project);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting projects with task info", e);
        }
        return projects;
    }

    public boolean addProjectMembers(String projectId, List<String> memberIds) {
        String sql = "INSERT INTO ProjectMembers (ProjectID, UserID, JoinedAt) VALUES (?, ?, GETDATE())";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            for (String memberId : memberIds) {
                ps.setString(1, projectId);
                ps.setString(2, memberId);
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            return results.length == memberIds.size();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách project bao gồm tất cả tasks
    public List<Project> getProjectsByManager(String managerId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Projects WHERE ManagerID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                String projectId = rs.getString("ProjectID");
                project.setProjectID(projectId);
                project.setProjectName(rs.getString("ProjectName"));
                project.setDescription(rs.getString("Description"));
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                project.setManagerId(rs.getString("ManagerID"));
                project.setCreatedAt(rs.getTimestamp("CreatedAt"));
                project.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                // Get or create encoded ID
                String encodedId = getEncodedProjectId(projectId);
                if (encodedId == null) {
                    encodedId = Base64.getUrlEncoder()
                            .encodeToString((SALT + projectId).getBytes());
                    if (saveIdProjectMapping(projectId, encodedId)) {
                        LOGGER.info("Created and saved new encoded ID for project " + projectId);
                    }
                }
                project.setEncodedProjectId(encodedId);

                projects.add(project);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error getting projects by manager: " + e.getMessage());
        }
        return projects;
    }

    public boolean deleteProject(String projectId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = JDBCUtil.getConnection();
            con.setAutoCommit(false);

            // 1. Xóa tasks trước
            String deleteTasksSQL = "DELETE FROM Tasks WHERE ProjectID = ?";
            ps = con.prepareStatement(deleteTasksSQL);
            ps.setString(1, projectId);
            ps.executeUpdate();

            // 2. Xóa project members
            String deleteMembersSQL = "DELETE FROM ProjectMembers WHERE ProjectID = ?";
            ps = con.prepareStatement(deleteMembersSQL);
            ps.setString(1, projectId);
            ps.executeUpdate();

            // 3. Xóa project ID mapping
            String deleteMappingSQL = "DELETE FROM ProjectIdMapping WHERE ProjectID = ?";
            ps = con.prepareStatement(deleteMappingSQL);
            ps.setString(1, projectId);
            ps.executeUpdate();

            // 4. Cuối cùng mới xóa project
            String deleteProjectSQL = "DELETE FROM Projects WHERE ProjectID = ?";
            ps = con.prepareStatement(deleteProjectSQL);
            ps.setString(1, projectId);
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
            LOGGER.log(Level.SEVERE, "Error deleting project", e);
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error closing PreparedStatement", e);
                }
            }
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error closing Connection", e);
                }
            }
        }
    }

    public boolean update(Project project) {
        String sql = "UPDATE Projects SET ProjectName=?, Description=?, StartDate=?, EndDate=?, UpdatedAt=? WHERE ProjectID=?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            LOGGER.info("Updating project: " + project.getProjectID());

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, project.getStartDate());
            ps.setDate(4, project.getEndDate());
            ps.setTimestamp(5, project.getUpdatedAt());
            ps.setString(6, project.getProjectID());

            int result = ps.executeUpdate();
            LOGGER.info("Update result: " + result);

            return result > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error updating project: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProjectMembers(String projectId, List<String> memberIds) {
        try (Connection con = JDBCUtil.getConnection()) {
            // First delete existing members
            String deleteSql = "DELETE FROM ProjectMembers WHERE ProjectID = ?";
            try (PreparedStatement deletePs = con.prepareStatement(deleteSql)) {
                deletePs.setString(1, projectId);
                deletePs.executeUpdate();
            }

            // Then insert new members if any
            if (memberIds != null && !memberIds.isEmpty()) {
                String insertSql = "INSERT INTO ProjectMembers (ProjectID, UserID, JoinedAt) VALUES (?, ?, GETDATE())";
                try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                    for (String memberId : memberIds) {
                        insertPs.setString(1, projectId);
                        insertPs.setString(2, memberId);
                        insertPs.addBatch();
                    }
                    insertPs.executeBatch();
                }
            }
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error updating project members: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Project getProjectById(String projectId) {
        String sql = "SELECT * FROM Projects WHERE ProjectID = ?";
        Project project = null;

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setProjectID(rs.getString("ProjectID"));
                project.setProjectName(rs.getString("ProjectName"));
                project.setDescription(rs.getString("Description"));
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                project.setManagerId(rs.getString("ManagerID"));
                project.setCreatedAt(rs.getTimestamp("CreatedAt"));
                project.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

                // Get project members
                project.setProjectMembers(getProjectMembers(projectId));

                // Get project tasks count
                project.setTotalTasks(getProjectTasksCount(projectId));
                project.setCompletedTasks(getCompletedTasksCount(projectId));

                // Set encoded project ID
                String encodedId = getEncodedProjectId(projectId);
                project.setEncodedProjectId(encodedId);

                return project;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error getting project by ID: " + e.getMessage());
        }
        return null;
    }

    private List<ProjectMember> getProjectMembers(String projectId) {
        List<ProjectMember> members = new ArrayList<>();
        String sql = "SELECT pm.*, u.UserID, p.FirstName, p.LastName, pm.JoinedAt " +
                "FROM ProjectMembers pm " +
                "JOIN Users u ON pm.UserID = u.UserID " +
                "JOIN Profiles p ON u.UserID = p.UserID " +
                "WHERE pm.ProjectID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProjectMember member = new ProjectMember();
                member.setUserId(rs.getString("UserID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                member.setJoinedAt(rs.getDate("JoinedAt"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    private int getProjectTasksCount(String projectId) {
        String sql = "SELECT COUNT(*) FROM Tasks WHERE ProjectID = ?";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCompletedTasksCount(String projectId) {
        String sql = "SELECT COUNT(*) FROM Tasks WHERE ProjectID = ? AND Status = 'COMPLETED'";
        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getEncodedProjectId(String projectId) {
        String sql = "SELECT EncodedProjectID FROM ProjectIdMapping WHERE ProjectID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String encodedId = rs.getString("EncodedProjectID");
                LOGGER.info("Found existing encoded ID for project " + projectId + ": " + encodedId);
                return encodedId;
            } else {
                LOGGER.info("No existing encoded ID found for project " + projectId + ", will create new one");
                return null;
            }
        } catch (SQLException e) {
            LOGGER.severe("Error getting encoded project ID: " + e.getMessage());
            return null;
        }
    }

    public String getOriginalProjectId(String encodedId) {
        String sql = "SELECT ProjectID FROM ProjectIdMapping WHERE EncodedProjectID = ?";

        try (Connection con = JDBCUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, encodedId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("ProjectID");
            }
            return null;
        } catch (SQLException e) {
            LOGGER.severe("Error getting original project ID: " + e.getMessage());
            return null;
        }
    }

    public boolean saveIdProjectMapping(String projectId, String encodedId) {
        // First check if mapping exists
        String existingEncodedId = getEncodedProjectId(projectId);

        if (existingEncodedId != null) {
            if (existingEncodedId.equals(encodedId)) {
                LOGGER.info("Mapping already exists for project " + projectId);
                return true; // Mapping already exists with same encoded ID
            }
            // Update existing mapping
            String updateSql = "UPDATE ProjectIdMapping SET EncodedProjectID = ? WHERE ProjectID = ?";
            try (Connection con = JDBCUtil.getConnection();
                    PreparedStatement ps = con.prepareStatement(updateSql)) {

                ps.setString(1, encodedId);
                ps.setString(2, projectId);
                int result = ps.executeUpdate();
                LOGGER.info("Updated mapping for project " + projectId);
                return result > 0;
            } catch (SQLException e) {
                LOGGER.severe("Error updating project ID mapping: " + e.getMessage());
                return false;
            }
        } else {
            // Insert new mapping
            String insertSql = "INSERT INTO ProjectIdMapping (ProjectID, EncodedProjectID) VALUES (?, ?)";
            try (Connection con = JDBCUtil.getConnection();
                    PreparedStatement ps = con.prepareStatement(insertSql)) {

                ps.setString(1, projectId);
                ps.setString(2, encodedId);
                int result = ps.executeUpdate();
                LOGGER.info("Inserted new mapping for project " + projectId);
                return result > 0;
            } catch (SQLException e) {
                LOGGER.severe("Error inserting project ID mapping: " + e.getMessage());
                return false;
            }
        }
    }
}
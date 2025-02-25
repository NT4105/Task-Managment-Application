package dao;

import model.Project;
import model.Task;
import utils.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ChanRoi
 */
public class ProjectDAO {
    private static ProjectDAO instance;

    public static ProjectDAO getInstance() {
        if (instance == null) {
            instance = new ProjectDAO();
        }
        return instance;
    }

    public int insert(Project project) {
        int result = 0;
        String sql = "INSERT INTO Projects (ProjectId, ProjectName, Description, ManagerId, StartDate, EndDate, CreatedAt, UpdatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, project.getprojectID());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getManagerId());
            ps.setDate(5, project.getStartDate());
            ps.setDate(6, project.getEndDate());
            ps.setDate(7, project.getCreatedAt());
            ps.setDate(8, project.getUpdatedAt());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int update(Project project) {
        int result = 0;
        String sql = "UPDATE Projects SET ProjectName = ?, Description = ?, StartDate = ?, EndDate = ?, UpdatedAt = ? WHERE ProjectId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, project.getStartDate());
            ps.setDate(4, project.getEndDate());
            ps.setDate(5, project.getUpdatedAt());
            ps.setString(6, project.getprojectID());

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete(String projectId) {
        int result = 0;
        String sql = "DELETE FROM Projects WHERE ProjectId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projectId);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Project getProjectById(String projectId) {
        Project project = null;
        String sql = "SELECT * FROM Projects WHERE ProjectId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, projectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                project = new Project();
                project.setprojectID(rs.getString("ProjectId"));
                project.setProjectName(rs.getString("ProjectName"));
                project.setDescription(rs.getString("Description"));
                project.setManagerId(rs.getString("ManagerId"));
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                project.setCreatedAt(rs.getDate("CreatedAt"));
                project.setUpdatedAt(rs.getDate("UpdatedAt"));

                List<Task> tasks = TaskDAO.getInstance().getTasksByProject(projectId);
                project.setTasks(tasks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }

    public List<Project> getProjectsByManager(String managerId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Projects WHERE ManagerId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, managerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setprojectID(rs.getString("ProjectId"));
                project.setProjectName(rs.getString("ProjectName"));
                project.setDescription(rs.getString("Description"));
                project.setManagerId(rs.getString("ManagerId"));
                project.setStartDate(rs.getDate("StartDate"));
                project.setEndDate(rs.getDate("EndDate"));
                project.setCreatedAt(rs.getDate("CreatedAt"));
                project.setUpdatedAt(rs.getDate("UpdatedAt"));

                List<Task> tasks = TaskDAO.getInstance().getTasksByProject(project.getprojectID());
                project.setTasks(tasks);

                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
}

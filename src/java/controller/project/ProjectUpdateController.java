package controller.project;

import utils.SecurityUtil;
import dao.ProjectDAO;
import dao.UserDAO;
import model.Project;
import model.User;
import model.enums.UserRole;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

@WebServlet("/project/update")
public class ProjectUpdateController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProjectUpdateController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute("userRole");

        if (session == null || !"MANAGER".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedProjectId = request.getParameter("id");
        if (encodedProjectId == null || encodedProjectId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
            if (projectId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Project project = ProjectDAO.getInstance().getProjectById(projectId);
            if (project == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            List<User> availableMembers = UserDAO.getInstance().getAllMembers();

            request.setAttribute("project", project);
            request.setAttribute("availableMembers", availableMembers);
            request.getRequestDispatcher("/static/project/update-project.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in update project GET", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute("userRole");

        if (session == null || !"MANAGER".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedProjectId = request.getParameter("projectId");
        LOGGER.info("Received encoded project ID: " + encodedProjectId);

        try {
            String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
            LOGGER.info("Decoded project ID: " + projectId);

            if (projectId == null) {
                LOGGER.warning("Failed to decode project ID");
                request.setAttribute("error", "Invalid project ID");
                doGet(request, response);
                return;
            }

            // Get existing project first
            Project existingProject = ProjectDAO.getInstance().getProjectById(projectId);
            if (existingProject == null) {
                LOGGER.warning("Project not found: " + projectId);
                request.setAttribute("error", "Project not found");
                doGet(request, response);
                return;
            }

            // Update project with new values
            existingProject.setProjectName(request.getParameter("projectName"));
            existingProject.setDescription(request.getParameter("description"));
            existingProject.setStartDate(Date.valueOf(request.getParameter("startDate")));
            existingProject.setEndDate(Date.valueOf(request.getParameter("endDate")));
            existingProject.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            LOGGER.info("Updating project: " + existingProject.getProjectName());

            // Update project
            if (ProjectDAO.getInstance().update(existingProject)) {
                // Update project members
                String[] selectedMembers = request.getParameterValues("selectedMembers");
                List<String> memberIds = selectedMembers != null ? Arrays.asList(selectedMembers) : new ArrayList<>();

                if (ProjectDAO.getInstance().updateProjectMembers(projectId, memberIds)) {
                    LOGGER.info("Project and members updated successfully");
                    session.setAttribute("successMessage", "Project updated successfully!");
                    response.sendRedirect(request.getContextPath() + "/project/view?id=" + encodedProjectId);
                    return;
                }
            }

            LOGGER.warning("Failed to update project");
            session.setAttribute("errorMessage", "Failed to update project");
            response.sendRedirect(request.getContextPath() + "/project/view?id=" + encodedProjectId);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating project", e);
            request.setAttribute("error", "Failed to update project: " + e.getMessage());
            doGet(request, response);
        }
    }
}
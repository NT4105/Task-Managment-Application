package controller.project;

import dao.ProjectDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.enums.UserRole;
import utils.SecurityUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/project/delete")
public class ProjectDeleteController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProjectDeleteController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute("userRole");

        // Kiểm tra quyền Manager
        if (session == null || !UserRole.MANAGER.name().equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedProjectId = request.getParameter("projectId");
        if (encodedProjectId == null || encodedProjectId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
            if (projectId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (ProjectDAO.getInstance().deleteProject(projectId)) {
                response.setStatus(HttpServletResponse.SC_OK);
                session.setAttribute("successMessage", "Project deleted successfully!");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                session.setAttribute("errorMessage", "Failed to delete project");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting project", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            session.setAttribute("errorMessage", "Error deleting project: " + e.getMessage());
        }
    }

}
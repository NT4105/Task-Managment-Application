package controller.project;

import dao.ProjectDAO;
import dao.UserDAO;
import model.Project;
import model.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.SecurityUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/project/create")
public class ProjectCreateController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProjectCreateController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String managerId = (String) session.getAttribute("userId");

        // Get available team members for selection (only MEMBER role)
        List<User> availableMembers = UserDAO.getInstance().getAllMembers();
        request.setAttribute("availableMembers", availableMembers);
        request.getRequestDispatcher("/static/project/create-project.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String managerId = (String) session.getAttribute("userId");

        try {
            // Get form data
            String projectName = request.getParameter("projectName");
            String description = request.getParameter("description");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String[] selectedMembers = request.getParameterValues("selectedMembers");

            // Validate required fields
            if (projectName == null || projectName.trim().isEmpty() ||
                    description == null || description.trim().isEmpty() ||
                    startDateStr == null || startDateStr.trim().isEmpty() ||
                    endDateStr == null || endDateStr.trim().isEmpty()) {

                request.setAttribute("error", "Project name, description, start date and end date are required");
                request.setAttribute("projectName", projectName);
                request.setAttribute("description", description);
                request.setAttribute("startDate", startDateStr);
                request.setAttribute("endDate", endDateStr);

                // Get available team members again for the form
                List<User> availableMembers = UserDAO.getInstance().getAllMembers();
                request.setAttribute("availableMembers", availableMembers);

                request.getRequestDispatcher("/static/project/create-project.jsp").forward(request, response);
                return;
            }

            // Create new project object
            Project project = new Project();
            project.setProjectID(UUID.randomUUID().toString());
            project.setProjectName(projectName);
            project.setDescription(description);
            project.setStartDate(Date.valueOf(startDateStr));
            project.setEndDate(Date.valueOf(endDateStr));
            project.setManagerId(managerId);
            project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            if (ProjectDAO.getInstance().insert(project) > 0) {
                // Update project members if needed
                if (selectedMembers != null) {
                    ProjectDAO.getInstance().updateProjectMembers(project.getProjectID(),
                            Arrays.asList(selectedMembers));
                }

                // Add success message
                request.getSession().setAttribute("successMessage", "Project created successfully!");
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to create project");
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating project", e);
            request.getSession().setAttribute("errorMessage", "Error creating project: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}

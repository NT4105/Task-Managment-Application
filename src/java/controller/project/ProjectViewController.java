package controller.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectDAO;
import dao.TaskDAO;
import model.Project;
import model.Task;
import utils.SecurityUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/project/view")
public class ProjectViewController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProjectViewController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Log full request details
        LOGGER.info("Full request URL: " + req.getRequestURL().toString());
        LOGGER.info("Query string: " + req.getQueryString());

        String encodedProjectId = req.getParameter("id");
        LOGGER.log(Level.INFO, "Received encoded project ID: {0}", encodedProjectId);

        if (encodedProjectId == null || encodedProjectId.trim().isEmpty()) {
            LOGGER.warning("Project ID is missing or empty");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
        LOGGER.log(Level.INFO, "Decoded project ID: {0}", projectId);

        if (projectId == null) {
            LOGGER.warning("Failed to decode project ID");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Project project = ProjectDAO.getInstance().getProjectById(projectId);
        if (project == null) {
            LOGGER.warning("Project not found");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Format description before sending to view
        if (project.getDescription() != null) {
            String formattedDesc = project.getDescription()
                    .replace("- ", "\n- ")
                    .replace("+ ", "\n+ ");
            project.setDescription(formattedDesc);
        }

        // Set the encoded ID for use in JSP
        project.setEncodedProjectId(encodedProjectId);

        // Load tasks with encoded IDs
        List<Task> tasks = TaskDAO.getInstance().getTasksByProject(projectId);
        for (Task task : tasks) {
            task.setEncodedTaskId(SecurityUtil.encodeTaskId(task.getTaskID()));
        }

        // Set attributes for view
        req.setAttribute("project", project);
        req.setAttribute("tasks", tasks);

        req.getRequestDispatcher("/static/project/view-project.jsp").forward(req, resp);
    }
}
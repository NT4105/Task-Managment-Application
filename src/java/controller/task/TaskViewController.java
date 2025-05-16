package controller.task;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import model.Task;
import dao.TaskDAO;
import model.enums.UserRole;
import utils.SecurityUtil;
import model.Project;
import dao.ProjectDAO;

@WebServlet("/task/details")
public class TaskViewController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(TaskViewController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String encodedTaskId = request.getParameter("id");
        LOGGER.info("Received encoded task ID: " + encodedTaskId);

        if (encodedTaskId == null || encodedTaskId.trim().isEmpty()) {
            LOGGER.warning("Task ID is missing or empty");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String taskId = SecurityUtil.decodeTaskId(encodedTaskId);
        LOGGER.info("Decoded task ID: " + taskId);

        if (taskId == null) {
            LOGGER.warning("Failed to decode task ID");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        Task task = TaskDAO.getInstance().selectById(taskId);
        if (task == null) {
            LOGGER.warning("Task not found");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Get project information for the back button
        Project project = ProjectDAO.getInstance().getProjectById(task.getProjectID());
        if (project == null) {
            LOGGER.warning("Project not found for task ID: " + task.getProjectID());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        } else {
            LOGGER.info("Found project: " + project.getProjectID() + " for task: " + task.getTaskID());
            project.setEncodedProjectId(SecurityUtil.encodeProjectId(project.getProjectID()));
        }

        // Set encoded ID for use in JSP
        task.setEncodedTaskId(encodedTaskId);

        request.setAttribute("task", task);
        request.setAttribute("project", project);
        request.getRequestDispatcher("/static/task/task-details.jsp").forward(request, response);
    }
}
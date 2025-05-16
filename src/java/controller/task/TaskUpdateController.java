package controller.task;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.TaskDAO;
import model.Task;
import model.User;
import utils.Validation;
import java.sql.Date;
import java.sql.Timestamp;
import dao.UserDAO;
import utils.SecurityUtil;
import java.util.logging.Logger;
import dao.ProjectDAO;
import model.Project;

@WebServlet("/task/update")
public class TaskUpdateController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(TaskUpdateController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"MANAGER".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

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
        if (project != null) {
            project.setEncodedProjectId(SecurityUtil.encodeProjectId(project.getProjectID()));
        }

        // Set encoded ID for use in JSP
        task.setEncodedTaskId(encodedTaskId);

        request.setAttribute("task", task);
        request.setAttribute("project", project);
        request.getRequestDispatcher("/static/task/update-task.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"MANAGER".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedTaskId = request.getParameter("id");
        String taskId = SecurityUtil.decodeTaskId(encodedTaskId);

        if (taskId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Task task = TaskDAO.getInstance().selectById(taskId);
        if (task == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Get project information for redirect
        Project project = ProjectDAO.getInstance().getProjectById(task.getProjectID());
        if (project != null) {
            project.setEncodedProjectId(SecurityUtil.encodeProjectId(project.getProjectID()));
            request.setAttribute("project", project);
        }

        // Update only allowed fields
        task.setTaskName(request.getParameter("taskName"));
        task.setDescription(request.getParameter("description"));
        task.setDueDate(java.sql.Date.valueOf(request.getParameter("dueDate")));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (TaskDAO.getInstance().update(task) > 0) {
            request.getSession().setAttribute("successMessage", "Task updated successfully!");
            response.sendRedirect(request.getContextPath() + "/task/details?id=" + encodedTaskId);
        } else {
            request.setAttribute("error", "Failed to update task");
            request.setAttribute("task", task);
            request.getRequestDispatcher("/static/task/update-task.jsp").forward(request, response);
        }
    }
}

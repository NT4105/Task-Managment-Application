package controller.task;

import dao.TaskDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.SecurityUtil;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/task/delete")
public class TaskDeleteController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(TaskDeleteController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute("userRole");

        if (session == null || !"MANAGER".equals(userRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedTaskId = request.getParameter("taskId");
        LOGGER.log(Level.INFO, "Attempting to delete task with encoded ID: {0}", encodedTaskId);

        if (encodedTaskId == null || encodedTaskId.trim().isEmpty()) {
            LOGGER.warning("Task ID is missing or empty");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String taskId = SecurityUtil.decodeTaskId(encodedTaskId);
        if (taskId == null) {
            LOGGER.warning("Failed to decode task ID");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if (TaskDAO.getInstance().deleteTask(taskId)) {
                session.setAttribute("successMessage", "Task deleted successfully!");
            } else {
                session.setAttribute("errorMessage", "Failed to delete task");
            }
            response.sendRedirect(request.getContextPath() + "/project/view?id=" + request.getParameter("projectId"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting task", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
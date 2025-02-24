package controller.task;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.TaskDAO;
import model.Task;
import model.User;
import utils.Validation;
import java.sql.Date;
import dao.UserDAO;

@WebServlet(name = "TaskEditController", urlPatterns = { "/TaskEditController" })
public class TaskUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in and is a manager
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);

        if (user == null || !user.getRole().equals("Manager")) {
            response.sendRedirect("home");
            return;
        }

        String taskId = request.getParameter("id");
        Task task = TaskDAO.getInstance().selectById(taskId);

        if (task == null) {
            request.setAttribute("error", "Task not found");
            request.getRequestDispatcher("/static/task/task-list.jsp").forward(request, response);
            return;
        }

        request.setAttribute("task", task);
        request.setAttribute("teamMembers", UserDAO.getInstance().getAllTeamMembers());
        request.getRequestDispatcher("/static/task/update-task.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in and is a manager
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);

        if (user == null || !user.getRole().equals("Manager")) {
            response.sendRedirect("home");
            return;
        }

        // Get form data
        String taskId = request.getParameter("taskId");
        String taskName = request.getParameter("taskName");
        String description = request.getParameter("description");
        String assignedTo = request.getParameter("assignedTo");
        String dueDateStr = request.getParameter("dueDate");

        // Validate input
        if (!Validation.isValidDate(dueDateStr)) {
            request.setAttribute("error", "Invalid date format");
            doGet(request, response);
            return;
        }

        // Update task
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);
        task.setDueDate(Date.valueOf(dueDateStr));
        task.setUpdatedAt(new Date(System.currentTimeMillis()));

        // Save updated task to database
        if (TaskDAO.getInstance().update(task) > 0) {
            response.sendRedirect("project-details?id=" + task.getProjectId());
        } else {
            request.setAttribute("error", "Failed to update task");
            doGet(request, response);
        }
    }

}

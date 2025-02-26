package controller.task;

import dao.ProjectDAO;
import dao.TaskDAO;
import dao.UserDAO;
import model.Task;
import model.User;
import model.enums.TaskStatus;
import utils.Validation;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/create-task")
public class TaskCreateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in and is a manager
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        // Forward to create task page
        request.getRequestDispatcher("/static/task/create-task.jsp").forward(request, response);
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
        String projectID = request.getParameter("projectID");
        String taskName = request.getParameter("taskName");
        String description = request.getParameter("description");
        String assignedUsers = request.getParameter("assignedUsers");
        String dueDateStr = request.getParameter("dueDate");

        // Validate input
        if (!Validation.isValidDate(dueDateStr)) {
            request.setAttribute("error", "Invalid date format");
            doGet(request, response);
            return;
        }

        // Create new task
        Task task = new Task();
        task.setTaskID(UUID.randomUUID().toString());
        task.setProjectID(projectID);
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(Date.valueOf(dueDateStr));
        task.getAssignedUsers().add(assignedUsers);
        task.setCreatedAt(new Date(System.currentTimeMillis()));
        task.setUpdatedAt(new Date(System.currentTimeMillis()));

        // Save task to database
        if (TaskDAO.getInstance().insert(task) > 0) {
            response.sendRedirect("project-details?id=" + projectID);
        } else {
            request.setAttribute("error", "Failed to create task");
            doGet(request, response);
        }
    }
}

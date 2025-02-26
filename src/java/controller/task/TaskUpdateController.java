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

@WebServlet("/update-task")
public class TaskUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskID = request.getParameter("id");

        // Get existing task
        Task task = TaskDAO.getInstance().selectById(taskID);
        if (task == null) {
            response.sendRedirect("home");
            return;
        }

        request.setAttribute("task", task);
        request.getRequestDispatcher("/static/task/update-task.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters
        String taskID = request.getParameter("id");
        String taskName = request.getParameter("taskName");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");

        // Validate input
        if (!Validation.isValidTaskName(taskName) ||
                !Validation.isValidTaskDueDate(dueDateStr)) {
            request.setAttribute("error", "Invalid input data");
            doGet(request, response);
            return;
        }

        // Get existing task to preserve status and other fields
        Task existingTask = TaskDAO.getInstance().selectById(taskID);
        if (existingTask == null) {
            response.sendRedirect("home");
            return;
        }

        // Update only allowed fields
        existingTask.setTaskName(taskName);
        existingTask.setDescription(description);
        existingTask.setDueDate(Date.valueOf(dueDateStr));
        existingTask.setUpdatedAt(new Date(System.currentTimeMillis()));

        // Save updated task to database
        if (TaskDAO.getInstance().update(existingTask) > 0) {
            response.sendRedirect("project-details?id=" + existingTask.getProjectID());
        } else {
            request.setAttribute("error", "Failed to update task");
            doGet(request, response);
        }
    }

}

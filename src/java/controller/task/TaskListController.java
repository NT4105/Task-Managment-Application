package controller.task;

import dao.TaskDAO;
import model.Task;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/task/list")
public class TaskListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get filter parameters
        String projectId = request.getParameter("projectId");
        String searchName = request.getParameter("searchName");
        String sortBy = request.getParameter("sortBy");

        // Validate project ID
        if (projectId == null || projectId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Get filtered tasks
        List<Task> tasks = TaskDAO.getInstance().getTasksWithFilters(projectId, searchName, sortBy);

        // Set attributes for JSP
        request.setAttribute("tasks", tasks);
        request.setAttribute("projectId", projectId);

        // Forward to JSP
        request.getRequestDispatcher("/static/task/task-list.jsp").forward(request, response);
    }
}

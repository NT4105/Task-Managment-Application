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
import java.sql.Timestamp;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.SecurityUtil;
import java.util.logging.Logger;
import model.Project;

@WebServlet("/task/create")
public class TaskCreateController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(TaskCreateController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"MANAGER".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String encodedProjectId = request.getParameter("projectId");
        if (encodedProjectId == null || encodedProjectId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
        if (projectId == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Get project to verify it exists
        Project project = ProjectDAO.getInstance().getProjectById(projectId);
        if (project == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Get project members instead of all members
        request.setAttribute("projectMembers", project.getProjectMembers());
        request.setAttribute("encodedProjectId", encodedProjectId);
        request.setAttribute("project", project); // Add project for breadcrumb/navigation
        request.getRequestDispatcher("/static/task/create-task.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String encodedProjectId = request.getParameter("projectId");
        String taskName = request.getParameter("taskName");
        String description = request.getParameter("description");
        String[] assignedUsers = request.getParameterValues("assignedUsers");
        String dueDateStr = request.getParameter("dueDate");

        // Validation
        List<String> errors = new ArrayList<>();

        if (taskName == null || taskName.trim().isEmpty()) {
            errors.add("Task name is required");
        }

        if (description == null || description.trim().isEmpty()) {
            errors.add("Description is required");
        }

        if (assignedUsers == null || assignedUsers.length == 0) {
            errors.add("Please assign at least one team member");
        }

        // Validate due date
        try {
            Date dueDate = Date.valueOf(dueDateStr);
            Date today = new Date(System.currentTimeMillis());
            if (dueDate.before(today)) {
                errors.add("Due date cannot be in the past");
            }
        } catch (IllegalArgumentException e) {
            errors.add("Invalid due date format");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("taskName", taskName);
            request.setAttribute("description", description);
            request.setAttribute("dueDate", dueDateStr);
            request.setAttribute("selectedUsers", assignedUsers);
            doGet(request, response);
            return;
        }

        // Create task logic here...
        String projectId = SecurityUtil.decodeProjectId(encodedProjectId);
        Task task = new Task();
        task.setTaskID(UUID.randomUUID().toString());
        task.setProjectID(projectId);
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setDueDate(Date.valueOf(dueDateStr));
        task.setStatus(TaskStatus.PENDING);
        task.setAssignedUsers(Arrays.asList(assignedUsers));
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (TaskDAO.getInstance().insert(task) > 0) {
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Task created successfully!");
            response.sendRedirect(request.getContextPath() + "/project/view?id=" + encodedProjectId);
        } else {
            errors.add("Failed to create task");
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}

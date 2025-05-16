package controller.auth;

import dao.ProjectDAO;
import dao.UserDAO;
import model.Project;
import model.User;
import model.enums.UserRole;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeController extends HttpServlet {

        private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                HttpSession session = request.getSession(false);
                String userRole = (String) session.getAttribute("userRole");
                String userId = (String) session.getAttribute("userId");

                List<Project> projects;
                if (UserRole.MANAGER.name().equals(userRole)) {
                        projects = ProjectDAO.getInstance().getProjectsWithTaskInfo(userId);
                        LOGGER.info("Found " + projects.size() + " projects for manager");
                        for (Project project : projects) {
                                LOGGER.info("Project ID: " + project.getProjectID() +
                                                ", Name: " + project.getProjectName());
                        }
                } else {
                        projects = ProjectDAO.getInstance().getProjectsWithTaskInfo(userId);
                }

                // Filter projects if search parameters are present
                String searchQuery = request.getParameter("searchQuery");
                String status = request.getParameter("status");

                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                        projects = projects.stream()
                                        .filter(p -> p.getProjectName().toLowerCase()
                                                        .contains(searchQuery.toLowerCase()) ||
                                                        p.getDescription().toLowerCase()
                                                                        .contains(searchQuery.toLowerCase()))
                                        .collect(Collectors.toList());
                }

                if (status != null && !status.isEmpty()) {
                        projects = projects.stream().filter(p -> {
                                if ("completed".equals(status.toLowerCase())) {
                                        return p.isCompleted();
                                } else if ("active".equals(status.toLowerCase())) {
                                        return !p.isCompleted();
                                }
                                return true;
                        }).collect(Collectors.toList());
                }

                // Calculate dashboard statistics
                int totalProjects = projects.size();
                int activeProjects = (int) projects.stream()
                                .filter(p -> !p.isCompleted())
                                .count();
                int completedProjects = totalProjects - activeProjects;

                // Set attributes for dashboard
                request.setAttribute("projects", projects);
                request.setAttribute("totalProjects", totalProjects);
                request.setAttribute("activeProjects", activeProjects);
                request.setAttribute("completedProjects", completedProjects);

                request.getRequestDispatcher("/static/home/manager-home.jsp").forward(request, response);
        }
}
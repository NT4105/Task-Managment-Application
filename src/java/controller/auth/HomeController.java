package controller.auth;

import dao.UserDAO;
import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get user information
        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);

        if (user != null) {
            request.setAttribute("user", user);

            // Redirect based on role
            if ("Manager".equals(user.getRole())) {
                request.getRequestDispatcher("/static/home/manager-home.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/static/home/member-home.jsp").forward(request, response);
            }
        } else {
            // Invalid user in session
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
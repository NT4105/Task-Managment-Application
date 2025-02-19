package controller.auth;

import dao.UserDAO;
import java.io.IOException;
import utils.Validation;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/static/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Add input validation
        if (!Validation.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("/static/auth/login.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = UserDAO.getInstance();
        String userId = userDAO.login(email, password);

        if (!userId.isEmpty()) {
            // Create session and store user information
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes session timeout

            // Redirect to dashboard or home page
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/static/auth/login.jsp").forward(request, response);
        }
    }
}

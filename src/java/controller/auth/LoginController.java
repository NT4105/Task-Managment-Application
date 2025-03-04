package controller.auth;

import model.User;
import dao.UserDAO;
import java.io.IOException;
import utils.Validation;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import utils.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/auth/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final Map<String, Integer> loginAttempts = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/static/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String userName = req.getParameter("userName");
            String password = req.getParameter("password");

            // Basic validation
            if (userName == null || userName.trim().isEmpty() ||
                    password == null || password.trim().isEmpty()) {
                LOGGER.log(Level.WARNING, "Empty username or password");
                req.setAttribute("error", "Username and password are required");
                req.getRequestDispatcher("/static/auth/login.jsp").forward(req, resp);
                return;
            }

            // Gọi trực tiếp login với password chưa hash
            User user = UserDAO.getInstance().login(userName, password);

            if (user != null) {
                // Set up session
                HttpSession session = req.getSession(true);
                session.setAttribute("userId", user.getUserID());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("userRole", user.getRole().toString());
                session.setAttribute("firstName", user.getFirstName());
                session.setAttribute("lastName", user.getLastName());

                // Redirect to home
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                req.setAttribute("error", "Invalid username or password");
                req.getRequestDispatcher("/static/auth/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "An error occurred during login");
            req.getRequestDispatcher("/static/auth/login.jsp").forward(req, resp);
        }
    }
}

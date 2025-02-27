package controller.auth;

import dao.UserDAO;
import model.User;
import utils.Validation;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String step = request.getParameter("step");
        String email = request.getParameter("email");

        if ("reset".equals(step) && email != null) {
            request.setAttribute("email", email);
            request.getRequestDispatcher("/static/auth/reset-password.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/static/auth/forgot-password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String step = request.getParameter("step");

        if ("verify".equals(step)) {
            // Xác thực email
            if (!Validation.isValidEmail(email)) {
                request.setAttribute("error", "Invalid email format");
                request.getRequestDispatcher("/static/auth/forgot-password.jsp").forward(request, response);
                return;
            }

            UserDAO userDAO = UserDAO.getInstance();
            User user = userDAO.getUserByEmail(email);

            if (user != null) {
                // Email tồn tại, chuyển đến trang reset password
                response.sendRedirect("forgot-password?step=reset&email=" + email);
            } else {
                request.setAttribute("error", "Email not found");
                request.getRequestDispatcher("/static/auth/forgot-password.jsp").forward(request, response);
            }
        } else if ("reset".equals(step)) {
            // Reset password
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!Validation.isValidPassword(newPassword)) {
                request.setAttribute("error", "Password must be at least 8 characters");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/static/auth/reset-password.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/static/auth/reset-password.jsp").forward(request, response);
                return;
            }

            UserDAO userDAO = UserDAO.getInstance();
            if (userDAO.resetPassword(email, newPassword)) {
                request.setAttribute("success", "Password has been reset successfully");
                request.getRequestDispatcher("/static/auth/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to reset password");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/static/auth/reset-password.jsp").forward(request, response);
            }
        }
    }
}
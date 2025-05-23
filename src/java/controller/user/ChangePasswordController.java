package controller.user;

import dao.UserDAO;
import model.User;
import utils.Util;
import utils.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/change-password")
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        request.getRequestDispatcher("/static/user/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match");
            request.getRequestDispatcher("/static/user/change-password.jsp").forward(request, response);
            return;
        }

        if (!Validation.isValidPassword(newPassword)) {
            request.setAttribute("error",
                    "Password must be at least 8 characters and contain at least one number and one special character");
            request.getRequestDispatcher("/static/user/change-password.jsp").forward(request, response);
            return;
        }

        // Verify current password and update
        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.selectById(userId);

        if (user != null && Util.verifyPassword(currentPassword, user.getPassword())) {
            user.setPassword(Util.encryptPassword(newPassword));
            if (userDAO.updatePassword(user)) {
                request.setAttribute("success", "Password changed successfully");
            } else {
                request.setAttribute("error", "Failed to change password");
            }
        } else {
            request.setAttribute("error", "Current password is incorrect");
        }

        request.getRequestDispatcher("/static/user/change-password.jsp").forward(request, response);
    }
}
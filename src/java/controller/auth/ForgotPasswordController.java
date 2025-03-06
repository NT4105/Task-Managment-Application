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
import javax.servlet.http.HttpSession;

@WebServlet("/auth/forgot-password")
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String step = req.getParameter("step");
        HttpSession session = req.getSession(false);

        if (step != null && step.equals("reset") && session != null && session.getAttribute("resetEmail") != null) {
            req.getRequestDispatcher("/static/auth/reset-password.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/static/auth/forgot-password.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String step = req.getParameter("step");
        HttpSession session = req.getSession();

        if (step.equals("verify")) {
            String email = req.getParameter("email");
            User user = UserDAO.getInstance().getUserByEmail(email);
            if (user != null) {
                session.setAttribute("resetEmail", email);
                resp.sendRedirect(req.getContextPath() + "/auth/forgot-password?step=reset");
            } else {
                req.setAttribute("error", "Email not found");
                req.getRequestDispatcher("/static/auth/forgot-password.jsp").forward(req, resp);
            }
        } else if (step.equals("reset")) {
            String email = (String) session.getAttribute("resetEmail");
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");

            if (email == null) {
                resp.sendRedirect(req.getContextPath() + "/auth/forgot-password");
                return;
            }

            if (newPassword.equals(confirmPassword)) {
                if (UserDAO.getInstance().resetPassword(email, newPassword)) {
                    session.removeAttribute("resetEmail");
                    req.setAttribute("success",
                            "Password has been reset successfully! You can now login with your new password.");
                    req.getRequestDispatcher("/static/auth/reset-password.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Failed to reset password");
                    req.getRequestDispatcher("/static/auth/reset-password.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "Passwords do not match");
                req.getRequestDispatcher("/static/auth/reset-password.jsp").forward(req, resp);
            }
        }
    }
}
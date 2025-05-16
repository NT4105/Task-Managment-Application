package controller.user;

import dao.UserDAO;
import dao.ProfileDAO;
import model.User;
import model.Profile;
import utils.Validation;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");

        // Get current user data
        User currentUser = UserDAO.getInstance().selectById(userId);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dobStr = request.getParameter("dob");

        // Create profile object with new data
        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setEmail(email);
        profile.setPhone(phone);

        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                profile.setDateOfBirth(Date.valueOf(dobStr));
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid date format");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
                return;
            }
        }

        // Validate changed fields
        ProfileDAO profileDAO = ProfileDAO.getInstance();

        // Only validate email if it changed
        if (!email.equals(currentUser.getEmail())) {
            if (!Validation.isValidEmail(email)) {
                request.setAttribute("error", "Invalid email format");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
                return;
            }

            if (!profileDAO.checkDuplicateEmail(email, userId)) {
                request.setAttribute("error", "Email already exists");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
                return;
            }
        }

        // Only validate phone if it changed
        if (!phone.equals(currentUser.getPhone())) {
            if (!Validation.isValidPhone(phone)) {
                request.setAttribute("error", "Invalid phone number format");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
                return;
            }

            if (!profileDAO.checkDuplicatePhone(phone, userId)) {
                request.setAttribute("error", "Phone number already exists");
                request.setAttribute("user", currentUser);
                request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
                return;
            }
        }

        // Update profile
        if (profileDAO.updateProfile(profile)) {
            // Update session attributes
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);

            request.setAttribute("success", "Profile updated successfully");
            // Refresh user data
            User updatedUser = UserDAO.getInstance().selectById(userId);
            request.setAttribute("user", updatedUser);
        } else {
            request.setAttribute("error", "Failed to update profile");
            request.setAttribute("user", currentUser);
        }

        request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
    }
}
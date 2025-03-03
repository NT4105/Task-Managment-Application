package controller.user;

import dao.UserDAO;
import dao.ProfileDAO;
import model.User;
import model.Profile;
import utils.Validation;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("auth/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("auth/login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        ProfileDAO profileDAO = ProfileDAO.getInstance();
        Profile profile = profileDAO.getProfileByUserId(userId);

        if (profile == null) {
            response.sendRedirect("auth/login");
            return;
        }

        // Get form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String dobStr = request.getParameter("dob");

        // Validate inputs
        if (!Validation.isValidName(firstName, lastName)) {
            request.setAttribute("error", "Invalid name format");
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
            return;
        }

        if (!Validation.isValidPhone(phone)) {
            request.setAttribute("error", "Invalid phone number");
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
            return;
        }

        if (!Validation.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format");
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
            return;
        }

        // Check for duplicates (excluding current user)
        if (!profileDAO.checkDuplicateEmail(email, userId)) {
            request.setAttribute("error", "Email already exists");
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
            return;
        }

        if (!profileDAO.checkDuplicatePhone(phone, userId)) {
            request.setAttribute("error", "Phone number already exists");
            request.setAttribute("profile", profile);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
            return;
        }

        // Update profile
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setPhone(phone);
        profile.setEmail(email);
        if (dobStr != null && !dobStr.isEmpty()) {
            profile.setDateOfBirth(java.sql.Date.valueOf(dobStr));
        }

        if (profileDAO.updateProfile(profile)) {
            request.setAttribute("success", "Profile updated successfully");
        } else {
            request.setAttribute("error", "Failed to update profile");
        }

        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
    }
}
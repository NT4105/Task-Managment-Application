package controller.user;

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

@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        User user = UserDAO.getInstance().selectById(userId);

        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
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
        User user = UserDAO.getInstance().selectById(userId);

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // Get form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        // Update only fields that were changed
        boolean isUpdated = false;

        if (firstName != null && !firstName.equals(user.getFirstName())) {
            if (Validation.isValidName(firstName, user.getLastName())) {
                user.setFirstName(firstName);
                isUpdated = true;
            } else {
                request.setAttribute("error", "Invalid first name");
            }
        }

        if (lastName != null && !lastName.equals(user.getLastName())) {
            if (Validation.isValidName(user.getFirstName(), lastName)) {
                user.setLastName(lastName);
                isUpdated = true;
            } else {
                request.setAttribute("error", "Invalid last name");
            }
        }

        if (phone != null && !phone.equals(user.getPhone())) {
            if (Validation.isValidPhone(phone)) {
                user.setPhone(phone);
                isUpdated = true;
            } else {
                request.setAttribute("error", "Invalid phone number");
            }
        }

        if (email != null && !email.equals(user.getEmail())) {
            if (Validation.isValidEmail(email)) {
                user.setEmail(email);
                isUpdated = true;
            } else {
                request.setAttribute("error", "Invalid email");
            }
        }

        // Save to database only if there were changes
        if (isUpdated) {
            if (UserDAO.getInstance().update(user) > 0) {
                request.setAttribute("success", "Profile updated successfully");
            } else {
                request.setAttribute("error", "Failed to update profile");
            }
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/static/user/profile.jsp").forward(request, response);
    }
}
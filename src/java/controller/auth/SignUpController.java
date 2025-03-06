package controller.auth;

import dao.UserDAO;
import model.User;
import utils.Validation;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.enums.UserRole;
import dto.UserDTO;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import utils.Util;

@WebServlet("/auth/sign-up")
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String dobStr = request.getParameter("dob");

            // Validate all inputs
            if (userName == null || userName.trim().isEmpty() ||
                    password == null || password.trim().isEmpty() ||
                    firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    role == null || role.trim().isEmpty() ||
                    dobStr == null || dobStr.trim().isEmpty()) {

                request.setAttribute("error", "All fields are required");
                request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
                return;
            }

            // Validate username format
            if (!Validation.isValidUsername(userName.trim())) {
                request.setAttribute("error",
                        "Invalid username format. Username must be 4-20 characters and contain only letters, numbers, and underscore");
                request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
                return;
            }

            // Validate password
            if (!Validation.isValidPassword(password)) {
                request.setAttribute("error",
                        "Password must be at least 8 characters and contain at least one number, one uppercase letter, and one special character");
                request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
                return;
            }

            // Validate email
            if (!Validation.isValidEmail(email.trim())) {
                request.setAttribute("error", "Invalid email format");
                request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
                return;
            }

            // Create UserDTO object
            UserDTO userDTO = new UserDTO(
                    UUID.randomUUID().toString(),
                    userName.trim(),
                    password, // Password will be hashed in DAO layer
                    firstName.trim(),
                    lastName.trim(),
                    email.trim(),
                    phone.trim(),
                    UserRole.fromString(role),
                    Date.valueOf(dobStr),
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()));

            // Try to create user
            if (UserDAO.getInstance().insert(userDTO) > 0) {
                // Set success message in session
                request.getSession().setAttribute("success", "Registration successful! Please login.");
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                request.setAttribute("error", "Failed to create account. Please try again.");
                request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during registration");
            request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/static/auth/signup.jsp").forward(request, response);
    }
}
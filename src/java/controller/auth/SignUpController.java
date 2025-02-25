/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.auth;

import dao.UserDAO;
import model.User;
import utils.Validation;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.enums.UserRole;
import dto.UserDTO;
import java.util.UUID;
import utils.Util;

/**
 *
 * @author Dell Latitude 7490
 */
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String dob = req.getParameter("dob");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Validate inputs
        if (!Validation.isValidRole(role)) {
            req.setAttribute("error", "Role must be either 'Manager' or 'Member'");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        if (!Validation.isValidEmail(email)) {
            req.setAttribute("error", "Invalid email format");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        if (!Validation.isValidUsername(userName)) {
            req.setAttribute("error", "Username must be at least 6 characters");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        if (!Validation.isValidDate(dob)) {
            req.setAttribute("error", "Invalid date format");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        // Create UserDTO object
        UserDTO userDTO = new UserDTO(
                UUID.randomUUID().toString(),
                userName,
                firstName,
                lastName,
                email,
                phone,
                UserRole.valueOf(role.toUpperCase()),
                Date.valueOf(dob),
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()));

        // Check for duplicates
        if (!UserDAO.getInstance().checkDuplicateUsername(userName)) {
            req.setAttribute("error", "Username already exists");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        if (!UserDAO.getInstance().checkDuplicateEmail(email)) {
            req.setAttribute("error", "Email already exists");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        if (!UserDAO.getInstance().checkDuplicatePhone(phone)) {
            req.setAttribute("error", "Phone number already exists");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        // Insert user
        int result = UserDAO.getInstance().insert(userDTO);
        if (result > 0) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("error", "Registration failed");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

}
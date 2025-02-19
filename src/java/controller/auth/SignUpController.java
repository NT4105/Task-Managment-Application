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

/**
 *
 * @author Dell Latitude 7490
 */
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // can hoan thien phan check form EMAIl
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String dob = req.getParameter("dob");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Validate role
        if (!Validation.isValidRole(role)) {
            req.setAttribute("error", "Role must be either 'Manager' or 'Member'");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }

        // Add validation for email format
        if (!Validation.isValidEmail(email)) {
            req.setAttribute("error", "Invalid email format");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        // Add validation for username
        if (!Validation.isValidUsername(userName)) {
            req.setAttribute("error", "Invalid username format");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        // Add validation for date of birth
        if (!Validation.isValidDate(dob)) {
            req.setAttribute("error", "Invalid date format");
            req.getRequestDispatcher("/static/auth/signup.jsp").forward(req, resp);
            return;
        }

        User user = new User(firstName, lastName, userName, Date.valueOf(dob), phone, email, password, role);

        if (!UserDAO.getInstance().checkDuplicateEmail(email)) {
            req.setAttribute("error", "Email is duplicated");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.isValidName(firstName, lastName)) {
            req.setAttribute("error", "Name is not valid");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.isValidPassword(password)) {
            req.setAttribute("error", "Password must not be empty, no spaces and at least six characters");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.isValidPhone(phone)) {
            req.setAttribute("error", "Phone is not valid or duplicated");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else {
            UserDAO.getInstance().insert(user);
            resp.sendRedirect("login");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

}
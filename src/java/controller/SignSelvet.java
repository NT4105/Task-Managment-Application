/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JDBC.ConnectJDBC;
import model.User;
import Function.AllInfo;

/**
 *
 * @author ChanRoi
 */
public class SignSelvet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println ("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SignSelvet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SignSelvet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }

//           ConnectJDBC.init();
//          if(ConnectJDBC.getConnection() != null){
//              response.getWriter().println("you success");
//          }else{
//              response.getWriter().println("No");
//          }
//          response.getWriter().println("ko");
         response.sendRedirect("Sign.html");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String ID = request.getParameter("User");
         String first = request.getParameter("firstName");
         String last = request.getParameter("lastName");
         String username = request.getParameter("userName");
         String email = request.getParameter("Email");
         String password = request.getParameter("password");
         String confirm = request.getParameter("confirm");
         String role = request.getParameter("Role");
         if(!confirm.equals(password)){
            //response.getWriter().println("Your confirm password is not match with the password.");
           // response.sendRedirect("Login.html");
            response.sendRedirect("Sign.html");
         }
         User user = new User(ID, first, last, username, email, password, role);
         if(user.getUserID().equals("Unexpected") || user.getFirstName().equals("Unexpected") 
                 || user.getLastName().equals("Unexpected") || user.getUserName().equals("Unexpected") || user.getEmail().equals("Unexpected")){
             response.sendRedirect("Sign.html"); 
         }else{
           AllInfo info = new AllInfo();
           info.saveUser(user);
         }
    }

    /**
     * Returns a short description of the servlet.
     * 
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

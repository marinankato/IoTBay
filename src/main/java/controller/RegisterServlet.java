package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.dao.AccessLogsDBManager;
import model.dao.DBUserManager;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Validator validator = new Validator();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNo = request.getParameter("phoneNo");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        if (role == null || (!role.equals("customer") && !role.equals("staff"))) {
            request.setAttribute("errorMsg", "Please select a valid role.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 5- retrieve the manager instance from session
        DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
        AccessLogsDBManager logsManager = (AccessLogsDBManager) session.getAttribute("logsManager");
        if (logsManager == null || dbmanager == null) {
            throw new IOException("Can't find DB Manager");
        }

        User user = null;
        try {
            // 6- find user by email 
            user = dbmanager.findUserByEmail(email);
            
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // don't need to validate email when logging in but will need to register
        if (!validator.validateEmail(email) /* 7- validate email */ ) {
            // 8-set incorrect email error to the session
            // purpose is to demonstrate error message in login page
            request.setAttribute("errorMsg", "Your email is not correctly formatted.");
            // 9- redirect user back to the login.jsp
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!validator.validatePassword(password) /* 10- validate password */ ) {
            // 11-set incorrect password error to the session
            request.setAttribute("errorMsg", "Your password must be at least 6 characters long.");
            // 12- redirect user back to the login.jsp
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (user != null) {
            request.setAttribute("errorMsg", "You already have an account.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // they are new so need to be added onto database
            try {
                session.removeAttribute("errorMsg");
                int userId = dbmanager.addUser(firstName, lastName, phoneNo, email, password, role);
                dbmanager.updateUserLoginDate(email, LocalDateTime.now());

                user = new User(userId, firstName, lastName, phoneNo, email, password, role);
                session.setAttribute("user", user);
                logsManager.addAccessDate(user.getUserID(), "logged in", LocalDateTime.now());
            } catch (SQLException e) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        }
    }
}
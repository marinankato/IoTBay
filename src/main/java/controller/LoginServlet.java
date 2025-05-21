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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Validator validator = new Validator();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
        AccessLogsDBManager logsManager = (AccessLogsDBManager) session.getAttribute("logsManager");
        if (logsManager == null || dbmanager == null) {
            throw new IOException("Can't find DB Manager");
        }

        User user = null;
        
        if (!validator.validateEmail(email)) {
            request.setAttribute("errorMsg", "Your email is not correctly formatted.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (!validator.validatePassword(password)) {
            request.setAttribute("errorMsg", "Your password should be at least 6 characters long.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            try {
                user = dbmanager.findUser(email, password);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            // check if there is a matching user account
            if (user == null) {
                request.setAttribute("errorMsg", "The login credentials don't match.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            // check if that account is active
            } else if (!"active".equalsIgnoreCase(user.getStatus())) {
                request.setAttribute("errorMsg", "The account has been deactivated. Contact admin.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // User is not null and status is active
                session.removeAttribute("errorMsg");
                session.setAttribute("user", user);
                LocalDateTime loginDateTime = LocalDateTime.now();
                try {
                    dbmanager.updateUserLoginDate(user.getEmail(), loginDateTime);
                    logsManager.addAccessDate(user.getUserID(), "logged in", loginDateTime);
                } catch (SQLException e) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, e);
                }
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
            }
        }   
    }
}
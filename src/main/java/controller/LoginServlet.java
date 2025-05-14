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
import model.dao.DBUserManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1- retrieve the current session
        HttpSession session = request.getSession();
        // 2- create an instance of the Validator class
        Validator validator = new Validator();
        // 3- capture the posted email
        String email = request.getParameter("email");
        // 4- capture the posted password
        String password = request.getParameter("password");
        // 5- retrieve the manager instance from session
        DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
        if (dbmanager == null) {
            throw new IOException("Can't find DB Manager");
        }

        User user = null;
        
        if (!validator.validateEmail(email) /* 7- validate email */ ) {
            // 8-set incorrect email error to the session
            // purpose is to demonstrate error message in login page
            session.setAttribute("errorMsg", "Your email is not correctly formatted.");
            // 9- redirect user back to the login.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (!validator.validatePassword(password) /* 10- validate password */ ) {
            // 11-set incorrect password error to the session
            session.setAttribute("errorMsg", "Your password should be at least 6 characters long.");
            // 12- redirect user back to the login.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            try {
                // find user by email and password
                user = dbmanager.findUser(email, password);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user != null) {
                // 12.5 clear your session of error messages
                session.removeAttribute("errorMsg");
                // 13-save the logged in user object to the session
                session.setAttribute("user", user);

                LocalDateTime loginTime = LocalDateTime.now();
                session.setAttribute("loginTime", loginTime);
                System.out.println("User " + user.getEmail() + " logged in at: " + loginTime);
                // dbmanager.AccessLogsDBManager(user.getUserId(), loginTime);

                // 14- redirect user to the main page
                response.sendRedirect("dashboard.jsp");
            } else {
            // 15-set user does not exist error to the session
            session.setAttribute("errorMsg", "The login credentials don't match.");
            // 16- redirect user back to the login.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }   
    }
}
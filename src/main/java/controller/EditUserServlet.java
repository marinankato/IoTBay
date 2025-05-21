package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AccessLogs;
import model.User;
import model.dao.AccessLogsDBManager;
import model.dao.DBUserManager;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {

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
        String originalEmail = request.getParameter("originalEmail"); 
        
        DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
        if (dbmanager == null) {
            throw new IOException("Can't find DB Manager");
        }

        if (!validator.validateEmail(email) /* 7- validate email */ ) {

            session.setAttribute("errorMsg", "Your email is not correctly formatted.");
            request.getRequestDispatcher("editUser.jsp").forward(request, response);

        } else if (!validator.validatePassword(password) /* 10- validate password */ ) {

            session.setAttribute("errorMsg", "Your password must be at least 6 characters long.");
            request.getRequestDispatcher("editUser.jsp").forward(request, response);

        } else {
            try {
                dbmanager.updateUser(firstName, lastName, phoneNo, email, password, originalEmail);
                // Fetch updated user and store it in session
                User updatedUser = dbmanager.findUser(email, password);
                session.setAttribute("user", updatedUser);
                session.removeAttribute("errorMsg");
                response.sendRedirect("dashboard.jsp");

            } catch (SQLException ex) {
                Logger.getLogger(EditUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getMessage().contains("UNIQUE constraint failed: Users.email")) {
                    session.setAttribute("errorMsg", "That email is already in use by another account.");
                    request.getRequestDispatcher("editUser.jsp").forward(request, response);
                } else {
                    throw new ServletException("Database error when updating user", ex);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        AccessLogsDBManager dbmanager = (AccessLogsDBManager) session.getAttribute("logsManager");
        if (dbmanager == null) {
            throw new ServletException("AccessLogsDBManager not found in session");
        }
        String filterDateStr = request.getParameter("filterDate");
        List<AccessLogs> logs;

        if (filterDateStr != null && !filterDateStr.isEmpty()) {
            LocalDate filterDate = LocalDate.parse(filterDateStr);  // Format: yyyy-MM-dd
            try {
                logs = dbmanager.getLogsByUserIdAndDate(user.getUserID(), filterDate);
                request.setAttribute("accessLogs", logs);
            } catch (SQLException e) {
                Logger.getLogger(EditUserServlet.class.getName()).log(Level.SEVERE, "Error fetching access logs", e);
                request.setAttribute("errorMsg", "Unable to load access logs.");
            }
        } else {
            try {
                logs = dbmanager.getLogsByUserId(user.getUserID());
                request.setAttribute("accessLogs", logs);
            } catch (SQLException e) {
                Logger.getLogger(EditUserServlet.class.getName()).log(Level.SEVERE, "Error fetching access logs", e);
                request.setAttribute("errorMsg", "Unable to load access logs.");
            }
        }
        request.getRequestDispatcher("editUser.jsp").forward(request, response);
    }
    
}
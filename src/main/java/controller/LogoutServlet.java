package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.dao.AccessLogsDBManager;
import model.dao.DBUserManager;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Get session if exists

        if (session != null) {
            DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
            AccessLogsDBManager logsManager = (AccessLogsDBManager) session.getAttribute("logsManager");

            if (logsManager == null || dbmanager == null) {
                throw new IOException("Can't find DB Manager");
            }
                User user = (User) session.getAttribute("user");
                LocalDateTime logoutDateTime = LocalDateTime.now();

                 // log the logout time if user is registered (they are not a guest)
                if (user != null) {
                    try {
                        if (!"guest".equalsIgnoreCase(user.getRole())) {
                            dbmanager.updateUserLogoutDate(user.getEmail(), logoutDateTime);
                            logsManager.addAccessDate(user.getUserID(), "logged out", logoutDateTime);
                        } else {
                            System.out.println("Guest session ended.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            session.invalidate(); // clear the session
        }
        response.sendRedirect("logout.jsp");
    }
}
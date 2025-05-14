package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.dao.DBUserManager;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); // Get session if exists

        if (session != null) {
            // Integer logId = (Integer) session.getAttribute("logId");
            DBUserManager dbManager = (DBUserManager) session.getAttribute("manager");
            User user = (User) session.getAttribute("user");
            // LocalDateTime loginTime = (LocalDateTime) session.getAttribute("loginTime");
            // LocalDateTime logoutTime = LocalDateTime.now();

            // if there is a user and user is registered (they are not a guest)
            if (user!= null && !"guest".equalsIgnoreCase(user.getRole())) {
                try {
                    dbManager.updateUserLogoutDate(user.getEmail(), LocalDateTime.now());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if ("guest".equalsIgnoreCase(user.getRole())) {
                System.out.println("Guest session ended.");
            }
            // if (logId != null && dbManager != null && user != null) {
            //     try {
            //     dbManager.updateLogoutDate(logId, LocalDateTime.now());
            // } catch (SQLException e) {
            //     e.printStackTrace(); 
            // }
            //     // System.out.println("User " + user.getEmail() + " logged out at: " + logoutTime);
            //     if (loginTime != null) {
            //         long durationMinutes = Duration.between(loginTime, logoutTime).toMinutes();
            //         System.out.println("Session duration: " + durationMinutes + " minutes");

            //         // Optional: Save logout to DB
            //         // dbmanager.logLogout(user.getUserId(), logoutTime);
            //     } else if ("guest".equalsIgnoreCase(user.getRole())) {
            //         System.out.println("Guest session ended.");
            //     }
            // }
            session.invalidate(); // Clear session
        }

        response.sendRedirect("logout.jsp");
    }
}

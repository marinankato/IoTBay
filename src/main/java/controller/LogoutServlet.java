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
import model.dao.DBUserManager;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Get session if exists

        if (session != null) {
            DBUserManager dbmanager = (DBUserManager) session.getAttribute("manager");
            User user = (User) session.getAttribute("user");
            LocalDateTime logoutDateTime = LocalDateTime.now();

            // log the logout time if user is registered (they are not a guest)
            if (user!= null && !"guest".equalsIgnoreCase(user.getRole())) {
                try {
                    dbmanager.updateUserLogoutDate(user.getEmail(), LocalDateTime.now());
                    dbmanager.addAccessDate(user.getUserID(), "logged out", logoutDateTime);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if ("guest".equalsIgnoreCase(user.getRole())) {
                System.out.println("Guest session ended.");
            }
            session.invalidate(); // Clear session
        }

        response.sendRedirect("logout.jsp");
    }
}

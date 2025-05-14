package controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); // Get session if exists

        if (session != null) {
            User user = (User) session.getAttribute("user");
            LocalDateTime loginTime = (LocalDateTime) session.getAttribute("loginTime");
            LocalDateTime logoutTime = LocalDateTime.now();

            if (user != null) {
                System.out.println("User " + user.getEmail() + " logged out at: " + logoutTime);
                if (loginTime != null) {
                    long durationMinutes = Duration.between(loginTime, logoutTime).toMinutes();
                    System.out.println("Session duration: " + durationMinutes + " minutes");

                    // Optional: Save logout to DB
                    // dbmanager.logLogout(user.getUserId(), logoutTime);
                } else if ("guest".equalsIgnoreCase(user.getRole())) {
                    System.out.println("Guest session ended.");
                }
            }
            session.invalidate(); // Clear session
        }

        response.sendRedirect("logout.jsp");
    }
}

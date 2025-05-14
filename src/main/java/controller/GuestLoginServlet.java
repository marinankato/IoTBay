package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;

@WebServlet("/GuestLoginServlet")
public class GuestLoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        // Check if a user already exists in session
        User existingUser = (User) session.getAttribute("user");
        if (existingUser != null) {
            // Already logged in as guest or registered user — don't overwrite
            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Otherwise, create a new guest user
        User guestUser = new User();
        guestUser.setFirstName("Guest");
        guestUser.setRole("guest");
        guestUser.setEmail(null);  // optional, clarify it's anonymous

        session.setAttribute("user", guestUser);

        response.sendRedirect("dashboard.jsp");
    }
}

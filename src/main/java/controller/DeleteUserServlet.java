package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.DBUserManager;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBUserManager dbManager = (DBUserManager) session.getAttribute("manager");

        if (dbManager == null) {
            throw new IOException("DB Manager not found in session.");
        }

        String email = request.getParameter("email");

        try {
            dbManager.deleteUser(email);
            session.invalidate(); // Log out the user
            response.sendRedirect("index.jsp"); // Or login.jsp
        } catch (SQLException e) {
            Logger.getLogger(DeleteUserServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMsg", "Account deletion failed.");
            request.getRequestDispatcher("editUser.jsp").forward(request, response);
        }
    }
}
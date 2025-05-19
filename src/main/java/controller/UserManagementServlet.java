package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.dao.DBUserConnector;
import model.dao.DBUserManager;
@WebServlet("/user-management")
public class UserManagementServlet extends HttpServlet {
    private DBUserManager dao;

    @Override
    public void init() {
        try {
            DBUserConnector connector = new DBUserConnector();
            dao = new DBUserManager(connector.openConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initilaise DBUserManager", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Map<String, String> user = dao.getUserById(id);
            req.setAttribute("editingUser", user);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteUser(id);
        }

        String fname = req.getParameter("firstName");
        String lname = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        List<Map<String, String>> users = dao.searchUsers(fname, lname, phone);
        req.setAttribute("users", users);
        req.getRequestDispatcher("userManagement.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            dao.createUser(
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("phone"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("role")
            );
        } else if ("updateUser".equals(action)) {
            dao.updateUser(
                Integer.parseInt(req.getParameter("userId")),
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("phone"),
                req.getParameter("email"),
                req.getParameter("role"),
                req.getParameter("status")
            );
        }

        resp.sendRedirect("user-management");
    }
}

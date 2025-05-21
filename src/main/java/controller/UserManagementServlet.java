package controller;

import java.io.IOException;
import java.sql.SQLException;
// import java.util.HashMap;
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
        String id = req.getParameter("userID");
        if ("updateUser".equals(action) && id != null) {
            Map<String, String> user = dao.getUserById(Integer.parseInt(id));
            req.setAttribute("editingUser", user);
            req.getRequestDispatcher("/userManagement.jsp").forward(req, resp);
        } else if ("delete".equals(action) && id != null) {
            
            dao.deleteUser(Integer.parseInt(id));
        }

        String fname = req.getParameter("firstName");
        String lname = req.getParameter("lastName");
        String phoneNo = req.getParameter("phoneNo");

        boolean isSearch = (fname != null && !fname.isEmpty()) || 
                   (lname != null && !lname.isEmpty()) || 
                   (phoneNo != null && !phoneNo.isEmpty());

        List<Map<String, String>> users;
        if (isSearch) {
            users = dao.searchUsers(fname, lname, phoneNo);
            
        } else {
            users = dao.getAllUsers();
            
        }
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
                req.getParameter("phoneNo"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("role")
            );
        } else if ("updateUser".equals(action)) {
            String userIdStr = req.getParameter("userID");
            int userId = Integer.parseInt(userIdStr);

            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String phoneNo = req.getParameter("phoneNo");
            String email = req.getParameter("email");
            String role = req.getParameter("role");


            
            dao.updateUser(userId, firstName, lastName, phoneNo, email, role);

            List<Map<String, String>> users = dao.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("userManagement.jsp").forward(req, resp);
            return;

        }

        resp.sendRedirect("user-management");
    }
}

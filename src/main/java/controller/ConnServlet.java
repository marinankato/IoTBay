package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.*;

@WebServlet("/ConnServlet")
public class ConnServlet extends HttpServlet {

    private DBUserConnector db;
    private DBUserManager manager;
    private AccessLogsDBManager logsManager;
    private Connection conn;

    @Override // Create and instance of DBConnector for the deployment session
    public void init() {
        try {
            db = new DBUserConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = db.openConnection();

        try {
            manager = new DBUserManager(conn);
            logsManager = new AccessLogsDBManager(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // export the DB manager to the view-session (JSPs)
        session.setAttribute("manager", manager);
        session.setAttribute("logsManager", logsManager);
    }

    @Override // Destroy the servlet and release the resources of the application (terminate
              // also the db connection)
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
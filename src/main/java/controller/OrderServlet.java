package controller;

import model.Order;
import model.User;
import model.dao.DBOrderConnector;
import model.dao.DBOrderManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String idParam   = req.getParameter("orderID");
        String dateParam = req.getParameter("orderDate");
        Integer orderID  = (idParam   != null && !idParam.isEmpty())   ? Integer.valueOf(idParam)   : null;
        java.sql.Date orderDate = null;
        try {
            if (dateParam != null && !dateParam.isEmpty()) {
                Date d = DF.parse(dateParam);
                orderDate = new java.sql.Date(d.getTime());
            }
        } catch (Exception ignore) { }
        try {
            DBOrderConnector connFactory = new DBOrderConnector();
            Connection conn = connFactory.openConnection();
            DBOrderManager mgr = new DBOrderManager(conn);

            List<Order> orders = (orderID != null || orderDate != null)
                ? mgr.searchOrders(user.getUserID(), orderID, orderDate)
                : mgr.getOrdersByUser(user.getUserID());

                if ((orderID != null || orderDate != null) && orders.isEmpty()) {
                    req.setAttribute("error", "No orders match your search.");
                    req.getRequestDispatcher("/orderHistory.jsp").forward(req, resp);
                    return;
                }
            
            session.setAttribute("orders", orders);
            connFactory.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/orderHistory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");
        try {
            DBOrderConnector connFactory = new DBOrderConnector();
            Connection conn = connFactory.openConnection();
            DBOrderManager mgr = new DBOrderManager(conn);

            switch (action) {
                case "checkout":
                    double total   = Double.parseDouble(req.getParameter("totalPrice"));
                    Order oNew  = new Order(0, user.getUserID(), new Date(), total, false);
                    mgr.insertOrder(oNew);
                    session.setAttribute("message", "Order placed.");
                    break;

                case "cancel":
                    int cancelId = Integer.parseInt(req.getParameter("orderID"));
                    mgr.deleteOrder(cancelId);
                    req.getSession().setAttribute("message", "Order cancelled.");
                    break;

                case "update":
                    int uid       = Integer.parseInt(req.getParameter("orderID"));
                    Date dt       = DF.parse(req.getParameter("orderDate"));
                    double price     = Double.parseDouble(req.getParameter("totalPrice"));
                    boolean stat  = Boolean.parseBoolean(req.getParameter("orderStatus"));
                    Order oUpd    = new Order(uid, user.getUserID(), dt, price, stat);
                    mgr.updateOrder(oUpd);
                    session.setAttribute("message", "Order updated.");
                    break;

                default:
                    session.setAttribute("error", "Unknown action: " + action);
            }

            connFactory.closeConnection();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/order");
    }
}
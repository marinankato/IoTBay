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
        // 1) Ensure user is logged in
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // ── DEBUG #1: which userID are we using? ──
        System.out.println(">>> OrderServlet.doGet: session userID = " + user.getUserID());

        // 2) Parse optional search filters
        String idParam   = req.getParameter("orderID");
        String dateParam = req.getParameter("orderDate");
        Integer orderID  = (idParam   != null && !idParam.isEmpty())
                              ? Integer.valueOf(idParam)
                              : null;
        java.sql.Date orderDate = null;
        try {
            if (dateParam != null && !dateParam.isEmpty()) {
                Date d = DF.parse(dateParam);
                orderDate = new java.sql.Date(d.getTime());
            }
        } catch (Exception ignore) { }

        List<Order> orders;
        try {
            // 3) Open JDBC connection & manager
            DBOrderConnector cf = new DBOrderConnector();
            Connection conn = cf.openConnection();

            // ── DEBUG #2: what Connection did we actually get? ──
            System.out.println(">>> DBOrderConnector.openConnection() → " + conn);

            DBOrderManager mgr = new DBOrderManager(conn);

            // 4) Either search or fetch all
            if (orderID != null || orderDate != null) {
                orders = mgr.searchOrders(user.getUserID(), orderID, orderDate);
                if (orders.isEmpty()) {
                    req.setAttribute("error", "No orders match your search.");
                }
            } else {
                orders = mgr.getOrdersByUser(user.getUserID());
            }

            // ── DEBUG #3: how many rows did we pull? ──
            System.out.println(">>> Retrieved " + orders.size()
                               + " orders (filters: orderID=" + orderID
                               + ", orderDate=" + orderDate + ")");
            for (Order o : orders) {
                System.out.println("    → " + o);
            }

            cf.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }

        // 5) Save into session & forward to JSP
        session.setAttribute("orders", orders);
        req.getRequestDispatcher("/orderHistory.jsp")
           .forward(req, resp);
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
            DBOrderConnector cf = new DBOrderConnector();
            Connection conn = cf.openConnection();
            DBOrderManager mgr = new DBOrderManager(conn);

            switch (action) {
                case "checkout":
                    // create new order & get its generated ID
                    double total = Double.parseDouble(req.getParameter("totalPrice"));
                    Order newOrder = new Order(0, user.getUserID(), new Date(), total, false);
                    int newId = mgr.insertOrderAndReturnKey(newOrder);
                    session.setAttribute("message", "Order #" + newId + " placed.");
                    break;

                case "update":
                    // update date & total (status remains false/"Saved")
                    int uid = Integer.parseInt(req.getParameter("orderID"));
                    Date dt = DF.parse(req.getParameter("orderDate"));
                    double price = Double.parseDouble(req.getParameter("totalPrice"));
                    mgr.updateOrder(new Order(uid, user.getUserID(), dt, price, false));
                    session.setAttribute("message", "Order #" + uid + " updated.");
                    break;

                case "cancel":
                    // mark as cancelled (status=false)
                    int cid = Integer.parseInt(req.getParameter("orderID"));
                    mgr.cancelOrder(cid);
                    session.setAttribute("message", "Order #" + cid + " cancelled.");
                    break;

                default:
                    session.setAttribute("error", "Unknown action: " + action);
            }

            cf.closeConnection();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        // after any POST, show the history again
        req.getRequestDispatcher("/orderHistory.jsp")
           .forward(req, resp);
    }
}
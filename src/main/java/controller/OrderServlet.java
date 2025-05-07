package controller;

import model.Order;
import model.User;
import model.dao.DBOrderConnector;
import model.dao.DBOrderManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/OrderServlet")
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

        // parse optional filters
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

            session.setAttribute("orders", orders);
            connFactory.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect("orderHistory.jsp");
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
                    int total   = Integer.parseInt(req.getParameter("totalPrice"));
                    Order oNew  = new Order(0, user.getUserID(), new Date(), total, false);
                    mgr.insertOrder(oNew);
                    session.setAttribute("message", "Order placed.");
                    break;

                case "cancel":
                    int cancelId = Integer.parseInt(req.getParameter("orderID"));
                    mgr.updateOrderStatus(cancelId, false);
                    session.setAttribute("message", "Order cancelled.");
                    break;

                case "update":
                    int uid       = Integer.parseInt(req.getParameter("orderID"));
                    Date dt       = DF.parse(req.getParameter("orderDate"));
                    int price     = Integer.parseInt(req.getParameter("totalPrice"));
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

        // redirect back through doGet so the list refreshes
        resp.sendRedirect("order");
    }
}
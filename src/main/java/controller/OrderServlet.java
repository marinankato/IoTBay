package controller;

import model.CartItem;
import model.Order;
import model.ShoppingCart;
import model.User;
import model.dao.DBOrderConnector;
import model.dao.DBOrderItem;
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

        String idParam = req.getParameter("orderID");
        String dateParam = req.getParameter("orderDate");
        Integer orderID = (idParam != null && !idParam.isEmpty())
                ? Integer.valueOf(idParam)
                : null;
        java.sql.Date orderDate = null;
        try {
            if (dateParam != null && !dateParam.isEmpty()) {
                Date d = DF.parse(dateParam);
                orderDate = new java.sql.Date(d.getTime());
            }
        } catch (Exception ignore) {
        }

        List<Order> orders;
        try {
            // 3) Open JDBC connection & manager
            DBOrderConnector cf = new DBOrderConnector();
            Connection conn = cf.openConnection();

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

            cf.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }

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
                    // 1) read total
                    double total = Double.parseDouble(req.getParameter("totalPrice"));
                    // 2) insert ORDER with status = SUBMITTED
                    Order placed = new Order(
                            0,
                            user.getUserID(),
                            new java.util.Date(),
                            total,
                            Order.SUBMITTED);
                    int newId = mgr.insertOrderAndReturnKey(placed);

                    // 3) write order-items
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
                    DBOrderItem itemMgr = new DBOrderItem(conn);
                    for (CartItem ci : cart.getItems()) {
                        itemMgr.addItem(
                                newId,
                                ci.getDeviceId(),
                                ci.getQuantity(),
                                ci.getUnitPrice());
                    }

                    // 4) clear the cart
                    cart.getItems().clear();

                    session.setAttribute("message", "Order #" + newId + " placed.");
                    resp.sendRedirect(req.getContextPath() + "/order");
                    return;

                case "update":
                    int uid = Integer.parseInt(req.getParameter("orderID"));
                    Date dt = DF.parse(req.getParameter("orderDate"));
                    double price = Double.parseDouble(req.getParameter("totalPrice"));
                    mgr.updateOrder(new Order(uid, user.getUserID(), dt, price, Order.SAVED));
                    session.setAttribute("message", "Order #" + uid + " updated.");
                    resp.sendRedirect(req.getContextPath() + "/order");
                    cf.closeConnection();
                    return;

                case "cancel":
                    int cid = Integer.parseInt(req.getParameter("orderID"));
                    mgr.cancelOrder(cid);
                    session.setAttribute("message", "Order #" + cid + " cancelled.");
                    // Redirect so doGet() re-loads the fresh data
                    resp.sendRedirect(req.getContextPath() + "/order");
                    cf.closeConnection();
                    return;

                default:
                    session.setAttribute("error", "Unknown action: " + action);
                    resp.sendRedirect(req.getContextPath() + "/order");
                    cf.closeConnection();
                    return;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
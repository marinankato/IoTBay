package controller;

import model.Order;
import model.User;
import model.CartItem;
import model.IoTDevice;
import model.dao.DBOrderConnector;
import model.dao.DBOrderManager;
import model.dao.IoTDeviceDAO;
import model.dao.DBOrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sess = req.getSession();
        User u = (User) sess.getAttribute("user");
        if (u == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int orderID = Integer.parseInt(req.getParameter("orderID"));
        try {
            DBOrderConnector cf = new DBOrderConnector();
            Connection conn = cf.openConnection();

            // 1) fetch the Order
            DBOrderManager om = new DBOrderManager(conn);
            Order order = om.findById(orderID);
            if (order.getUserID() != u.getUserID()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // 2) fetch its line‐items
            DBOrderItem itemDao = new DBOrderItem(conn);
            List<CartItem> items = itemDao.getItemsForOrder(orderID);

            IoTDeviceDAO deviceDao = new IoTDeviceDAO();
            List<IoTDevice> allDevices = deviceDao.getAllDevices();
            
            cf.closeConnection();

            req.setAttribute("order", order);
            req.setAttribute("items", items);
            req.setAttribute("allDevices", allDevices);
            req.getRequestDispatcher("/orderDetails.jsp")
                    .forward(req, resp);

        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int orderID = Integer.parseInt(req.getParameter("orderID"));
        String action = req.getParameter("action");

        try {
            DBOrderConnector cf = new DBOrderConnector();
            Connection conn = cf.openConnection();
            DBOrderItem itemDao = new DBOrderItem(conn);
            DBOrderManager mgr = new DBOrderManager(conn);

            switch (action) {
                case "remove":
                    int remDev = Integer.parseInt(req.getParameter("deviceID"));
                    itemDao.deleteItem(orderID, remDev);
                    break;

                case "updateQty":
                    int updDev = Integer.parseInt(req.getParameter("deviceID"));
                    int qty = Integer.parseInt(req.getParameter("quantity"));
                    double up = Double.parseDouble(req.getParameter("unitPrice"));
                    itemDao.updateItem(orderID, updDev, qty, up);
                    break;

                case "add":
                    int addDev = Integer.parseInt(req.getParameter("deviceID"));
                    int addQty = Integer.parseInt(req.getParameter("quantity"));
                    double unit = Double.parseDouble(req.getParameter("unitPrice"));
                    itemDao.addItem(orderID, addDev, addQty, unit);
                    break;

                default:
                    throw new ServletException("Unknown action: " + action);
            }

            // re‐compute the order total
            mgr.recalcTotal(orderID);

            cf.closeConnection();

            // redirect back to details so we see refreshed table
            resp.sendRedirect(req.getContextPath()
                    + "/orderDetails?orderID=" + orderID);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
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

import model.User;
import model.Order;

public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        orderDAO = new OrderDAO(conn);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                Order order = new Order();
                order.setUserId(Integer.parseInt(req.getParameter("userId")));
                order.setOrderDate(new java.util.Date());
                order.setStatus("saved");

                orderDAO.insertOrder(order);
                resp.sendRedirect("view/listOrders.jsp");

            } else if ("cancel".equals(action)) {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                orderDAO.updateOrderStatus(orderId, "cancelled");
                resp.sendRedirect("view/listOrders.jsp");
            }

        } catch (Exception e) {
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
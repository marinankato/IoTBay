package controller;

import model.Order;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        int userID = ((model.User) session.getAttribute("user")).getUserID(); // assuming User object is in session

        try {
            DBOrderConnector connector = new DBOrderConnector();
            Connection conn = connector.openConnection();
            OrderManager manager = new DBOrderManager(conn);

            switch (action) {
                case "checkout":
                    // Normally you'd calculate totalPrice from Cart, but here it's hardcoded/tested
                    int totalPrice = 199; // Replace with dynamic logic later
                    Date orderDate = new Date();

                    Order newOrder = new Order(0, userID, orderDate, totalPrice, false); // status 'saved'
                    manager.insertOrder(newOrder);
                    response.sendRedirect("orderHistory.jsp");
                    break;

                case "cancel":
                    int cancelOrderId = Integer.parseInt(request.getParameter("orderID"));
                    manager.updateOrderStatus(cancelOrderId, false); // set to 'saved' or 'cancelled'
                    response.sendRedirect("orderHistory.jsp");
                    break;

                default:
                    response.sendRedirect("error.jsp");
                    break;
            }

            connector.closeConnection();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int userID = ((model.User) session.getAttribute("user")).getUserID();

        try {
            DBOrderConnector connector = new DBOrderConnector();
            Connection conn = connector.openConnection();
            OrderManager manager = new OrderManager(conn);

            List<Order> orders = manager.getOrdersByUser(userID);
            session.setAttribute("orders", orders);

            connector.closeConnection();
            response.sendRedirect("orderHistory.jsp");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving order history: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}


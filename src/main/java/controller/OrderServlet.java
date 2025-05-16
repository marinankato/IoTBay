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
  
      // load everything for that user
      List<Order> orders;
      try {
        // 1) open connection
        DBOrderConnector cf = new DBOrderConnector();
        System.out.println(">>> DBOrderConnector opening at URL=" + cf.openConnection());  // <<<<

        Connection conn    = cf.openConnection();
        DBOrderManager mgr = new DBOrderManager(conn);
  
        // 2) actually load them
        orders = mgr.getOrdersByUser(user.getUserID());

        cf.closeConnection();
      } catch (Exception e) {
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
            DBOrderConnector connFactory = new DBOrderConnector();
            Connection conn = connFactory.openConnection();
            DBOrderManager mgr = new DBOrderManager(conn);

            switch (action) {
                case "checkout":
                    double tot = Double.parseDouble(req.getParameter("totalPrice"));
                    Order newOrder = new Order(0, user.getUserID(), new Date(), tot, false);
                    int newId = mgr.insertOrderAndReturnKey(newOrder);
                    session.setAttribute("message", "Order #" + newId + " placed.");
                    break;

                case "update":
                    int uid   = Integer.parseInt(req.getParameter("orderID"));
                    Date dt   = DF.parse(req.getParameter("orderDate"));
                    double pr = Double.parseDouble(req.getParameter("totalPrice"));
                    mgr.updateOrder(new Order(uid, user.getUserID(), dt, pr, false));
                    session.setAttribute("message", "Order #" + uid + " updated.");
                    break;

                case "cancel":
                    int cid = Integer.parseInt(req.getParameter("orderID"));
                    mgr.cancelOrder(cid);
                    session.setAttribute("message", "Order #" + cid + " cancelled.");
                    break;

                default:
                    session.setAttribute("error", "Unknown action: " + action);
            }

            connFactory.closeConnection();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/orderHistory.jsp").forward(req, resp);
    }
}
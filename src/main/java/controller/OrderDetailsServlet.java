package controller;

import model.Order;
import model.User;
import model.CartItem;
import model.dao.DBOrderConnector;
import model.dao.DBOrderManager;
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

      cf.closeConnection();

      req.setAttribute("order", order);
      req.setAttribute("items", items);
      req.getRequestDispatcher("/orderDetails.jsp")
         .forward(req, resp);

    } catch (ClassNotFoundException | SQLException e) {
      throw new ServletException(e);
    }
  }
}
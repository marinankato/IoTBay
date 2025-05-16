package controller;

import model.CartItem;
import model.IoTDevice;
import model.ShoppingCart;
import model.dao.IoTDeviceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // simply show the cart
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("shoppingCart", cart);
        }

        String action = req.getParameter("action");
        int did = Integer.parseInt(req.getParameter("deviceId"));

        try {
            IoTDeviceDAO dao = new IoTDeviceDAO();
            switch (action) {
                case "add": {
                    int qty = Integer.parseInt(req.getParameter("quantity"));
                    IoTDevice dev = dao.getDeviceById(did);
                    cart.addItem(new CartItem(dev.getId(), dev.getName(), dev.getPrice(), qty));
                    // decrement stock
                    dao.adjustQuantity(did, -qty);
                    break;
                }
                case "updateQty": {
                    int newQty = Integer.parseInt(req.getParameter("quantity"));
                    int oldQty = cart.getQuantity(did);
                    cart.updateQuantity(did, newQty);
                    // if user increased qty, delta negative => minus from stock; if decreased, delta positive => add back
                    dao.adjustQuantity(did, oldQty - newQty);
                    break;
                }
                case "remove": {
                    int removedQty = cart.getQuantity(did);
                    cart.removeItem(did);
                    // restore stock
                    dao.adjustQuantity(did, removedQty);
                    break;
                }
                default:
                    session.setAttribute("error", "Unknown cart action: " + action);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
}
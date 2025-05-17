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
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
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
        String didParam = req.getParameter("deviceId");
        if (didParam == null || !didParam.matches("\\d+")) {
            session.setAttribute("error", "Invalid product ID.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        int did = Integer.parseInt(didParam);

        try {
            IoTDeviceDAO dao = new IoTDeviceDAO();
            switch (action) {
                case "add": {
                    String qtyParam = req.getParameter("quantity");
                    if (qtyParam == null || !qtyParam.matches("\\d+")) {
                        session.setAttribute("error", "Invalid quantity.");
                        break;
                    }
                    int qty = Integer.parseInt(qtyParam);
                    IoTDevice dev = dao.getDeviceById(did);
                    if (dev.getQuantity() < qty) {
                        session.setAttribute("error", "Sorry, only " 
                            + dev.getQuantity() + " left in stock.");
                        break;
                    }
                    cart.addItem(new CartItem(dev.getId(), dev.getName(), dev.getPrice(), qty));
                    dao.adjustQuantity(did, -qty);
                    session.setAttribute("message", 
                        "Added " + qty + " × " + dev.getName() + " to your cart.");
                    break;
                }
                case "updateQty": {
                    String newQtyParam = req.getParameter("quantity");
                    if (newQtyParam == null || !newQtyParam.matches("\\d+")) {
                        session.setAttribute("error", "Invalid quantity.");
                        break;
                    }
                    int newQty = Integer.parseInt(newQtyParam);
                    int oldQty = cart.getQuantity(did);
                    IoTDevice dev = dao.getDeviceById(did);
                    int delta = oldQty - newQty; // positive => add back, negative => remove more
                    if (delta < 0 && dev.getQuantity() < -delta) {
                        session.setAttribute("error", "Sorry, only " 
                            + dev.getQuantity() + " left in stock.");
                        break;
                    }
                    cart.updateQuantity(did, newQty);
                    dao.adjustQuantity(did, delta);
                    session.setAttribute("message", "Updated quantity for " + dev.getName() + ".");
                    break;
                }
                case "remove": {
                    int removedQty = cart.getQuantity(did);
                    cart.removeItem(did);
                    dao.adjustQuantity(did, removedQty);
                    session.setAttribute("message", "Removed item from your cart.");
                    break;
                }
                default:
                    session.setAttribute("error", "Unknown cart action: " + action);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // just show cart.jsp
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
}
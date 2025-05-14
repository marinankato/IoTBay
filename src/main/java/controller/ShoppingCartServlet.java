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
        if ("add".equals(action)) {
            int did = Integer.parseInt(req.getParameter("deviceId"));
            int qty = Integer.parseInt(req.getParameter("quantity"));

            List<IoTDevice> all;
            try {
                IoTDeviceDAO dao = new IoTDeviceDAO();    
                all = dao.getAllDevices();               
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException("Unable to load devices from database", e);
            }

            IoTDevice chosen = null;
            for (IoTDevice d : all) {
                if (d.getId() == did) {
                    chosen = d;
                    break;
                }
            }
            if (chosen == null) {
                throw new ServletException("Device #" + did + " not found");
            }

            cart.addItem(new CartItem(
                chosen.getId(),
                chosen.getName(),
                chosen.getPrice(),
                qty
            ));

        } else if ("remove".equals(action)) {
            int did = Integer.parseInt(req.getParameter("deviceId"));
            cart.removeItem(did);
        }

        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
}
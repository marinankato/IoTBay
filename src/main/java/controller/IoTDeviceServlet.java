package controller;

import model.IoTDevice;
import model.User;
import model.dao.IoTDeviceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/devices")
public class IoTDeviceServlet extends HttpServlet {

    @Override
    // Handles the HTTP GET request
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                IoTDevice device = new IoTDeviceDAO().getDeviceById(id);// Fetch the device by ID
                request.setAttribute("device", device);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);// Forward to edit page
            } else {
                List<IoTDevice> devices = new IoTDeviceDAO().getAllDevices();// Fetch all devices
                request.setAttribute("devices", devices);
                request.getRequestDispatcher("/iotdeviceView.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("GET failed", e);
        }
    }

    @Override
    // Handles the HTTP POST request
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String role = (user != null) ? user.getRole() : "";
        IoTDeviceDAO dao = new IoTDeviceDAO();
        String action = request.getParameter("action");

        try {
            if ("search".equals(action)) {
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                List<IoTDevice> results = new IoTDeviceDAO().searchDevices(name, type);// Search for devices
                request.setAttribute("devices", results);
                request.getRequestDispatcher("/iotdeviceView.jsp").forward(request, response);
            }

            // Handle delete, update, and add actions
            else if ("delete".equals(action)) {
                if (!"staff".equalsIgnoreCase(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only staff can delete.");// Only staff can delete
                    return;
                }
                int id = Integer.parseInt(request.getParameter("id"));
                new IoTDeviceDAO().deleteDevice(id);
                response.sendRedirect("devices");

            } else if ("update".equals(action)) {
                if (!"staff".equalsIgnoreCase(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only staff can update.");// Only staff can update
                    return;
                }
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (price < 0 || quantity < 0) {
                    request.setAttribute("error", "Price and quantity must be non-negative.");
                    IoTDevice device = new IoTDevice(id, name, type, price, quantity);
                    request.setAttribute("device", device);
                    request.getRequestDispatcher("/edit.jsp").forward(request, response);
                    return;
                }// Validate price and quantity
                IoTDevice updated = new IoTDevice(id, name, type, price, quantity);
                new IoTDeviceDAO().updateDevice(updated);// Update the device
                response.sendRedirect("devices");

            } else {
                if (!"staff".equalsIgnoreCase(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only staff can add.");
                    return;
                }// Only staff can add
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (price < 0 || quantity < 0) {
                    request.setAttribute("error", "Price and quantity must be non-negative.");
                    request.setAttribute("devices", dao.getAllDevices());
                    request.getRequestDispatcher("/iotdeviceView.jsp").forward(request, response);
                    return;
                }// Validate price and quantity
                IoTDevice device = new IoTDevice(0, name, type, price, quantity);
                new IoTDeviceDAO().addDevice(device);
                response.sendRedirect("devices");// Add the device
            }
        } catch (Exception e) {
            throw new ServletException("POST failed", e);
        }
    }
}



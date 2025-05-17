package model.dao;

import model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOrderItem {
    private final Connection conn;

    public DBOrderItem(Connection conn) {
        this.conn = conn;
    }

    public List<CartItem> getItemsForOrder(int orderID) throws SQLException {
        String sql = "SELECT oi.deviceID, d.device_name, oi.quantity, oi.unitPrice " +
                "FROM OrderItems oi " +
                "  JOIN iot_device d ON oi.deviceID = d.device_id " +
                "WHERE oi.orderID = ?";

        List<CartItem> items = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new CartItem(
                            rs.getInt("deviceID"),
                            rs.getString("device_name"),
                            rs.getDouble("unitPrice"),
                            rs.getInt("quantity")));
                }
            }
        }
        return items;
    }

    /**
     * Insert a single order–item row.
     */
    public void addItem(int orderID,
            int deviceID,
            int quantity,
            double unitPrice)
            throws SQLException {
        String sql = "INSERT INTO OrderItems (orderID, deviceID, quantity, unitPrice) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.setInt(2, deviceID);
            ps.setInt(3, quantity);
            ps.setDouble(4, unitPrice);
            ps.executeUpdate();
        }
    }

    public void insertItem(int orderId, model.CartItem ci)
            throws SQLException {
        String sql = "INSERT INTO OrderItems (orderID, deviceID, quantity, unitPrice) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, ci.getDeviceId());
            ps.setInt(3, ci.getQuantity());
            ps.setDouble(4, ci.getUnitPrice());
            ps.executeUpdate();
        }
    }

}
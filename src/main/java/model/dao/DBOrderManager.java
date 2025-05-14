package model.dao;

import model.Order;
import java.sql.*;
import java.util.*;

public class DBOrderManager {
    private final Connection conn;

    public DBOrderManager(Connection conn) {
        this.conn = conn;
    }

    // --- CREATE ---
    public void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getRelatedCustomer());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setBoolean(4, order.getOrderStatus());
            stmt.executeUpdate();
        }
    }

    // --- READ ALL ---
    public List<Order> getOrdersByUser(int userID) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE userID=? ORDER BY orderDate DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // --- SEARCH by ID and/or DATE ---
    public List<Order> searchOrders(int userID, Integer orderID, java.sql.Date orderDate)
            throws SQLException {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM Orders WHERE userID=?");
        if (orderID   != null) sql.append(" AND orderID=?");
        if (orderDate != null) sql.append(" AND orderDate=?");
        sql.append(" ORDER BY orderDate DESC");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            stmt.setInt(idx++, userID);
            if (orderID   != null) stmt.setInt(idx++, orderID);
            if (orderDate != null) stmt.setDate(idx++, orderDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Order(
                      rs.getInt("orderID"),
                      rs.getInt("userID"),
                      rs.getDate("orderDate"),
                      rs.getDouble("totalPrice"),
                      rs.getBoolean("orderStatus")
                    ));
                }
            }
        }
        return list;
    }

    // --- UPDATE STATUS (cancel) ---
    public void updateOrderStatus(int orderID, boolean status) throws SQLException {
        String sql = "UPDATE Orders SET orderStatus = ? WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, orderID);
            stmt.executeUpdate();
        }
    }

    // --- UPDATE FULL ORDER ---
    public void updateOrder(Order o) throws SQLException {
        String sql = "UPDATE Orders SET orderDate=?, totalPrice=?, orderStatus=? WHERE orderID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(o.getOrderDate().getTime()));
            stmt.setDouble(2, o.getTotalPrice());
            stmt.setBoolean(3, o.getOrderStatus());
            stmt.setInt(4, o.getOrderID());
            stmt.executeUpdate();
        }
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        return new Order(
            rs.getInt("orderID"),
            rs.getInt("userID"),
            rs.getDate("orderDate"),
            rs.getDouble("totalPrice"),
            rs.getBoolean("orderStatus")
        );
    }

    public void deleteOrder(int orderID) throws SQLException {
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, orderID);
            p.executeUpdate();
        }
    }
}

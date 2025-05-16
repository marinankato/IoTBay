


package model.dao;

import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOrderManager {
    private final Connection conn;

    public DBOrderManager(Connection conn) {
        this.conn = conn;
    }

    /** 
     * INSERT and return the generated orderID 
     */
    public int insertOrderAndReturnKey(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserID());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setBoolean(4, order.getOrderStatus());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                else throw new SQLException("No generated key returned");
            }
        }
    }

    /** READ all orders for a user */
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

    /** SEARCH by optional orderID and/or orderDate */
    public List<Order> searchOrders(int userID, Integer orderID, java.sql.Date orderDate)
            throws SQLException {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Orders WHERE userID=?");
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
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /** UPDATE just the status (e.g. cancel or resubmit) */
    public void updateOrderStatus(int orderID, boolean status) throws SQLException {
        String sql = "UPDATE Orders SET orderStatus=? WHERE orderID=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, orderID);
            stmt.executeUpdate();
        }
    }

    /** FULL UPDATE */
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

    /** CANCEL = set status=false */
    public void cancelOrder(int orderID) throws SQLException {
        updateOrderStatus(orderID, false);
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        int orderId       = rs.getInt("orderID");
        int customerId    = rs.getInt("userID");
        String dateString = rs.getString("orderDate");            // grab the raw “yyyy-MM-dd”
        java.sql.Date d   = java.sql.Date.valueOf(dateString);    // convert it
        double total      = rs.getDouble("totalPrice");
        boolean status    = rs.getInt("orderStatus") != 0;         // or rs.getBoolean(...)
    
        return new Order(orderId, customerId, d, total, status);
    }

}
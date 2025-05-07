package model.dao;
import model.Order;
import java.sql.*;
import java.util.List;

public class DBOrderManager {
    private Connection conn;

    public DBOrderManager(Connection conn) throws SQLException {
        this.conn = conn;
    }

    public void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (userID, orderDate, totalPrice, orderStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getRelatedCustomer());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(3, order.getTotalPrice());
            stmt.setBoolean(4, order.getOrderStatus());
            stmt.executeUpdate();
        }
    }

    public void updateOrderStatus(int cancelOrderId, boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    public List<Order> getOrdersByUser(int userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByUser'");
    }

}

package model.dao;
import model.Order;
import java.sql.*;
public class DBOrderManager {
    private Connection conn;

    public DBOrderManager(Connection conn) {
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

    public List<Order> fetchOrdersByUser(int userID) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("orderID"),
                        rs.getInt("userID"),
                        rs.getDate("orderDate"),
                        rs.getInt("totalPrice"),
                        rs.getBoolean("orderStatus")));
            }
        }

        return orders;
    }
}

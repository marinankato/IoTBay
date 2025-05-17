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
    String sql =
      "SELECT oi.deviceID, d.device_name, oi.quantity, oi.unitPrice " +
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
            rs.getInt("quantity")
          ));
        }
      }
    }
    return items;
  }
}
package model.dao;

import model.IoTDevice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IoTDeviceDAO extends DBUser {

    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(URL);
    }

    public List<IoTDevice> getAllDevices() throws SQLException, ClassNotFoundException {
        List<IoTDevice> devices = new ArrayList<>();
        String sql = "SELECT * FROM iot_device";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                IoTDevice d = new IoTDevice(
                    rs.getInt("device_id"),
                    rs.getString("device_name"),
                    rs.getString("device_type"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity")
                );
                devices.add(d);
            }
        }

        return devices;
    }

    public void addDevice(IoTDevice device) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iot_device (device_name, device_type, unit_price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, device.getName());
            ps.setString(2, device.getType());
            ps.setDouble(3, device.getPrice());
            ps.setInt(4, device.getQuantity());
            ps.executeUpdate();
        }
    }

    public void updateDevice(IoTDevice device) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE iot_device SET device_name=?, device_type=?, unit_price=?, quantity=? WHERE device_id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, device.getName());
            ps.setString(2, device.getType());
            ps.setDouble(3, device.getPrice());
            ps.setInt(4, device.getQuantity());
            ps.setInt(5, device.getId());
            ps.executeUpdate();
        }
    }

    public void deleteDevice(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM iot_device WHERE device_id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<IoTDevice> searchDevices(String name, String type) throws SQLException, ClassNotFoundException {
        List<IoTDevice> results = new ArrayList<>();
        String sql = "SELECT * FROM iot_device WHERE device_name LIKE ? AND device_type LIKE ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + type + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IoTDevice d = new IoTDevice(
                        rs.getInt("device_id"),
                        rs.getString("device_name"),
                        rs.getString("device_type"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity")
                    );
                    results.add(d);
                }
            }
        }

        return results;
    }

    public IoTDevice getDeviceById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM iot_device WHERE device_id = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new IoTDevice(
                        rs.getInt("device_id"),
                        rs.getString("device_name"),
                        rs.getString("device_type"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity")
                    );
                }
            }
        }

        return null;
    }
}


package model.dao;

import model.IoTDevice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IoTDeviceDAO {
    private static final String URL = "jdbc:sqlite:/Users/marinakato/Intro to Software Dev/IoTBay/database/iotdb.db";
    private static final String DRIVER = "org.sqlite.JDBC";

    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL);
    }

    /** change stock by + */
    public void adjustQuantity(int deviceId, int delta) 
            throws SQLException, ClassNotFoundException {
        String selectSql = "SELECT quantity FROM iot_device WHERE device_id = ?";
        String updateSql = "UPDATE iot_device SET quantity = ? WHERE device_id = ?";

        try (Connection c = connect();
             PreparedStatement sel = c.prepareStatement(selectSql)) {

            // 1) fetch current quantity
            sel.setInt(1, deviceId);
            int currentQty = 0;
            try (ResultSet rs = sel.executeQuery()) {
                if (rs.next()) {
                    currentQty = rs.getInt("quantity");
                }
            }

            // 2) compute new quantity, clamp at 0
            int newQty = currentQty + delta;
            if (newQty < 0) {
                newQty = 0;
            }

            try (PreparedStatement upd = c.prepareStatement(updateSql)) {
                upd.setInt(1, newQty);
                upd.setInt(2, deviceId);
                upd.executeUpdate();
            }
        }
    }
    /** fetch one device */
    public IoTDevice getDeviceId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM iot_device WHERE device_id = ?";
        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
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
    
    public List<IoTDevice> getAllDevices() throws SQLException, ClassNotFoundException {
        List<IoTDevice> devices = new ArrayList<>();
        String sql = "SELECT * FROM iot_device";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Execute the query and process the result set
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
    }// Fetch all devices from the database

    public void addDevice(IoTDevice device) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iot_device (device_name, device_type, unit_price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, device.getName());
            ps.setString(2, device.getType());
            ps.setDouble(3, device.getPrice());
            ps.setInt(4, device.getQuantity());
            ps.executeUpdate();// Execute the insert statement
        }
    }// Add a new device to the database

    public void updateDevice(IoTDevice device) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE iot_device SET device_name=?, device_type=?, unit_price=?, quantity=? WHERE device_id=?";

        try (Connection conn = connect();// Establish a connection
             // Prepare the SQL statement
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, device.getName());
            ps.setString(2, device.getType());
            ps.setDouble(3, device.getPrice());
            ps.setInt(4, device.getQuantity());
            ps.setInt(5, device.getId());
            ps.executeUpdate();
        }
    }// Update an existing device in the database

    public void deleteDevice(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM iot_device WHERE device_id=?";// Delete a device by ID

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }// Delete a device from the database

    public List<IoTDevice> searchDevices(String name, String type) throws SQLException, ClassNotFoundException {
        List<IoTDevice> results = new ArrayList<>();
        String sql = "SELECT * FROM iot_device WHERE device_name LIKE ? AND device_type LIKE ?";// Search for devices by name and type
        // Use prepared statements to prevent SQL injection

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");// Use wildcard search for name
            ps.setString(2, "%" + type + "%");// Use wildcard search for type

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
    }// Search for devices by name and type

    public IoTDevice getDeviceById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM iot_device WHERE device_id = ?";
        // Fetch a device by its ID
        // Use prepared statements to prevent SQL injection
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
    }// Fetch a device by its ID
}


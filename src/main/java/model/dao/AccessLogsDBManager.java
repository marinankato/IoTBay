package model.dao;

import model.AccessLogs;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccessLogsDBManager {

    private Connection conn;

    public AccessLogsDBManager(Connection conn) throws SQLException {
        this.conn = conn;
    }

    // store user id, action (login/logout) and date 
    public void addAccessDate(int userId, String action, LocalDateTime accessDate) throws SQLException {
        String query = "INSERT INTO AccessLogs (userId, action, accessDate) VALUES (?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(query);
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = accessDate.format(formatter);
    
        ps.setInt(1, userId);
        ps.setString(2, action);
        ps.setString(3, formattedDate);
    
        int rowsInserted = ps.executeUpdate();
        System.out.println(rowsInserted + " access timestamp added for userId:" + userId + " at " + formattedDate);
    }

    public List<AccessLogs> getLogsByUserId(int userId) throws SQLException {
        List<AccessLogs> logs = new ArrayList<>();
        String sql = "SELECT * FROM AccessLogs WHERE userId = ? ORDER BY accessDate DESC";
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next()) {
            AccessLogs log = new AccessLogs();
            log.setLogId(rs.getInt("logId"));
            log.setUserID(rs.getInt("userId")); 
            log.setAction(rs.getString("action"));

            String dateStr = rs.getString("accessDate"); 
            LocalDateTime accessDate = LocalDateTime.parse(dateStr, formatter);
            log.setAccessDate(accessDate);

            logs.add(log);
        }
        return logs;
    }

    public List<AccessLogs> getLogsByUserIdAndDate(int userId, LocalDate date) throws SQLException {
        List<AccessLogs> logs = new ArrayList<>();
        String query = "SELECT * FROM AccessLogs WHERE userID = ? AND accessDate LIKE ? ORDER BY accessDate DESC";
        PreparedStatement ps = conn.prepareStatement(query);
    
        ps.setInt(1, userId);
        ps.setString(2, date.toString() + "%");  // to get date as "2025-05-15%"
    
        ResultSet rs = ps.executeQuery();
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
        while (rs.next()) {
            AccessLogs log = new AccessLogs();
            log.setLogId(rs.getInt("logId"));
            log.setUserID(rs.getInt("userID")); 
            log.setAction(rs.getString("action"));
    
            String dateStr = rs.getString("accessDate"); 
            LocalDateTime accessDate = LocalDateTime.parse(dateStr, formatter);
            log.setAccessDate(accessDate);
    
            logs.add(log);
        }
        return logs;
    }
}
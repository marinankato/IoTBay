package model.dao;

import model.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBUserManager {

    private Connection conn;
     // Format the datetime as a string SQLite can understand (e.g. "2025-05-14 18:32:00")
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DBUserManager(Connection conn) throws SQLException {
        this.conn = conn;
    }

    // Find user by email and password in the database
    public User findUser(String email, String password) throws SQLException {
        // setup the select sql query string
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);

        // execute this query using the statement field
        // add the results to a ResultSet
        ResultSet rs = ps.executeQuery();

        // search the ResultSet for a user using the parameters
        if (rs.next()) {
            // If a match is found, retrieve user data from the ResultSet
            int userId = rs.getInt("userID"); 
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNo = rs.getString("phoneNo");
            String role = rs.getString("role");
            String status = rs.getString("status");
            // Read as string then convert to LocalDateTime
            String loginDateStr = rs.getString("loginDate");
            LocalDateTime loginDate = loginDateStr != null ? LocalDateTime.parse(loginDateStr, formatter) : null;
            String logoutDateStr = rs.getString("logoutDate");
            LocalDateTime logoutDate = logoutDateStr != null ? LocalDateTime.parse(logoutDateStr, formatter) : null;
            
            // Create and return a new User object with the retrieved data
            return new User(userId, firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate, status);
        }
        return null;
    }

    // Add a user-data into the database
    public int addUser(String firstName, String lastName, String phoneNo, String email, String password, String role) throws SQLException {
        String query = "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); // also get the auto-generated userId
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, phoneNo);
        ps.setString(4, email);
        ps.setString(5, password);
        ps.setString(6, role);
        int rowsInserted = ps.executeUpdate();

        if (rowsInserted == 0) {
            throw new SQLException("Creating user failed, no rows inserted.");
        }

        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1); // this is the userId
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
    }

    // update a user's details in the database
    public void updateUser(String firstName, String lastName, String phoneNo, String email, String password, String originalEmail) throws SQLException {
        String sql = "UPDATE Users SET firstName = ?, lastName = ?, phoneNo = ?, email = ?, password = ? WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);

        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, phoneNo);
        ps.setString(4, email);         // new email if edited
        ps.setString(5, password);
        ps.setString(6, originalEmail); // used to find the correct row

        int rowsUpdated = ps.executeUpdate();
        System.out.println(rowsUpdated + " user fields updated.");
    }

    // MODIFY SO THE USER IS DEACTIVATED RATHER THAN DELETED
    // delete a user from the database
    public void deleteUser(String email) throws SQLException {
        String query = "DELETE FROM Users WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, email);
        int rowsDeleted = ps.executeUpdate();
        
        if (rowsDeleted > 0) {
            System.out.println("User with email " + email + " has been deleted.");
        } else {
            System.out.println("No user found with email " + email);
        }
    }

    // set the user's account to inactive
    public void deactivateUser(String email) throws SQLException {
        String sql = "UPDATE Users SET status = ? WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);

        String status = "inactive";
        ps.setString(1, status);
        ps.setString(2, email); 
        int rowsUpdated = ps.executeUpdate();
        System.out.println(rowsUpdated + " account deactivated for " + email);
    }

    //  set/update the user to hold their most recent login date/time 
    public void updateUserLoginDate(String email, LocalDateTime loginDateTime) throws SQLException {
        String query = "UPDATE Users SET loginDate = ? WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(query);
        String formattedDate = loginDateTime.format(formatter);

        ps.setString(1, formattedDate);
        ps.setString(2, email);

        int rowsUpdated = ps.executeUpdate();
        System.out.println(rowsUpdated + " login timestamp updated for " + email + " at " + formattedDate);
    }

    //  set/update the user to hold their most recent logout date/time
    public void updateUserLogoutDate(String email, LocalDateTime logoutDateTime) throws SQLException {
        String query = "UPDATE Users SET logoutDate = ? WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(query);
        String formattedDate = logoutDateTime.format(formatter);

        ps.setString(1, formattedDate);
        ps.setString(2, email);

        int rowsUpdated = ps.executeUpdate();
        System.out.println(rowsUpdated + " logout timestamp updated for " + email + " at " + formattedDate);
    }

    public User findUserByEmail(String email) throws SQLException{
        String sql = "SELECT * FROM Users WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // If a match is found, retrieve user data from the ResultSet
            int userId = rs.getInt("userID"); 
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNo = rs.getString("phoneNo");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String status = rs.getString("status");
            // Read as string then convert to LocalDateTime
            String loginDateStr = rs.getString("loginDate");
            LocalDateTime loginDate = loginDateStr != null ? LocalDateTime.parse(loginDateStr, formatter) : null;
            String logoutDateStr = rs.getString("logoutDate");
            LocalDateTime logoutDate = logoutDateStr != null ? LocalDateTime.parse(logoutDateStr, formatter) : null;
            
            // Create and return a new User object with the retrieved data
            return new User(userId, firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate, status);
        }
        return null;
    }

    //method to get all the users for the table
    public List<Map<String, String>> getAllUsers() {
        List<Map<String, String>> allUsers = new ArrayList<>();
        try {
            String query = "SELECT * from Users";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("userID", String.valueOf(rs.getInt("userID")));
                user.put("firstName", rs.getString("firstName"));
                user.put("lastName", rs.getString("lastName"));
                user.put("phoneNo", rs.getString("phoneNo"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                user.put("status", rs.getString("status"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return allUsers;
    }

    
    //delete users with user id
    public void deleteUser(int userID) {
        try {
            String query = "DELETE FROM Users WHERE userID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, String> getUserById(int id) {
    Map<String, String> user = new HashMap<>();
    String sql = "SELECT * FROM Users WHERE userID = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user.put("userID", String.valueOf(rs.getInt("userID")));
            user.put("firstName", rs.getString("firstName"));
            user.put("lastName", rs.getString("lastName"));
            user.put("phoneNo", rs.getString("phoneNo"));
            user.put("email", rs.getString("email"));
            user.put("role", rs.getString("role"));
            user.put("status", rs.getString("status"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}

        //update users search with id
        public void updateUser(int userID, String firstName, String lastName, String phoneNo, String email, String role, String status) {
            String sql = "UPDATE Users SET firstName=?, lastName=?, phoneNo=?, email=?, role=?, status=? WHERE userID=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, phoneNo);
                ps.setString(4, email);
                ps.setString(5, role);
                ps.setString(6, status);
                ps.setInt(7, userID);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    //create users properly
    public void createUser(String parameter, String parameter2, String parameter3, String parameter4, String parameter5,
            String parameter6, String parameter7) {
        String sql = "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, parameter);
            ps.setString(2, parameter2);
            ps.setString(3, parameter3);
            ps.setString(4, parameter4);
            ps.setString(5, parameter5);
            ps.setString(6, parameter6);
            ps.setString(7, parameter7);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //search users
   public List<Map<String, String>> searchUsers(String fname, String lname, String phone) {
    List<Map<String, String>> results = new ArrayList<>();
    String sql = "SELECT * FROM Users WHERE " +
                 "(firstName LIKE ? OR ? = '') AND " +
                 "(lastName LIKE ? OR ? = '') AND " +
                 "(phoneNo LIKE ? OR ? = '')";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + fname + "%");
        ps.setString(2, fname);
        ps.setString(3, "%" + lname + "%");
        ps.setString(4, lname);
        ps.setString(5, "%" + phone + "%");
        ps.setString(6, phone);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Map<String, String> user = new HashMap<>();
            user.put("userID", String.valueOf(rs.getInt("userID")));
            user.put("firstName", rs.getString("firstName"));
            user.put("lastName", rs.getString("lastName"));
            user.put("phoneNo", rs.getString("phoneNo"));
            user.put("email", rs.getString("email"));
            user.put("role", rs.getString("role"));
            user.put("status", rs.getString("status"));
            results.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return results;
}

}
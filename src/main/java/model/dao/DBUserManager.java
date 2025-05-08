package model.dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBUserManager {

    private Connection conn;

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
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNo = rs.getString("phoneNo");
            String role = rs.getString("role");
            // Date loginDate = rs.getDate("loginDate");
            // Date logoutDate = rs.getDate("logoutDate");

            // Create and return a new User object with the retrieved data
            // return new User(email, password);
            return new User(firstName, lastName, phoneNo, email, password, role);
        }
        return null;
    }

    // Add a user-data into the database
    public void addUser(String firstName, String lastName, String phoneNo, String email, String password, String role) throws SQLException {
        String query = "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(query);
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, phoneNo);
        ps.setString(4, email);
        ps.setString(5, password);
        ps.setString(6, role);
        int rowsInserted = ps.executeUpdate();
        System.out.println(rowsInserted + " user inserted");
    }

    // update a user details in the database
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

    public User findUserEmail(String email) throws SQLException{
        String sql = "SELECT * FROM Users WHERE email = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // If a match is found, retrieve user data from the ResultSet
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNo = rs.getString("phoneNo");
            String password = rs.getString("password");
            String role = rs.getString("role");

            return new User(firstName, lastName, phoneNo, email, password, role);
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
                user.put("id", String.valueOf(rs.getInt("id")));
                user.put("firstName", rs.getString("firstName"));
                user.put("lastName", rs.getString("lastName"));
                user.put("phoneNo", rs.getString("phoneNo"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return allUsers;
    }

}

package model.dao;

import model.User;
import java.sql.*;

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
            return new User(firstName, lastName, email, password, role, phoneNo);
        }
        return null;
    }

    // Add a user-data into the database
    public void addUser(String email, String name, String password, String gender, String favcol) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");

        ps.setString(1, email);
        ps.setString(2, password);
        int rs = ps.executeUpdate();
        System.out.println(rs + " user inserted");

    }

    // update a user details in the database
    public void updateUser(String email, String name, String password, String gender, String favcol)
            throws SQLException {
        // code for update-operation

    }

    // delete a user from the database
    public void deleteUser(String email) throws SQLException {
        // code for delete-operation

    }

    public void createUser(String firstName, String lastName, String phoneNo, String email, String password,
            String role) {
        try {
            PreparedStatement ps = this.conn.prepareStatement(
                    "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNo);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, role);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

}

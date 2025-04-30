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
        PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM \"user\" WHERE email = ? AND password = ?");
        ps.setString(1, email);
        ps.setString(2, password);

        // execute this query using the statement field
        // add the results to a ResultSet
        ResultSet rs = ps.executeQuery();

        // search the ResultSet for a user using the parameters
        if (rs.next()){
            // If a match is found, retrieve user data from the ResultSet
            String firstName = rs.getString("firstName");
            String lastName= rs.getString("lastName");
            String phoneNo = rs.getString("phoneNo");
            String role = rs.getString("role");
            Date loginDate = rs.getDate("loginDate");
            Date logoutDate = rs.getDate("logoutDate");

            // Create and return a new User object with the retrieved data
            return new User(email, password);
            // return new User(userID, firstName, lastName, email, password, role, phoneNo, loginDate, logoutDate);
        }

        return null;
    }

    // Add a user-data into the database
    public void addUser(String email, String name, String password, String gender, String favcol) throws SQLException { // code
                                                                                                                        // for
                                                                                                                        // add-operation
        // st.executeUpdate("sql query");

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

}

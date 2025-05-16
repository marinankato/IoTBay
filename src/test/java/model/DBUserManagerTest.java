package model;

import model.dao.DBUserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;

public class DBUserManagerTest {

    private Connection conn;
    private DBUserManager dbUserManager;

    @Before
    public void setUp() throws Exception {
        // Create an in-memory SQLite database for testing
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        dbUserManager = new DBUserManager(conn);

        // Create Users table
        String createTableSQL = "CREATE TABLE Users (" +
                "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "phoneNo TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "role TEXT, " +
                "loginDate TEXT, " +
                "logoutDate TEXT)";
        conn.createStatement().execute(createTableSQL);

        // Insert a test user
        String insertUserSQL = "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role) " +
                "VALUES ('John', 'Doe', '0404111222', 't@test.com', 'password', 'customer')";
        conn.createStatement().execute(insertUserSQL);
    }

    @After
    public void tearDown() throws Exception {
        conn.close();  // Clean up the in-memory database
    }

    @Test
    public void testFindUserSuccess() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "password");

        assertNotNull("User should be found with correct credentials", user);
        assertEquals("t@test.com", user.getEmail());
        assertEquals("customer", user.getRole());
    }

    @Test
    public void testFindUserFail() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "wrongpassword");

        assertNull("User should not be found with incorrect password", user);
    }
}
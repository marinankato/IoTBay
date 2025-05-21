package model;

import model.dao.AccessLogsDBManager;
import model.dao.DBUserManager;
import org.junit.*;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class DBUserManagerTest {

    private Connection conn;
    private DBUserManager dbUserManager;
    private AccessLogsDBManager logsManager;

    @Before
    public void setUp() throws Exception {
        // Create an in-memory SQLite database for testing
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        dbUserManager = new DBUserManager(conn);
        logsManager = new AccessLogsDBManager(conn);

        // Create Users table
        String createUserTable = "CREATE TABLE Users (" +
                "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "phoneNo TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "role TEXT, " +
                "loginDate TEXT, " +
                "logoutDate TEXT," +
                "status TEXT)";
        conn.createStatement().execute(createUserTable);

        // Create AccessLogs table
        String createAccessLogsTable = "CREATE TABLE AccessLogs (" +
                "logID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, action TEXT, accessDate TEXT)";
        conn.createStatement().execute(createAccessLogsTable);
        
        // Insert a test user
        String insertUserSQL = "INSERT INTO Users (firstName, lastName, phoneNo, email, password, role, loginDate, logoutDate, status) " +
                "VALUES ('Bob', 'Watts','0411222333','t@test.com','password', 'admin', '2025-05-18 14:17:07', '2025-05-18 14:47:07', 'active')";
        conn.createStatement().execute(insertUserSQL);
    }

    @After
    public void tearDown() throws Exception {
        conn.close(); // Clean up the in-memory database
    }

    // user story 101 (success)
    @Test
    public void testFindUserSuccess() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "password");

        assertNotNull("User should be found with correct credentials", user);
        assertEquals("t@test.com", user.getEmail());
        assertEquals("admin", user.getRole());
    }

    // user story 101 (fail)
    @Test
    public void testFindUserFail() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "wrongpassword");

        assertNull("User should not be found with incorrect password", user);
    }

    // user story 102 (success)
    @Test
    public void testAddUserSuccess() throws SQLException {
        int userId = dbUserManager.addUser("Jane", "Smith", "0411222333", "jane@test.com", "pass123", "customer");

        assertTrue("User ID should be greater than 0", userId > 0);
        User user = dbUserManager.findUser("jane@test.com", "pass123");
        assertNotNull("Newly added user should be found", user);
    }

    // user story 102 (fail)
    @Test(expected = SQLException.class)
    public void testAddUserDuplicateEmail() throws SQLException {
        dbUserManager.addUser("Duplicate", "User", "0499999999", "t@test.com", "newpass", "customer");
    }

    // user story 901 (success)
    @Test
    public void testUpdateLogoutTimestamp() throws SQLException {
        LocalDateTime logoutTime = LocalDateTime.now();
        dbUserManager.updateUserLogoutDate("t@test.com", logoutTime);

        PreparedStatement ps = conn.prepareStatement("SELECT logoutDate FROM Users WHERE email = ?");
        ps.setString(1, "t@test.com");
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next());
        assertNotNull("Logout date should be set", rs.getString("logoutDate"));
    }

    // user story 902 (success)
    @Test
    public void testAddAndRetrieveAccessLog() throws SQLException {
        // First get user ID
        User user = dbUserManager.findUser("t@test.com", "password");
        assertNotNull("User should exist", user);

        // Add an access log
        logsManager.addAccessDate(user.getUserID(), "login", LocalDateTime.of(2025, 5, 19, 10, 0));

        // Check logs directly
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM AccessLogs WHERE userId = ?");
        ps.setInt(1, user.getUserID());
        ResultSet rs = ps.executeQuery();

        assertTrue("Log entry should exist", rs.next());
        assertEquals("Action should be login", "login", rs.getString("action"));
    }

    @Test
    public void testRetrieveAllAccessLogs() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "password");
        assertNotNull("User must exist", user);

        logsManager.addAccessDate(user.getUserID(), "login", LocalDateTime.now());
        logsManager.addAccessDate(user.getUserID(), "logout", LocalDateTime.now());

        List<AccessLogs> logs = logsManager.getLogsByUserId(user.getUserID());

        assertTrue("Should return at least two logs", logs.size() >= 2);
    }

    // user story 903 (success)
    @Test
    public void testFilterAccessLogByDate() throws SQLException {
        User user = dbUserManager.findUser("t@test.com", "password");
        assertNotNull("User must exist", user);

        // Insert test access logs using DBUserManager
        logsManager.addAccessDate(user.getUserID(), "login", LocalDateTime.of(2025, 5, 19, 9, 0));
        logsManager.addAccessDate(user.getUserID(), "logout", LocalDateTime.of(2025, 5, 18, 18, 0));

        // Use AccessLogsDBManager to test retrieval by date
        List<AccessLogs> logs = logsManager.getLogsByUserIdAndDate(user.getUserID(), LocalDate.of(2025, 5, 19));

        assertEquals("Should only return logs for the matching date", 1, logs.size());
        assertEquals("Action should be login", "login", logs.get(0).getAction());
        assertEquals("Date should match", 19, logs.get(0).getAccessDate().getDayOfMonth());
    }
}
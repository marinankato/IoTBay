package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


// package model.dao;

// import java.sql.Connection;

// public abstract class DBUser {

//     // this path URL will differ for everyone, change to your path to db
//     protected String URL = "jdbc:sqlite:/Users/marinakato/Intro to Software Dev/IoTBay-1/database/iotdb.db";  
//     protected String driver = "org.sqlite.JDBC";
//     protected Connection conn;

// }
// make sure you have above code in a file called DBUser.java
public class DBUserConnector extends DBUser {

    public DBUserConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(URL);

        // Set the connection to use WAL mode
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
        }

        System.out.println(" WAL mode applied (in DBUserConnector)");
    }

    public Connection openConnection() {
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
            System.out.println(" Database connection closed.");
        }
    }
}

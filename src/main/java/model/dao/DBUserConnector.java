package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

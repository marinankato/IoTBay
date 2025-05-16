package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOrderConnector {
    protected String url    = "jdbc:sqlite:/Users/jquisumbing/IoTBay/database/iotdb.db";
    protected String driver = "org.sqlite.JDBC";

    protected Connection conn;

    public DBOrderConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.conn = DriverManager.getConnection(url);
    }

    public Connection openConnection() {
        System.out.println(">>> [DBOrderConnector] opening DB at: " + url);
        return this.conn;
       // return conn;
    }
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    
}
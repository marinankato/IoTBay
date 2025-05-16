package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// package model.dao;
// import java.sql.Connection;

// public class AccessLogsDB {
    
//     // this path URL will differ for everyone, change to your path to db
//     protected String URL = "jdbc:sqlite:/Users/marinakato/Intro to Software Dev/IoTBay-1/database/iotdb.db";
//     protected String driver = "org.sqlite.JDBC";
//     protected Connection conn;

// }
// add above to a new file called AccessLogsDB.java with your path URL to DB

public class AccessLogsDBConnector extends AccessLogsDB {
    public AccessLogsDBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(URL);
    }
    
    public Connection openConnection() {
        return this.conn;
    }
    public void closeConnection() throws SQLException {
        this.conn.close();
    }
}
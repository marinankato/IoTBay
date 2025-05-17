package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// package model.dao;

// import java.sql.Connection;

// public abstract class DBOrder {

//     // this path URL will differ for everyone, change to your path to db
//     protected String URL = "jdbc:sqlite:/Users/marinakato/Intro to Software Dev/IoTBay-1/database/iotdb.db";
//     protected String driver = "org.sqlite.JDBC";
//     protected Connection conn;

// }
// add above commented code to a new file called DBOrder.java

public class DBOrderConnector extends DBOrder{
   
    public DBOrderConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.conn = DriverManager.getConnection(url);
    }

    public Connection openConnection() {
       return conn;
    }
    
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
    
}
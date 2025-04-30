package model.dao;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

public class DBUserConnector extends DBUser {

    public DBUserConnector() throws ClassNotFoundException, SQLException {

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
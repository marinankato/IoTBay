package model.dao;

import java.sql.Connection;

/* Super class of DAO classes that stores the database information */
public abstract class DBOrder {

    // this path URL will differ for everyone, change to your path to db
    protected String URL = "jdbc:sqlite:/Users/marinakato/Intro to Software Dev/IoTBay/database/iotdb.db";
    protected String driver = "org.sqlite.JDBC";
    protected Connection conn;

}
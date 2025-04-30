package model.dao;

import java.sql.Connection;

/* Super class of DAO classes that stores the database information */
public abstract class DB {

    // this path URL will differ for everyone, to change to complete path
    protected String URL = "jdbc:sqlite:database/iotdb.db";
    protected String driver = "org.sqlite.JDBC"; 
    protected Connection conn; 

}
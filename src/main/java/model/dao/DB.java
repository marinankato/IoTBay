package model.dao;

import java.sql.Connection;

/* Super class of DAO classes that stores the database information */
public abstract class DB {

    protected String URL = "jdbc:sqlite:database/iotdb.db";
    protected String driver = "org.sqlite.JDBC"; 
    protected Connection conn; 

}
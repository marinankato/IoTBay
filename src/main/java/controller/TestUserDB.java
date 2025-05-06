package controller;

import java.sql.*;

import java.util.logging.*;

import model.*;

import model.dao.*;

public class TestUserDB {

    public static void main(String[] args) {

        try {

            DBUserConnector connector = new DBUserConnector();

            Connection conn = connector.openConnection();

            DBUserManager db = new DBUserManager(conn);

            System.out.print("User email: ");

            User user = db.findUser("admin@gmail.com", "password123");

            if (user != null) {
                System.out.println(user);
            } else {
                System.out.println("User does not exits.");
            }

            connector.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {

            Logger.getLogger(TestUserDB.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
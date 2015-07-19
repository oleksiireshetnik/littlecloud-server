package com.getupandgo;

import java.sql.*;

/**
 * Created by getupandgo on 7/19/15.
 */
public class DatabaseDriver {

    DatabaseDriver(){
        try {
            this.initConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.createLoginTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createLoginTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USERS " +
                           "USER_ID INTEGER, " +
                           "EMAIL CHAR(20), PASS CHAR(32), " +
                           "USERNAME CHAR(32)");

    }

    void initConnection() throws ClassNotFoundException, SQLException {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
    }

    void newUser(UserInfo user) throws SQLException {
        String query = "SELECT EMAIL " +
                       "FROM USERS " +
                       "WHERE EMAIL = " + user.email + " ";
        ResultSet rs = stmt.executeQuery(query);

        if(!rs.first()){
            //create user table
        }
        else {
            //nothing
        }
    }

    void setUser(String username) {
        this.USER = username;
    }

    void setPass(String password) {
        this.PASS = password;
    }

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/EMP";

    private static String USER = "username";
    private static String PASS = "password";

    private Connection conn;
    private Statement stmt;
}

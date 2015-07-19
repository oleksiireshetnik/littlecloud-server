package com.getupandgo;

import java.sql.*;

/**
 * Created by getupandgo on 7/19/15.
 */
public class DatabaseDriver {

    DatabaseDriver(){
        try {
            this.initConnection();
            this.createLoginTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createLoginTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AUTHENTICATION " +
                "USER_ID INTEGER AUTO_INCREMENT PRIMARY KEY, " +
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
                       "FROM AUTHENTICATION WHERE EMAIL = \"" + user.email + "\"";

        ResultSet rs = stmt.executeQuery(query);

        if(!rs.first()){
            stmt.executeUpdate("CREATE TABLE USER_DATA" +
                    "USER_ID INTEGER, " +
                    "HASH_SUM INTEGER, " +
                    "VARCHAR(40) FILENAME, CHAR(20) DATA"); //20 so random
        }
        else {
            //nothing
        }
    }

    void setUser(String username) {
        USER = username;
    }

    void setPass(String password) {
        PASS = password;
    }

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/EMP";

    private static String USER = "username";
    private static String PASS = "password";

    private Connection conn;
    private Statement stmt;
}

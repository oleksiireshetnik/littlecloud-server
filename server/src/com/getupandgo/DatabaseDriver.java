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
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS REGISTRATION " +
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

    private ResultSet checkEmail(String email) throws SQLException {
        String query = "SELECT EMAIL " +
                "FROM REGISTRATION WHERE EMAIL = \"" + email + "\"";

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    void registration(UserInfo user) throws SQLException {

        ResultSet emails = checkEmail(user.email);

        if(!emails.first()){

            stmt.executeUpdate("INSERT INTO REGISTRATION " +
                    "VALUES ('French_Roast', 49, 8.99, 0, 0)");

            stmt.executeUpdate("CREATE TABLE USER_DATA" +
                    "USER_ID INTEGER, " +
                    "HASH_SUM INTEGER, " +
                    "VARCHAR(40) FILENAME, CHAR(20) DATA"); //20 so random
        }
        else System.out.println("User exists");
    }

    void login(UserInfo user) throws SQLException {

        ResultSet emails = checkEmail(user.email);

        if(!emails.first()){
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

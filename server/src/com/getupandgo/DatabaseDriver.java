package com.getupandgo;

import java.sql.*;
import java.util.logging.Logger;

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

            LOGGER.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            LOGGER.info("Creating statement...");
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

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO REGISTRATION (USER_ID, EMAIL, PASS, USERNAME) " +
                            "VALUES(NULL, ?, ?, ?)");

            pstmt.setString(1, user.email);
            pstmt.setString(2, user.pass);
            pstmt.setString(3, user.username);

            int rows = pstmt.executeUpdate(); // "rows" save the affected rows

            System.out.println(rows);

            stmt.executeUpdate("CREATE TABLE USER_DATA" +
                    "USER_ID INTEGER, " +
                    "HASH_SUM INTEGER, " +
                    "VARCHAR(40) FILENAME, CHAR(20) DATA"); //20 so random
        }
        else LOGGER.info("User exists");
    }

    void login(UserInfo user) throws SQLException {

        ResultSet emails = checkEmail(user.email);

        if(emails.first()){
            //do smth
        }
        else {
            //send error to site
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

    private static Logger LOGGER = Logger.getLogger(ConnectionDriver.class.getName());
}

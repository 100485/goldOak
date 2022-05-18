package services;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gold_oak?useTimezone=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private Connection conn = null;

    public Connection getConnection() {
        if(conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME,  DB_PASSWORD);
                if(conn == null) {
                    System.out.println("\n\nCould NOT connect to the database!\n\n");
                }
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void closeConnection() throws SQLException {
        if(conn != null) {
            conn.close();
        }
    }
}

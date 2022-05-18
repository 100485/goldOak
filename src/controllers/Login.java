package controllers;

import services.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private final Database db = new Database();

    public boolean userExists(String username, String password) {
        Connection conn = db.getConnection();
        final String query = "SELECT * FROM `admin` WHERE `username` = ? AND `password` = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            // if the query returns some result
            if(res.next())
                return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

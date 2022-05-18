package controllers;

import app.Company;
import services.Database;
import services.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static services.Helper.deleteRecord;

public class Admin {

    private final Database db = new Database();

    public Boolean deleteCompany(int companyId) {
        Connection conn = db.getConnection();
        return deleteRecord(conn, "company", "id", companyId);
    }

    public Boolean createCompany(String name, String details) {
        Connection conn = db.getConnection();
        String query = "INSERT INTO `company` (`name`, `details`) VALUES(?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, details);

            final int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                stmt.close();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateCompany(Company company) {
        Connection conn = db.getConnection();
        String query = "UPDATE `company` SET `name` = ?, `details` = ? WHERE `id` = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getDetails());
            stmt.setInt(3, company.getId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                stmt.close();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, String>> queryPrices() {
        final String query = "SELECT " +
                "`price`.`company_id`, " +
                "`company`.`name` AS company, " +
                "`price`.`insurance_type_id`, " +
                "`insurance_type`.`name` AS insurance_type, " +
                "`price`.`subscription_id`, " +
                "`subscription`.`name` AS subscription, " +
                "`price`.`age_range_id`, " +
                "`age_range`.`age_min`, " +
                "`age_range`.`age_max`, " +
                "`price`.`role_id`, " +
                "`role`.`name` AS role, " +
                "`price`.`amount` " +

                "FROM `price` " +
                "LEFT JOIN `company` ON `company`.`id` = `price`.`company_id` " +
                "LEFT JOIN `insurance_type` ON `insurance_type`.`id` = `price`.`insurance_type_id` " +
                "LEFT JOIN `subscription` ON `subscription`.`id` = `price`.`subscription_id` " +
                "LEFT JOIN `age_range` ON `age_range`.`id` = `price`.`age_range_id` " +
                "LEFT JOIN `role` ON `role`.`id` = `price`.`role_id`" +
                "ORDER BY `price`.`insurance_type_id` ASC;";

        List<Map<String, String>> records = new ArrayList<>();
        Connection conn = db.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while (res.next()) {
                Map<String, String> record = new HashMap<>();
                final String ageRange = "[" +
                        res.getString("age_min") + "-" +
                        res.getString("age_max") +
                        "]";
                record.put("company_id", String.valueOf(res.getInt("company_id")));
                record.put("company", res.getString("company"));
                record.put("insurance_type_id", String.valueOf(res.getInt("insurance_type_id")));
                record.put("insurance_type", res.getString("insurance_type"));
                record.put("subscription_id", String.valueOf(res.getInt("subscription_id")));
                record.put("subscription", res.getString("subscription"));
                record.put("age_range_id", String.valueOf(res.getInt("age_range_id")));
                record.put("age_range", ageRange);
                record.put("role_id", String.valueOf(res.getInt("role_id")));
                record.put("role", res.getString("role"));
                record.put("amount", res.getString("amount"));
                records.add(record);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public ArrayList<Company> queryCompanies() {
        Connection conn = db.getConnection();
        final String query = "SELECT * FROM `company`";
        ArrayList<Company> companyList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while (res.next()) {
                int companyId = res.getInt("id");
                String companyName = res.getString("name");
                String companyDetails = res.getString("details");
                companyList.add(new Company(companyId, companyName, companyDetails));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

}

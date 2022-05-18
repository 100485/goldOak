package controllers;

import app.*;
import app.Company;
import services.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {

    // create a database instance to connect to db
    private final Database db = new Database();

    /**
     * no args
     * @return a list of all companies stored in the database
     * arrayList = dynamic array of objects (string, integer, class instances)
     */
    public ArrayList<Company> queryCompanies() {
        // get connection from db
        Connection conn = db.getConnection();

        // database query
        String query = "SELECT * FROM `company`";

        // initialize an empty dynamic array of 'Company' objects
        ArrayList<Company> companyList = new ArrayList<>();

        try {
            // prepare sql statement to be executed
            PreparedStatement stmt = conn.prepareStatement(query);

            // execute the statement and store the result in a result set
            ResultSet res = stmt.executeQuery();

            // make statement close itself after it completes execution
            stmt.closeOnCompletion();

            // loop through result set rows
            while (res.next()) {
                final int companyId = res.getInt("id");
                final String name = res.getString("name");
                final String details = res.getString("details");

                // create a new company instance and add it to the 'companyList'
                companyList.add(new Company(companyId, name, details));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // return the 'companyList'
        return companyList;
    }

    /**
     * no args
     * @return arrayList of all roles stored in the database
     */
    public ArrayList<Role> queryRoles() {
        Connection conn = db.getConnection();
        String query = "SELECT * FROM `role`";
        ArrayList<Role> roleList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while (res.next()) {
                final int id = res.getInt("id");
                final String name = res.getString("name");
                roleList.add(new Role(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }

    public ArrayList<InsuranceType> queryInsuranceTypes() {
        Connection conn = db.getConnection();
        String query = "SELECT * FROM `insurance_type`";
        ArrayList<InsuranceType> insuranceTypeList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while (res.next()) {
                final int id = res.getInt("id");
                final String name = res.getString("name");
                insuranceTypeList.add(new InsuranceType(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insuranceTypeList;
    }

    public ArrayList<Subscription> querySubscriptions() {
        Connection conn = db.getConnection();
        String query = "SELECT * FROM `subscription`";
        ArrayList<Subscription> subscriptionList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while (res.next()) {
                final int id = res.getInt("id");
                final String name = res.getString("name");
                final double limit = res.getDouble("limit_per_family");
                subscriptionList.add(new Subscription(id, name, limit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptionList;
    }


    /**
     * compare the age with tha age ranges we have saved in the database
     * @param age - the input age of the person
     * @param insuranceType - used to return the right age ranges
     * @return ageRangeId - id of the age range the person fall into
     */
    public int getAgeRange(int age, InsuranceType insuranceType) {
        Connection conn = db.getConnection();
        int ageId = 0;

        // omit age range [0, 40] for 'inpatient'
        String query = "SELECT `id`, `age_min`, `age_max` FROM `age_range` " +
                "WHERE `id` NOT IN " +
                "(SELECT `id` FROM `age_range` WHERE `age_min` = 0 AND `age_max` = 40) " +
                "ORDER BY `age_max` DESC;";

        // for 'outpatient'
        if(insuranceType.getDescription().equalsIgnoreCase("outpatient"))
            query = "SELECT `id`, `age_min`, `age_max` FROM `age_range` " +
                    "WHERE `age_max` >= 40 AND `age_min` != 31 " +
                    "ORDER BY `age_max` DESC;";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            stmt.closeOnCompletion();

            while(res.next()) {
                final int ageMin = res.getInt("age_min");
                final int ageMax = res.getInt("age_max");
                if(age >= ageMin && age <= ageMax) {
                    ageId = res.getInt("id");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ageId;
    }


    public List<Map<String, String>> calculateInsurance(int company, InsuranceType insuranceType, ArrayList<Person> people) {
        Connection conn = db.getConnection();
        List<Map<String, String>> records = new ArrayList<>();
        List<Person> peopleCleaned = new ArrayList<>(); // to keep one child
        int numberOfKids = 0;

        // keep only one child to make the query efficient
        for(Person person : people) {
            if(person == null)
                continue;
            if(!person.getRole().getName().equalsIgnoreCase("child")) // if the person is NOT a child, add them
                peopleCleaned.add(person);
            else {
                if(numberOfKids < 1) // if we haven't added any child yet, add
                    peopleCleaned.add(person);
                numberOfKids++;
            }
        }

        StringBuilder query = new StringBuilder("SELECT " +
                "`role`.`name` as `role`," +
                "`age_range`.`age_min`," +
                "`age_range`.`age_max`, " +
                "`price`.`amount`," +
                "`subscription`.`name` AS `subscription`" +

                "FROM `price` " +
                "LEFT JOIN `role` ON `price`.`role_id` = `role`.`id` " +
                "LEFT JOIN `age_range` ON `price`.`age_range_id` = `age_range`.`id` " +
                "LEFT JOIN `subscription` ON `price`.`subscription_id` = `subscription`.`id` " +

                "WHERE `price`.`company_id` = ? " +
                "AND `price`.`insurance_type_id` = ? " +
                "AND (");

        // add placeholders in the query for all the 'cleaned' people
        // role_id and the age_range_id for every single person
        for(int i = 0; i < peopleCleaned.size(); i++) {
            query.append("(`price`.`role_id` = ? AND `price`.`age_range_id` = ?) ");
            if(i != peopleCleaned.size() - 1)
                query.append("OR ");
        }
        query.append(") ORDER BY `price`.`role_id` ASC");
        // System.out.println("\n" + query + "\n");

        try {
            PreparedStatement stmt = conn.prepareStatement(query.toString());
            stmt.closeOnCompletion();
            stmt.setInt(1, company);
            stmt.setInt(2, insuranceType.getId());

            int paramCount = 2; // the parameters already passed in: company, insuranceType

            for(Person person : peopleCleaned) {
                final int ageId = getAgeRange(person.getAge(), insuranceType);
                final int roleId = person.getRole().getId();
                stmt.setInt(++paramCount, roleId);
                stmt.setInt(++paramCount, ageId);
            }
            // System.out.println("\n" + stmt + "\n");

            ResultSet res = stmt.executeQuery();
            String prevRole = "";
            int counter = 0; // number of records retrieved from database
            int kidIdx = -1; // index of the child data map in the list of records retrieved from database

            while (res.next()) {
                if(!res.getString("role").equals(prevRole)) {
                    Map<String, String> record = new HashMap<>();
                    final String role = res.getString("role");
                    final int ageMin = res.getInt("age_min");
                    final int ageMax = res.getInt("age_max");
                    final String ageRange = "[" + ageMin + "-" + ageMax + "]";

                    record.put("role", role);
                    record.put("age_min", String.valueOf(ageMin));
                    record.put("age_max", String.valueOf(ageMax));
                    record.put("age_range", ageRange);
                    record.put(
                            res.getString("subscription").toLowerCase(),
                            String.valueOf(res.getDouble("amount"))
                    );

                    records.add(record);
                    prevRole = role;
                    if(role.equalsIgnoreCase("child"))
                        kidIdx = counter;
                    counter++;
                }
                else {
                    records.get(records.size() - 1).put(
                            res.getString("subscription").toLowerCase(),
                            String.valueOf(res.getDouble("amount"))
                    );
                }
            }

            // duplicate child record in case of many kids
            if(numberOfKids > 1 && kidIdx != -1) {
                for (int i = 0; i < numberOfKids - 1; i++) {
                    records.add(records.get(kidIdx));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

}

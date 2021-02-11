package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ScientificWorkDAO {
    private static ScientificWorkDAO instance;
    private Connection conn;

    private PreparedStatement getUserFromLoginQuery, getGenderQuery;

    public static ScientificWorkDAO getInstance() {
        if (instance == null) instance = new ScientificWorkDAO();
        return instance;
    }
    private ScientificWorkDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:scientificWorks.db");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            getUserFromLoginQuery = conn.prepareStatement("SELECT person.*, u.username, u.password, u.mail, u.image FROM user u, person WHERE u.id=person.id AND u.username=? AND u.password=?");
        } catch (SQLException exception) {
            regenerateDatabase();
            try {
                getUserFromLoginQuery = conn.prepareStatement("SELECT person.*, u.username, u.password, u.mail, u.image FROM user u, person WHERE u.id=person.id AND u.username=? AND u.password=?");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            getGenderQuery = conn.prepareStatement("SELECT gender.title FROM user, gender WHERE gender.user_id=user.id AND user.id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeInstance() {
        instance.close();
    }

    private void close() {
        try {
            conn.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //setup
    private void regenerateDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("scientific.db.sql"));
            StringBuilder sqlQuery = new StringBuilder();
            while (input.hasNext()) {
                sqlQuery.append(input.nextLine());
                if (sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery.toString());
                        sqlQuery = new StringBuilder();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //getter methods
    public String getGender(int id) {
        try {
            getGenderQuery.setInt(1, id);
            return String.valueOf(getGenderQuery.executeQuery());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public User getUser(String username, String password) throws IllegalUserException {
        try {
            getUserFromLoginQuery.setString(1, username);
            getUserFromLoginQuery.setString(2, password);

            ResultSet rs = getUserFromLoginQuery.executeQuery();
            if (!rs.next()) {
                throw new IllegalUserException("Account not found");
            }
            return new User(rs.getInt("id"), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5).equals("male") ? Gender.MALE : Gender.FEMALE, rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}

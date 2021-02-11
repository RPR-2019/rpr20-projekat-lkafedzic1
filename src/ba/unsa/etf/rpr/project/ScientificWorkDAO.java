package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class ScientificWorkDAO {
    private static ScientificWorkDAO instance;
    private Connection conn;

    private PreparedStatement getUserFromLoginQuery;

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
    public User getUser(String username, String password) throws IllegalUserException {
        try {
            getUserFromLoginQuery.setString(1, username);
            getUserFromLoginQuery.setString(2, password);

            ResultSet rs = getUserFromLoginQuery.executeQuery();
            if (!rs.next()) {
                throw new IllegalUserException("Account not found");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}

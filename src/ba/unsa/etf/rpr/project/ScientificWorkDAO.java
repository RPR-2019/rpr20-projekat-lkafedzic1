package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ScientificWorkDAO {
    private static ScientificWorkDAO instance = null;
    private Connection conn;

    private PreparedStatement getUserFromLoginQuery, getWorksQuery, getUsersQuery, getGenderQuery, getFieldsQuery, getTypesQuery;

    public static ScientificWorkDAO getInstance() {
        if (instance == null) instance = new ScientificWorkDAO();
        return instance;
    }

    public static void initialize() {
        instance = new ScientificWorkDAO();
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
            getFieldsQuery = conn.prepareStatement("SELECT * FROM field");
            getTypesQuery = conn.prepareStatement("SELECT * FROM publication_type");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeInstance() {
        instance.close();
    }

/*    public static ArrayList<ScientificWork> scientificWorks() {
        ArrayList<ScientificWork> result = new ArrayList<>();
        try {
            ResultSet rs = getWorksQuery.executeQuery();
            while (rs.next()) {
                ScientificWork work = new ScientificWork();//dodati iz rs atribute
                result.add(work);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public static Object users() {

    }*/

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


    public ObservableList<FieldOfStudy> getFields() {
        ObservableList<FieldOfStudy> fields = FXCollections.observableArrayList();
        try {
            ResultSet rs = getFieldsQuery.executeQuery();
            while(rs.next()) {
                FieldOfStudy field = new FieldOfStudy(rs.getInt(1), rs.getString(2));
                fields.add(field);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return fields;
    }

    public ObservableList<PublicationType> getTypes() {
        ObservableList<PublicationType> types = FXCollections.observableArrayList();
        try {
            ResultSet rs = getTypesQuery.executeQuery();
            while(rs.next()) {
                PublicationType type = new PublicationType(rs.getInt(1), rs.getString(2));
                types.add(type);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return types;
    }
}

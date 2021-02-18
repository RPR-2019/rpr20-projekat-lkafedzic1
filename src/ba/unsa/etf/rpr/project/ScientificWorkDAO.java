package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

import javax.xml.transform.Result;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ScientificWorkDAO {
    private static ScientificWorkDAO instance = null;
    private Connection conn;
    private ArrayList<User> userList;

    private PreparedStatement getLoginQuery, getRoleFromIdQuery, getUserFromLoginQuery, getScientificWork, getWorkPopulationInfoQuery, changePasswordQuery,getWorksQuery, getUsersQuery, getGenderQuery, getFieldsQuery, getTypesQuery, addFieldQuery, addTypeQuery, maxIdField, maxIdType;

    public static ScientificWorkDAO getInstance() {
        if (instance == null) instance = new ScientificWorkDAO();
        return instance;
    }

    private ScientificWorkDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:scientific.db");
        } catch (SQLException exception) {
            //Error with connection
            exception.printStackTrace();
        }
        try {
            getUserFromLoginQuery = conn.prepareStatement("SELECT person.*, u.username, u.password, u.email, u.image FROM user u, person WHERE u.id=person.id AND u.username=? AND u.password=?");
        } catch (SQLException exception) {
            //If there is no database, make it
            regenerateDatabase();
            try {
                //Testing
                getUserFromLoginQuery = conn.prepareStatement("SELECT person.*, u.username, u.password, u.email, u.image FROM user u, person WHERE u.id=person.id AND u.username=? AND u.password=?");
            } catch (SQLException throwables) {
                //There is a fatal error
                throwables.printStackTrace();
            }
        }

      try {
          getLoginQuery = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?" );
          getRoleFromIdQuery = conn.prepareStatement("SELECT title FROM role WHERE id=?");
          getFieldsQuery = conn.prepareStatement("SELECT * FROM field");
          getTypesQuery = conn.prepareStatement("SELECT * FROM publication_type");
          getWorkPopulationInfoQuery = conn.prepareStatement("SELECT sw.title, person.first_name || ' ' || person.last_name, sw.year, field.title, publication_type.title, sw.additional FROM scientific_work sw, author, person, scientific_work_author swa, field, publication_type WHERE sw.id=swa.scientific_work_id AND swa.author_id=author.id AND author.person_id=person.id AND sw.field=field.id AND sw.type=publication_type.id;");
          changePasswordQuery = conn.prepareStatement("UPDATE user SET password=? WHERE username=?");
          /* getUserFromLoginQuery = conn.prepareStatement("SELECT person.*, u.username, u.password, u.email, u.image FROM user u, person WHERE u.id=person.id AND u.username=? AND u.password=?");
          System.out.println("Evo me evo");
            getGenderQuery = conn.prepareStatement("SELECT gender.title FROM user, gender WHERE gender.user_id=user.id AND user.id=?");

            addFieldQuery = conn.prepareStatement("INSERT INTO field VALUES(?,?)");
            addTypeQuery = conn.prepareStatement("INSERT INTO field VALUES(?,?)");
            maxIdField = conn.prepareStatement("SELECT max(id)+1 FROM field");
            maxIdType = conn.prepareStatement("SELECT max(id)+1 FROM publication_type");*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    private void close() {
        try {
            conn.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

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

    public boolean isAccount(String username, String password) {
        try {
            getLoginQuery.setString(1, username);
            getLoginQuery.setString(2, password);
            ResultSet rs = getLoginQuery.executeQuery();
            System.out.println("tu");
            return rs.next();
        } catch (SQLException exception) {
            return false;
        }
    }

    private String getRole(int role_id) {
        try {
            getRoleFromIdQuery.setInt(1,role_id);
            ResultSet rs = getRoleFromIdQuery.executeQuery();
            return rs.getString("title");
        } catch (SQLException exception) {
            return null;
        }
    }

    public boolean isAdministrator(String username, String password) {
        try {
            getLoginQuery.setString(1, username);
            getLoginQuery.setString(2, password);
            ResultSet rs = getLoginQuery.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = instance.getRole(id);
                assert role != null;
                if (role.equals("administrator")) {
                    return true;}
            }
        } catch (SQLException exception) {
            return false;
        }
        return false;
    }

    public void loadChoices(ChoiceBox<String> choiceFields) {
        Set<String> res = new TreeSet<>();
        try {
            ResultSet rs = getFieldsQuery.executeQuery();
            while(rs.next()) {
                res.add(rs.getString("title"));
            }
            choiceFields.getItems().addAll(res);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void loadTypeChoices(ChoiceBox<String> choicePublicationType) {
        Set<String> res = new TreeSet<>();
        try {
            ResultSet rs = getTypesQuery.executeQuery();
            while(rs.next()) {
                res.add(rs.getString("title"));
            }
            choicePublicationType.getItems().addAll(res);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ObservableList<ScientificWork> getPopulationTableView(TableView tableView) {
        ObservableList<ScientificWork> scientificWorksList = FXCollections.observableArrayList();
        try {
            ResultSet rs = getWorkPopulationInfoQuery.executeQuery();
            while(rs.next()) {
                scientificWorksList.add(new ScientificWork(rs.getString(1), rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return scientificWorksList;
    }

    public void updatePassword(String username, String newPassword) {
        try {
            changePasswordQuery.setString(1, newPassword);
            changePasswordQuery.setString(2, username);
            changePasswordQuery.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



/*
    public String getGender(int id) {
        try {
            getGenderQuery.setInt(1, id);
            return String.valueOf(getGenderQuery.executeQuery());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
*/

    /*public User getUser(String username, String password) throws IllegalUserException {
        try {
            System.out.println("Ušao u metodu getUser");
            getUserFromLoginQuery.setString(1, username);
            getUserFromLoginQuery.setString(2, password);

            ResultSet rs = getUserFromLoginQuery.executeQuery();
            System.out.println("poslije rs");
            if (!rs.next()) {
                System.out.println("kofa nema u rs nista");
                return null;
            }
            System.out.println("sve ok");
            return new User(rs.getInt("id"), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5).equals("male") ? Gender.MALE : Gender.FEMALE, rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        System.out.println("kraj");
        return null;
    }*/

/*    public void addField(String title) {
        try {
            ResultSet rs = maxIdField.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addFieldQuery.setInt(1, id);
            addFieldQuery.setString(2, title);
            addFieldQuery.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }*/

   /* public ObservableList<PublicationType> getTypes() {
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
    }*/

}

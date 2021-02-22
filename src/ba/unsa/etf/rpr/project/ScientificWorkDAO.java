package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    private PreparedStatement getLoginQuery, getRoleFromIdQuery, maxIdUserQuery, maxIdPersonQuery, maxIdAuthorQuery, getUserQuery, getUsersQuery, getAuthorsQuery, getAuthorQuery, getPersonsQuery, findAuthorFromPerson, getWorkPopulationInfoQuery, getPersonFromAuthorQuery, changePasswordQuery, addUserQuery,addPersonQuery, addFieldQuery, addTypeQuery, addAuthorQuery, getFieldsQuery, getTypesQuery, maxIdFieldQuery, maxIdTypeQuery;

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
            getLoginQuery = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?" );
        } catch (SQLException exception) {
            //If there is no database, make it
            regenerateDatabase();
            try {
                //Testing
                getLoginQuery = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            } catch (SQLException throwables) {
                //There is a fatal error
                throwables.printStackTrace();
            }
        }

      try {
          getLoginQuery = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?" );
          getRoleFromIdQuery = conn.prepareStatement("SELECT title FROM role WHERE id=?");
          getPersonFromAuthorQuery = conn.prepareStatement("SELECT p.id FROM author a, person p WHERE p.id=a.person_id AND a.id=?");
          getAuthorQuery = conn.prepareStatement("SELECT author.* FROM author, person WHERE person.id=author.person_id AND person.first_name=? AND person.last_name=?");
          getFieldsQuery = conn.prepareStatement("SELECT * FROM field");
          getTypesQuery = conn.prepareStatement("SELECT * FROM publication_type");
          getWorkPopulationInfoQuery = conn.prepareStatement("SELECT sw.title, person.first_name || ' ' || person.last_name, sw.year, field.title, publication_type.title, sw.additional, sw.tags FROM scientific_work sw, author, person, scientific_work_author swa, field, publication_type WHERE sw.id=swa.scientific_work_id AND swa.author_id=author.id AND author.person_id=person.id AND sw.field=field.id AND sw.type=publication_type.id;");
          changePasswordQuery = conn.prepareStatement("UPDATE user SET password=? WHERE username=?");
          getUserQuery = conn.prepareStatement("SELECT * FROM user WHERE username=?");
          findAuthorFromPerson = conn.prepareStatement("SELECT author.* FROM author, person WHERE author.person_id=person.id AND person.first_name=? AND person.last_name=?");
          maxIdUserQuery = conn.prepareStatement("SELECT max(id)+1 FROM user");
          maxIdFieldQuery = conn.prepareStatement("SELECT max(id)+1 FROM field");
          maxIdTypeQuery = conn.prepareStatement("SELECT max(id)+1 FROM publication_type");
          maxIdPersonQuery = conn.prepareStatement("SELECT max(id)+1 FROM person");
          maxIdAuthorQuery = conn.prepareStatement("SELECT max(id)+1 FROM author");
          addUserQuery = conn.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
          addPersonQuery = conn.prepareStatement("INSERT INTO person VALUES(?,?,?,?,?)");
          addFieldQuery = conn.prepareStatement("INSERT INTO field VALUES(?,?)");
          getUsersQuery = conn.prepareStatement("SELECT username FROM user");
          getPersonsQuery = conn.prepareStatement("SELECT first_name FROM person, author WHERE author.person_id=person.id");
          getAuthorsQuery = conn.prepareStatement("SELECT p.first_name || ' ' || p.last_name FROM person p, author a WHERE a.person_id=p.id");
          addTypeQuery = conn.prepareStatement("INSERT INTO publication_type VALUES(?,?)");
          addAuthorQuery = conn.prepareStatement("INSERT INTO author VALUES(?,?)");
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
            return "user";
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

    public void loadAuthorChoices(ChoiceBox<String> choiceAuthor) {
        Set<String> res = new TreeSet<>();
        try {
            ResultSet rs = getAuthorsQuery.executeQuery();
            while(rs.next()) {
                res.add(rs.getString(1));
            }
            choiceAuthor.getItems().addAll(res);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ObservableList<ScientificWork> getPopulationTableView(TableView tableView) {
        ObservableList<ScientificWork> scientificWorksList = FXCollections.observableArrayList();
        try {
            ResultSet rs = getWorkPopulationInfoQuery.executeQuery();
            while(rs.next()) {
                scientificWorksList.add(new ScientificWork(rs.getString(1), rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7)));
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

    public boolean findUser(TextField fldUsername) {
        try {
            getUserQuery.setString(1, fldUsername.getText());
            ResultSet rs = getUserQuery.executeQuery();
            return rs.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void addUser(User user) {
        try {
            ResultSet rs = maxIdUserQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addUserQuery.setInt(1, id);
            addUserQuery.setString(2, user.getUsername());
            addUserQuery.setString(3,user.getPassword());
            addUserQuery.setString(4, user.getEmail());
            addUserQuery.setInt(5, user.getPersonId());
            addUserQuery.setInt(6, user.getRole().ordinal());
            addUserQuery.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void addFieldOfStudy(FieldOfStudy fieldOfStudy) {
        try {
            ResultSet rs = maxIdFieldQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addFieldQuery.setInt(1, id);
            addFieldQuery.setString(2, fieldOfStudy.getTitle());
            addFieldQuery.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void getAllUsers() {
        try {
            ResultSet rs = getUsersQuery.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addPublicationType(PublicationType publicationType) {
        try {
            ResultSet rs = maxIdTypeQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addTypeQuery.setInt(1, id);
            addTypeQuery.setString(2, publicationType.getTitle());
            addTypeQuery.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean findAuthorFromPerson(String firstName, String lastName) {
        try {
            getAuthorQuery.setString(1, firstName);
            getAuthorQuery.setString(2, lastName);
            ResultSet rs = getAuthorQuery.executeQuery();
            return rs.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void addAuthor(Author author) {
        try {
            ResultSet rs = maxIdAuthorQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addAuthorQuery.setInt(1, id);
            int personId = getPersonIdFromAuthor(author, id);
            addAuthorQuery.setInt(2, personId );

            addAuthorQuery.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private int getPersonIdFromAuthor(Author author, int id) {
        try {
            getPersonFromAuthorQuery.setInt(1, id);
            ResultSet rs = getPersonFromAuthorQuery.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                return addPersonFromAuthor(author);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    private int addPersonFromAuthor(Author author) {
        try {
            ResultSet rs = maxIdPersonQuery.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            addPersonQuery.setInt(1, id);
            addPersonQuery.setString(2, author.getFirstName());
            addPersonQuery.setString(3, author.getLastName());
            addPersonQuery.setString(4, author.getDateOfBirth().toString());
            addPersonQuery.setString(5, String.valueOf(author.getGender()));

            addPersonQuery.execute();
            return id;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public void getAllAuthors() {
        try {
            ResultSet rs = getPersonsQuery.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}

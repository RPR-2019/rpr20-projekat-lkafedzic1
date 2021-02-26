package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.enums.Role;
import ba.unsa.etf.rpr.project.Validation;
import javafx.beans.property.SimpleStringProperty;

public class User implements Validation {
    private int id = - 1;
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private int personId;
    private Role role;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        if(isValidUsername(username)) {
            this.username.set(username);
        }
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password.set(password);
        }
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        if(isValidEmail(email)) {
            this.email.set(email);
        }
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
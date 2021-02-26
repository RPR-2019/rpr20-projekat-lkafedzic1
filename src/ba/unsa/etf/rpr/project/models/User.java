package ba.unsa.etf.rpr.project.models;

import ba.unsa.etf.rpr.project.enums.Role;
import ba.unsa.etf.rpr.project.Validation;
import javafx.beans.property.SimpleStringProperty;

public class User implements Validation {
    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty password = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private int personId;
    private Role role;

    public User() {
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        if(isValidUsername(username)) {
            this.username.set(username);
        }
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password.set(password);
        }
    }

    public String getEmail() {
        return email.get();
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
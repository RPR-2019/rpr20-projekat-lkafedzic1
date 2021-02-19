package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class SignUpController {

    public TextField fldFirstName;
    public TextField fldLastName;
    public DatePicker dateOfBirth;
    public TextField fldEmail;
    public TextField fldUsername;
    public RadioButton radioMale;
    public ToggleGroup toggleGender;
    public RadioButton radioFemale;
    public PasswordField fldPassword;
    public Label lblStatusBar;


    private ScientificWorkDAO instance;

    @FXML
    public void initialize() {
        fldFirstName.getStyleClass().add("fieldNotValid");
        fldFirstName.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidName(fldFirstName.getText())) {
                        fldFirstName.getStyleClass().removeAll("fieldNotValid");
                        fldFirstName.getStyleClass().add("fieldValid");
                    } else {
                        fldFirstName.getStyleClass().removeAll("fieldValid");
                        fldFirstName.getStyleClass().add("fieldNotValid");
                    }
                }
        );
        fldLastName.getStyleClass().add("fieldNotValid");
        fldLastName.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidName(fldLastName.getText())) {
                        fldLastName.getStyleClass().removeAll("fieldNotValid");
                        fldLastName.getStyleClass().add("fieldValid");
                    } else {
                        fldLastName.getStyleClass().removeAll("fieldValid");
                        fldLastName.getStyleClass().add("fieldNotValid");
                    }
                }
        );
        fldEmail.getStyleClass().add("fieldNotValid");
        fldEmail.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidEmail(fldEmail.getText())) { //koristi li se
                        fldEmail.getStyleClass().removeAll("fieldNotValid");
                        fldEmail.getStyleClass().add("fieldValid");
                    } else {
                        fldLastName.getStyleClass().removeAll("fieldValid");
                        fldLastName.getStyleClass().add("fieldNotValid");
                    }
                }
        );
        fldPassword.getStyleClass().add("fieldNotValid");
        fldPassword.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldPassword.getText().trim().isEmpty() || fldPassword.getText().trim().length() < 8) {
                        fldPassword.getStyleClass().removeAll("fieldValid");
                        fldPassword.getStyleClass().add("fieldNotValid");
                    } else {
                        fldPassword.getStyleClass().removeAll("fieldNotValid");
                        fldPassword.getStyleClass().add("fieldValid");
                    }
                }
        );
        fldUsername.getStyleClass().add("fieldNotValid");
        fldUsername.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidUsername(fldUsername.getText())) { //koristi li se
                        fldUsername.getStyleClass().removeAll("fieldNotValid");
                        fldUsername.getStyleClass().add("fieldValid");
                    } else {
                        fldUsername.getStyleClass().removeAll("fieldValid");
                        fldUsername.getStyleClass().add("fieldNotValid");
                    }
                }
        );
        dateOfBirth.getStyleClass().add("fieldNotValid");
        dateOfBirth.valueProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidDate(dateOfBirth)) {
                        dateOfBirth.getStyleClass().removeAll("fieldNotValid");
                        dateOfBirth.getStyleClass().add("fieldValid");
                    } else {
                        dateOfBirth.getStyleClass().removeAll("fieldValid");
                        dateOfBirth.getStyleClass().add("fieldNotValid");
                    }
                }
        );
        radioMale.getStyleClass().add("fieldNotValid");
        radioFemale.getStyleClass().add("fieldNotValid");
        toggleGender.selectedToggleProperty().addListener(
                (observable, o, n) -> {
                    if (toggleGender.getSelectedToggle() == null) {
                        radioMale.getStyleClass().removeAll();
                        radioMale.getStyleClass().add("fieldNotValid");
                        radioFemale.getStyleClass().removeAll();
                        radioFemale.getStyleClass().add("fieldNotValid");
                    } else {
                        radioMale.getStyleClass().removeAll("fieldNotValid");
                        radioFemale.getStyleClass().removeAll("fieldNotValid");
                    }
                }
        );
    }

    private boolean isInputValid(TextField fld) {
        String validValue = "fieldValid";
        return fld.getStyleClass().stream().anyMatch(style -> style.equals(validValue));
    }

    private boolean isEveryInputValid() {
        return (isInputValid(fldFirstName) && isInputValid(fldLastName) && isInputValid(fldEmail) &&  isInputValid(fldUsername) && isInputValid(fldPassword) && !radioMale.getStyleClass().contains("fieldNotValid") && dateOfBirth.getStyleClass().contains("fieldValid"));
    }

    public void actionSave(ActionEvent actionEvent) {
        if (isEveryInputValid()) {
            lblStatusBar.setText("Successful sign up");
            //todo dodaj account
        }
        else {
            //if any field on the form is red
            lblStatusBar.setText("Please, fill the form properly");
        }
    }

    private boolean isValidName(String s) {
        System.out.println(s);
        return s.length() >= 2 && s.chars()
                .allMatch(Character::isLetter);
    }

    private boolean isValidUsername (String s) {
        return s.length() >= 2 && s.chars()
                .allMatch(Character::isLetterOrDigit) && isValidStart(s);
    }

    private boolean isValidStart (String s) {
        return !Character.isDigit(s.charAt(0));
    }

    private boolean isValidDate (DatePicker datePicker) {
        return datePicker.getValue().isBefore(LocalDate.now());
    }

    private boolean isValidEmail(String s) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        if (s == null) return false;
        return pattern.matcher(s).matches();
    }

    public void actionCancel(ActionEvent actionEvent) throws IOException {
        fldFirstName.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        LoginController loginWindow = loader.getController();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
}

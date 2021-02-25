package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class SignUpController implements Validation{


    public DatePicker dateOfBirth;
    public TextField fldEmail;
    public TextField fldUsername;
    public RadioButton radioMale;
    public ToggleGroup toggleGender;
    public RadioButton radioFemale;
    public PasswordField fldPassword;
    public Label lblStatusBar;
    public TextField fldName;

    private User user;
    private ScientificWorkDAO instance;

    @FXML
    public void initialize() {
        instance = ScientificWorkDAO.getInstance();

        fldName.getStyleClass().add("fieldNotValid");
        fldName.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (isValidName(fldName.getText())) {
                        fldName.getStyleClass().removeAll("fieldNotValid");
                        fldName.getStyleClass().add("fieldValid");
                    } else {
                        fldName.getStyleClass().removeAll("fieldValid");
                        fldName.getStyleClass().add("fieldNotValid");
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
                        fldEmail.getStyleClass().removeAll("fieldValid");
                        fldEmail.getStyleClass().add("fieldNotValid");
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
        return fld.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"));
    }

    private boolean isEveryInputValid() {
        return (isInputValid(fldName) && isInputValid(fldEmail) &&  isInputValid(fldUsername) && isInputValid(fldPassword) && !radioMale.getStyleClass().contains("fieldNotValid") && dateOfBirth.getStyleClass().contains("fieldValid"));
    }

    public void actionSave(ActionEvent actionEvent) {
        if (isEveryInputValid()) {
            if(instance.findUser(fldUsername)) {
                fldUsername.getStyleClass().removeAll("fieldValid");
                fldUsername.getStyleClass().add("fieldNotValid");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Username is already taken");
                alert.setContentText("Please, try again!");
                alert.showAndWait();
                lblStatusBar.setText("Username is already taken");
            }
            else {
                fldUsername.getStyleClass().removeAll("fieldNotValid");
                fldUsername.getStyleClass().add("fieldValid");
                lblStatusBar.setText("Successful sign up");
            }
            //adding new account
            Person person = new Person(fldName.getText() ,dateOfBirth.getValue(), this.getGender());
            if (user == null) user = new User();
            user.setUsername(fldUsername.getText());
            user.setPassword(fldPassword.getText());
            user.setEmail(fldEmail.getText());
            user.setPersonId(person.getId());
            user.setRole(Role.USER);
            instance.addUser(user);
            //show users in concole
            instance.getAllUsers();
        }
        else {
            //if any field on the form is red
            lblStatusBar.setText("Please, fill the form properly");
        }
    }

    private Gender getGender() {
        Gender gender = null;
        if (toggleGender.getSelectedToggle() == radioMale) {
            gender = Gender.MALE;
        }
        else if (toggleGender.getSelectedToggle() == radioFemale) {
            gender = Gender.FEMALE;
        }
        return gender;
    }

    private boolean isValidDate (DatePicker datePicker) {
        return datePicker.getValue().isBefore(LocalDate.now());
    }

    public void actionCancel(ActionEvent actionEvent) throws IOException {
        fldName.getScene().getWindow().hide();
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

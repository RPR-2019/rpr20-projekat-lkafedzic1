package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import ba.unsa.etf.rpr.project.enums.Gender;
import ba.unsa.etf.rpr.project.models.Author;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class AuthorController implements Validation {
    
    public DatePicker dateOfBirth;
    public RadioButton radioMale;
    public RadioButton radioFemale;
    public ToggleGroup toggleGender;
    public Label lblStatusBar;
    public TextField fldName;
    public GridPane formPane;
    private Author author = null;

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

    private boolean isValidDate(DatePicker dateOfBirth) {
        return dateOfBirth.getValue().isBefore(LocalDate.now());
    }

    private boolean isInputValid(TextField fld) {
        return fld.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"));
    }

    private boolean isEveryInputValid() {
        return (isInputValid(fldName) && !radioMale.getStyleClass().contains("fieldNotValid") && dateOfBirth.getStyleClass().contains("fieldValid"));
    }

    public void actionSave() {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if (isEveryInputValid()) {
            if(instance.findAuthorFromPerson(fldName.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("Error"));
                alert.setHeaderText(bundle.getString("authorError"));
                alert.showAndWait();
                lblStatusBar.setText(bundle.getString("authorError"));
            }
            else {
                lblStatusBar.setText(bundle.getString("successAuthor"));
            }
            //adding new account
            if (author == null) author = new Author();
            author.setName(fldName.getText());
            author.setDateOfBirth(dateOfBirth.getValue());
            author.setGender(getGender());
            instance.addAuthor(author);
            //show users in console
            instance.getAllAuthors();
        }
        else {
            lblStatusBar.setText(bundle.getString("notFilled"));
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

    public void actionCancel() {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }
}

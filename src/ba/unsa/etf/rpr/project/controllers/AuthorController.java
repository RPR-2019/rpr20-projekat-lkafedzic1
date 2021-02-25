package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AuthorController implements Validation{
    
    public DatePicker dateOfBirth;
    public RadioButton radioMale;
    public RadioButton radioFemale;
    public ToggleGroup toggleGender;
    public Label lblStatusBar;
    public TextField fldName;
    private Author author = null;
    String authorName = null;

    private ScientificWorkDAO instance;

    public Author getAuthor() {
        return author;
    }

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

    public void actionSave(ActionEvent actionEvent) {
        if (isEveryInputValid()) {
            if(instance.findAuthorFromPerson(fldName.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Author already exists");
                alert.showAndWait();
                lblStatusBar.setText("Couldn't add author");
            }
            else {
                lblStatusBar.setText("Successfully added author");
            }
            //adding new account
            if (author == null) author = new Author();
            author.setName(fldName.getText());
            author.setDateOfBirth(dateOfBirth.getValue());
            author.setGender(getGender());
            instance.addAuthor(author);
            //show users in concole
            instance.getAllAuthors();
        }
        else {
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

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

}
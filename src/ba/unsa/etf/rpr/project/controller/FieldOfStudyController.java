package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.FieldOfStudy;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FieldOfStudyController{
    public TextField fldTitle;
    public Label lblStatusBar;
    private FieldOfStudy fieldOfStudy;

    @FXML
    public void initialize() {
        fldTitle.getStyleClass().add("fieldNotValid");
        fldTitle.textProperty().addListener(
                (observableValue, o, n) -> {
                    if (fldTitle.getText().trim().isEmpty() || fldTitle.getText().trim().length() < 3 || fldTitle.getText().chars()
                            .anyMatch(Character::isDigit)) {
                        fldTitle.getStyleClass().removeAll("fieldValid");
                        fldTitle.getStyleClass().add("fieldNotValid");
                    } else {
                        fldTitle.getStyleClass().removeAll("fieldNotValid");
                        fldTitle.getStyleClass().add("fieldValid");
                    }
                }
        );
    }

    public void actionAddNewField(ActionEvent actionEvent) {
        if (fldTitle.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"))) {
            ScientificWorkDAO instance = ScientificWorkDAO.getInstance();
            if (fieldOfStudy == null) fieldOfStudy = new FieldOfStudy();
            fieldOfStudy.setTitle(fldTitle.getText());
            instance.addFieldOfStudy(fieldOfStudy);
            lblStatusBar.setText("Successfully added");
        }
        else {
            //if any field on the form is red
            lblStatusBar.setText("Please, fill the form properly");
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

}

package ba.unsa.etf.rpr.project.controllers;

import ba.unsa.etf.rpr.project.models.FieldOfStudy;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ResourceBundle;

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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        if (fldTitle.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"))) {
            ScientificWorkDAO instance = ScientificWorkDAO.getInstance();
            if (fieldOfStudy == null) fieldOfStudy = new FieldOfStudy();
            fieldOfStudy.setTitle(fldTitle.getText());
            instance.addFieldOfStudy(fieldOfStudy);
            lblStatusBar.setText(bundle.getString("added"));
        }
        else {
            //if any field on the form is red
            lblStatusBar.setText(bundle.getString("notFilled"));
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) lblStatusBar.getScene().getWindow();
        stage.close();
    }

}

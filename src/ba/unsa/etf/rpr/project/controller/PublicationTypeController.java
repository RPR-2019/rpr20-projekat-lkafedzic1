package ba.unsa.etf.rpr.project.controller;


import ba.unsa.etf.rpr.project.FieldOfStudy;
import ba.unsa.etf.rpr.project.PublicationType;
import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PublicationTypeController {
    public TextField fldTitle;
    public Label lblStatusBar;
    private PublicationType publicationType;

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

    public void actionAddNewType(ActionEvent actionEvent) {
        if (fldTitle.getStyleClass().stream().anyMatch(style -> style.equals("fieldValid"))) {
            ScientificWorkDAO instance = ScientificWorkDAO.getInstance();
            if (publicationType == null) publicationType = new PublicationType();
            publicationType.setTitle(fldTitle.getText());
            instance.addPublicationType(publicationType);
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

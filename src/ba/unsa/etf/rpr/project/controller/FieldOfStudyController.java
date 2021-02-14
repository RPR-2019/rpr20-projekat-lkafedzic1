package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.ScientificWorkDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FieldOfStudyController {
    public TextField title;
    public Button btnAdd, btnCancel;


    public void actionAddNewField(ActionEvent actionEvent) {
        ScientificWorkDAO database = ScientificWorkDAO.getInstance();
        database.addField(title.getText());
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
    }
}
